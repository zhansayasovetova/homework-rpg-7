package com.narxoz.rpg.combatant;

import com.narxoz.rpg.strategy.CombatStrategy;

/**
 * Represents a player-controlled hero participating in the dungeon encounter.
 */
public class Hero {

    private final String name;
    private int hp;
    private final int maxHp;
    private final int attackPower;
    private final int defense;

    // Strategy pattern
    private CombatStrategy strategy;

    public Hero(String name, int hp, int attackPower, int defense, CombatStrategy strategy) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.strategy = strategy; // ❗ always not null
    }

    public String getName()        { return name; }
    public int getHp()             { return hp; }
    public int getMaxHp()          { return maxHp; }
    public int getAttackPower()    { return attackPower; }
    public int getDefense()        { return defense; }
    public boolean isAlive()       { return hp > 0; }

    public CombatStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(CombatStrategy strategy) {
        this.strategy = strategy;
    }

    // Attack using strategy
    public int attack() {
        return strategy.calculateDamage(attackPower);
    }

    // Defense using strategy
    public int defend() {
        return strategy.calculateDefense(defense);
    }

    /**
     * Reduces this hero's HP by the given amount, clamped to zero.
     */
    public void takeDamage(int amount) {
        hp = Math.max(0, hp - amount);
    }

    /**
     * Restores this hero's HP by the given amount, clamped to maxHp.
     */
    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount);
    }
}