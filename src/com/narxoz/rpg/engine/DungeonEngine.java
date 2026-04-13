package com.narxoz.rpg.engine;

import com.narxoz.rpg.combatant.DungeonBoss;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.observer.EventManager;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.strategy.DefensiveStrategy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Runs the round-based dungeon encounter.
 */
public class DungeonEngine {

    private final List<Hero> heroes;
    private final DungeonBoss boss;
    private final EventManager eventManager;
    private final int maxRounds;

    public DungeonEngine(List<Hero> heroes, DungeonBoss boss, EventManager eventManager) {
        this.heroes = heroes;
        this.boss = boss;
        this.eventManager = eventManager;
        this.maxRounds = 50;
    }

    public EncounterResult runEncounter() {
        int roundsPlayed = 0;
        Set<String> lowHpTriggered = new HashSet<>();
        boolean heroStrategySwitched = false;

        while (boss.isAlive() && hasLivingHeroes() && roundsPlayed < maxRounds) {
            roundsPlayed++;
            System.out.println("\n========== ROUND " + roundsPlayed + " ==========");

            if (!heroStrategySwitched && roundsPlayed == 3) {
                for (Hero hero : heroes) {
                    if (hero.isAlive()) {
                        hero.setStrategy(new DefensiveStrategy());
                        System.out.println("[DungeonEngine] " + hero.getName()
                                + " switched strategy to: " + hero.getStrategy().getName());
                        heroStrategySwitched = true;
                        break;
                    }
                }
            }

            for (Hero hero : heroes) {
                if (!hero.isAlive() || !boss.isAlive()) {
                    continue;
                }

                int rawDamage = hero.attack();
                int blocked = boss.defend();
                int finalDamage = Math.max(1, rawDamage - blocked);

                System.out.println(hero.getName() + " attacks with "
                        + hero.getStrategy().getName()
                        + " strategy and deals " + finalDamage + " damage.");

                boss.takeDamage(finalDamage);

                eventManager.notifyObservers(
                        new GameEvent(GameEventType.ATTACK_LANDED, hero.getName(), finalDamage)
                );

                System.out.println("Boss HP: " + boss.getHp() + "/" + boss.getMaxHp());

                if (!boss.isAlive()) {
                    eventManager.notifyObservers(
                            new GameEvent(GameEventType.BOSS_DEFEATED, boss.getName(), roundsPlayed)
                    );
                    break;
                }
            }

            if (!boss.isAlive()) {
                break;
            }

            for (Hero hero : heroes) {
                if (!hero.isAlive()) {
                    continue;
                }

                int rawDamage = boss.attack();
                int blocked = hero.defend();
                int finalDamage = Math.max(1, rawDamage - blocked);

                System.out.println("Boss attacks " + hero.getName()
                        + " with " + boss.getStrategy().getName()
                        + " strategy and deals " + finalDamage + " damage.");

                hero.takeDamage(finalDamage);

                eventManager.notifyObservers(
                        new GameEvent(GameEventType.ATTACK_LANDED, boss.getName(), finalDamage)
                );

                System.out.println(hero.getName() + " HP: " + hero.getHp() + "/" + hero.getMaxHp());

                if (hero.isAlive() && hero.getHp() <= (int) Math.floor(hero.getMaxHp() * 0.30)
                        && !lowHpTriggered.contains(hero.getName())) {

                    lowHpTriggered.add(hero.getName());
                    eventManager.notifyObservers(
                            new GameEvent(GameEventType.HERO_LOW_HP, hero.getName(), hero.getHp())
                    );
                }

                if (!hero.isAlive()) {
                    eventManager.notifyObservers(
                            new GameEvent(GameEventType.HERO_DIED, hero.getName(), 0)
                    );
                }
            }
        }

        if (roundsPlayed >= maxRounds && boss.isAlive() && hasLivingHeroes()) {
            System.out.println("\n[DungeonEngine] Max rounds reached. Encounter ended in a draw.");
        }

        return new EncounterResult(!boss.isAlive(), roundsPlayed, countLivingHeroes());
    }

    private boolean hasLivingHeroes() {
        for (Hero hero : heroes) {
            if (hero.isAlive()) {
                return true;
            }
        }
        return false;
    }

    private int countLivingHeroes() {
        int count = 0;
        for (Hero hero : heroes) {
            if (hero.isAlive()) {
                count++;
            }
        }
        return count;
    }
}