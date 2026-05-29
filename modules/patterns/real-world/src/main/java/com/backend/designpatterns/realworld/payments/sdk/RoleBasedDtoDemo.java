package com.backend.designpatterns.realworld.payments.sdk;

import java.util.Map;
import java.util.UUID;

public class RoleBasedDtoDemo {

    public static void main(String[] args) {
        System.out.println("""
            === L6 PATTERN COMPOSITION: Bidirectional Role-Based DTO ===

            Patterns:  Adapter (request) + Adapter (response) + Strategy + Factory + Proxy

            REALITY: Different roles send different request shapes AND expect different response shapes.
            Customer:  {amount, currency}                  → sees: {status, amount}
            Support:   {amount, currency, override_limit}   → sees: {status, amount, errorMessage, providerRef}
            Admin:     {amount, currency, metadata:{...}}   → sees: EVERYTHING including raw provider data

            The canonical DTO is the INTERNAL boundary. Role adapters translate on both sides.
            """);

        // 1. Different roles, different request payloads
        Map<String, Object> customerRequest = Map.of(
            "amount", 4999, "currency", "USD", "method", "credit_card"
        );

        Map<String, Object> supportRequest = Map.of(
            "amount", 15000, "currency", "USD", "method", "debit_card",
            "override_limit", "true"
        );

        Map<String, Object> adminRequest = Map.of(
            "amount", 999900, "currency", "USD", "method", "wire_transfer",
            "metadata", Map.of("internal_note", "urgent settlement", "fee_waiver", "true")
        );

        var builderFactory = RequestBuilderFactory.defaultBuilders();
        var normalizerFactory = ResponseNormalizerFactory.defaultNormalizers();
        var stripeClient = new ProviderSdkClient.StripeClient();

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO A: Customer request → canonical → Stripe → customer response");
        System.out.println("=".repeat(70));

        // REQUEST SIDE: Adapter normalizes role-specific shape → canonical
        RoleBasedRequestAdapter customerReqAdapter = RoleBasedRequestAdapter.forRole("customer");
        CanonicalPaymentRequest canonicalReqA = customerReqAdapter.adapt("ORD-C-001", "user_customer", customerRequest);
        System.out.println("[Customer Request] raw: " + customerRequest);
        System.out.println("[Adapter] canonical: " + canonicalReqA);

        // Process through SDK
        RequestBuilderStrategy stripeBuilder = builderFactory.forProvider("Stripe");
        var sdkReq = stripeBuilder.build(canonicalReqA, UUID.randomUUID().toString());
        var sdkRes = stripeClient.charge(sdkReq);
        var canonicalRes = normalizerFactory.forProvider("Stripe").normalize(sdkRes, canonicalReqA, "Stripe");

        // RESPONSE SIDE: Proxy shapes canonical → role-specific view
        RoleBasedView customerView = RoleBasedView.forRole("customer");
        System.out.println("[Response:Customer] " + customerView.apply(canonicalRes));
        RoleBasedView adminView = RoleBasedView.forRole("admin");
        System.out.println("[Response:Admin]    " + adminView.apply(canonicalRes));

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO B: Support request with override → canonical → support response");
        System.out.println("=".repeat(70));

        // REQUEST SIDE: Support adapter extracts override fields into metadata
        RoleBasedRequestAdapter supportReqAdapter = RoleBasedRequestAdapter.forRole("support");
        CanonicalPaymentRequest canonicalReqB = supportReqAdapter.adapt("ORD-S-001", "user_support", supportRequest);
        System.out.println("[Support Request] raw: " + supportRequest);
        System.out.println("[Adapter] canonical with metadata: " + canonicalReqB);

        sdkReq = stripeBuilder.build(canonicalReqB, UUID.randomUUID().toString());
        sdkRes = stripeClient.charge(sdkReq);
        canonicalRes = normalizerFactory.forProvider("Stripe").normalize(sdkRes, canonicalReqB, "Stripe");

        RoleBasedView supportView = RoleBasedView.forRole("support");
        System.out.println("[Response:Support] " + supportView.apply(canonicalRes));
        System.out.println("[Response:Customer] " + customerView.apply(canonicalRes));

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO C: Admin request with metadata → admin sees raw provider data");
        System.out.println("=".repeat(70));

        RoleBasedRequestAdapter adminReqAdapter = RoleBasedRequestAdapter.forRole("admin");
        CanonicalPaymentRequest canonicalReqC = adminReqAdapter.adapt("ORD-A-001", "user_admin", adminRequest);
        System.out.println("[Admin Request] raw: " + adminRequest);
        System.out.println("[Adapter] canonical with full metadata: " + canonicalReqC);

        sdkReq = stripeBuilder.build(canonicalReqC, UUID.randomUUID().toString());
        sdkRes = stripeClient.charge(sdkReq);
        canonicalRes = normalizerFactory.forProvider("Stripe").normalize(sdkRes, canonicalReqC, "Stripe");

        System.out.println("[Response:Admin]  " + adminView.apply(canonicalRes));
        System.out.println("[Response:Customer] " + customerView.apply(canonicalRes));

        System.out.println("\n" + "=".repeat(70));
        System.out.println("L6 TAKEAWAY: Role-based DTO shaping is bidirectional.");
        System.out.println("  REQUEST  → RoleBasedRequestAdapter normalizes role-specific shapes → canonical");
        System.out.println("  RESPONSE → Canonical → RoleBasedView shapes per role");
        System.out.println("  Adapter + Strategy + Proxy = 3 patterns, 2 directions, any number of roles.");
        System.out.println("  Adding a new role = new RequestAdapter + new View. No core processing changes.");
        System.out.println("=".repeat(70));
    }
}
