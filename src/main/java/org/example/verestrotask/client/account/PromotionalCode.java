package org.example.verestrotask.client.account;

public enum PromotionalCode {
    KOD_1(100),
    KOD_2(200),
    KOD_3(300),
    KOD_4(400),
    KOD_5(500);
    private final int bonus;

    PromotionalCode(int bonus) {
        this.bonus = bonus;
    }

    public int getBonus() {
        return bonus;
    }
}
