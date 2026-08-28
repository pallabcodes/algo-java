package com.backend.designpatterns.realworld.notifications;

/**
 * [1/5] Implementation side of Bridge pattern — provider abstraction.
 * Each provider (SendGrid, Twilio, FirebasePush) implements this interface.
 * Without Bridge: channels and providers are coupled into N×M classes.
 * With Bridge: 3 channels + 3 providers = 6 classes, composable at runtime.
 */
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
