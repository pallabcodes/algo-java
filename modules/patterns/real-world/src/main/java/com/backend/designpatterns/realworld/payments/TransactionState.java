package com.backend.designpatterns.realworld.payments;

/**
 * [7/10] State pattern — manages transaction lifecycle via a sealed interface with
 * 6 states. Each state transition is explicit (Initiated → Authorizing → etc).
 *
 * Without this, state is an enum + if/else switch scattered across the codebase.
 * Every new state requires hunting down every switch. With sealed State pattern,
 * adding a state = new record + transitions in one place.
 *
 * Alternative rejected: boolean flags (isCaptured, isSettled) — leads to
 * invalid state combinations. Sealed interface guarantees only valid transitions.
 */
public sealed interface TransactionState
    permits TransactionState.Initiated, TransactionState.Authorizing,
            TransactionState.Captured, TransactionState.Settled,
            TransactionState.Failed, TransactionState.Refunded {

    record Initiated() implements TransactionState {}
    record Authorizing() implements TransactionState {}
    record Captured() implements TransactionState {}
    record Settled() implements TransactionState {}
    record Failed(String reason) implements TransactionState {}
    record Refunded(String reason) implements TransactionState {}

    default TransactionState next(boolean success) {
        return switch (this) {
            case Initiated _ -> success ? new Authorizing() : new Failed("rejected before authorization");
            case Authorizing _ -> success ? new Captured() : new Failed("authorization declined");
            case Captured _ -> new Settled();
            case Settled _ -> this;
            case Failed _ -> this;
            case Refunded _ -> this;
        };
    }

    default boolean isTerminal() {
        return this instanceof Failed || this instanceof Refunded || this instanceof Settled;
    }
}
