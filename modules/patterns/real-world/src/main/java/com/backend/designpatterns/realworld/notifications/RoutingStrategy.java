package com.backend.designpatterns.realworld.notifications;

import java.util.List;

public interface RoutingStrategy {
    String selectChannel(String userId, String messageType, List<String> channels);

    record PriorityOrder(List<String> preference) implements RoutingStrategy {
        public String selectChannel(String uid, String type, List<String> available) {
            for (String pref : preference) {
                if (available.contains(pref)) {
                    System.out.println("[Strategy:Priority] " + uid + " -> " + pref);
                    return pref;
                }
            }
            String fallback = available.getFirst();
            System.out.println("[Strategy:Priority] no preference match, fallback to " + fallback);
            return fallback;
        }
    }

    record ByMessageType() implements RoutingStrategy {
        public String selectChannel(String uid, String type, List<String> available) {
            return switch (type) {
                case "urgent" -> "sms";
                case "promotional" -> "email";
                case "alert" -> "push";
                default -> available.getFirst();
            };
        }
    }
}
