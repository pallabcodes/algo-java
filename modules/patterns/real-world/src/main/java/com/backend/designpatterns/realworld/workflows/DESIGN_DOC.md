# Design Doc: Transactional Workflow (Saga)

| **Design Doc** | **Transactional Workflow (Saga)** |
|---|---|
| **Owner** | [You] |
| **Date** | 2026-05 |
| **Status** | Draft |
| **Teams** | Checkout, Fulfillment, Payments |

## Context

Booking workflow (hotel → car → charge → confirmation) spans multiple services. Currently no rollback on partial failure — if charge fails, hotel and car are already booked with no compensation. No observability into step-level progress.

## Problem

- **No compensation**: Charge fails → hotel and car booked but user not charged (target: rollback all prior steps via compensating actions)
- **No step-level monitoring**: Can't tell which step failed or how long each step took (target: Observer per step)
- **No undo capability**: Each step has no `undo()` — manual reversal required (target: Command with execute + undo)
- **Orchestration scattered**: Flow logic spread across callers (target: central orchestrator)

## Alternatives Considered

| Alternative | Why Rejected |
|---|---|
| **1. 2PC / distributed transaction** | Blocking coordinators; not all services support XA |
| **2. Manual compensation** | Error-prone, forgotten rollbacks leak resources |
| **3. Event-driven saga** | Hard to reason about flow; eventual consistency gaps |
| **4. Command + Memento + Observer** | ✅ Chosen |

## Design

3 patterns compose:

```
  WorkflowOrchestrator ──▶ then(BookHotel) ──▶ execute() → push to Memento stack
                               │
                          then(ReserveCar) ──▶ execute() → push to Memento stack
                               │
                          then(ChargeCard) ──▶ execute() → FAILS!
                               │
                          then(SendConfirmation) — skipped
                               │
                               ▼
                          WorkflowMemento ──▶ pop() → undo(ReserveCar)
                                          └── pop() → undo(BookHotel)
                               │
                               ▼
                          WorkflowObserver ──▶ Logger
                                          └── Metrics
                                          └── AlertOnFailure
```

### Pattern composition

| Pattern | File(s) | What it does |
|---------|---------|--------------|
| **Command** | `WorkflowCommand` + `BookHotel`/`ReserveCar`/`ChargeCard`/`SendConfirmation` | Each step = Command with `execute()` + `undo()`. Enables Saga compensation. |
| **Memento** | `WorkflowMemento` | Stack of executed commands. On failure: pop + undo in reverse. |
| **Observer** | `WorkflowObserver` + logger/metrics/alertOnFailure | Step-level events decoupled from orchestrator. |

### Key interfaces

- `WorkflowCommand` — `execute()`, `undo()`
- `WorkflowMemento` — `push(Command)`, `undoAll()` (reverse-order rollback)
- `WorkflowObserver` — `onStepSuccess(step)`, `onStepFailure(step, error)`, `onRollback(step)`

## Reading Order

| Step | File | Why read this next |
|------|------|--------------------|
| 1 | `DESIGN_DOC.md` (this file) | Understand problem, design, trade-offs |
| 2 | `WorkflowCommand.java` | Command — each step has execute + undo |
| 3 | `WorkflowMemento.java` | Memento — stack of executed commands for rollback |
| 4 | `WorkflowObserver.java` | Observer — step-level lifecycle hooks |
| 5 | `WorkflowOrchestrator.java` | Builder + Orchestrator — fluent then(), execute(), rollback |
| 6 | `WorkflowDemo.java` | See all 3 patterns compose |

## Migration Plan

| Phase | Time | What | Risk |
|-------|------|------|------|
| 1 | Week 1 | Extract `WorkflowCommand` interface + `execute()` per step | Low |
| 2 | Week 2 | Add `undo()` to each Command | Medium — must implement correct compensating action |
| 3 | Week 3 | Add `WorkflowMemento` — stack + rollback | Low |
| 4 | Week 4 | Add `WorkflowOrchestrator` fluent builder (`then()`) | Low |
| 5 | Week 5 | Add `WorkflowObserver` — log, metrics, alert | Low |

## Rollback / Safety

- Memento undoes in reverse order — guarantees correct compensation sequence
- Commands must be idempotent — safe to call `undo()` even if `execute()` partially succeeded
- Observer failures caught per subscriber — one bad observer doesn't block rollback
- Orchestrator captures step exception → triggers rollback immediately (no orphaned steps)

## Metrics

| Metric | Current | Target | How |
|--------|---------|--------|-----|
| Partial failure recovery | Manual | Automatic rollback | Memento undoAll() |
| Failure detection time | Minutes | < 1s | Observer alertOnFailure |
| Step-level observability | None | Per-step metrics | Observer metrics sink |

## Risks

- **Undo failure**: Compensating action (undo hotel booking) also fails. **Mitigation**: `undo()` has own compensation with max retries + dead-letter alert.
- **Non-compensatable steps**: Some actions (send confirmation email) can't be undone. **Mitigation**: mark step as fire-and-forget; skip from Memento stack.
- **Memento memory**: Long workflows may accumulate large stacks. **Mitigation**: cap stack depth; snapshot completed steps to DB.
