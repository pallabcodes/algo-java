package com.backend.designpatterns.realworld.workflows;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * [2/5] Memento pattern — maintains a stack of executed Commands for rollback.
 * On failure, pops each command and calls undo() in reverse order.
 *
 * Without Memento, the workflow has no memory of what was executed, making
 * partial-failure recovery impossible. Composes with WorkflowCommand (Command)
 * for the actions and WorkflowOrchestrator for the choreography.
 */
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
