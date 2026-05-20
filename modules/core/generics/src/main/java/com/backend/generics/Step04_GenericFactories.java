package com.backend.generics;

/**
 * Step 04: Generic Factories (Type Inference Mastery)
 * 
 * L7 Principles:
 * 1. Type Inference: Removing redundant type declarations in client code.
 * 2. Singleton Factory Pattern: Reusing a single instance of a generic class
 * for all types (e.g., Collections.emptyList()).
 * 3. Covariant Returns: Using wildcards in return types for maximum
 * flexibility.
 */
public class Step04_GenericFactories {

    public interface Identity<T> {
        T identify(T input);
    }

    /**
     * Mastery: A singleton factory that works for ANY type T
     * And this `input -> input` is a lambda function so e.g. whatever arguments it
     * takes, it returns that unchanged
     * Why Identity<Object> because using it allows to accept ANY type of input
     * So, again below is a lambda function that takes arugments and retunrs it
     * unchanged and we have taked Obejct as type since it is super type of all the
     * types
     */
    private static final Identity<Object> IDENTITY_INSTANCE = input -> input; // take the input return it

    /**
     * Here, we seem to have two return types i.e. <T> and Identity<T>
     * But in reality we only have one generic type i.e. <T> and Identity<Object>
     * --
     * Perfect — now I see exactly where the confusion happened.
     * 
     * You actually understood MOST of it already. The confusion is specifically
     * around THIS:
     * 
     * ```java
     * public static <T> Identity<T> getIdentity()
     * ```
     * 
     * and:
     * 
     * ```java
     * return (Identity<T>) IDENTITY_INSTANCE;
     * ```
     * 
     * You’re thinking:
     * 
     * ```txt id="’wini2200"
     * "Wait...
     * where does T exist?
     * How can Identity<Object>
     * become Identity<String>?"
     * ```
     * 
     * THIS is the exact core of Java generic erasure.
     * 
     * Let’s go VERY slowly.
     * 
     * ---
     * 
     * # Step 1 — Your Real Runtime Object
     * 
     * THIS line:
     * 
     * ```java id="’wini2201"
     * private static final Identity<Object>
     * IDENTITY_INSTANCE =
     * input -> input;
     * ```
     * 
     * creates ONE actual object.
     * 
     * Think of it roughly like:
     * 
     * ```java id="’wini2202"
     * class AnonymousIdentity
     * implements Identity<Object> {
     * 
     * public Object identify(
     * Object input
     * ) {
     * return input;
     * }
     * 
     * }
     * ```
     * 
     * and:
     * 
     * ```java id="’wini2203"
     * IDENTITY_INSTANCE
     * ```
     * 
     * stores ONE singleton instance.
     * 
     * ---
     * 
     * # VERY Important
     * 
     * At runtime JVM only has:
     * 
     * ```txt id="’wini2204"
     * Identity<Object>
     * ```
     * 
     * ONE object.
     * 
     * NOT:
     * 
     * Identity<String>
     * Identity<Integer>
     * 
     * Those generic types mostly disappear after compilation.
     * 
     * ---
     * 
     * # Step 2 — Generic Method
     * 
     * Now THIS:
     * 
     * ```java id="’wini2205"
     * public static <T> Identity<T>
     * getIdentity()
     * ```
     * 
     * does NOT create object.
     * 
     * This ONLY tells compiler:
     * 
     * ```txt id="’wini2206"
     * "Whoever calls me,
     * pretend I return Identity<T>"
     * ```
     * 
     * ---
     * 
     * # IMPORTANT
     * 
     * `<T>` here is NOT runtime object.
     * 
     * It is:
     * 
     * # compile-time placeholder type
     * 
     * ONLY for compiler.
     * 
     * ---
     * 
     * # Think Of It Like
     * 
     * ```txt id="’wini2207"
     * "For any type T,
     * I promise to return Identity<T>"
     * ```
     * 
     * ---
     * 
     * # Step 3 — What Happens Here?
     * 
     * ```java id="’wini2208"
     * Identity<String> stringId =
     * getIdentity();
     * ```
     * 
     * Compiler sees assignment target:
     * 
     * ```java id="’wini2209"
     * Identity<String>
     * ```
     * 
     * Therefore compiler infers:
     * 
     * ```txt id="’wini2210"
     * T = String
     * ```
     * 
     * So compiler mentally rewrites:
     * 
     * ```java id="’wini2211"
     * Identity<String> stringId =
     * getIdentity<String>();
     * ```
     * 
     * EVEN though you didn’t write it.
     * 
     * ---
     * 
     * # Therefore Method Signature Becomes
     * 
     * Compiler temporarily thinks:
     * 
     * ```java id="’wini2212"
     * Identity<String>
     * getIdentity()
     * ```
     * 
     * ---
     * 
     * # BUT Actual Runtime Object Is Still
     * 
     * ```java id="’wini2213"
     * Identity<Object>
     * ```
     * 
     * ---
     * 
     * # Therefore This Line Needed
     * 
     * ```java id="’wini2214"
     * return (Identity<T>)
     * IDENTITY_INSTANCE;
     * ```
     * 
     * ---
     * 
     * # Why Cast?
     * 
     * Because compiler says:
     * 
     * ```txt id="’wini2215"
     * "Wait...
     * Identity<Object>
     * NOT same as
     * Identity<String>"
     * ```
     * 
     * Remember:
     * 
     * Java generics invariant
     * 
     * So:
     * 
     * ```java id="’wini2216"
     * Identity<Object>
     * !=
     * Identity<String>
     * ```
     * 
     * ---
     * 
     * # Therefore Compiler Requires Explicit Cast
     * 
     * You’re basically telling compiler:
     * 
     * ```txt id="’wini2217"
     * "Trust me.
     * Treat this singleton
     * as Identity<T>."
     * ```
     * 
     * ---
     * 
     * # Step 4 — Why Is This Actually Safe?
     * 
     * Because implementation:
     * 
     * ```java id="’wini2218"
     * input -> input
     * ```
     * 
     * does NOT depend on type.
     * 
     * ---
     * 
     * # Example
     * 
     * Suppose compiler inferred:
     * 
     * ```txt id="’wini2219"
     * T = String
     * ```
     * 
     * So compiler thinks:
     * 
     * ```java id="’wini2220"
     * Identity<String>
     * ```
     * 
     * ---
     * 
     * # Then You Call
     * 
     * ```java id="’wini2221"
     * stringId.identify("hello")
     * ```
     * 
     * ---
     * 
     * # Runtime Actually Executes
     * 
     * ```java id="’wini2222"
     * Object identify(Object input) {
     * return input;
     * }
     * ```
     * 
     * ---
     * 
     * # Input
     * 
     * ```txt id="’wini2223"
     * "hello"
     * ```
     * 
     * is already:
     * 
     * Object
     * String
     * 
     * Perfectly valid.
     * 
     * ---
     * 
     * # Returned Value
     * 
     * same String object returned.
     * 
     * ---
     * 
     * # Therefore Safe.
     * 
     * ---
     * 
     * # SAME Object Used Again
     * 
     * Now:
     * 
     * ```java id="’wini2224"
     * Identity<Integer> intId =
     * getIdentity();
     * ```
     * 
     * Compiler now infers:
     * 
     * ```txt id="’wini2225"
     * T = Integer
     * ```
     * 
     * Compiler PRETENDS:
     * 
     * ```java id="’wini2226"
     * Identity<Integer>
     * ```
     * 
     * ---
     * 
     * # BUT Runtime STILL Uses SAME Singleton
     * 
     * ```java id="’wini2227"
     * IDENTITY_INSTANCE
     * ```
     * 
     * same object.
     * 
     * ---
     * 
     * # Why Does This Work?
     * 
     * Because singleton behavior:
     * 
     * stateless
     * type-independent
     * immutable
     * 
     * ---
     * 
     * # THIS Is The Entire Trick
     * 
     * ---
     * 
     * # Compile-Time Illusion
     * 
     * Compiler pretends:
     * 
     * ```txt id="’wini2228"
     * Identity<String>
     * Identity<Integer>
     * ```
     * 
     * different typed objects.
     * 
     * ---
     * 
     * # Runtime Reality
     * 
     * JVM actually has:
     * 
     * ```txt id="’wini2229"
     * ONE Identity<Object> singleton
     * ```
     * 
     * ---
     * 
     * # This Line Proves It
     * 
     * ```java id="’wini2230"
     * (Object) stringId ==
     * (Object) intId
     * ```
     * 
     * returns:
     * 
     * ```txt id="’wini2231"
     * true
     * ```
     * 
     * because:
     * 
     * same JVM object reference
     * 
     * ---
     * 
     * # Why Cast To Object Before `==`?
     * 
     * To avoid generic typing noise.
     * 
     * You’re comparing:
     * 
     * raw object identity
     * 
     * ---
     * 
     * # THIS Is EXACTLY Like `Collections.emptyList()`
     * 
     * ---
     * 
     * # Compiler Pretends
     * 
     * ```java id="’wini2232"
     * List<String>
     * ```
     * 
     * ---
     * 
     * # Runtime Actually Uses
     * 
     * ```txt id="’wini2233"
     * ONE shared immutable empty list
     * ```
     * 
     * ---
     * 
     * # Because Empty List Has No Type-Specific State
     * 
     * Safe to reuse.
     * 
     * ---
     * 
     * # YOUR Comment Confusion
     * 
     * You wrote:
     * 
     * ```java
     * Here, we seem to have two return types
     * i.e. <T> and Identity<T>
     * ```
     * 
     * Not exactly.
     * 
     * ---
     * 
     * # REAL Breakdown
     * 
     * ---
     * 
     * # `<T>`
     * 
     * declares:
     * 
     * # generic type parameter
     * 
     * ---
     * 
     * # `Identity<T>`
     * 
     * is actual return type USING that generic parameter.
     * 
     * ---
     * 
     * # Similar To
     * 
     * ```java id="’wini2234"
     * public static <X> List<X> foo()
     * ```
     * 
     * Only ONE generic type:
     * 
     * X
     * 
     * ---
     * 
     * # Think Of `<T>` As Variable Declaration
     * 
     * and:
     * 
     * ```java id="’wini2235"
     * Identity<T>
     * ```
     * 
     * as:
     * 
     * usage of that variable.
     * 
     * ---
     * 
     * # VERY Important Analogy
     * 
     * ---
     * 
     * # THIS
     * 
     * ```java id="’wini2236"
     * <T>
     * ```
     * 
     * is like:
     * 
     * ```txt id="’wini2237"
     * declare variable T
     * ```
     * 
     * ---
     * 
     * # THIS
     * 
     * ```java id="’wini2238"
     * Identity<T>
     * ```
     * 
     * is like:
     * 
     * ```txt id="’wini2239"
     * use T here
     * ```
     * 
     * ---
     * 
     * # Final Deep Mental Model
     * 
     * This code is basically doing:
     * 
     * ```txt id="’wini2240"
     * 1. create ONE universal singleton object
     * 2. compiler pretends it matches any generic type requested
     * 3. cast bridges compile-time generic illusion
     * 4. runtime reuses same erased object
     * ```
     * 
     * ---
     * 
     * # Deepest Insight
     * 
     * This pattern works ONLY because:
     * 
     * ```txt id="’wini2241"
     * behavior does not depend on T
     * ```
     * 
     * If singleton had:
     * 
     * mutable T state
     * cached T values
     * type-specific behavior
     * 
     * this would become unsafe VERY quickly.
     * 
     * That’s why this pattern is usually only used for:
     * 
     * immutable
     * stateless
     * type-agnostic
     * objects/functions.
     * 
     * 
     */
    @SuppressWarnings("unchecked")
    public static <T> Identity<T> getIdentity() {
        return (Identity<T>) IDENTITY_INSTANCE;
    }

    public static void main(String[] args) {
        System.out.println("=== Step 04: Generic Factories (Inference Demo) ===");

        // Mastery: No need to explicitly say <String> in the call; Java infers it from
        // the assignment.
        Identity<String> stringId = getIdentity();
        Identity<Integer> intId = getIdentity();

        System.out.println("String Result: " + stringId.identify("L7-Engineer"));
        System.out.println("Integer Result: " + intId.identify(42));

        System.out.println("\nsameInstance (String vs Integer): " + ((Object) stringId == (Object) intId));

        System.out.println("\nL5 Insight: Type inference makes APIs feel 'sovereign' and clean.");
        System.out.println("L7 Tip: Use singleton factories for immutable generic behaviors to save heap space.");
    }
}
