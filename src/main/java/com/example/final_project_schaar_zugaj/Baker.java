package com.example.final_project_schaar_zugaj;

import java.util.HashMap;
import java.util.Map;

public class Baker extends GrandmaUpgrade {
    public Baker() {
        this.multiplier = 2;
        this.description = "A Baker";
        this.level = 0;
    }

    @Override
    public void multiplierPerLevel() {
        double j = 2;
        for (int i = 1; i <= 10; i++) {
            levelMultiplier.put(i, j);
            j++;
        }

    }
}
