package com.example.final_project_schaar_zugaj;

import java.util.HashMap;
import java.util.Map;

public class Worker extends GrandmaUpgrade {
    public Worker() {
        this.multiplier = 4;
        this.description = "A worker";
        this.level = 0;
    }

    @Override
    public long getPrice() {
        return Math.round(1800 * Math.exp(this.getLevel()) * (this.getLevel() + 1));
    }
}
