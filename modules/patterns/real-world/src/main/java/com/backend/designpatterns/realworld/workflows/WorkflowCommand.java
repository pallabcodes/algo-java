package com.backend.designpatterns.realworld.workflows;

/**
 * [1/5] Command pattern — each step in a transactional workflow is a Command with
 * execute() and undo(). If any step fails, previously succeeded steps are
 * rolled back in reverse order (Saga pattern).
 *
 * Without Command, each workflow step directly modifies state and there's no
 * way to undo partial progress. Alternative rejected: distributed transaction
 * (2PC) — too expensive, doesn't scale across services. With Command + Memento,
 * each step is independently compensatable.
 */
public interface WorkflowCommand {
    record Result(boolean success, String message) {
        static Result ok(String msg) { return new Result(true, msg); }
        static Result fail(String msg) { return new Result(false, msg); }
    }

    Result execute();
    Result undo();

    record BookHotel(String bookingId, String hotel, String dates) implements WorkflowCommand {
        public Result execute() {
            System.out.println("[Command] booking " + hotel + " for " + dates);
            return Result.ok("booked " + bookingId);
        }
        public Result undo() {
            System.out.println("[Command] CANCELLING hotel booking " + bookingId);
            return Result.ok("cancelled " + bookingId);
        }
    }

    record ReserveCar(String reservationId, String car, String pickup) implements WorkflowCommand {
        public Result execute() {
            System.out.println("[Command] reserving " + car + " at " + pickup);
            return Result.ok("reserved " + reservationId);
        }
        public Result undo() {
            System.out.println("[Command] RELEASING car reservation " + reservationId);
            return Result.ok("released " + reservationId);
        }
    }

    record ChargeCard(String chargeId, long amountCents, String currency) implements WorkflowCommand {
        public Result execute() {
            System.out.println("[Command] charging " + currency + " " + amountCents);
            if (amountCents > 100_000) {
                return Result.fail("amount exceeds daily limit");
            }
            return Result.ok("charged " + chargeId);
        }
        public Result undo() {
            System.out.println("[Command] REFUNDING charge " + chargeId);
            return Result.ok("refunded " + chargeId);
        }
    }

    record SendConfirmation(String email, String message) implements WorkflowCommand {
        public Result execute() {
            System.out.println("[Command] emailing " + email + ": " + message);
            return Result.ok("emailed");
        }
        public Result undo() {
            System.out.println("[Command] cannot undo email; sending cancellation notice");
            return Result.ok("cancellation notice sent");
        }
    }
}
