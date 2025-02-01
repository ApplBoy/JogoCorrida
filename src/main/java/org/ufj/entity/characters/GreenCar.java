package org.ufj.entity.characters;

import org.ufj.entity.Character;
import org.ufj.game.screens.GamePanel;
import org.ufj.tile.TileManager;
import org.ufj.util.Point2D;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GreenCar extends Character {
    int carWidth = 16;

    // Constructor need the super arguments
    public GreenCar(TileManager tm, GamePanel gp, Point2D position, double angle) {
        super(tm, gp, position, angle);
        // Initialize specific attributes for PlayerCharacter

        /*
         GREEN CAR: Slow but easy to control

            - Acceleration: ####------  (4)
            - Max Speed:    ####------  (4)
            - Handling:     ########--  (8)

        */
        name = "Green Car";
        description = "Slow but easy to control";

        accelerationSpeed = 4;
        maxSpeed = 4;
        turnSpeed = 8f;
    }

    @Override
    public void getImagePlayer() {
        String playerResourcePath = "/assets/cars/car02/";

        try {
            up1 = loadImage(playerResourcePath + "car02iso_0005.png");
            down1 = loadImage(playerResourcePath + "car02iso_0001.png");
            left1 = loadImage(playerResourcePath + "car02iso_0003.png");
            right1 = loadImage(playerResourcePath + "car02iso_0007.png");
            upLeft1 = loadImage(playerResourcePath + "car02iso_0000.png");
            upRight1 = loadImage(playerResourcePath + "car02iso_0006.png");
            downLeft1 = loadImage(playerResourcePath + "car02iso_0002.png");
            downRight1 = loadImage(playerResourcePath + "car02iso_0004.png");
        } catch (IOException e) {
            Logger.getLogger(GreenCar.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
