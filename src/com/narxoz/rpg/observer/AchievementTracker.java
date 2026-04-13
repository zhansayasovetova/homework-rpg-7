package com.narxoz.rpg.observer;

/**
 * Tracks and unlocks battle achievements.
 */
public class AchievementTracker implements GameObserver {

    private boolean firstBloodUnlocked;
    private boolean bossSlayerUnlocked;
    private boolean relentlessUnlocked;
    private int attacksLanded;

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.ATTACK_LANDED) {
            attacksLanded++;

            if (!firstBloodUnlocked) {
                firstBloodUnlocked = true;
                System.out.println("[AchievementTracker] Achievement unlocked: First Blood");
            }

            if (!relentlessUnlocked && attacksLanded >= 10) {
                relentlessUnlocked = true;
                System.out.println("[AchievementTracker] Achievement unlocked: Relentless");
            }
        }

        if (event.getType() == GameEventType.BOSS_DEFEATED && !bossSlayerUnlocked) {
            bossSlayerUnlocked = true;
            System.out.println("[AchievementTracker] Achievement unlocked: Boss Slayer");
        }
    }
}