package com.backend.designpatterns.realworld.rules;

import java.util.Map;

/**
 * [2/4] Command variant — encapsulates side effects of a matched rule.
 * Each implementation (ApplyDiscount, FlagForReview, SendNotification, FreeShipping)
 * is a self-contained action. Separated from RuleExpr (condition) so conditions
 * and actions can vary independently.
 *
 * Without this separation: conditions and actions are coupled in the same if/else block,
 * making it impossible to reuse conditions across actions or vice versa.
 */
@FunctionalInterface
public interface RuleAction {
    void execute(Map<String, Object> context);

    record ApplyDiscount(int percent) implements RuleAction {
        public void execute(Map<String, Object> ctx) {
            long orig = ((Number) ctx.getOrDefault("amount", 0)).longValue();
            long discounted = orig * (100 - percent) / 100;
            ctx.put("amount", discounted);
            ctx.put("discount_applied", percent + "%");
            System.out.println("[Action] applied " + percent + "% discount: " + orig + " -> " + discounted);
        }
    }

    record FlagForReview(String reason) implements RuleAction {
        public void execute(Map<String, Object> ctx) {
            ctx.put("flagged", true);
            ctx.put("flag_reason", reason);
            System.out.println("[Action] FLAGGED: " + reason);
        }
    }

    record SendNotification(String template) implements RuleAction {
        public void execute(Map<String, Object> ctx) {
            System.out.println("[Action] sending notification: " + template);
        }
    }

    record FreeShipping() implements RuleAction {
        public void execute(Map<String, Object> ctx) {
            ctx.put("shipping_cost", 0);
            System.out.println("[Action] free shipping applied");
        }
    }
}
