package com.narxoz.rpg.strategy;

public class BossPhaseTwoStrategy implements CombatStrategy {

    @Override
    public int calculateDamage(int basePower) {
        return (int) Math.round(basePower * 1.4);
    }

    @Override
    public int calculateDefense(int baseDefense) {
        return baseDefense;
    }

    @Override
    public String getName() {
        return "Boss Phase 2";
    }
}