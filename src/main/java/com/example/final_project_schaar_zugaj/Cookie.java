package com.example.final_project_schaar_zugaj;

import java.util.ArrayList;

public class Cookie {
    static int amount = 0;

    static void handleOnClick() {
        amount += Math.round(Pointer.multiplier);
    }

    static void handlePassiveCookies() {
        if(!(amount >= Integer.MAX_VALUE - 20000)) {
            amount += Math.round(Shop.farmer.multiplier * Math.pow(Shop.farmer.getLevel(), 2) * 1.5);
            amount += Math.round(Shop.miner.multiplier * Math.pow(Shop.miner.getLevel(), 2) * 3);
            amount += Math.round(Shop.worker.multiplier * Math.pow(Shop.worker.getLevel(), 2) * 5);
            amount += Math.round(Shop.baker.multiplier * Math.pow(Shop.baker.getLevel(), 2) * 8);
        }
    }
}
