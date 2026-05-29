package com.backend.designpatterns.realworld.featureflags;

/**
 * Strategy pattern — different ways to evaluate whether a flag is enabled.
 * SimpleFlag checks a condition directly. ExperimentRollout uses an experiment
 * framework with holdout groups. StagedRollout supports phased (alpha/beta/stable)
 * rollouts at different percentages.
 *
 * Without this, flag evaluation is a single method with if/else for each
 * rollout type. With Strategy, adding a new rollout mechanism = new EvaluationStrategy
 * implementation. No existing code changes.
 */
public interface EvaluationStrategy {
    boolean isEnabled(String flagName, UserContext user);

    record SimpleFlag(FlagCondition condition) implements EvaluationStrategy {
        public boolean isEnabled(String flagName, UserContext user) {
            boolean result = condition.evaluate(user);
            System.out.println("[Eval:" + flagName + "] simple check → " + result);
            return result;
        }
    }

    record ExperimentRollout(String experimentId, FlagCondition rollout, FlagCondition holdout) implements EvaluationStrategy {
        public boolean isEnabled(String flagName, UserContext user) {
            if (holdout.evaluate(user)) {
                System.out.println("[Eval:" + flagName + "] user in HOLDOUT group → false");
                return false;
            }
            boolean result = rollout.evaluate(user);
            System.out.println("[Eval:" + flagName + "] experiment " + experimentId + " → " + result);
            return result;
        }
    }

    record StagedRollout(int stablePercent, int betaPercent, int alphaPercent) implements EvaluationStrategy {
        public boolean isEnabled(String flagName, UserContext user) {
            if (user.rolloutBasis() < stablePercent) {
                System.out.println("[Eval:" + flagName + "] STABLE rollout → true");
                return true;
            }
            if (user.rolloutBasis() < stablePercent + betaPercent) {
                boolean inBeta = new FlagCondition.RolloutCondition(betaPercent).evaluate(user);
                System.out.println("[Eval:" + flagName + "] BETA rollout → " + inBeta);
                return inBeta;
            }
            boolean inAlpha = new FlagCondition.RolloutCondition(alphaPercent).evaluate(user);
            System.out.println("[Eval:" + flagName + "] ALPHA rollout → " + inAlpha);
            return inAlpha;
        }
    }
}
