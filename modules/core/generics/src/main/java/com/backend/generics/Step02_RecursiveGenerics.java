package com.backend.generics;

/**
 * Step 02: Recursive Generics (Fluent APIs)
 * 
 * L7 Principles:
 * 1. Self-referencing Types: 'T extends Base<T>' allows methods to return the
 * actual subclass type.
 * 2. Method Chaining: Essential for deep builders where subclassing must
 * preserve the chain.
 * 3. Type Safety: Avoiding 'instanceof' or manual casting in builder methods.
 */
public class Step02_RecursiveGenerics {

    public abstract static class BaseBuilder<T extends BaseBuilder<T>> {

        private String id;

        // The 'self' method trick for L7 builders
        protected abstract T self();

        public T withId(String id) {
            this.id = id;
            return self();
        }

        public void print() {
            System.out.println("ID: " + id);
        }
    }

    public static class TaskBuilder extends BaseBuilder<TaskBuilder> {
        private String owner;

        @Override
        protected TaskBuilder self() {
            return this;
        }

        public TaskBuilder withOwner(String owner) {
            this.owner = owner;
            return self();
        }

        @Override
        public void print() {
            super.print();
            System.out.println("Owner: " + owner);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Step 02: Recursive Generics (Fluent API) ===");

        // Mastery: withId (from Base) returns TaskBuilder, allowing withOwner (from
        // Sub)
        TaskBuilder builder = new TaskBuilder()
                .withId("DEPLOY-123")
                .withOwner("L7-Engineer");

        builder.print();

        System.out.println("\nL5 Insight: This is how sophisticated DSLs and cloud-native SDKs are built in Java.");
    }
}
