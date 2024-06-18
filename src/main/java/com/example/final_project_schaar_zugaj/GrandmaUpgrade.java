package com.example.final_project_schaar_zugaj;

import java.util.HashMap;
import java.util.Map;

public abstract class GrandmaUpgrade {
    double multiplier;
    String description;
    int level;

    public void levelUp() {
        level += 1;
    }

    public String getDescription() {
        return description;
    }

    public int getLevel() {
        return level;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
