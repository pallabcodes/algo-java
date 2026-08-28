package com.backend.designpatterns.realworld.notifications;

/**
 * [2/5] Bridge pattern — separates the ABSTRACTION (channel: email/sms/push) from
 * the IMPLEMENTATION (provider: SendGrid/Twilio/Firebase). Both can vary
 * independently. 3 channels × 3 providers = 9 combos from 6 classes (3+3).
 *
 * Without Bridge: 9 separate classes (EmailSendGrid, EmailTwilio,
 * EmailFirebase, SmsSendGrid...). Adding a new channel or provider means
 * editing existing classes. With Bridge, adding a provider = inheriting
 * NotificationSender. Adding a channel = subclassing Notification.
 * Neither affects the other hierarchy.
 */
public abstract class Notification {
    protected final NotificationSender sender;

    protected Notification(NotificationSender sender) { this.sender = sender; }

    public abstract boolean notify(String recipient, String message);
    public abstract String channel();
    public String provider() { return sender.providerName(); }

    static class Email extends Notification {
        public Email(NotificationSender sender) { super(sender); }
        public boolean notify(String to, String msg) {
            System.out.print("[Bridge:Email/" + sender.providerName() + "] ");
            return sender.send(to, "[EMAIL] " + msg);
        }
        public String channel() { return "email"; }
    }

    static class SMS extends Notification {
        public SMS(NotificationSender sender) { super(sender); }
        public boolean notify(String to, String msg) {
            System.out.print("[Bridge:SMS/" + sender.providerName() + "] ");
            return sender.send(to, msg);
        }
        public String channel() { return "sms"; }
    }

    static class Push extends Notification {
        public Push(NotificationSender sender) { super(sender); }
        public boolean notify(String to, String msg) {
            System.out.print("[Bridge:Push/" + sender.providerName() + "] ");
            return sender.send(to, msg);
        }
        public String channel() { return "push"; }
    }
}
