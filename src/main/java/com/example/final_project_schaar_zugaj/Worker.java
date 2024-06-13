package com.example.final_project_schaar_zugaj;

import java.util.HashMap;
import java.util.Map;

public class Worker extends GrandmaUpgrade {
    public Worker() {
        this.multiplier = 1.75;
        this.description = "A worker";
        this.level = 0;
    }

    @Override
    public void multiplierPerLevel() {
        double j = 1.75;
        for (int i = 1; i <= 10; i++) {
            levelMultiplier.put(i, j);
            j = j + 0.75;
        }

    }
}
