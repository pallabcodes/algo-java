package com.backend.designpatterns.realworld.rules;

import java.util.Map;

public record RuleEngine(RuleExpr condition, RuleAction action) {

    public boolean evaluate(Map<String, Object> context) {
        boolean matched = condition.evaluate(context);
        System.out.println("[Engine] " + condition.describe() + " -> " + matched);
        if (matched) action.execute(context);
        return matched;
    }

    public static class DiscountEngine {
        private final RuleEngine[] rules;

        public DiscountEngine(RuleEngine... rules) { this.rules = rules; }

        public void applyAll(Map<String, Object> context) {
            System.out.println("[Engine] evaluating " + rules.length + " rules");
            for (var rule : rules) {
                rule.evaluate(context);
            }
        }

        public static DiscountEngine defaultRules() {
            return new DiscountEngine(
                new RuleEngine(
                    new RuleExpr.And(
                        new RuleExpr.FieldGreaterThan("amount", 10_000),
                        new RuleExpr.FieldEquals("region", "US")
                    ),
                    new RuleAction.ApplyDiscount(10)
                ),
                new RuleEngine(
                    new RuleExpr.Or(
                        new RuleExpr.FieldEquals("is_vip", true),
                        new RuleExpr.FieldGreaterThan("loyalty_years", 5)
                    ),
                    new RuleAction.FreeShipping()
                ),
                new RuleEngine(
                    new RuleExpr.And(
                        new RuleExpr.FieldGreaterThan("amount", 100_000),
                        new RuleExpr.Not(new RuleExpr.FieldEquals("region", "US"))
                    ),
                    new RuleAction.FlagForReview("high value international order")
                )
            );
        }
    }
}
