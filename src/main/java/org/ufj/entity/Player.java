package org.ufj.entity;

import org.ufj.entity.characters.*;
import org.ufj.game.KeyHandler;
import org.ufj.game.screens.GamePanel;
import org.ufj.util.CharactersID;
import org.ufj.util.Point2D;

import java.awt.*;

public class Player {

    private final KeyHandler tecla;
    private final Character character;
    private final boolean isPlayer1;


    public Player(GamePanel gp, KeyHandler tecla, Point2D startPosition, CharactersID characterID, boolean isPlayer1) {
        this.tecla = tecla;
        this.isPlayer1 = isPlayer1;
        switch (characterID) {
            case RED_CAR:
                this.character = new RedCar(gp.getTileManager(), gp, startPosition, 27);
                break;
            case BLUE_CAR:
                this.character = new BlueCar(gp.getTileManager(), gp, startPosition, 27);
                break;
            case GREEN_CAR:
                this.character = new GreenCar(gp.getTileManager(), gp, startPosition, 27);
                break;
            case RED_PICKUP:
                this.character = new RedPickup(gp.getTileManager(), gp, startPosition, 27);
                break;
            case BUS:
                this.character = new Bus(gp.getTileManager(), gp, startPosition, 27);
                break;
            case NONE:
            default:
                this.character = new Character(gp.getTileManager(), gp, startPosition, 27);
        }
        this.character.getImagePlayer();
    }

    public void update(CollisionDetector cd) {
        // Keys
        if (isPlayer1) {
            if (tecla.upPressedP1 || tecla.downPressedP1 || tecla.leftPressedP1 || tecla.rightPressedP1) {
                // Will accelerate the player1
                if (tecla.upPressedP1) {
                    character.accelerate();
                }
                if (tecla.downPressedP1) {
                    character.carBrake();
                }

                // Will turn the player1
                if (tecla.leftPressedP1) {
                    character.turn(-1);
                }
                if (tecla.rightPressedP1) {
                    character.turn(1);
                }
//            System.out.println("Player is moving | Acceleration: " + character.acceleration + " | Speed X: " + character.velocityX + " | Speed Y: " + character.velocityY + " | Angle: " + character.turning + " | Position: " + character.getPosition().toString());
            } else {
                // Not moving or accelerating
                character.decelerate();
            }
        } else {
            if (tecla.upPressedP2 || tecla.downPressedP2 || tecla.leftPressedP2 || tecla.rightPressedP2) {
                // Will accelerate the player2
                if (tecla.upPressedP2) {
                    character.accelerate();
                }
                if (tecla.downPressedP2) {
                    character.carBrake();
                }

                // Will turn the player2
                if (tecla.leftPressedP2) {
                    character.turn(-1);
                }
                if (tecla.rightPressedP2) {
                    character.turn(1);
                }
            } else {
                // Not moving or accelerating
                character.decelerate();
            }
        }

        character.spriteIncrement();
        character.update(cd);
    }

    public void draw(Graphics2D g2) {
        character.draw(g2);
    }

    public Character getCharacter() {
        return character;
    }
}