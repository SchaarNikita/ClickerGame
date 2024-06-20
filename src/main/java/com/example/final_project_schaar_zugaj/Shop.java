package com.example.final_project_schaar_zugaj;

import com.almasb.fxgl.core.math.FXGLMath;

public class Shop {
    static Worker worker = new Worker();
    static Baker baker = new Baker();
    static Farmer farmer = new Farmer();
    static Miner miner = new Miner();

    public static void handleBuyFarmer() {
        if(Cookie.amount >= farmer.getPrice() && farmer.getLevel() != 10) {
            Cookie.amount -= farmer.getPrice();
            farmer.levelUp();
        }
    }

    public static void handleBuyMiner() {
        if(Cookie.amount >= miner.getPrice() && miner.getLevel() != 10) {
            Cookie.amount -= miner.getPrice();
            miner.levelUp();
        }
    }

    public static void handleBuyWorker() {
        if(Cookie.amount >= worker.getPrice() && worker.getLevel() != 10) {
            Cookie.amount -= worker.getPrice();
            worker.levelUp();
        }
    }

    public static void handleBuyBaker() {
        if(Cookie.amount >= baker.getPrice() && baker.getLevel() != 10) {
            Cookie.amount -= baker.getPrice();
            baker.levelUp();
        }
    }

    public static void handleBuyPointer() {
        if(Cookie.amount >= Pointer.levelPrice[Pointer.level-1] && Pointer.level != 3) {
            Cookie.amount -= Pointer.levelPrice[Pointer.level-1];
            Pointer.levelUp();
        }
    }


}
