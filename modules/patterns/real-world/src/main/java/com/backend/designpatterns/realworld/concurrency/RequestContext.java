package com.backend.designpatterns.realworld.concurrency;

/**
 * ScopedValue + Factory — propagates request-scoped context across virtual
 * threads without ThreadLocal leaks. Bound at the request entry point,
 * inherited by StructuredTaskScope forks automatically.
 *
 * Why not ThreadLocal? ThreadLocal + VirtualThread = memory leak (VT carrier
 * pool reuses platform threads indefinitely). ScopedValue is the Loom-native
 * replacement with proper lifecycle boundaries.
 *
 * Why not method parameters? 6 services × 3 context fields = 18 extra params.
 * ScopedValue removes plumbing without sacrificing type safety.
 *
 * Critical L6 distinction:
 *   StructuredTaskScope forks → inherit ScopedValue (correct for parallelism)
 *   CompletableFuture → does NOT inherit ScopedValue (use closures instead)
 *   Both are correct — know which applies when.
 */
public record RequestContext(String requestId, String userId, String traceId, String region) {

    private static final ScopedValue<RequestContext> INSTANCE = ScopedValue.newInstance();

    public static RequestContext current() {
        return INSTANCE.get();
    }

    public static <T> T runWith(RequestContext ctx, java.util.concurrent.Callable<T> action) throws Exception {
        return ScopedValue.where(INSTANCE, ctx).call(action);
    }

    public static void runWith(RequestContext ctx, Runnable action) {
        ScopedValue.where(INSTANCE, ctx).run(action);
    }
}
