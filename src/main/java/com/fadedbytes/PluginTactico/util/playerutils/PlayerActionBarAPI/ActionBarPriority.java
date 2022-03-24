package com.fadedbytes.PluginTactico.util.playerutils.PlayerActionBarAPI;

import org.apache.http.annotation.Contract;

/**
 * Defines the priority of the actionbar.
 */
public enum ActionBarPriority {

    /**
     * Ambient events, such as time, weather...
     */
    LOWEST(100),
    /**
     * Unimportant events, such as regular entities spawning near
     */
    LOWER(200),
    /**
     * Low importance events, such as informative announcements.
     */
    LOW(300),
    /**
     * Default. Regular events.
     */
    MEDIUM(400),
    /**
     * Important events, such as alarms.
     */
    HIGH(500),
    /**
     * Very important events, such as a boss spawning near
     */
    HIGHER(600),
    /**
     * Critical events, such as health-related events
     */
    HIGHEST(700),
    /**
     * Debug events. Use this priority when developing a feature so the actionbar appears always.
     */
    DEBUG(999);

    private final int PRIORITY;

    ActionBarPriority(int priority) {
        this.PRIORITY = priority;
    }

    int getPriority() {
        return this.PRIORITY;
    }

}
