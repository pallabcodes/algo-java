# Design Doc: Order Composite Tree

| **Design Doc** | **Order Composite Tree** |
|---|---|
| **Owner** | [You] |
| **Date** | 2026-05 |
| **Status** | Draft |
| **Teams** | Checkout, Fulfillment, Billing |

## Context

Order contains line items, discounts, shipping. Operations (total calc, invoice gen, inventory reserve) traverse the structure. Currently each operation iterates manually — adding a new operation means editing every node class.

## Problem

- **Operation coupling**: `calculateTotal()` mixed into `Order`, `LineItem` classes (target: operations separate from structure)
- **Adding operation = edit N classes**: Each new operation touches Order, LineItem, Discount, Shipping (target: add 1 class per operation via Visitor)
- **Traversal logic scattered**: Each operation reimplements tree walk (target: shared Iterator)

## Alternatives Considered

| Alternative | Why Rejected |
|---|---|
| **1. Keep methods on nodes** | Each new op touches N classes |
| **2. Strategy per operation** | Strategy selects algorithm but traversal still per-node |
| **3. Composite + Visitor + Iterator** | ✅ Chosen |

## Design

3 patterns compose:

```
                    ┌─────────────────────────┐
                    │       OrderNode          │── Composite (sealed interface)
                    ├─────────────────────────┤
                    │ Order ──▶ LineItem(s)    │
                    │       └── Discount(s)   │
                    │       └── Shipping      │
                    └────────────┬────────────┘
                                 │ accept(Visitor)
                                 ▼
                    ┌─────────────────────────┐
                    │      OrderVisitor        │── Visitor
                    ├─────────────────────────┤
                    │ TotalCalculator          │
                    │ InvoiceGenerator         │
                    │ InventoryReserver        │
                    └────────────┬────────────┘
                                 │ traverse using
                                 ▼
                    ┌─────────────────────────┐
                    │      OrderIterator       │── Iterator (BFS)
                    └─────────────────────────┘
```

### Pattern composition

| Pattern | File(s) | What it does |
|---------|---------|--------------|
| **Composite** | `OrderNode` sealed interface + `Order`/`LineItem`/`Discount`/`Shipping` | Uniform tree structure. Treat single and composite nodes same way. |
| **Visitor** | `OrderVisitor` + `TotalCalculator`/`InvoiceGenerator`/`InventoryReserver` | Operations separate from nodes. New operation = new Visitor, zero node changes. |
| **Iterator** | `OrderIterator` | BFS traversal reusable across all Visitors and standalone walks. |

### Key interfaces

- `OrderNode` — sealed: `Order | LineItem | Discount | Shipping`
- `OrderVisitor<T>` — `visit(Order)`, `visit(LineItem)`, etc per node type
- `OrderIterator` — `hasNext()`, `next()` BFS over tree

## Reading Order

| Step | File | Why read this next |
|------|------|--------------------|
| 1 | `DESIGN_DOC.md` (this file) | Understand problem, design, trade-offs |
| 2 | `OrderNode.java` | Composite — sealed tree node types |
| 3 | `OrderIterator.java` | Iterator — BFS traversal shared across all visitors |
| 4 | `OrderVisitor.java` | Visitor — operations separate from tree structure |
| 5 | `OrderDemo.java` | See all 3 patterns compose |

## Migration Plan

| Phase | Time | What | Risk |
|-------|------|------|------|
| 1 | Week 1 | Extract `OrderNode` sealed interface from existing order model | Low — no behavior change |
| 2 | Week 2 | Add `OrderIterator` (BFS) | Low |
| 3 | Week 3 | Extract `TotalCalculator` as Visitor | Low |
| 4 | Week 4 | Extract remaining operations as Visitors | Low |

## Rollback / Safety

- Old methods remain on nodes until all callers migrate to Visitor
- Iterator is read-only — no mutation risk during traversal
- Sealed interface guarantees all node types known at compile time

## Metrics

| Metric | Current | Target | How |
|--------|---------|--------|-----|
| Time to add new operation | N classes edited | 1 class added | Code review |
| Traversal duplication | Per-operation | Shared Iterator | Code count |
| Node class stability | Changes per new op | Zero changes | Git blame |

## Risks

- **Visitor adds indirection**: Simple field access (order.total) becomes Visitor call. **Mitigation**: accept both direct getters (for hot path) and Visitor (for complex ops).
- **Sealed limits extensibility**: Adding new node type requires changing sealed permits. **Mitigation**: rare — order nodes are stable.
