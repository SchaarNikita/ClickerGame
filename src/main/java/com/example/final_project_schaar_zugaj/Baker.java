package com.example.final_project_schaar_zugaj;

import java.util.HashMap;
import java.util.Map;

public class Baker extends GrandmaUpgrade {
    public Baker() {
        this.multiplier = 8;
        this.description = "A Baker";
        this.level = 0;
    }

    @Override
    public long getPrice() {
        return Math.round(5400 * Math.exp(this.getLevel()) * (this.getLevel() + 1));
    }
}
