package com.backend.designpatterns.realworld.featureflags;

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
