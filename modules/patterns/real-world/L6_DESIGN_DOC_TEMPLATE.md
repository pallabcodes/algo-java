# L6 Design Doc Template

> Use this template to write a 1-page design doc for any pattern composition.
> Google's L6 promo packet needs the *story* — not just the code.
>
> Below is a **filled example** (Payment Gateway + SDK Integration), followed by a
> **blank template**.

---

## BLANK TEMPLATE

Copy this for your own design doc.

---

| **Design Doc** | **[Title — What are you building?]** |
|---|---|
| **Owner** | [Your Name] |
| **Date** | [YYYY-MM] |
| **Status** | Draft |
| **Teams** | [Affected Teams] |

### Context

[2-3 sentences. What's the current state? What's the business/organizational
pressure? What systems are involved?]

### Problem

[Bullet list. What's broken? Make it measurable.]

- **Problem 1**: [Current metric] (target: [target metric])
- **Problem 2**: [Current behavior] (target: [desired behavior])
- **Problem 3**: [Org pain] (target: [org goal])

### Alternatives Considered

| Alternative | Why Rejected |
|---|---|
| **1. Do nothing** | [Reason] |
| **2. [Alternative]** | [Why not chosen] |
| **3. [Alternative]** | [Why not chosen] |
| **4. [Chosen]** | ✅ |

### Design

[2-3 sentences + architecture diagram (ASCII).]

```
[ASCII architecture diagram showing:
 - Entry point
 - Which patterns compose at which layer
 - Data flow arrows
 - State transitions if applicable
]
```

**Pattern composition**:
- **[Pattern 1]**: [What it does in this system]
- **[Pattern 2]**: [What it does in this system]
- **...**

**Key interfaces**:
- `[Interface/Class]` — [Responsibility]
- `[Interface/Class]` — [Responsibility]

### Migration Plan

| Phase | Time | What | Risk |
|-------|------|------|------|
| 1 | [Timeline] | [First step — smallest possible change] | [Risk level + why] |
| 2 | [Timeline] | [Second step] | [Risk level + why] |
| 3 | [Timeline] | [Third step] | [Risk level + why] |
| ... | ... | ... | ... |

### Rollback / Safety

- [Feature flag? Shadow mode?]
- [Data integrity guarantees]
- [Error handling strategy]

### Metrics

| Metric | Current | Target | How measured |
|--------|---------|--------|-------------|
| [Metric 1] | [Current value] | [Target value] | [Measurement tool] |
| [Metric 2] | [Current value] | [Target value] | [Measurement tool] |
| [Metric 3] | [Current value] | [Target value] | [Measurement tool] |

### Risks

- **[Risk 1]**: [Description]. **Mitigation**: [Plan].
- **[Risk 2]**: [Description]. **Mitigation**: [Plan].

---

## How to Use This for L6 Promo

Your promo packet needs 3-5 of these design docs covering different
pattern compositions you shipped. Each doc should show:

1. **Before** — concrete pain (broken metrics, slow teams)
2. **Alternatives** — you considered other approaches and can explain why
3. **Migration** — you didn't ship it all at once; you phased it
4. **Impact** — metrics moved in the right direction

The **code** in `real-world/` shows what the final state looks like.
The **design doc** shows how you got there without breaking things.
The L6 committee needs both.
