package com.example.final_project_schaar_zugaj;

import java.util.HashMap;
import java.util.Map;

public class Miner extends GrandmaUpgrade {
    public Miner() {
        this.multiplier = 2;
        this.description = "A Miner";
        this.level = 0;
    }

    @Override
    public long getPrice() {
        return Math.round(600 * Math.exp(this.getLevel()) * (this.getLevel() + 1));
    }
}
