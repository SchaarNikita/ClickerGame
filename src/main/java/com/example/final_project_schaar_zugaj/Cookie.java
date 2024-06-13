package com.example.final_project_schaar_zugaj;

import java.util.ArrayList;

public class Cookie {
    static long amount = 0;
    static ArrayList<GrandmaUpgrade> upgrades = new ArrayList<>();

    static void handleOnClick() {
        amount += Math.round(Pointer.multiplier);
    }

    static void handlePassiveCookies() {
        if(!upgrades.isEmpty()) {
            for(GrandmaUpgrade u : upgrades) {
                amount += Math.round(u.multiplier);
            }
        }

    }
}
