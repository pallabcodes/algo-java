package com.backend.designpatterns.realworld.payments.sdk;

import java.util.Map;
import java.util.UUID;

public class SdkIntegrationDemo {

    public static void main(String[] args) {
        System.out.println("""
            === L6 PATTERN COMPOSITION: Multi-Provider SDK Integration ===

            Patterns combined:  Builder + Strategy + Adapter + Factory + Proxy

            REALITY: Each provider SDK has a DIFFERENT request shape and response shape.
            Stripe wants {source_token, description}. PayPal wants {payer_id, intent, return_url}.
            GPay wants {encrypted_token, merchant_id}.

            The solution: Canonical DTO → BuilderStrategy → Provider SDK → NormalizerAdapter → Canonical DTO
            Then: RoleBased Proxy shapes the response per caller role.
            """);

        RequestBuilderFactory builderFactory = RequestBuilderFactory.defaultBuilders();
        ResponseNormalizerFactory normalizerFactory = ResponseNormalizerFactory.defaultNormalizers();

        Map<String, ProviderSdkClient> sdks = Map.of(
            "Stripe", new ProviderSdkClient.StripeClient(),
            "PayPal", new ProviderSdkClient.PayPalClient(),
            "GPay", new ProviderSdkClient.GPayClient()
        );

        String idempotencyKey = UUID.randomUUID().toString();

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO A: Canonical Request → Stripe SDK → Canonical Response");
        System.out.println("=".repeat(70));

        CanonicalPaymentRequest reqA = CanonicalPaymentRequest.of("ORD-001", "user_1", 4999, "USD", "credit_card");
        System.out.println("[Canonical] input: " + reqA);

        // Step 1: Build provider-specific request (Builder + Strategy)
        RequestBuilderStrategy stripeBuilder = builderFactory.forProvider("Stripe");
        ProviderSdkRequest sdkReqA = stripeBuilder.build(reqA, idempotencyKey);
        System.out.println("[Builder:Stripe] SDK request: " + sdkReqA);

        // Step 2: Call provider SDK
        var stripeClient = sdks.get("Stripe");
        ProviderSdkResponse sdkResA = stripeClient.charge(sdkReqA);

        // Step 3: Normalize response (Adapter)
        ResponseNormalizer stripeNormalizer = normalizerFactory.forProvider("Stripe");
        CanonicalPaymentResponse canonicalResA = stripeNormalizer.normalize(sdkResA, reqA, "Stripe");
        System.out.println("[Normalizer:Stripe] canonical response: " + canonicalResA);

        // Step 4: Role-based view (Proxy)
        System.out.println("\n--- Role-Based Views ---");
        RoleBasedView customerView = RoleBasedView.forRole("customer");
        RoleBasedView adminView = RoleBasedView.forRole("admin");

        System.out.println("Customer sees:  " + customerView.apply(canonicalResA));
        System.out.println("Admin sees:     " + adminView.apply(canonicalResA));

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO B: Same canonical request → Different provider (PayPal)");
        System.out.println("=".repeat(70));

        RequestBuilderStrategy paypalBuilder = builderFactory.forProvider("PayPal");
        ProviderSdkRequest sdkReqB = paypalBuilder.build(reqA, idempotencyKey);
        System.out.println("[Builder:PayPal] SDK request: " + sdkReqB);

        var paypalClient = sdks.get("PayPal");
        ProviderSdkResponse sdkResB = paypalClient.charge(sdkReqB);

        ResponseNormalizer paypalNormalizer = normalizerFactory.forProvider("PayPal");
        CanonicalPaymentResponse canonicalResB = paypalNormalizer.normalize(sdkResB, reqA, "PayPal");
        System.out.println("[Normalizer:PayPal] canonical response: " + canonicalResB);

        System.out.println("Customer sees:  " + customerView.apply(canonicalResB));
        System.out.println("Admin sees:     " + adminView.apply(canonicalResB));

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO C: Declined card — error handling across provider boundary");
        System.out.println("=".repeat(70));

        CanonicalPaymentRequest reqC = CanonicalPaymentRequest.of("ORD-002", "user_2", 10000, "USD", "decline_card");
        System.out.println("[Canonical] input: " + reqC);

        ProviderSdkRequest sdkReqC = stripeBuilder.build(reqC, UUID.randomUUID().toString());
        ProviderSdkResponse sdkResC = stripeClient.charge(sdkReqC);
        CanonicalPaymentResponse canonicalResC = stripeNormalizer.normalize(sdkResC, reqC, "Stripe");
        System.out.println("[Normalizer:Stripe] canonical response: " + canonicalResC);

        System.out.println("Support agent sees: " + RoleBasedView.forRole("support").apply(canonicalResC));
        System.out.println("Customer sees:       " + customerView.apply(canonicalResC));

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO D: Dynamic provider selection at runtime");
        System.out.println("=".repeat(70));

        String runtimeProvider = "GPay";
        System.out.println("(runtime decision: region supports GPay, selecting provider = " + runtimeProvider + ")");

        var client = sdks.get(runtimeProvider);
        RequestBuilderStrategy builder = builderFactory.forProvider(runtimeProvider);
        ResponseNormalizer normalizer = normalizerFactory.forProvider(runtimeProvider);

        CanonicalPaymentRequest reqD = CanonicalPaymentRequest.of("ORD-003", "user_3", 2500, "JPY", "wallet");
        ProviderSdkRequest sdkReqD = builder.build(reqD, UUID.randomUUID().toString());
        ProviderSdkResponse sdkResD = client.charge(sdkReqD);
        CanonicalPaymentResponse canonicalResD = normalizer.normalize(sdkResD, reqD, runtimeProvider);

        System.out.println("Admin sees: " + RoleBasedView.forRole("admin").apply(canonicalResD));

        System.out.println("\n" + "=".repeat(70));
        System.out.println("L6 TAKEAWAY: Canonical DTO pattern with 5 composed patterns.");
        System.out.println("  Builder  → translates canonical → provider-specific request shapes");
        System.out.println("  Strategy → different build strategies per provider");
        System.out.println("  Adapter  → normalizes provider-specific responses → canonical");
        System.out.println("  Factory  → selects builder/normalizer per provider at runtime");
        System.out.println("  Proxy    → shapes response per role without modifying DTO");
        System.out.println("=".repeat(70));
    }
}
