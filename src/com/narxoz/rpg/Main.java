package com.narxoz.rpg;

import com.narxoz.rpg.combatant.DungeonBoss;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.engine.DungeonEngine;
import com.narxoz.rpg.engine.EncounterResult;
import com.narxoz.rpg.observer.AchievementTracker;
import com.narxoz.rpg.observer.BattleLogger;
import com.narxoz.rpg.observer.EventManager;
import com.narxoz.rpg.observer.HeroStatusMonitor;
import com.narxoz.rpg.observer.LootDropper;
import com.narxoz.rpg.observer.PartySupport;
import com.narxoz.rpg.strategy.AggressiveStrategy;
import com.narxoz.rpg.strategy.BalancedStrategy;
import com.narxoz.rpg.strategy.DefensiveStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Entry point for Homework 7 — The Cursed Dungeon: Boss Encounter System.
 *
 * Build your heroes, boss, observers, and engine here, then run the encounter.
 */
public class Main {
    public static void main(String[] args) {
        EventManager eventManager = new EventManager();

        List<Hero> heroes = new ArrayList<>();
        heroes.add(new Hero("Aruzhan", 120, 34, 12, new AggressiveStrategy()));
        heroes.add(new Hero("Dias", 135, 28, 15, new BalancedStrategy()));
        heroes.add(new Hero("Madi", 150, 24, 18, new DefensiveStrategy()));

        DungeonBoss boss = new DungeonBoss("Cursed Overlord", 320, 30, 10, eventManager);

        boss.registerObserver(new BattleLogger());
        boss.registerObserver(new AchievementTracker());
        boss.registerObserver(new PartySupport(heroes));
        boss.registerObserver(new HeroStatusMonitor(heroes));
        boss.registerObserver(new LootDropper());
        boss.registerObserver(boss);

        DungeonEngine engine = new DungeonEngine(heroes, boss, eventManager);
        EncounterResult result = engine.runEncounter();

        System.out.println("\n========== FINAL RESULT ==========");
        System.out.println("Heroes won: " + result.isHeroesWon());
        System.out.println("Rounds played: " + result.getRoundsPlayed());
        System.out.println("Surviving heroes: " + result.getSurvivingHeroes());
    }
}