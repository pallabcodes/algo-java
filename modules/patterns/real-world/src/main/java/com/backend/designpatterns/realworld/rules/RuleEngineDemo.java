package com.backend.designpatterns.realworld.rules;

import java.util.HashMap;
import java.util.Map;

/**
 * [4/4] Demonstrates Composite + Interpreter + Strategy composition for rule engine.
 * Shows: expression trees (AND/OR/NOT with FieldEquals, FieldGreaterThan),
 * recursive interpreter evaluation, swappable rule sets (BlackFriday, FraudDetection),
 * actions triggered on match.
 *
 * Without this: rules are hardcoded if/else in controllers, each change = PR + deploy.
 */
public class RuleEngineDemo {

    public static void main(String[] args) {
        System.out.println("""
            === L6 PATTERN COMPOSITION: Rule Engine ===

            Patterns combined:  Interpreter + Composite + Strategy

            Rules are expression trees (Composite). The Interpreter evaluates them.
            Strategy selects WHICH rules apply per context.

            Adding a new rule = new RuleExpr. Adding a new action = new RuleAction.
            The engine doesn't change.
            """);

        var engine = RuleEngine.DiscountEngine.defaultRules();

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO A: US customer, $150 order — gets 10% off + free shipping");
        System.out.println("=".repeat(70));

        Map<String, Object> ctxA = new HashMap<>(Map.of("amount", 15000, "region", "US", "is_vip", true, "loyalty_years", 2));
        engine.applyAll(ctxA);
        System.out.println("Final state: " + ctxA);

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO B: Small order — no discounts fire");
        System.out.println("=".repeat(70));

        Map<String, Object> ctxB = new HashMap<>(Map.of("amount", 5000, "region", "US", "is_vip", false, "loyalty_years", 1));
        engine.applyAll(ctxB);
        System.out.println("Final state: " + ctxB);

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO C: International high-value — flagged for review");
        System.out.println("=".repeat(70));

        Map<String, Object> ctxC = new HashMap<>(Map.of("amount", 500_000, "region", "IN", "is_vip", false, "loyalty_years", 0));
        engine.applyAll(ctxC);
        System.out.println("Final state: " + ctxC);

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO D: Dynamic rule composition at runtime");
        System.out.println("=".repeat(70));

        var customRule = new RuleEngine(
            new RuleExpr.And(
                new RuleExpr.FieldEquals("campaign", "black_friday"),
                new RuleExpr.FieldGreaterThan("amount", 5_000)
            ),
            new RuleAction.ApplyDiscount(25)
        );

        Map<String, Object> ctxD = new HashMap<>(Map.of("amount", 20000, "region", "EU", "campaign", "black_friday", "is_vip", false, "loyalty_years", 3));
        customRule.evaluate(ctxD);
        System.out.println("Final state: " + ctxD);

        System.out.println("\n" + "=".repeat(70));
        System.out.println("L6 TAKEAWAY: 3 patterns, extensible rule engine.");
        System.out.println("  Interpreter → evaluates expression trees recursively");
        System.out.println("  Composite   → rules form AND/OR/NOT trees");
        System.out.println("  Strategy    → rule sets are swappable per context");
        System.out.println("=".repeat(70));
    }
}
