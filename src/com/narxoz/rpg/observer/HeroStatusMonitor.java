package com.narxoz.rpg.observer;

import com.narxoz.rpg.combatant.Hero;

import java.util.List;

/**
 * Prints current hero status when a hero becomes low HP or dies.
 */
public class HeroStatusMonitor implements GameObserver {

    private final List<Hero> heroes;

    public HeroStatusMonitor(List<Hero> heroes) {
        this.heroes = heroes;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.HERO_LOW_HP
                || event.getType() == GameEventType.HERO_DIED) {

            System.out.println("[HeroStatusMonitor] Current hero status:");
            for (Hero hero : heroes) {
                System.out.println(" - " + hero.getName()
                        + ": HP = " + hero.getHp()
                        + "/" + hero.getMaxHp()
                        + ", alive = " + hero.isAlive());
            }
        }
    }
}