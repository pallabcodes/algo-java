package com.backend.designpatterns.realworld.workflows;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@FunctionalInterface
public interface WorkflowObserver {
    void onStep(String workflowId, String step, boolean success, String detail);

    static WorkflowObserver logger() {
        return (wf, step, ok, detail) ->
            System.out.println("[Observer:Log] " + wf + " | " + step + " | " + (ok ? "OK" : "FAIL") + " | " + detail);
    }

    static WorkflowObserver metrics() {
        return (wf, step, ok, detail) ->
            System.out.println("[Observer:Metrics] increment workflow." + wf + "." + step);
    }

    static WorkflowObserver alertOnFailure() {
        return (wf, step, ok, detail) -> {
            if (!ok) System.out.println("[Observer:Alert] PAGING: " + wf + " failed at " + step);
        };
    }

    class EventBus {
        private final List<WorkflowObserver> observers = new CopyOnWriteArrayList<>();

        public EventBus subscribe(WorkflowObserver o) { observers.add(o); return this; }
        public void publish(String wf, String step, boolean ok, String detail) {
            observers.forEach(o -> o.onStep(wf, step, ok, detail));
        }

        public static EventBus defaultBus() {
            return new EventBus()
                .subscribe(logger())
                .subscribe(metrics())
                .subscribe(alertOnFailure());
        }
    }
}
