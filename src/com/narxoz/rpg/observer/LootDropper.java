package com.narxoz.rpg.observer;

/**
 * Drops loot on boss phase changes and boss defeat.
 */
public class LootDropper implements GameObserver {

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.BOSS_PHASE_CHANGED) {
            if (event.getValue() == 2) {
                System.out.println("[LootDropper] Loot dropped: Enchanted Armor Fragment");
            } else if (event.getValue() == 3) {
                System.out.println("[LootDropper] Loot dropped: Cursed Crystal");
            }
        }

        if (event.getType() == GameEventType.BOSS_DEFEATED) {
            System.out.println("[LootDropper] Loot dropped: Legendary Dungeon Core");
        }
    }
}
