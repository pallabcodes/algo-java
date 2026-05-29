package com.backend.designpatterns.realworld.payments;

import java.util.ArrayList;
import java.util.List;

/**
 * Chain of Responsibility pattern — each PipelineHandler is a stage that can
 * process, short-circuit (return non-null PaymentResult), or pass through.
 * Stages: validate → fraud check → charge → post-process.
 *
 * Without this, cross-cutting concerns (fraud, logging, validation) are either
 * mixed into the provider adapter or duplicated across every charge() call.
 * Chain lets each concern be a separate handler, independently testable and
 * reorderable. Composes with PipelineHandler static factories for stage definitions.
 */
public class ProcessingPipeline {
    private final List<PipelineHandler> handlers = new ArrayList<>();

    public ProcessingPipeline add(PipelineHandler handler) {
        handlers.add(handler);
        return this;
    }

    public PaymentResult execute(PaymentRequest request, PaymentProvider provider, Transaction tx) {
        System.out.println("\n[Pipeline] executing " + handlers.size() + " handlers sequentially");
        for (PipelineHandler handler : handlers) {
            PaymentResult result = handler.handle(request, provider, tx);
            if (result != null) {
                System.out.println("[Pipeline] early return from " + handler.getClass().getSimpleName());
                return result;
            }
        }
        System.out.println("[Pipeline] all handlers passed");
        return provider.charge(request);
    }

    public static ProcessingPipeline defaultPipeline() {
        return new ProcessingPipeline()
            .add(PipelineHandler.validate())
            .add(PipelineHandler.fraudCheck())
            .add(PipelineHandler.charge())
            .add(PipelineHandler.postProcess());
    }
}
