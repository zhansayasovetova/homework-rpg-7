package com.narxoz.rpg.observer;

/**
 * Logs every event that happens during the battle.
 */
public class BattleLogger implements GameObserver {

    @Override
    public void onEvent(GameEvent event) {
        switch (event.getType()) {
            case ATTACK_LANDED:
                System.out.println("[BattleLogger] " + event.getSourceName()
                        + " landed an attack for " + event.getValue() + " damage.");
                break;

            case HERO_LOW_HP:
                System.out.println("[BattleLogger] " + event.getSourceName()
                        + " is low on HP! Remaining HP: " + event.getValue());
                break;

            case HERO_DIED:
                System.out.println("[BattleLogger] " + event.getSourceName()
                        + " has died.");
                break;

            case BOSS_PHASE_CHANGED:
                System.out.println("[BattleLogger] Boss changed to phase "
                        + event.getValue() + ".");
                break;

            case BOSS_DEFEATED:
                System.out.println("[BattleLogger] Boss was defeated after "
                        + event.getValue() + " rounds.");
                break;

            default:
                System.out.println("[BattleLogger] Event: " + event.getType());
        }
    }
}