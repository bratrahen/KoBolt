package com.kobot.framework.entitysystem.components;

public class HealthComponent implements Component{
    public int currentHealth;
    public int maxHealth;
    public boolean isAlive;

    public HealthComponent(int currentHealth, int maxHealth) {
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
        this.isAlive = true;
    }
}
