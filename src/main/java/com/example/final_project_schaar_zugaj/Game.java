/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package com.example.final_project_schaar_zugaj;

import com.almasb.fxgl.achievement.Achievement;
import com.almasb.fxgl.achievement.AchievementService;
import com.almasb.fxgl.app.*;
import com.almasb.fxgl.app.MenuItem;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.cutscene.Cutscene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.profile.DataFile;
import com.almasb.fxgl.profile.SaveLoadHandler;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.ui.property.BooleanPropertyView;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.awt.*;
import java.util.*;

import static com.almasb.fxgl.dsl.FXGL.*;

// NOTE: this import above is crucial, it pulls in many useful methods
public class Game extends GameApplication {
    Text cookieAmount;
    Entity cookie;

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
        settings.setFullScreenAllowed(false);
        settings.setTitle("Clicker Game");
        settings.setEnabledMenuItems(EnumSet.of(MenuItem.EXTRA));
        settings.setApplicationMode(ApplicationMode.DEVELOPER);
        settings.getCredits().addAll(Arrays.asList(
                "Nikita Schaar - Programmer, Designer, Planning",
                "Moritz Zugaj - Programmer, Designer, Planning"
        ));
        settings.setAppIcon("cookie.png");
        settings.setDefaultCursor(new CursorInfo("pointer.png", 33, 17));

        // Adding all achievements + their goals to the game
        settings.getAchievements().add(new Achievement("Be Lucky", "Click the cookie, maybe you'll get it...", "luckyCondition", true));
        settings.getAchievements().add(new Achievement("Small Fry Clicker", "Click the cookie 100 times", "clickCount", 100));
        settings.getAchievements().add(new Achievement("Big Fry Clicker", "Click the cookie 1000 times", "clickCount", 1000));
        settings.getAchievements().add(new Achievement("Giga Clicker", "Click the cookie 10000 times", "clickCount", 10000));
        settings.getAchievements().add(new Achievement("Sigma Clicker", "Click the cookie 100000 times", "clickCount", 100000));
        settings.getAchievements().add(new Achievement("Ultimate Clicker", "Click the cookie 1000000 times", "clickCount", 1000000));
        settings.getAchievements().add(new Achievement("Clicker G.O.A.T.", "Click the cookie 10000000 times", "clickCount", 10000000));
    }

    @Override
    protected void onPreInit() {
        getSaveLoadService().addHandler(new SaveLoadHandler() {
            @Override
            public void onSave(DataFile data) {
                // create a new bundle to store your data
                var bundle = new Bundle("gameData");

                // store some data
                long cookieAmount = Cookie.amount;
                bundle.put("cookieAmount", cookieAmount);

                // give the bundle to data file
                data.putBundle(bundle);
            }

            @Override
            public void onLoad(DataFile data) {
                // get your previously saved bundle
                var bundle = data.getBundle("gameData");

                // retrieve some data and update your game with saved data
                Cookie.amount = bundle.get("cookieAmount");
            }
        });
    }

    @Override
    protected void initInput() {
        onKeyDown(KeyCode.S, "Save", () -> {
            getSaveLoadService().saveAndWriteTask("save1.sav").run();
        });

        onKeyDown(KeyCode.L, "Load", () -> {
            getSaveLoadService().readAndLoadTask("save1.sav").run();
        });
    }

    @Override
    protected void initGame() {
        spawnCookie();
        //run(() -> anyMethod(), Duration.seconds(1));
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("luckyCondition", false);
        vars.put("clickCount", 0);
    }

    @Override
    protected void initUI() {
        // Cookie Amount Label
        cookieAmount = new Text();
        cookieAmount.setTranslateX(15);
        cookieAmount.setTranslateY(40);
        cookieAmount.fontProperty().set(Font.font("Verdana", 40));

        cookieAmount.textProperty().set(Cookie.amount + "");

        getGameScene().addUINode(cookieAmount); // add to the scene graph
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
                .viewWithBBox("Cookie_test.png")
                //.with(new AnimationComponent())
                .onClick(entity -> {
                    animationBuilder()
                            .translate(cookie)
                            .from(new Point2D(200, 200))
                            .to(new Point2D(200, 230))
                            .from(new Point2D(200, 230))
                            .to(new Point2D(200, 200))
                            .buildAndPlay();
                    Cookie.handleOnClick();
                    spawnPlusOne();
                    handleClickAchievements();
                })
                .buildAndAttach();
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

    public void handleClickAchievements() {
        // update clickCount
        set("clickCount", geti("clickCount") + 1);

        // Achievement: Be Lucky
        if (!getb("luckyCondition")) {
            if (random(0, 50000) == 12345) {
                FXGL.set("luckyCondition", true);
                getNotificationService().setBackgroundColor(Color.SEASHELL);
                getNotificationService().pushNotification("Got achievement \"Be Lucky\"!");
            }
        }

        // Achievement: Small Fry Clicker
        if (geti("clickCount") == 100) {
            getNotificationService().setBackgroundColor(Color.SEASHELL);
            getNotificationService().pushNotification("Got achievement \"Small Fry Clicker\"!");
        }

        // Achievement: Big Fry Clicker
        if (geti("clickCount") == 1000) {
            getNotificationService().setBackgroundColor(Color.SEASHELL);
            getNotificationService().pushNotification("Got achievement \"Big Fry Clicker\"!");
        }

        // Achievement: Giga Clicker
        if (geti("clickCount") == 10000) {
            getNotificationService().setBackgroundColor(Color.SEASHELL);
            getNotificationService().pushNotification("Got achievement \"Giga Clicker\"!");
        }

        // Achievement: Sigma Clicker
        if (geti("clickCount") == 100000) {
            getNotificationService().setBackgroundColor(Color.SEASHELL);
            getNotificationService().pushNotification("Got achievement \"Sigma Clicker\"!");
        }

        // Achievement: Ultimate Clicker
        if (geti("clickCount") == 1000000) {
            getNotificationService().setBackgroundColor(Color.SEASHELL);
            getNotificationService().pushNotification("Got achievement \"Ultimate Clicker\"!");
        }

        // Achievement: Clicker G.O.A.T.
        if (geti("clickCount") == 10000000) {
            getNotificationService().setBackgroundColor(Color.SEASHELL);
            getNotificationService().pushNotification("Got achievement \"Clicker G.O.A.T.\"!");
        }
    }

    public void handlePassiveAchievements() {
        // achievements got through passive means will be checked for here
    }

    public static void main(String[] args) {
        launch(args);
    }
}
