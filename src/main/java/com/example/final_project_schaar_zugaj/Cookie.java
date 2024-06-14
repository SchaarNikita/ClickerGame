package com.example.final_project_schaar_zugaj;

import java.util.ArrayList;

public class Cookie {
    static long amount = 1000000000;

    static void handleOnClick() {
        amount += Math.round(Pointer.multiplier);
    }

    static void handlePassiveCookies() {
        amount += Math.round(Shop.worker.multiplier);
        amount += Math.round(Shop.farmer.multiplier);
        amount += Math.round(Shop.baker.multiplier);
        amount += Math.round(Shop.miner.multiplier);
    }
}
