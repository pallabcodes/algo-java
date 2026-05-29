package com.backend.designpatterns.realworld.payments.sdk;

public interface ResponseNormalizer {
    CanonicalPaymentResponse normalize(ProviderSdkResponse response, CanonicalPaymentRequest original, String providerName);

    record StripeNormalizer() implements ResponseNormalizer {
        public CanonicalPaymentResponse normalize(ProviderSdkResponse raw, CanonicalPaymentRequest req, String provider) {
            if (!(raw instanceof StripeSdkResponse s)) {
                return CanonicalPaymentResponse.failure(null, "unexpected response type: " + raw.getClass().getSimpleName(), provider);
            }
            return switch (s.status()) {
                case "succeeded" -> CanonicalPaymentResponse.success(s.id(), s.id(), provider, req.amountCents(), req.currency());
                case "failed" -> CanonicalPaymentResponse.failure(s.id(), s.failureMessage(), provider);
                default -> CanonicalPaymentResponse.failure(s.id(), "stripe_status:" + s.status(), provider);
            };
        }
    }

    record PayPalNormalizer() implements ResponseNormalizer {
        public CanonicalPaymentResponse normalize(ProviderSdkResponse raw, CanonicalPaymentRequest req, String provider) {
            if (!(raw instanceof PayPalSdkResponse p)) {
                return CanonicalPaymentResponse.failure(null, "unexpected response type", provider);
            }
            return switch (p.status()) {
                case "completed" -> CanonicalPaymentResponse.success(p.id(), p.id(), provider, req.amountCents(), req.currency());
                case "denied" -> CanonicalPaymentResponse.failure(p.id(), p.reason(), provider);
                default -> CanonicalPaymentResponse.failure(p.id(), "paypal_status:" + p.status(), provider);
            };
        }
    }

    record GPayNormalizer() implements ResponseNormalizer {
        public CanonicalPaymentResponse normalize(ProviderSdkResponse raw, CanonicalPaymentRequest req, String provider) {
            if (!(raw instanceof GPaySdkResponse g)) {
                return CanonicalPaymentResponse.failure(null, "unexpected response type", provider);
            }
            return switch (g.status()) {
                case "captured" -> CanonicalPaymentResponse.success(g.id(), g.id(), provider, req.amountCents(), req.currency());
                case "declined" -> CanonicalPaymentResponse.failure(g.id(), g.errorReason(), provider);
                default -> CanonicalPaymentResponse.failure(g.id(), "gpay_status:" + g.status(), provider);
            };
        }
    }
}
