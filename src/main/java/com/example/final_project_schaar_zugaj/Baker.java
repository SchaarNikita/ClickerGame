package com.example.final_project_schaar_zugaj;

import java.util.HashMap;
import java.util.Map;

public class Baker extends GrandmaUpgrade {
    public Baker(double multiplier, String description, int level) {
        this.multiplier = multiplier;
        this.description = description;
        this.level = level;
    }

    @Override
    public void multiplierPerLevel() {
        for (int i = 1; i <= 10; i++) {
            levelMultiplier.put(i, 1.05);
        }

    }
}
