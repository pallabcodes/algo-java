package com.backend.designpatterns.realworld.rules;

import java.util.Map;

/**
 * Composite + Interpreter patterns — rules are expression trees (AND/OR/NOT
 * of field comparisons). evaluate() recursively walks the tree (Interpreter).
 * AND/OR/NOT nodes compose arbitrarily (Composite).
 *
 * Alternative without this: hardcoded if/else for each rule (doesn't scale
 * beyond 5-10 rules, can't be configured at runtime). With Composite+Interpreter,
 * rules can be built dynamically from config, queried, serialized, and tested
 * independently. Compare with the RuleEngine which selects WHICH rules to apply
 * (Strategy).
 */
public sealed interface RuleExpr {

    boolean evaluate(Map<String, Object> context);
    String describe();

    record FieldEquals(String field, Object expected) implements RuleExpr {
        public boolean evaluate(Map<String, Object> ctx) {
            return expected.equals(ctx.get(field));
        }
        public String describe() { return field + " == " + expected; }
    }

    record FieldGreaterThan(String field, Number threshold) implements RuleExpr {
        public boolean evaluate(Map<String, Object> ctx) {
            Object v = ctx.get(field);
            if (v instanceof Number n) return n.doubleValue() > threshold.doubleValue();
            return false;
        }
        public String describe() { return field + " > " + threshold; }
    }

    record And(RuleExpr left, RuleExpr right) implements RuleExpr {
        public boolean evaluate(Map<String, Object> ctx) { return left.evaluate(ctx) && right.evaluate(ctx); }
        public String describe() { return "(" + left.describe() + " AND " + right.describe() + ")"; }
    }

    record Or(RuleExpr left, RuleExpr right) implements RuleExpr {
        public boolean evaluate(Map<String, Object> ctx) { return left.evaluate(ctx) || right.evaluate(ctx); }
        public String describe() { return "(" + left.describe() + " OR " + right.describe() + ")"; }
    }

    record Not(RuleExpr inner) implements RuleExpr {
        public boolean evaluate(Map<String, Object> ctx) { return !inner.evaluate(ctx); }
        public String describe() { return "NOT (" + inner.describe() + ")"; }
    }
}
