package com.backend.designpatterns.realworld.notifications;

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
