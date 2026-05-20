package com.backend.generics;

import java.util.ArrayList;
import java.util.List;

/**
 * Step 01: Variance Mastery (PECS Principle)
 * 
 * L7 Principles:
 * 1. Covariance (extends): 'Producer Extends' - Used for reading from a
 * collection.
 * 2. Contravariance (super): 'Consumer Super' - Used for writing into a
 * collection.
 * 3. Invariance: Standard List<T> is invariant; you cannot assign List<Integer>
 * to List<Number>.
 */
public class Step01_VarianceMastery {

    interface Media {
        String getName();
    }

    // This is a nested static class which implements the Media interface
    static class Task implements Media {
        private final String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    /**
     * L7 Mastery: A pipeline that handles variance.
     * 
     * @param source Producer (provides media)
     * @param sink   Consumer (accepts media)
     * 
     * @subtype List <? extends Media> is read-only; List <? super Media> is
     *          write-only. SubType i.e. ' ? ' so here List's item must be a
     *          sub-type of
     *          Media and so it will have what Media has then it can have its own
     *          additional properties and methods. So, here we are List has a
     *          subtype of Media so now the compiler knows every item within List is
     *          a subtype of Media.
     * 
     *          e.g. List<Movie> -> Movie is a type of Media so List<Movie> can be
     *          passed to List<? extends Media>
     * 
     *          Whereas sink can be a List of Media or SuperType of media e.g.
     *          List<Media> , List<Object> since Object is parentType or in java
     *          supertype of Media
     * 
     * 
     *          So, we get the data i.e. source/producer , then gets processed and
     *          put in the sink/consumer. And, in this pipeline we can add other
     *          processing steps in between.
     * 
     */
    public static void processMedia(List<? extends Media> source, List<? super Media> sink) {

        for (Media media : source) {
            System.out.println("Processing: " + media.getName());
            sink.add(media); // Valid because sink is 'super Media'
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Step 01: Variance Mastery (PECS) ===");

        List<Task> sourceTasks = List.of(new Task("L7-Deployment"), new Task("Core-Refactor"));
        List<Object> sinkResults = new ArrayList<>();

        // Covariance allows us to pass List<Task> to List<? extends Media>
        // Contravariance allows us to pass List<Object> to List<? super Media>
        processMedia(sourceTasks, sinkResults);

        System.out.println("Sink Size: " + sinkResults.size());
        System.out.println("\nL5 Insight: List<? extends T> is read-only; List<? super T> is write-only.");
    }
}

/**
 * 
 * 
 * public class Outer {
 * 
 * public class Inner { }
 * 
 * public static class StaticNested { }
 * 
 * public void method () {
 * // non-static methods can instantiate static and non-static nested classes
 * Inner i = new Inner(); // 'this' is the implied enclosing instance
 * StaticNested s = new StaticNested();
 * }
 * 
 * public static void staticMethod () {
 * Inner i = new Inner(); // <-- ERROR! there's no enclosing instance, so cant
 * do this
 * StaticNested s = new StaticNested(); // ok: no enclosing instance needed
 * 
 * // but we can create an Inner if we have an Outer:
 * Outer o = new Outer();
 * Inner oi = o.new Inner(); // ok: 'o' is the enclosing instance
 * }
 * 
 * }
 * 
 * 
 * 
 */