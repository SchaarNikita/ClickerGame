package com.example.final_project_schaar_zugaj;

import java.util.HashMap;
import java.util.Map;

public class Farmer extends GrandmaUpgrade {
    public Farmer(double multiplier, String description, int level) {
        this.multiplier = multiplier;
        this.description = description;
        this.level = level;
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
