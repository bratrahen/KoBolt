package com.kobot.framework.entitysystem.components;

import com.kobot.framework.entitysystem.components.api.UniqueComponent;

public class HealthComponent implements UniqueComponent {
    public int currentHealth;
    public int maxHealth;
    public boolean isAlive;

    public HealthComponent(int currentHealth, int maxHealth) {
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
        this.isAlive = true;
    }
}
