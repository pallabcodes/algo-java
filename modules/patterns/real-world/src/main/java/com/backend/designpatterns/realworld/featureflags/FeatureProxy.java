package com.backend.designpatterns.realworld.featureflags;

import java.util.function.Supplier;

/**
 * [5/6] Proxy pattern — wraps a feature behind a flag check. Provides one implementation
 * when the flag is enabled, another when disabled. The caller doesn't know about
 * flags — it calls execute() and gets a result regardless.
 *
 * Without this, every feature behind a flag needs if/else in the caller code.
 * Alternative rejected: scattering flag checks across controllers (violates DRY,
 * makes it easy to forget the check). With Proxy, features are toggled centrally.
 * Composes with FlagRegistry to resolve flag state.
 */
public class FeatureProxy<T> {
    private final String flagName;
    private final FlagRegistry registry;
    private final Supplier<T> enabledImpl;
    private final Supplier<T> disabledImpl;
    private final boolean isToggle;

    public FeatureProxy(String flagName, FlagRegistry registry,
                        Supplier<T> enabledImpl, Supplier<T> disabledImpl) {
        this(flagName, registry, enabledImpl, disabledImpl, false);
    }

    public FeatureProxy(String flagName, FlagRegistry registry,
                        Supplier<T> enabledImpl, Supplier<T> disabledImpl,
                        boolean isToggle) {
        this.flagName = flagName;
        this.registry = registry;
        this.enabledImpl = enabledImpl;
        this.disabledImpl = disabledImpl;
        this.isToggle = isToggle;
    }

    public T execute(UserContext user) {
        boolean enabled = registry.isEnabled(flagName, user);
        System.out.println("[FeatureProxy:" + flagName + "] user=" + user.userId()
            + " region=" + user.region() + " → " + (enabled ? "ENABLED" : "DISABLED"));
        return enabled ? enabledImpl.get() : disabledImpl.get();
    }

    public boolean isToggle() { return isToggle; }
}
