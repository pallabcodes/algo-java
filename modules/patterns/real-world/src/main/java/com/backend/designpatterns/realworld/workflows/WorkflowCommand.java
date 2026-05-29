package com.backend.designpatterns.realworld.workflows;

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
