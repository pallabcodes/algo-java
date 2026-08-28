package com.backend.designpatterns.realworld;

import java.util.List;

/**
 * Demonstrates all nested types allowed inside a Java interface (except records).
 */
public interface _NestedTypesDemo {

    // ─── 1. NESTED ENUM ────────────────────────────────────────
    // Groups constants related to the interface contract.
    // Implicitly public static.
    enum Status { SUCCESS, FAILED, PENDING, DECLINED }

    void process(Status status);

    // ─── 2. NESTED CLASS (concrete) ──────────────────────────────
    // Default implementation bundled with the interface.
    // Useful for base behavior or common helpers.
    class DefaultHandler implements _NestedTypesDemo {
        @Override
        public void process(Status status) {
            System.out.println("Processing: " + status);
        }

        static String defaultName() {
            return "DefaultHandler";
        }
    }

    // ─── 3. NESTED INTERFACE ─────────────────────────────────────
    // Sub-contract: extends or specializes the parent.
    // Common for extensibility hooks or strategy sub-types.
    interface AdvancedHandler extends _NestedTypesDemo {
        void rollback(Status status);
    }

    // ─── 4. NESTED ANNOTATION ────────────────────────────────────
    // Metadata for implementors. Usually marks policy or config.
    @interface Config {
        int timeout() default 1000;
        boolean retry() default false;
    }

    // ─── USAGE ───────────────────────────────────────────────────
    static void main(String[] args) {
        // Enum usage
        Status s = Status.SUCCESS;

        // Nested class usage
        var h = new DefaultHandler();
        h.process(s);

        // Nested annotation usage (on anonymous impl)
        _NestedTypesDemo annotated = new _NestedTypesDemo() {
            @Override
            public void process(Status status) {
                System.out.println("Annotated process: " + status);
            }
        };
        annotated.process(Status.PENDING);

        // Nested interface implemented
        AdvancedHandler ah = new AdvancedHandler() {
            @Override
            public void process(Status status) {
                System.out.println("Advanced: " + status);
            }

            @Override
            public void rollback(Status status) {
                System.out.println("Rollback: " + status);
            }
        };
        ah.process(Status.FAILED);
        ah.rollback(Status.FAILED);
    }
}
