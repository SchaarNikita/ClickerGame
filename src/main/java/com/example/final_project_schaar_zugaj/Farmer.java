package com.example.final_project_schaar_zugaj;

import java.util.HashMap;
import java.util.Map;

public class Farmer extends GrandmaUpgrade {
    public Farmer() {
        this.multiplier = 1.25;
        this.description = "A Farmer";
        this.level = 0;
    }

    @Override
    public void multiplierPerLevel() {
        double j = 1.25;
        for (int i = 1; i <= 10; i++) {
            levelMultiplier.put(i, j);
            j = j + 0.25;
        }

    }
}
