package org.ufj.entity.characters;

import org.ufj.entity.Character;
import org.ufj.game.screens.GamePanel;
import org.ufj.tile.TileManager;
import org.ufj.util.Point2D;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RedCar extends Character {
    int carWidth = 16;

    // Constructor need the super arguments
    public RedCar(TileManager tm, GamePanel gp, Point2D position, double angle) {
        super(tm, gp, position, angle);
        // Initialize specific attributes for PlayerCharacter

        /*
         RED CAR: "Fast but hard to control"

            - Acceleration: #######---  (7)
            - Max Speed:    ######----  (6)
            - Handling:     ##--------  (2)

        */
        name = "Red Car";
        description = "Fast but hard to control";

        accelerationSpeed = 7;
        maxSpeed = 6;
        turnSpeed = 2f;
    }

    @Override
    public void getImagePlayer() {
        String playerResourcePath = "/assets/cars/car01/";

        try {
            up1 = loadImage(playerResourcePath + "car01iso_0005.png");
            down1 = loadImage(playerResourcePath + "car01iso_0001.png");
            left1 = loadImage(playerResourcePath + "car01iso_0003.png");
            right1 = loadImage(playerResourcePath + "car01iso_0007.png");
            upLeft1 = loadImage(playerResourcePath + "car01iso_0000.png");
            upRight1 = loadImage(playerResourcePath + "car01iso_0006.png");
            downLeft1 = loadImage(playerResourcePath + "car01iso_0002.png");
            downRight1 = loadImage(playerResourcePath + "car01iso_0004.png");
        } catch (IOException e) {
            Logger.getLogger(RedCar.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
