package com.example.final_project_schaar_zugaj;

import javafx.application.Application;
import javafx.stage.Stage;

public class Pointer {
    static int level = 1;
    static double multiplier = 1;
    static String description = "The basic pointer.";

    public static void levelUp() {
        level += 1;
        multiplier = Math.pow(level, 2);
    }
}
