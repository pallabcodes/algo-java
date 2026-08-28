package com.backend.designpatterns.realworld.notifications;

import java.util.List;

/**
 * [5/5] Demonstrates Bridge + Factory + Strategy composition for notifications.
 * Shows: 3 channels × 3 providers = 9 combos from 6 classes via Bridge,
 * Factory creating any combo, Strategy selecting channel at runtime.
 *
 * Without Bridge: 9 classes, combinatorial explosion at 5×4 = 20.
 */
public class BridgeDemo {

    public static void main(String[] args) {
        System.out.println("""
            === L6 PATTERN COMPOSITION: Notification Bridge ===

            Patterns combined:  Bridge + Factory + Strategy

            Bridge separates ABSTRACTION (email/sms/push) from IMPLEMENTATION (SendGrid/Twilio/Firebase).
            Both can vary independently. Factory creates the right combo.
            Strategy selects channel per user/message-type.

            2 channels × 3 providers × 2 strategies = 12 combos without class explosion.
            """);

        var factory = NotificationFactory.defaultFactory();
        var priorityStrategy = new RoutingStrategy.PriorityOrder(List.of("sms", "push", "email"));
        var typeStrategy = new RoutingStrategy.ByMessageType();

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO A: Bridge — Email via SendGrid + SMS via Twilio");
        System.out.println("=".repeat(70));

        Notification emailViaSendGrid = factory.create("email", "sendgrid");
        Notification smsViaTwilio = factory.create("sms", "twilio");

        emailViaSendGrid.notify("user@example.com", "Your order has shipped!");
        smsViaTwilio.notify("+1-555-0123", "Package delivered.");

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO B: Bridge — all channels via Firebase");
        System.out.println("=".repeat(70));

        Notification emailViaFirebase = factory.create("email", "firebase");
        Notification pushViaFirebase = factory.create("push", "firebase");

        emailViaFirebase.notify("user@example.com", "Weekly digest available");
        pushViaFirebase.notify("device-token-abc", "You have 3 new messages");

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO C: Strategy — route urgent message to SMS");
        System.out.println("=".repeat(70));

        String channel = typeStrategy.selectChannel("user_42", "urgent", List.of("email", "sms", "push"));
        Notification n = factory.create(channel, "twilio");
        n.notify("+1-555-9999", "URGENT: Account security alert");

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO D: Strategy + Bridge — priority routing over factory-created instances");
        System.out.println("=".repeat(70));

        String prefChannel = priorityStrategy.selectChannel("vip_user", "promotional", List.of("email", "push"));
        Notification prefNotif = factory.create(prefChannel, "sendgrid");
        prefNotif.notify("vip@example.com", "Exclusive 20% off for VIP members!");

        System.out.println("\n" + "=".repeat(70));
        System.out.println("L6 TAKEAWAY: 3 patterns, 2D abstraction without class explosion.");
        System.out.println("  Bridge   → channel abstraction × provider implementation vary independently");
        System.out.println("  Factory  → creates any combo lazily, cached per combo key");
        System.out.println("  Strategy → selects channel at runtime based on context");
        System.out.println("  Without Bridge: 3 channels × 3 providers = 9 classes (EmailSendGrid, EmailTwilio...)");
        System.out.println("  With Bridge:    3 + 3 = 6 classes, composable at runtime");
        System.out.println("=".repeat(70));
    }
}
