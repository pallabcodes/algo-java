package com.backend.designpatterns.realworld.featureflags;

/**
 * [6/6] Demonstrates Composite + Strategy + Proxy + Factory composition for feature flags.
 * Shows: condition trees (AND/OR/NOT) evaluated recursively, pluggable rollout
 * strategies (SimpleFlag, ExperimentRollout, StagedRollout), transparent Proxy wrapping,
 * FlagRegistry as central factory.
 *
 * Without this: features ship 0→100% instantly, no kill switch, no gradual rollout.
 */
public class FeatureFlagDemo {

    public static void main(String[] args) {
        System.out.println("""
            === L6 PATTERN COMPOSITION: Feature Flag System ===

            Patterns combined:  Composite + Strategy + Factory + Proxy

            At Google, every feature ships behind a flag. Flags compose conditions
            into trees (Composite), use different rollout strategies, are created
            from config (Factory), and wrap features transparently (Proxy).
            """);

        FlagRegistry registry = FlagRegistry.productionFlags();

        FeatureProxy<String> searchEngine = new FeatureProxy<>("new-search-engine", registry,
            () -> "NewSearchEngine(results=neural_ranked)",
            () -> "LegacySearchEngine(results=keyword_based)");

        FeatureProxy<String> checkout = new FeatureProxy<>("checkout-v2", registry,
            () -> "CheckoutV2(form=3_step, pay=wallet)",
            () -> "CheckoutV1(form=5_step, pay=form_only)");

        FeatureProxy<String> darkMode = new FeatureProxy<>("dark-mode", registry,
            () -> "DarkModeUI(theme=dark)",
            () -> "LightModeUI(theme=light)");

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO A: US user — gets dark mode + 10% search rollout");
        System.out.println("=".repeat(70));

        UserContext usUser = UserContext.of("user_001", "US");
        System.out.println("Search:    " + searchEngine.execute(usUser));
        System.out.println("Checkout:  " + checkout.execute(usUser));
        System.out.println("UI Theme:  " + darkMode.execute(usUser));

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO B: Internal (Googler) user — all flags on");
        System.out.println("=".repeat(70));

        UserContext googler = UserContext.of("user_g_42", "US").withCohort("internal");
        System.out.println("Search:    " + searchEngine.execute(googler));
        System.out.println("Checkout:  " + checkout.execute(googler));
        System.out.println("UI Theme:  " + darkMode.execute(googler));

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO C: EU user — no dark mode, checkout holdout");
        System.out.println("=".repeat(70));

        UserContext euUser = UserContext.of("user_eu_99", "EU");
        System.out.println("Checkout:  " + checkout.execute(euUser));
        System.out.println("UI Theme:  " + darkMode.execute(euUser));

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO D: Dynamic flag registration at runtime");
        System.out.println("=".repeat(70));

        registry.register("premium-features",
            new EvaluationStrategy.SimpleFlag(
                new FlagCondition.CohortCondition("beta")));

        FeatureProxy<String> premium = new FeatureProxy<>("premium-features", registry,
            () -> "PremiumFeatures(gadgets=[ai_summary, csv_export])",
            () -> "BasicFeatures(gadgets=[export_only])");

        UserContext betaUser = UserContext.of("user_beta_7", "US").withCohort("beta");
        UserContext normalUser = UserContext.of("user_normal_7", "US");
        System.out.println("Beta user:   " + premium.execute(betaUser));
        System.out.println("Normal user: " + premium.execute(normalUser));

        System.out.println("\n" + "=".repeat(70));
        System.out.println("L6 TAKEAWAY: 4 patterns, 1 feature flag system.");
        System.out.println("  Composite  → conditions form trees (AND/OR/NOT)");
        System.out.println("  Strategy   → rollout, experiment, staged strategies");
        System.out.println("  Factory    → FlagRegistry creates evaluators from config");
        System.out.println("  Proxy      → features wrapped transparently, callers unaware");
        System.out.println("=".repeat(70));
    }
}
