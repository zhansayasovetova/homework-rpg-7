package com.narxoz.rpg.observer;

import com.narxoz.rpg.combatant.Hero;

import java.util.List;

/**
 * Supports the party by healing the first living hero
 * when a low HP event is detected.
 */
public class PartySupport implements GameObserver {

    private final List<Hero> heroes;

    public PartySupport(List<Hero> heroes) {
        this.heroes = heroes;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.HERO_LOW_HP) {
            for (Hero hero : heroes) {
                if (hero.isAlive()) {
                    hero.heal(15);
                    System.out.println("[PartySupport] " + hero.getName()
                            + " was healed for 15 HP.");
                    break;
                }
            }
        }
    }
}