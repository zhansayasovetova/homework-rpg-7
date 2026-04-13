package com.narxoz.rpg.strategy;

public class BossPhaseThreeStrategy implements CombatStrategy {

    @Override
    public int calculateDamage(int basePower) {
        return (int) Math.round(basePower * 1.8);
    }

    @Override
    public int calculateDefense(int baseDefense) {
        return (int) Math.round(baseDefense * 0.6);
    }

    @Override
    public String getName() {
        return "Boss Phase 3";
    }
}