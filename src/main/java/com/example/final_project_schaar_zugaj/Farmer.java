package com.example.final_project_schaar_zugaj;

import java.util.HashMap;
import java.util.Map;

public class Farmer extends GrandmaUpgrade{
    private double multiplier;
    private String description = "Have you ever seen a cookietree?";
    private int level;
    public Farmer(double multiplier, String description, int level){
        this.multiplier = multiplier;
        this.description = description;
        this.level  = level;
    }
    Map<Integer, Double> levelMultiplier = new HashMap<>();

    public void multiplierPerLevel(){
        for(int i = 1; i <= 10; i++){
            levelMultiplier.put(i, 1.05);
        }

    }

    public double getMultiplier() {
        return multiplier = levelMultiplier.get(level);
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
