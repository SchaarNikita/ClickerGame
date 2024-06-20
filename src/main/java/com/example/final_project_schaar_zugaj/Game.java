/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package com.example.final_project_schaar_zugaj;

import com.almasb.fxgl.achievement.Achievement;
import com.almasb.fxgl.app.*;
import com.almasb.fxgl.app.MenuItem;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.profile.DataFile;
import com.almasb.fxgl.profile.SaveLoadHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.*;

import static com.almasb.fxgl.dsl.FXGL.*;

// NOTE: this import above is crucial, it pulls in many useful methods
public class Game extends GameApplication {
    Text cookieAmount;
    Text farmerData;
    Text minerData;
    Text workerData;
    Text bakerData;
    Text pointerData;
    Entity cookie;

    // Entities for shop
    Entity shop;
    Entity buyFarmer;
    Entity buyMiner;
    Entity buyWorker;
    Entity buyBaker;
    Entity buyPointer;

    /**
     * Types of entities in this game.
     */
    public enum Type {
        COOKIE, PLUSNUMBER, SHOP
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

        // Adding all active (bound to onClick events) achievements + their goals to the game
        settings.getAchievements().addAll(Arrays.asList(
                new Achievement("Be Lucky", "Click the cookie, maybe you'll get it...", "luckyCondition", true),
                new Achievement("Small Fry Clicker", "Click the cookie 100 times", "clickCount", 100),
                new Achievement("Big Fry Clicker", "Click the cookie 1000 times", "clickCount", 1000),
                new Achievement("Giga Clicker", "Click the cookie 10000 times", "clickCount", 10000),
                new Achievement("Sigma Clicker", "Click the cookie 100000 times", "clickCount", 100000),
                new Achievement("Ultimate Clicker", "Click the cookie 1000000 times", "clickCount", 1000000),
                new Achievement("Clicker G.O.A.T.", "Click the cookie 10000000 times", "clickCount", 10000000)
        ));

        // Adding all passive (bound to cookieAmount) achievements + goals
        settings.getAchievements().addAll(Arrays.asList(
                new Achievement("Cookie Crook", "Get 1000 cookies", "cookieAmount", 1000),
                new Achievement("Cookie Pookie", "Get 10000 cookies", "cookieAmount", 10000),
                new Achievement("Cookie Lord", "Get 100000 cookies", "cookieAmount", 100000),
                new Achievement("Cookie Prince", "Get 1000000 cookies", "cookieAmount", 1000000),
                new Achievement("Cookie King", "Get 10000000 cookies", "cookieAmount", 10000000),
                new Achievement("Cookie Emperor", "Get 100000000 cookies", "cookieAmount", 100000000),
                new Achievement("Cookie God", "Get 1000000000 cookies", "cookieAmount", 1000000000)
        ));
    }

