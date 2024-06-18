package com.example.final_project_schaar_zugaj;

import java.util.ArrayList;

public class Cookie {
    static long amount = 10000000;

    static void handleOnClick() {
        amount += Math.round(Pointer.multiplier);
    }

    static void handlePassiveCookies() {
        amount += (Math.round(1 + Shop.worker.multiplier * Shop.worker.level) * Shop.worker.level);
        amount += (Math.round(1 + Shop.farmer.multiplier * Shop.farmer.level) * Shop.farmer.level);
        amount += (Math.round(1 + Shop.baker.multiplier * Shop.baker.level) * Shop.baker.level);
        amount += (Math.round(1 + Shop.miner.multiplier * Shop.miner.level) * Shop.miner.level);
    }
}
