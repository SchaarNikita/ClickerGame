package com.example.final_project_schaar_zugaj;

public class Shop {
    public static void handleBuyWorker() {
        if(Cookie.amount >= 100) {
            Cookie.amount -= 100;
            Cookie.upgrades.add(new Worker());
        }
    }
}
