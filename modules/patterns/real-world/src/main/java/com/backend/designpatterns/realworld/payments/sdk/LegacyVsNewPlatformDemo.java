package com.backend.designpatterns.realworld.payments.sdk;

import java.util.Map;
import java.util.UUID;

public class LegacyVsNewPlatformDemo {

    public static void main(String[] args) {
        System.out.println("""
            === L6 PATTERN COMPOSITION: Legacy vs New Platform API ===

            Patterns:  Adapter (×2 for v1/v2) + Strategy (version routing) + Factory + ResponseMapper

            REALITY: v1 and v2 coexist for 12-24 months during migration.
            v1 sends {total, cc_last4}. v2 sends {amount_cents, currency, payment_method, metadata}.
            BOTH map to the SAME canonical model internally. Processing pipeline doesn't know about versions.

            Response Mapper != Response DTO.
            Response DTO = the shape returned to the client.
            Response Mapper = the logic that transforms canonical → role-specific × version-specific shape.
            """);

        var v1Adapter = new ApiRequestAdapter.V1Adapter();
        var v2Adapter = new ApiRequestAdapter.V2Adapter();
        var headerRouter = new VersionRouter.HeaderBased();
        var gradualRouter = new VersionRouter.GradualMigration(30);

        var builderFactory = RequestBuilderFactory.defaultBuilders();
        var normalizerFactory = ResponseNormalizerFactory.defaultNormalizers();
        var stripeClient = new ProviderSdkClient.StripeClient();

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO A: v1 client sends legacy shape → always works");
        System.out.println("=".repeat(70));

        Map<String, Object> v1Payload = Map.of("total", 4999, "cc_last4", "4242");
        ApiVersion version = headerRouter.resolve("Accept: application/vnd.api.v1+json");
        System.out.println("[Router] resolved: " + version);

        var reqAdapter = version.version().equals("v1") ? v1Adapter : v2Adapter;
        CanonicalPaymentRequest canonicalA = reqAdapter.toCanonical("ORD-V1-001", "user_legacy", v1Payload, version);
        System.out.println("[Adapter] v1 map → canonical: " + canonicalA);

        var sdkReq = builderFactory.forProvider("Stripe").build(canonicalA, UUID.randomUUID().toString());
        var sdkRes = stripeClient.charge(sdkReq);
        var canonicalRes = normalizerFactory.forProvider("Stripe").normalize(sdkRes, canonicalA, "Stripe");

        var v1Mapper = ApiResponseMapper.forVersion(version);
        System.out.println("[ResponseMapper:v1] customer: " + v1Mapper.toResponse(canonicalRes, version, "customer"));
        System.out.println("[ResponseMapper:v1] admin:   " + v1Mapper.toResponse(canonicalRes, version, "admin"));

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO B: v2 client sends modern shape → richer response");
        System.out.println("=".repeat(70));

        Map<String, Object> v2Payload = Map.of("amount_cents", 12999, "currency", "EUR",
            "payment_method", "wallet", "metadata", Map.of("promo_code", "WELCOME10"));
        version = headerRouter.resolve("application/vnd.api.v2+json");
        System.out.println("[Router] resolved: " + version);

        reqAdapter = version.version().equals("v1") ? v1Adapter : v2Adapter;
        CanonicalPaymentRequest canonicalB = reqAdapter.toCanonical("ORD-V2-001", "user_new", v2Payload, version);
        System.out.println("[Adapter] v2 map → canonical: " + canonicalB);

        sdkReq = builderFactory.forProvider("Stripe").build(canonicalB, UUID.randomUUID().toString());
        sdkRes = stripeClient.charge(sdkReq);
        canonicalRes = normalizerFactory.forProvider("Stripe").normalize(sdkRes, canonicalB, "Stripe");

        var v2Mapper = ApiResponseMapper.forVersion(version);
        System.out.println("[ResponseMapper:v2] customer: " + v2Mapper.toResponse(canonicalRes, version, "customer"));
        System.out.println("[ResponseMapper:v2] admin:    " + v2Mapper.toResponse(canonicalRes, version, "admin"));

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO C: Gradual migration — 30% of unversioned traffic goes to v2 pipeline");
        System.out.println("=".repeat(70));

        for (int i = 1; i <= 5; i++) {
            ApiVersion routed = gradualRouter.resolve("");
            System.out.println("[Migration] request " + i + " → " + routed);
        }

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO D: ResponseMapper composes version + role");
        System.out.println("=".repeat(70));

        // v1 admin sees legacy field names
        System.out.println("[v1+admin]   " + v1Mapper.toResponse(canonicalRes, ApiVersion.V1, "admin"));
        // v2 admin sees modern field names (RoleBasedView)
        System.out.println("[v2+admin]   " + v2Mapper.toResponse(canonicalRes, ApiVersion.V2, "admin"));
        // v2 customer sees minimal fields
        System.out.println("[v2+customer] " + v2Mapper.toResponse(canonicalRes, ApiVersion.V2, "customer"));

        System.out.println("\n" + "=".repeat(70));
        System.out.println("L6 TAKEAWAY: ResponseMapper = broader than Response DTO.");
        System.out.println("  Response DTO = the shape. ResponseMapper = HOW to get there.");
        System.out.println("  Our RoleBasedView = one ResponseMapper strategy (by role).");
        System.out.println("  ApiVersion + Role = another ResponseMapper (by version × role).");
        System.out.println("  Both use the SAME canonical response. The mapper is a Strategy.");
        System.out.println("=".repeat(70));
    }
}
