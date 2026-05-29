package com.backend.designpatterns.realworld.notifications;

public interface NotificationSender {
    String providerName();
    boolean send(String recipient, String message);

    record SendGrid() implements NotificationSender {
        public String providerName() { return "SendGrid"; }
        public boolean send(String to, String msg) {
            System.out.println("[SendGrid] email to " + to + ": " + msg.substring(0, Math.min(msg.length(), 30)) + "...");
            return true;
        }
    }

    record Twilio() implements NotificationSender {
        public String providerName() { return "Twilio"; }
        public boolean send(String to, String msg) {
            System.out.println("[Twilio] SMS to " + to + ": " + msg);
            return true;
        }
    }

    record FirebasePush() implements NotificationSender {
        public String providerName() { return "Firebase"; }
        public boolean send(String to, String msg) {
            System.out.println("[Firebase] push notification to device " + to + ": " + msg);
            return true;
        }
    }
}
