package org.ufj.entity.characters;

import org.ufj.entity.Character;
import org.ufj.game.screens.GamePanel;
import org.ufj.tile.TileManager;
import org.ufj.util.Point2D;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BlueCar extends Character {
    int carWidth = 16;

    // Constructor need the super arguments
    public BlueCar(TileManager tm, GamePanel gp, Point2D position, double angle) {
        super(tm, gp, position, angle);
        // Initialize specific attributes for PlayerCharacter

        /*
         BLUE CAR: "Balanced"

            - Acceleration: #####-----  (5)
            - Max Speed:    ######----  (6)
            - Handling:     #####-----  (5)

        */
        name = "Blue Car";
        description = "Balanced";

        accelerationSpeed = 5;
        maxSpeed = 6;
        turnSpeed = 5f;
    }

    @Override
    public void getImagePlayer() {
        String playerResourcePath = "/assets/cars/car03/";

        try {
            up1 = loadImage(playerResourcePath + "car03iso_0005.png");
            down1 = loadImage(playerResourcePath + "car03iso_0001.png");
            left1 = loadImage(playerResourcePath + "car03iso_0003.png");
            right1 = loadImage(playerResourcePath + "car03iso_0007.png");
            upLeft1 = loadImage(playerResourcePath + "car03iso_0000.png");
            upRight1 = loadImage(playerResourcePath + "car03iso_0006.png");
            downLeft1 = loadImage(playerResourcePath + "car03iso_0002.png");
            downRight1 = loadImage(playerResourcePath + "car03iso_0004.png");
        } catch (IOException e) {
            Logger.getLogger(BlueCar.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
