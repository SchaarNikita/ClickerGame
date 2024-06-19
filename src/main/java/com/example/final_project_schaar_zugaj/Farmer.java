package com.example.final_project_schaar_zugaj;

import java.util.HashMap;
import java.util.Map;

public class Farmer extends GrandmaUpgrade {
    public Farmer() {
        this.multiplier = 1;
        this.description = "A Farmer";
        this.level = 0;
    }

    @Override
    public long getPrice() {
        return Math.round(200 * Math.exp(this.getLevel()) * (this.getLevel() + 1));
    }
}
