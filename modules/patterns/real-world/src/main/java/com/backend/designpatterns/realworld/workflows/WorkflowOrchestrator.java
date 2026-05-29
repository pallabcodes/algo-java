package com.backend.designpatterns.realworld.workflows;

import java.util.ArrayList;
import java.util.List;

public class WorkflowOrchestrator {
    private final String workflowId;
    private final WorkflowMemento memento = new WorkflowMemento();
    private final WorkflowObserver.EventBus eventBus;
    private final List<WorkflowCommand> steps = new ArrayList<>();

    public WorkflowOrchestrator(String workflowId, WorkflowObserver.EventBus eventBus) {
        this.workflowId = workflowId;
        this.eventBus = eventBus;
    }

    public WorkflowOrchestrator then(WorkflowCommand cmd) {
        steps.add(cmd);
        return this;
    }

    public boolean execute() {
        System.out.println("\n[Orchestrator] executing " + steps.size() + " step(s) for " + workflowId);
        for (WorkflowCommand cmd : steps) {
            WorkflowCommand.Result r = cmd.execute();
            eventBus.publish(workflowId, cmd.getClass().getSimpleName(), r.success(), r.message());
            if (!r.success()) {
                System.out.println("[Orchestrator] FAILED at " + cmd.getClass().getSimpleName() + ": " + r.message());
                memento.record(cmd);
                memento.rollback();
                return false;
            }
            memento.record(cmd);
        }
        System.out.println("[Orchestrator] ALL STEPS COMPLETED for " + workflowId);
        return true;
    }
}
