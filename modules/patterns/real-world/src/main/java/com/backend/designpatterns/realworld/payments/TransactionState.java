package com.backend.designpatterns.realworld.payments;

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
