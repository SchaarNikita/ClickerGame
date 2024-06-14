package com.example.final_project_schaar_zugaj;

import java.util.ArrayList;

public class Cookie {
    static long amount = 10000000;

    static void handleOnClick() {
        amount += Math.round(Pointer.multiplier);
    }

    static void handlePassiveCookies() {
        amount += Math.round(Shop.worker.multiplier) * Shop.worker.level;
        amount += Math.round(Shop.farmer.multiplier) * Shop.farmer.level;
        amount += Math.round(Shop.baker.multiplier) * Shop.baker.level;
        amount += Math.round(Shop.miner.multiplier) * Shop.miner.level;
    }
}
