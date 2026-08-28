# Design Doc: Multi-Channel Notification System

| **Design Doc** | **Multi-Channel Notification System** |
|---|---|
| **Owner** | [You] |
| **Date** | 2026-05 |
| **Status** | Draft |
| **Teams** | Notifications, Platform |

## Context

System sends notifications via email, SMS, push. Each channel has 1-2 providers (SendGrid, Twilio, Firebase). Currently 3 channels × 2 providers = 6 classes, but adding a new channel or provider requires creating N×M classes.

## Problem

- **Class explosion**: 3 channels × 2 providers = 6 classes. 5 channels × 4 providers = 20. (target: N + M classes via Bridge)
- **Provider coupling**: Switching from SendGrid to SES requires rewriting Email class (target: provider swappable independent of channel)
- **Routing rigidity**: Which channel to use per message hardcoded (target: pluggable RoutingStrategy)
- **Wiring complexity**: Callers construct channel+provider combos manually (target: Factory)

## Alternatives Considered

| Alternative | Why Rejected |
|---|---|
| **1. Keep N×M classes** | Doesn't scale — 5×4 = 20 classes |
| **2. Single class with if/else per provider** | Violates OCP, switch per new provider |
| **3. Just use Twilio SendGrid + SES** | Firebase push not covered |
| **4. Bridge + Factory + Strategy** | ✅ Chosen |

## Design

3 patterns compose:

```
  Caller ──▶ RoutingStrategy ──▶ Notification (Bridge Abstraction)
                                     │
                                     ▼
                              NotificationSender (Bridge Implementation)
                                     ├── SendGrid (email)
                                     ├── Twilio (sms)
                                     └── Firebase (push)
                                     │
                                     ▼
                              NotificationFactory ──▶ creates combo by key
```

### Pattern composition

| Pattern | File(s) | What it does |
|---------|---------|--------------|
| **Bridge** | `Notification` (channel abstraction) + `NotificationSender` (provider implementation) | 3×3 = 9 combos from 6 classes. Both axes vary independently. |
| **Factory** | `NotificationFactory` | Creates + caches channel+provider combos. Key = "email:sendgrid". |
| **Strategy** | `RoutingStrategy` + `PriorityOrder`/`ByMessageType` | Selects channel at runtime per user preference or message type |

### Key interfaces

- `Notification` — channel abstraction: `send(user, content)`
- `NotificationSender` — provider implementation: `deliver(address, payload)`
- `RoutingStrategy` — `selectChannel(message, userPrefs)` → channel enum

## Reading Order

| Step | File | Why read this next |
|------|------|--------------------|
| 1 | `DESIGN_DOC.md` (this file) | Understand problem, design, trade-offs |
| 2 | `NotificationSender.java` | Provider interface — implementation side of Bridge |
| 3 | `Notification.java` | Channel abstraction — abstraction side of Bridge, holds sender |
| 4 | `NotificationFactory.java` | Factory — creates any channel+provider combo |
| 5 | `RoutingStrategy.java` | Strategy — selects channel at runtime |
| 6 | `BridgeDemo.java` | See all 3 patterns compose |

## Migration Plan

| Phase | Time | What | Risk |
|-------|------|------|------|
| 1 | Week 1 | Extract `NotificationSender` interface per provider | Low |
| 2 | Week 2 | Extract `Notification` abstraction per channel | Low |
| 3 | Week 3 | Wire Bridge (abstraction holds implementation ref) | Low |
| 4 | Week 4 | Add `NotificationFactory` — register combos | Low |
| 5 | Week 5 | Add `RoutingStrategy` — channel selection | Low |

## Rollback / Safety

- Factory returns cached instance — thread-safe
- Provider failure: Notification catches exception, logs, retries (once)
- RoutingStrategy default: PriorityOrder (email > sms > push) if user prefs missing

## Metrics

| Metric | Current | Target | How |
|--------|---------|--------|-----|
| Classes per provider+channel | N×M | N+M | Class count |
| Time to add provider | 1 week | < 1 day | Dev hours |
| Time to add channel | 1 week | < 1 day | Dev hours |
| Provider swap impact | Rewrite channel class | Add single class | Classes changed |

## Risks

- **Abstraction leak**: Provider-specific features (SendGrid templates vs SES raw) don't fit Bridge. **Mitigation**: canonical payload with provider extensions map.
- **RoutingStrategy too simple**: ByMessageType can't handle urgency + user pref combined. **Mitigation**: chain of routing strategies.
