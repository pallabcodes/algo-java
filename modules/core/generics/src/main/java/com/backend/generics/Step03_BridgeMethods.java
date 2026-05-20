package com.backend.generics;

import java.lang.reflect.Method;

/**
 * Step 03: Bridge Methods (The 'Invisible' Physics)
 * 
 * L7 Principles:
 * 1. Type Erasure: Generics only exist at compile-time. At runtime, they are
 * Object.
 * 2. Bridge Methods: Synthesized methods created by the compiler to support
 * covariance in generic types.
 * 3. Inheritance: What happens when a subclass provides a specific type for a
 * generic parameter.
 */
public class Step03_BridgeMethods {

    public interface Processor<T> {
        void process(T data);
    }

    public static class StringProcessor implements Processor<String> {
        @Override
        public void process(String data) {
            System.out.println("Processing String: " + data);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Step 03: Bridge Methods (Bytecode Mechanics) ===");

        // Below is a Reflection, but why ? "inspect program structure at runtime"

        // Class<?> means class of unknown type, StringProcessor.class means give me
        // metadata of this type and for more details read below.

        /**
         * 
         * YES — your reaction is actually common among many senior engineers when first
         * deeply seeing Java reflection APIs.
         * 
         * The syntax feels:
         * 
         * verbose
         * ceremony-heavy
         * awkward
         * very “90s enterprise Java”
         * 
         * because reflection APIs were designed around:
         * 
         * JVM metadata model
         * backward compatibility
         * security
         * bytecode abstraction
         * 
         * NOT ergonomic DX.
         * 
         * But the reflection here was VERY important conceptually.
         * 
         * Let’s break both parts clearly:
         * 
         * ---
         * 
         * # 1. WHY Did We Use Reflection Here?
         * 
         * Because:
         * 
         * # bridge methods are compiler-generated hidden methods
         * 
         * You NEVER wrote:
         * 
         * ```java id="jlwm2200"
         * process(Object)
         * ```
         * 
         * yourself.
         * 
         * So how do we prove:
         * 
         * compiler synthesized it
         * it really exists
         * JVM class actually contains it?
         * 
         * We need to inspect:
         * 
         * # compiled class metadata
         * 
         * That is exactly what reflection does.
         * 
         * ---
         * 
         * # Reflection Means
         * 
         * ```txt id="’wini2201"
         * "inspect program structure at runtime"
         * ```
         * 
         * ---
         * 
         * # Reflection Lets You Ask JVM
         * 
         * Questions like:
         * 
         * ```txt id="’wini2202"
         * What methods exist?
         * What fields exist?
         * What annotations exist?
         * What superclass exists?
         * ```
         * 
         * ---
         * 
         * # Therefore We Used
         * 
         * ```java id="’wini2203"
         * clazz.getDeclaredMethods()
         * ```
         * 
         * to literally inspect:
         * 
         * compiled JVM class structure
         * 
         * ---
         * 
         * # Without Reflection
         * 
         * You only see:
         * 
         * source code illusion
         * 
         * With reflection:
         * 
         * you see compiler-generated reality
         * 
         * ---
         * 
         * # Reflection Revealed
         * 
         * Compiler secretly inserted:
         * 
         * ```txt id="’wini2204"
         * process(Object) [bridge]
         * ```
         * 
         * That was the entire point.
         * 
         * ---
         * 
         * # 2. Why Reflection Syntax Looks Weird
         * 
         * You said:
         * 
         * ```txt id="’wini2205"
         * this Reflection syntax quite stupid looking
         * ```
         * 
         * Honestly:
         * 
         * many developers agree.
         * 
         * Because Java reflection API exposes:
         * 
         * JVM internals almost directly
         * 
         * instead of giving:
         * 
         * ergonomic abstractions
         * 
         * ---
         * 
         * # Example
         * 
         * ```java id="’wini2206"
         * Class<?> clazz =
         * StringProcessor.class;
         * ```
         * 
         * looks weird because:
         * 
         * Java type system old
         * reflection API old
         * generics added later
         * 
         * ---
         * 
         * # This Part Especially
         * 
         * ```java id="’wini2207"
         * for (Method method :
         * clazz.getDeclaredMethods())
         * ```
         * 
         * feels verbose because:
         * 
         * reflection APIs designed around metadata objects
         * 
         * Everything becomes:
         * 
         * `Class`
         * `Method`
         * `Field`
         * `Constructor`
         * 
         * objects.
         * 
         * ---
         * 
         * # Java Reflection API Basically Mirrors JVM Metadata Tables
         * 
         * Meaning:
         * 
         * ```txt id="’wini2208"
         * reflection API
         * ≈
         * object-oriented wrapper over JVM class metadata
         * ```
         * 
         * That’s why it feels:
         * 
         * low-level
         * mechanical
         * descriptor-heavy
         * 
         * ---
         * 
         * # Why `Class<?>` Looks Ugly
         * 
         * This:
         * 
         * ```java id="’wini2209"
         * Class<?>
         * ```
         * 
         * means:
         * 
         * ```txt id="’wini2210"
         * Class of unknown type
         * ```
         * 
         * because:
         * 
         * reflection APIs heavily genericized later
         * 
         * Originally pre-generics Java had:
         * 
         * ```java id="’wini2211"
         * Class
         * ```
         * 
         * only.
         * 
         * Later generics retrofitted awkwardly.
         * 
         * ---
         * 
         * # Why `.class` Syntax Exists
         * 
         * ```java id="’wini2212"
         * StringProcessor.class
         * ```
         * 
         * means:
         * 
         * ```txt id="’wini2213"
         * give JVM metadata object
         * for this type
         * ```
         * 
         * Equivalent mental model:
         * 
         * ```txt id="’wini2214"
         * type token
         * ```
         * 
         * ---
         * 
         * # Reflection Is VERY Runtime-Oriented
         * 
         * You’re basically querying JVM:
         * 
         * ```txt id="’wini2215"
         * "Hey JVM,
         * show me actual compiled class structure."
         * ```
         * 
         * ---
         * 
         * # Why Reflection Important In Real World
         * 
         * Frameworks use reflection heavily for:
         * 
         * dependency injection
         * annotations
         * route scanning
         * ORM mapping
         * serialization
         * validation
         * plugins
         * 
         * ---
         * 
         * # Example
         * 
         * Spring scans:
         * 
         * ```java id="’wini2216"
         * 
         * @Controller
         * @Service
         * @Repository
         *             ```
         * 
         *             using reflection.
         * 
         *             ---
         * 
         *             # Hibernate inspects:
         * 
         *             ```java id="’wini2217"
         * @Entity
         * @Column
         *         ```
         * 
         *         using reflection.
         * 
         *         ---
         * 
         *         # Jackson inspects:
         * 
         *         ```java id="’wini2218"
         *         fields/getters/setters
         *         ```
         * 
         *         using reflection.
         * 
         *         ---
         * 
         *         # Why Bridge Methods Matter There
         * 
         *         Suppose framework scans methods:
         * 
         *         ```java id="’wini2219"
         *         clazz.getDeclaredMethods()
         *         ```
         * 
         *         Without understanding bridge methods:
         * 
         *         duplicate methods appear
         *         generic overrides confusing
         *         dispatch bugs happen
         * 
         *         So frameworks often do:
         * 
         *         ```java id="’wini2220"
         *         if (!method.isBridge())
         *         ```
         * 
         *         ---
         * 
         *         # Reflection Is Basically Java’s Runtime Introspection System
         * 
         *         Equivalent concepts in other languages:
         * 
         *         | Language | Similar System |
         *         | -------- | ----------------------------- |
         *         | Java | Reflection |
         *         | C# | Reflection |
         *         | Python | introspection |
         *         | JS | prototype/meta inspection |
         *         | Rust | limited/no runtime reflection |
         *         | Go | reflect package |
         * 
         *         ---
         * 
         *         # Why Java Reflection Feels More “Heavy”
         * 
         *         Because Java designed around:
         * 
         *         strong encapsulation
         *         explicit metadata objects
         *         JVM abstraction layer
         * 
         *         instead of dynamic scripting ergonomics.
         * 
         *         ---
         * 
         *         # Compare JS
         * 
         *         JS reflection-ish code:
         * 
         *         ```js id="’wini2221"
         *         Object.keys(obj)
         *         ```
         * 
         *         feels lightweight.
         * 
         *         ---
         * 
         *         # Java Equivalent
         * 
         *         ```java id="’wini2222"
         *         clazz.getDeclaredMethods()
         *         ```
         * 
         *         feels heavyweight because:
         * 
         *         JVM metadata richer
         *         stronger type model
         *         security/access rules
         *         static typing constraints
         * 
         *         ---
         * 
         *         # Deepest Realization
         * 
         *         Reflection APIs expose:
         * 
         *         # compiled program structure as runtime data
         * 
         *         That’s why syntax feels unusual.
         * 
         *         You are no longer:
         * 
         *         “calling methods normally”
         * 
         *         You are:
         * 
         *         inspecting code structure itself
         * 
         *         almost like a runtime AST/meta-model.
         * 
         *         ---
         * 
         *         # Final Clarification
         * 
         *         We used reflection specifically because:
         * 
         *         # bridge methods are invisible compiler-generated runtime artifacts
         * 
         *         and reflection lets us inspect:
         * 
         *         actual compiled JVM class structure
         * 
         *         instead of merely:
         * 
         *         source-code illusion.
         * 
         *         And yes — Java reflection syntax often feels verbose because it
         *         exposes:
         * 
         *         low-level JVM metadata abstractions
         *         rather than ergonomic high-level APIs.
         * 
         */

        Class<?> clazz = StringProcessor.class; // runtime JVM metadata object
        System.out.println("Methods in StringProcessor:");

        // L7 Mastery: Reflecting on the generated bytecode
        for (Method method : clazz.getDeclaredMethods()) { // returns developer methods and compiler-generated methods
            System.out.println(" - Name: " + method.getName() + " | Bridge: " + method.isBridge());
        }

        System.out.println(
                "\nL5 Insight: The compiler creates a 'process(Object)' bridge method to satisfy the interface contract.");
        System.out
                .println("L7 Tip: Knowing about bridge methods is critical for debugging reflection-heavy frameworks.");
    }
}

/**
 * If generic types are erased during compilation, then how does subclass knows
 * the correct type? Well it is done via bridge methods implicity during
 * compilation
 * 
 * YES — exactly. You’ve now hit the MOST important mental shift about Java
 * generics:
 * 
 * # Java generics are mostly compile-time sugar
 * 
 * and compiler secretly inserts:
 * 
 * erasure
 * casts
 * bridge methods
 * 
 * to preserve type safety + polymorphism.
 * 
 * And yes — the bridge method LOOKS like weird recursion at first glance, but
 * it’s NOT recursion.
 * 
 * Let’s deeply untangle this carefully.
 * 
 * ---
 * 
 * # Start With Original Source Code
 * 
 * ---
 * 
 * # Interface
 * 
 * ```java id="jlwm2000"
 * interface Processor<T> {
 * void process(T data);
 * }
 * ```
 * 
 * ---
 * 
 * # Subclass
 * 
 * ```java id="’wini2001"
 * class StringProcessor
 * implements Processor<String> {
 * 
 * @Override
 *           public void process(String data) {
 *           System.out.println(data);
 *           }
 * 
 *           }
 *           ```
 * 
 *           Looks perfectly normal.
 * 
 *           ---
 * 
 *           # Step 1 — Type Erasure Happens
 * 
 *           Java compiler erases generics.
 * 
 *           ---
 * 
 *           # Interface Becomes Roughly
 * 
 *           ```java id="’wini2002"
 *           interface Processor {
 *           void process(Object data);
 *           }
 *           ```
 * 
 *           because:
 * 
 *           T erased to Object
 * 
 *           ---
 * 
 *           # BUT Subclass Still Has
 * 
 *           ```java id="’wini2003"
 *           void process(String data)
 *           ```
 * 
 *           NOT:
 * 
 *           ```java id="’wini2004"
 *           void process(Object data)
 *           ```
 * 
 *           ---
 * 
 *           # HUGE PROBLEM
 * 
 *           Now JVM sees:
 * 
 *           ---
 * 
 *           # Interface Contract
 * 
 *           ```java id="’wini2005"
 *           process(Object)
 *           ```
 * 
 *           ---
 * 
 *           # Class Method
 * 
 *           ```java id="’wini2006"
 *           process(String)
 *           ```
 * 
 *           ---
 * 
 *           # These Are DIFFERENT Methods
 * 
 *           At JVM level:
 * 
 *           ```txt id="’wini2007"
 *           process(Object)
 *           ≠
 *           process(String)
 *           ```
 * 
 *           No override relationship anymore.
 * 
 *           ---
 * 
 *           # Therefore Polymorphism Breaks
 * 
 *           Example:
 * 
 *           ```java id="’wini2008"
 *           Processor p =
 *           new StringProcessor();
 * 
 *           p.process("hello");
 *           ```
 * 
 *           JVM tries calling:
 * 
 *           ```txt id="’wini2009"
 *           process(Object)
 *           ```
 * 
 *           because erased interface only knows:
 * 
 *           Object
 * 
 *           BUT class only has:
 * 
 *           process(String)
 * 
 *           Without bridge methods:
 * 
 *           method resolution fails
 * 
 *           ---
 * 
 *           # Step 2 — Compiler Inserts Bridge Method
 * 
 *           Compiler secretly generates:
 * 
 *           ```java id="’wini2010"
 *           public void process(Object data) {
 * 
 *           process((String) data);
 * 
 *           }
 *           ```
 * 
 *           THIS is the bridge method.
 * 
 *           ---
 * 
 *           # IMPORTANT
 * 
 *           This is NOT recursion.
 * 
 *           Because:
 * 
 *           ---
 * 
 *           # Bridge Method
 * 
 *           ```java id="’wini2011"
 *           process(Object)
 *           ```
 * 
 *           calls:
 * 
 *           ---
 * 
 *           # Real Method
 * 
 *           ```java id="’wini2012"
 *           process(String)
 *           ```
 * 
 *           Different signatures.
 * 
 *           Different JVM methods.
 * 
 *           ---
 * 
 *           # JVM Sees Them As Separate
 * 
 *           ---
 * 
 *           # Method 1
 * 
 *           ```txt id="’wini2013"
 *           process(Ljava/lang/Object;)V
 *           ```
 * 
 *           ---
 * 
 *           # Method 2
 * 
 *           ```txt id="’wini2014"
 *           process(Ljava/lang/String;)V
 *           ```
 * 
 *           Totally distinct bytecode signatures.
 * 
 *           ---
 * 
 *           # So Why Cast?
 * 
 *           Because erased interface only knows:
 * 
 *           ```txt id="’wini2015"
 *           Object
 *           ```
 * 
 *           But actual implementation expects:
 * 
 *           ```txt id="’wini2016"
 *           String
 *           ```
 * 
 *           Therefore bridge method adapts:
 * 
 *           ```txt id="’wini2017"
 *           Object
 *           ↓ cast
 *           String
 *           ↓
 *           real method
 *           ```
 * 
 *           ---
 * 
 *           # THIS Is The Key
 * 
 *           Bridge method is basically:
 * 
 *           # adapter middleware
 * 
 *           ---
 * 
 *           # Visual Flow
 * 
 *           ---
 * 
 *           # Source-Level
 * 
 *           ```txt id="’wini2018"
 *           Processor<String>
 *           ```
 * 
 *           ---
 * 
 *           # Erased JVM-Level
 * 
 *           ```txt id="’wini2019"
 *           Processor<Object>
 *           ```
 * 
 *           ---
 * 
 *           # Bridge Method Adapts
 * 
 *           ```txt id="’wini2020"
 *           process(Object)
 *           ->
 *           cast to String
 *           ->
 *           process(String)
 *           ```
 * 
 *           ---
 * 
 *           # Why Cast Is Safe?
 * 
 *           Because compile-time type checking already guaranteed:
 * 
 *           ```txt id="’wini2021"
 *           T = String
 *           ```
 * 
 *           Compiler trusts type system.
 * 
 *           ---
 * 
 *           # UNLESS Raw Types Used
 * 
 *           Example:
 * 
 *           ```java id="’wini2022"
 *           Processor p =
 *           new StringProcessor();
 * 
 *           p.process(123);
 *           ```
 * 
 *           Now:
 * 
 *           ```txt id="’wini2023"
 *           Integer cast to String
 *           ```
 * 
 *           fails at runtime:
 * 
 *           ```txt id="’wini2024"
 *           ClassCastException
 *           ```
 * 
 *           ---
 * 
 *           # THIS Is Why Raw Types Dangerous
 * 
 *           They bypass generic safety.
 * 
 *           ---
 * 
 *           # The "Weird Recursion" Illusion
 * 
 *           You saw:
 * 
 *           ```java id="’wini2025"
 *           process(Object) {
 *           process((String)data)
 *           }
 *           ```
 * 
 *           and thought:
 * 
 *           ```txt id="’wini2026"
 *           process calling itself?
 *           ```
 * 
 *           ---
 * 
 *           # But JVM Overloading Rules Apply
 * 
 *           Compiler resolves:
 * 
 *           ```java id="’wini2027"
 *           process((String)data)
 *           ```
 * 
 *           to:
 * 
 *           ```java id="’wini2028"
 *           process(String)
 *           ```
 * 
 *           NOT:
 * 
 *           process(Object)
 * 
 *           because:
 * 
 *           most specific overload chosen
 * 
 *           ---
 * 
 *           # So NOT Recursive
 * 
 *           ---
 * 
 *           # Real Call Chain
 * 
 *           ```txt id="’wini2029"
 *           process(Object)
 *           ↓
 *           process(String)
 *           ```
 * 
 *           end.
 * 
 *           ---
 * 
 *           # EXACT Same Thing Happens In Covariant Returns
 * 
 *           Example:
 * 
 *           ---
 * 
 *           # Parent
 * 
 *           ```java id="’wini2030"
 *           class Parent {
 *           Object getValue()
 *           }
 *           ```
 * 
 *           ---
 * 
 *           # Child
 * 
 *           ```java id="’wini2031"
 *           class Child extends Parent {
 *           String getValue()
 *           }
 *           ```
 * 
 *           Compiler may generate bridge:
 * 
 *           ```java id="’wini2032"
 *           Object getValue() {
 *           return getValue();
 *           }
 *           ```
 * 
 *           Again looks recursive.
 * 
 *           But actually means:
 * 
 *           ```txt id="’wini2033"
 *           bridge getValue():Object
 *           ->
 *           real getValue():String
 *           ```
 * 
 *           Different signatures.
 * 
 *           ---
 * 
 *           # Deep JVM Insight
 * 
 *           Java source language allows:
 * 
 *           covariance
 *           generics
 *           fluent overriding
 * 
 *           BUT JVM bytecode fundamentally works using:
 * 
 *           exact method descriptors/signatures
 * 
 *           Bridge methods glue these worlds together.
 * 
 *           ---
 * 
 *           # VERY Important Mental Model
 * 
 *           ---
 * 
 *           # Java Source Code
 * 
 *           High-level illusion:
 * 
 *           generics
 *           covariance
 *           subtype polymorphism
 * 
 *           ---
 * 
 *           # JVM Bytecode
 * 
 *           Actually works with:
 * 
 *           erased types
 *           exact signatures
 *           Object-based compatibility
 * 
 *           ---
 * 
 *           # Bridge Methods
 * 
 *           Compiler-generated compatibility shims.
 * 
 *           ---
 * 
 *           # Another Good Mental Analogy
 * 
 *           Think of bridge method as:
 * 
 *           ```txt id="’wini2034"
 *           network protocol adapter
 *           ```
 * 
 *           It converts:
 * 
 *           erased generic contract
 *           into
 *           actual concrete implementation
 * 
 *           ---
 * 
 *           # Final Deep Realization
 * 
 *           Java compiler essentially performs:
 * 
 *           ```txt id="’wini2035"
 *           1. erase generics
 *           2. preserve polymorphism
 *           3. preserve overriding
 *           4. insert casts
 *           5. synthesize bridge adapters
 *           ```
 * 
 *           so source-level generic inheritance still works correctly on the
 *           older JVM object model.
 * 
 * 
 * 
 * 
 * 
 */