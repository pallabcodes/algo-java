package com.backend.designpatterns.realworld.workflows;

import java.util.ArrayDeque;
import java.util.Deque;

public class WorkflowMemento {
    private final Deque<WorkflowCommand> executed = new ArrayDeque<>();

    public void record(WorkflowCommand cmd) {
        executed.push(cmd);
    }

    public void rollback() {
        System.out.println("[Memento] ROLLING BACK " + executed.size() + " steps");
        while (!executed.isEmpty()) {
            WorkflowCommand failed = executed.pop();
            failed.undo();
        }
    }

    public int stepsExecuted() { return executed.size(); }
}
