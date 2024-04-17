package org.example.verestrotask.client.account;

public enum Balance {
    KOD_1(100), KOD_2(200), KOD_3(300), KOD_4(400), KOD_5(500);
    private final int BALANCE;

    Balance(int balance) {
        BALANCE = balance;
    }

    public int getBALANCE() {
        return BALANCE;
    }
}
