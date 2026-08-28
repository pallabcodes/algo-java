package com.backend.designpatterns.realworld.notifications;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * [3/5] Factory pattern — creates and caches channel+provider combinations.
 * Key format: "channel:provider" (e.g., "email:sendgrid").
 * Without Factory: callers must know both the channel and provider classes
 * to construct a notification — violates the "program to an interface" principle.
 */
public class NotificationFactory {
    private final Map<String, Notification> cache = new ConcurrentHashMap<>();

    public Notification create(String channel, String provider) {
        String key = channel + ":" + provider;
        return cache.computeIfAbsent(key, k -> {
            NotificationSender sender = createSender(provider);
            return createNotification(channel, sender);
        });
    }

    private NotificationSender createSender(String provider) {
        return switch (provider) {
            case "sendgrid" -> new NotificationSender.SendGrid();
            case "twilio" -> new NotificationSender.Twilio();
            case "firebase" -> new NotificationSender.FirebasePush();
            default -> throw new IllegalArgumentException("unknown provider: " + provider);
        };
    }

    private Notification createNotification(String channel, NotificationSender sender) {
        return switch (channel) {
            case "email" -> new Notification.Email(sender);
            case "sms" -> new Notification.SMS(sender);
            case "push" -> new Notification.Push(sender);
            default -> throw new IllegalArgumentException("unknown channel: " + channel);
        };
    }

    public static NotificationFactory defaultFactory() {
        return new NotificationFactory();
    }
}