    @Override
    protected void onPreInit() {
        getSaveLoadService().addHandler(new SaveLoadHandler() {
            @Override
            public void onSave(DataFile data) {
                // create a new bundle to store your data
                var bundle = new Bundle("gameData");

                // store some data
                bundle.put("cookieAmount", Cookie.amount);
                bundle.put("clickCount", geti("clickCount"));
                bundle.put("farmerLevel", Shop.farmer.level);
                bundle.put("minerLevel", Shop.miner.level);
                bundle.put("workerLevel", Shop.worker.level);
                bundle.put("bakerLevel", Shop.baker.level);
                bundle.put("pointerLevel", Pointer.level);

                // give the bundle to data file
                data.putBundle(bundle);
            }

            @Override
            public void onLoad(DataFile data) {
                // get your previously saved bundle
                var bundle = data.getBundle("gameData");

                // retrieve some data and update your game with saved data
                Cookie.amount = bundle.get("cookieAmount");
                set("clickCount", bundle.get("clickCount"));
                Shop.farmer.level = bundle.get("farmerLevel");
                Shop.miner.level = bundle.get("minerLevel");
                Shop.worker.level = bundle.get("workerLevel");
                Shop.baker.level = bundle.get("bakerLevel");
                Pointer.level = bundle.get("pointerLevel");

                if(Shop.farmer.getLevel() == 10) {
                    farmerData.textProperty().set("LVL: MAX");
                } else {
                    farmerData.textProperty().set("LVL: " + Shop.farmer.getLevel() + " - Price: " + Shop.farmer.getPrice());
                }

                if(Shop.miner.getLevel() == 10) {
                    minerData.textProperty().set("LVL: MAX");
                } else {
                    minerData.textProperty().set("LVL: " + Shop.miner.getLevel() + " - Price: " + Shop.miner.getPrice());
                }

                if(Shop.worker.getLevel() == 10) {
                    workerData.textProperty().set("LVL: MAX");
                } else {
                    workerData.textProperty().set("LVL: " + Shop.worker.getLevel() + " - Price: " + Shop.worker.getPrice());
                }

                if(Shop.baker.getLevel() == 10) {
                    bakerData.textProperty().set("LVL: MAX");
                } else {
                    bakerData.textProperty().set("LVL: " + Shop.baker.getLevel() + " - Price: " + Shop.baker.getPrice());
                }
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

        onKeyDown(KeyCode.E, "Shop", () -> {
            if(shop.isVisible()) {
                shop.setVisible(false);
                buyFarmer.setVisible(false);
                buyMiner.setVisible(false);
                buyWorker.setVisible(false);
                buyBaker.setVisible(false);
                buyPointer.setVisible(false);
                farmerData.setVisible(false);
                minerData.setVisible(false);
                workerData.setVisible(false);
                bakerData.setVisible(false);
                pointerData.setVisible(false);
            } else {
                shop.setVisible(true);
                buyFarmer.setVisible(true);
                buyMiner.setVisible(true);
                buyWorker.setVisible(true);
                buyBaker.setVisible(true);
                buyPointer.setVisible(true);
                farmerData.setVisible(true);
                minerData.setVisible(true);
                workerData.setVisible(true);
                bakerData.setVisible(true);
                pointerData.setVisible(true);
            }
        });
    }

    @Override
    protected void initGame() {
        getGameScene().setBackgroundRepeat("background.png");
        spawnCookie();
        initShop();

        run(Cookie::handlePassiveCookies, Duration.seconds(1));
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("luckyCondition", false);
        vars.put("clickCount", 0);
        vars.put("cookieAmount", Cookie.amount);
    }

    @Override
    protected void initUI() {
        cookieAmount = new Text();
        cookieAmount.setTranslateX(15);
        cookieAmount.setTranslateY(40);
        cookieAmount.fontProperty().set(Font.font("Verdana", 40));

        cookieAmount.textProperty().set(Cookie.amount + "");

        farmerData = new Text();
        farmerData.setTranslateX(getAppWidth()/2-115);
        farmerData.setTranslateY(getAppHeight()/2-170);
        farmerData.fontProperty().set(Font.font("Verdana", 30));

        farmerData.textProperty().set("LVL: " + Shop.farmer.getLevel() + " - Price: " + Shop.farmer.getPrice());
        farmerData.setVisible(false);

        minerData = new Text();
        minerData.setTranslateX(getAppWidth()/2-115);
        minerData.setTranslateY(getAppHeight()/2-70);
        minerData.fontProperty().set(Font.font("Verdana", 30));

        minerData.textProperty().set("LVL: " + Shop.miner.getLevel() + " - Price: " + Shop.miner.getPrice());
        minerData.setVisible(false);

        workerData = new Text();
        workerData.setTranslateX(getAppWidth()/2-115);
        workerData.setTranslateY(getAppHeight()/2+20);
        workerData.fontProperty().set(Font.font("Verdana", 30));

        workerData.textProperty().set("LVL: " + Shop.worker.getLevel() + " - Price: " + Shop.worker.getPrice());
        workerData.setVisible(false);

        bakerData = new Text();
        bakerData.setTranslateX(getAppWidth()/2-115);
        bakerData.setTranslateY(getAppHeight()/2+120);
        bakerData.fontProperty().set(Font.font("Verdana", 30));

        bakerData.textProperty().set("LVL: " + Shop.baker.getLevel() + " - Price: " + Shop.baker.getPrice());
        bakerData.setVisible(false);

        pointerData = new Text();
        pointerData.setTranslateX(getAppWidth()/2-115);
        pointerData.setTranslateY(getAppHeight()/2+240);
        pointerData.fontProperty().set(Font.font("Verdana", 30));

        pointerData.textProperty().set("LVL: " + Pointer.level + " - Price: " + Pointer.levelPrice[Pointer.level-1]);
        pointerData.setVisible(false);

        getGameScene().addUINode(cookieAmount);
        getGameScene().addUINode(farmerData);
        getGameScene().addUINode(minerData);
        getGameScene().addUINode(workerData);
        getGameScene().addUINode(bakerData);
        getGameScene().addUINode(pointerData);
    }

    @Override
    protected void onUpdate(double tpf) {
        // Updating textProperty of cookie count
        cookieAmount.textProperty().set(Cookie.amount + "");

        // Updating cookie amount game variable for achievements
        set("cookieAmount", Cookie.amount);

        // moving every +1 particle upwards on the screen
        getGameWorld().getEntitiesByType(Type.PLUSNUMBER).forEach(plusOne -> plusOne.translateY(-600 * tpf));
    }

    private void spawnCookie() {
        cookie = entityBuilder()
                .type(Type.COOKIE)
                .at(getAppWidth() / 2 - 200, getAppHeight() / 2 - 200)
                .viewWithBBox("cookie.png")
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
                    handleAchievements();
                })
                .buildAndAttach();
    }

    private void spawnPlusOne() {
        if(Pointer.level == 1) {
            entityBuilder()
                    .type(Type.PLUSNUMBER)
                    .at(FXGLMath.random(0, getAppWidth() - 64), FXGLMath.random(64, getAppHeight() - 64))
                    .viewWithBBox("plusone.png")
                    .collidable()
                    .with(new OffscreenCleanComponent())
                    .buildAndAttach();
        } else if (Pointer.level == 2) {
            entityBuilder()
                    .type(Type.PLUSNUMBER)
                    .at(FXGLMath.random(0, getAppWidth() - 64), FXGLMath.random(64, getAppHeight() - 64))
                    .viewWithBBox("plusfour.png")
                    .collidable()
                    .with(new OffscreenCleanComponent())
                    .buildAndAttach();
        } else {
            entityBuilder()
                    .type(Type.PLUSNUMBER)
                    .at(FXGLMath.random(0, getAppWidth() - 64), FXGLMath.random(64, getAppHeight() - 64))
                    .viewWithBBox("plusnine.png")
                    .collidable()
                    .with(new OffscreenCleanComponent())
                    .buildAndAttach();
        }

    }

    private void initShop() {
        shop = entityBuilder()
                .type(Type.SHOP)
                .at(getAppWidth()/2-350, getAppHeight()/2-350)
                .view("shop.png")
                .buildAndAttach();
        shop.setVisible(false);

        buyFarmer = entityBuilder()
                .at(getAppWidth()/2-325, getAppHeight()/2-230)
                .view("buyfarmer.png")
                .onClick(entity -> {
                    Shop.handleBuyFarmer();
                    if(Shop.farmer.getLevel() == 10) {
                        farmerData.textProperty().set("LVL: MAX");
                    } else {
                        farmerData.textProperty().set("LVL: " + Shop.farmer.getLevel() + " - Price: " + Shop.farmer.getPrice());
                    }
                })
                .buildAndAttach();
        buyFarmer.setVisible(false);

        buyMiner = entityBuilder()
                .at(getAppWidth()/2-325, getAppHeight()/2-130)
                .view("buyminer.png")
                .onClick(entity -> {
                    Shop.handleBuyMiner();
                    if(Shop.miner.getLevel() == 10) {
                        minerData.textProperty().set("LVL: MAX");
                    } else {
                        minerData.textProperty().set("LVL: " + Shop.miner.getLevel() + " - Price: " + Shop.miner.getPrice());
                    }
                })
                .buildAndAttach();
        buyMiner.setVisible(false);

        buyWorker = entityBuilder()
                .at(getAppWidth()/2-325, getAppHeight()/2-30)
                .view("buyworker.png")
                .onClick(entity -> {
                    Shop.handleBuyWorker();
                    if(Shop.worker.getLevel() == 10) {
                        workerData.textProperty().set("LVL: MAX");
                    } else {
                        workerData.textProperty().set("LVL: " + Shop.worker.getLevel() + " - Price: " + Shop.worker.getPrice());
                    }
                })
                .buildAndAttach();
        buyWorker.setVisible(false);

        buyBaker = entityBuilder()
                .at(getAppWidth()/2-325, getAppHeight()/2+70)
                .view("buybaker.png")
                .onClick(entity -> {
                    Shop.handleBuyBaker();
                    if(Shop.baker.getLevel() == 10) {
                        bakerData.textProperty().set("LVL: MAX");
                    } else {
                        bakerData.textProperty().set("LVL: " + Shop.baker.getLevel() + " - Price: " + Shop.baker.getPrice());
                    }
                })
                .buildAndAttach();
        buyBaker.setVisible(false);

        buyPointer = entityBuilder()
                .at(getAppWidth()/2-290, getAppHeight()/2+170)
                .view("pointer.png")
                .onClick(entity -> {
                    Shop.handleBuyPointer();
                    if(Pointer.level == 3) {
                        pointerData.textProperty().set("LVL: MAX");
                    } else {
                        pointerData.textProperty().set("LVL: " + Pointer.level + " - Price: " + Pointer.levelPrice[Pointer.level-1]);
                    }
                })
                .buildAndAttach();
        buyPointer.setVisible(false);
    }

    public void handleAchievements() {
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

        switch (geti("clickCount")) {
            case 100:
                // Achievement: Small Fry Clicker
                getNotificationService().setBackgroundColor(Color.SEASHELL);
                getNotificationService().pushNotification("Got achievement \"Small Fry Clicker\"!");
                break;

            case 1000:
                // Achievement: Big Fry Clicker
                getNotificationService().setBackgroundColor(Color.SEASHELL);
                getNotificationService().pushNotification("Got achievement \"Big Fry Clicker\"!");
                break;

            case 10000:
                // Achievement: Giga Clicker
                getNotificationService().setBackgroundColor(Color.SEASHELL);
                getNotificationService().pushNotification("Got achievement \"Giga Clicker\"!");
                break;

            case 100000:
                // Achievement: Sigma Clicker
                getNotificationService().setBackgroundColor(Color.SEASHELL);
                getNotificationService().pushNotification("Got achievement \"Sigma Clicker\"!");
                break;

            case 1000000:
                // Achievement: Ultimate Clicker
                getNotificationService().setBackgroundColor(Color.SEASHELL);
                getNotificationService().pushNotification("Got achievement \"Ultimate Clicker\"!");
                break;

            case 10000000:
                // Achievement: Clicker G.O.A.T.
                getNotificationService().setBackgroundColor(Color.SEASHELL);
                getNotificationService().pushNotification("Got achievement \"Clicker G.O.A.T.\"!");
                break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
