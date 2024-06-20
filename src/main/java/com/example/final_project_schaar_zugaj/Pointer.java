package com.example.final_project_schaar_zugaj;

import com.almasb.fxgl.dsl.FXGL;
import javafx.application.Application;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Pointer {
    static int level = 1;
    static double multiplier = 1;
    static String description = "The basic pointer.";
    static int[] levelPrice = {50000, 1000000};

    public static void levelUp() {
        level += 1;
        multiplier = Math.pow(level, 2);
        if(level == 2) {
            Image betterImage = new Image("assets/textures/betterpointer.png");
            ImageCursor betterPointer = new ImageCursor(betterImage, 33, 17);
            FXGL.getGameScene().setCursor(betterPointer);
        } else {
            Image bestImage = new Image("assets/textures/bestpointer.png");
            ImageCursor bestPointer = new ImageCursor(bestImage, 33, 17);
            FXGL.getGameScene().setCursor(bestPointer);
        }
    }
}
