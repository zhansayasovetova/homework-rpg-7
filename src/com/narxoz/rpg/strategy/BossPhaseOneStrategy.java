package com.narxoz.rpg.strategy;

public class BossPhaseOneStrategy implements CombatStrategy {

    @Override
    public int calculateDamage(int basePower) {
        return basePower;
    }

    @Override
    public int calculateDefense(int baseDefense) {
        return (int) Math.round(baseDefense * 1.2);
    }

    @Override
    public String getName() {
        return "Boss Phase 1";
    }
}