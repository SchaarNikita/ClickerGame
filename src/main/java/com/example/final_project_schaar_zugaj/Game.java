/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package com.example.final_project_schaar_zugaj;

import com.almasb.fxgl.achievement.Achievement;
import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.MenuItem;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import javafx.beans.value.ObservableValue;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.awt.*;
import java.util.Arrays;
import java.util.EnumSet;

import static com.almasb.fxgl.dsl.FXGL.*;
// NOTE: this import above is crucial, it pulls in many useful methods

/**
 * This is an FXGL version of the libGDX simple game tutorial, which can be found
 * here - https://github.com/libgdx/libgdx/wiki/A-simple-game
 *
 * The player can move the bucket left and right to catch water droplets.
 * There are no win/lose conditions.
 *
 * Note: for simplicity's sake all of the code is kept in this file.
 * In addition, most of typical FXGL API is not used to avoid overwhelming
 * FXGL beginners with a lot of new concepts to learn.
 *
 * Although the code is self-explanatory, some may find the comments useful
 * for following the code.
 *
 * @author Almas Baimagambetov (AlmasB) (almaslvl@gmail.com)
 */
public class Game extends GameApplication {
    Entity pointer;
    Entity cookie;
    Text cookieAmount;

    /**
     * Types of entities in this game.
     */
    public enum Type {
        COOKIE, POINTER, PLUSONE
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(800);
        settings.setFullScreenAllowed(true);
        settings.setMainMenuEnabled(true);
        settings.setGameMenuEnabled(true);
        settings.setFullScreenAllowed(true);
        settings.setTitle("Clicker Game");
        settings.setEnabledMenuItems(EnumSet.of(MenuItem.EXTRA));
        settings.setApplicationMode(ApplicationMode.DEVELOPER);
        settings.getCredits().addAll(Arrays.asList(
                "Nikita Schaar - Programmer, Designer, Planning",
                "Moritz Zugaj - Programmer, Designer"
        ));
        settings.setAppIcon("cookie.png");

        settings.getAchievements().add(new Achievement("Test", "Test", "", 0));
    }

    @Override
    protected void initGame() {
        spawnCookie();
        //spawnPointer();

        //run(() -> anyMethod(), Duration.seconds(1));
    }

    @Override
    protected void initPhysics() {
//        onCollisionBegin(Type.BUCKET, Type.COOKIE, (bucket, cookie) -> {
//
//            // code in this block is called when there is a collision between Type.BUCKET and Type.DROPLET
//
//            // remove the collided droplet from the game
//            cookie.removeFromWorld();
//
//            // play a sound effect located in /resources/assets/sounds/
//            play("drop.wav");
//        });
    }

    @Override
    protected void initUI() {
        cookieAmount = new Text();
        cookieAmount.setTranslateX(15);
        cookieAmount.setTranslateY(40);
        cookieAmount.fontProperty().set(Font.font("Verdana", 40));

        cookieAmount.textProperty().set(Cookie.amount + "");

        getGameScene().addUINode(cookieAmount); // add to the scene graph
        getGameScene();
    }

    @Override
    protected void onUpdate(double tpf) {
        cookieAmount.textProperty().set(Cookie.amount + "");

        getGameWorld().getEntitiesByType(Type.PLUSONE).forEach(plusOne -> plusOne.translateY(-600 * tpf));
    }

    private void spawnCookie() {
        cookie = entityBuilder()
                .type(Type.COOKIE)
                .at(getAppWidth() / 2 - 200, getAppHeight() / 2 - 200)
                .viewWithBBox("cookie.png")
                //.with(new AnimationComponent())
                .onClick(entity -> {
                    Cookie.handleOnClick();
                    spawnPlusOne();
                })
                .buildAndAttach();
    }

    private void spawnPointer() {
        pointer = entityBuilder()
            .type(Type.POINTER)
                .at(getInput().mouseXUIProperty().get(), getInput().mouseYUIProperty().get())
                .viewWithBBox("pointer.png")
                .collidable()
                .buildAndAttach();

        // bind properties of pointer sprite to x and y properties of mouse pointer
        pointer.xProperty().bind(getInput().mouseXUIProperty().subtract(30));
        pointer.yProperty().bind(getInput().mouseYUIProperty().subtract(45));
    }

    private void spawnPlusOne() {
        entityBuilder()
                .type(Type.PLUSONE)
                .at(FXGLMath.random(0, getAppWidth() - 64), FXGLMath.random(64, getAppHeight() - 64))
                .viewWithBBox("plusone.png")
                .collidable()
                .with(new OffscreenCleanComponent())
                .buildAndAttach();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
