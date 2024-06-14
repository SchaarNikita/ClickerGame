package com.example.final_project_schaar_zugaj;

import com.almasb.fxgl.core.math.FXGLMath;

public class Shop {
    static Worker worker = new Worker();
    static Baker baker = new Baker();
    static Farmer farmer = new Farmer();
    static Miner miner = new Miner();

    public static void handleBuyWorker() {
        if(Cookie.amount >= Math.round(100 * Math.exp(worker.getLevel())) && worker.getLevel() != 10) {
            Cookie.amount -= Math.round(100 * Math.exp(worker.getLevel()));
            worker.levelUp();
        }
    }
}
