package com.narxoz.rpg.combatant;

import com.narxoz.rpg.observer.EventManager;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;
import com.narxoz.rpg.strategy.BossPhaseOneStrategy;
import com.narxoz.rpg.strategy.BossPhaseThreeStrategy;
import com.narxoz.rpg.strategy.BossPhaseTwoStrategy;
import com.narxoz.rpg.strategy.CombatStrategy;

/**
 * Represents the dungeon boss.
 * The boss is both:
 * 1) a publisher of boss-related events
 * 2) an observer that reacts to BOSS_PHASE_CHANGED
 */
public class DungeonBoss implements GameObserver {

    private final String name;
    private int hp;
    private final int maxHp;
    private final int attackPower;
    private final int defense;

    private int currentPhase;
    private CombatStrategy strategy;

    private final CombatStrategy phaseOneStrategy;
    private final CombatStrategy phaseTwoStrategy;
    private final CombatStrategy phaseThreeStrategy;

    private final EventManager eventManager;

    public DungeonBoss(String name, int hp, int attackPower, int defense, EventManager eventManager) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.eventManager = eventManager;

        this.phaseOneStrategy = new BossPhaseOneStrategy();
        this.phaseTwoStrategy = new BossPhaseTwoStrategy();
        this.phaseThreeStrategy = new BossPhaseThreeStrategy();

        this.currentPhase = 1;
        this.strategy = phaseOneStrategy;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getDefense() {
        return defense;
    }

    public int getCurrentPhase() {
        return currentPhase;
    }

    public CombatStrategy getStrategy() {
        return strategy;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public int attack() {
        return strategy.calculateDamage(attackPower);
    }

    public int defend() {
        return strategy.calculateDefense(defense);
    }

    public void registerObserver(GameObserver observer) {
        eventManager.subscribe(observer);
    }

    public void fireEvent(GameEvent event) {
        eventManager.notifyObservers(event);
    }

    public void takeDamage(int amount) {
        if (amount < 0) {
            return;
        }

        int oldHp = hp;
        hp = Math.max(0, hp - amount);

        double oldPercent = (double) oldHp / maxHp;
        double newPercent = (double) hp / maxHp;

        if (oldPercent > 0.60 && newPercent <= 0.60) {
            fireEvent(new GameEvent(GameEventType.BOSS_PHASE_CHANGED, name, 2));
        }

        if (oldPercent > 0.30 && newPercent <= 0.30) {
            fireEvent(new GameEvent(GameEventType.BOSS_PHASE_CHANGED, name, 3));
        }
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() != GameEventType.BOSS_PHASE_CHANGED) {
            return;
        }

        int newPhase = event.getValue();

        if (newPhase == 2 && currentPhase < 2) {
            currentPhase = 2;
            strategy = phaseTwoStrategy;
            System.out.println("[DungeonBoss] " + name + " switched strategy to: " + strategy.getName());
        } else if (newPhase == 3 && currentPhase < 3) {
            currentPhase = 3;
            strategy = phaseThreeStrategy;
            System.out.println("[DungeonBoss] " + name + " switched strategy to: " + strategy.getName());
        }
    }
}