package org.ufj.entity;

import org.ufj.game.screens.GamePanel;
import org.ufj.tile.TileManager;
import org.ufj.util.Direction;
import org.ufj.util.Point2D;
import org.ufj.util.RotatedRectangle;
import org.ufj.util.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class Character extends Entity {
    // Per character attributes placeholder
    public String description = "This is an entity";
    protected float turnSpeed = 0.5f;
    protected int maxSpeed = 5;
    protected int accelerationSpeed = 1;
    protected int accelerationDelay = 0;
    protected boolean isTurning = false;
    protected boolean crashed = false;
    protected int crashedDelay = 0;

    // Cardinal sprites
    protected BufferedImage up1, up2;
    protected BufferedImage down1, down2;
    protected BufferedImage left1, left2;
    protected BufferedImage right1, right2;
    // Diagonal sprites
    protected BufferedImage upLeft1, upLeft2;
    protected BufferedImage upRight1, upRight2;
    protected BufferedImage downLeft1, downLeft2;
    protected BufferedImage downRight1, downRight2;
    // 45 degree sprites
    protected BufferedImage up45_1, up45_2;
    protected BufferedImage down45_1, down45_2;
    protected BufferedImage left45_1, left45_2;
    protected BufferedImage right45_1, right45_2;
    // 135 degree sprites
    protected BufferedImage up135_1, up135_2;
    protected BufferedImage down135_1, down135_2;
    protected BufferedImage left135_1, left135_2;
    protected BufferedImage right135_1, right135_2;

    // Car size attributes
    protected final int carWidth = 16;
    protected final int carHeight = 16;

    // VelocityX and VelocityY are the speed of the entity in the X and Y axis calculated by the turn and acceleration
    protected double velocityX, velocityY;
    protected double acceleration;
    protected double turning, turningOld = 0;
    protected Direction direction;

    private final TileManager tm;
    public int spriteCounter;
    public int spriteNum = 1;

    // For music and sound effects
    protected final GamePanel gp;

    // Game logic
    private int lapsCompleted = 0;

    public Character(TileManager tm, GamePanel gp, Point2D position, double angle) {
        this.tm = tm;
        this.gp = gp;
        this.position = position;
        this.isSolid = true;
        this.velocityX = 0;
        this.velocityY = 0;
        this.turning = angle;
        this.direction = Direction.getDirection(turning);
        this.collisionBox = new RotatedRectangle(position, carWidth, carHeight, angle); // Map defined size
    }

    public void move(CollisionDetector cd, Point2D newPosition) {
        if (!cd.iterateCollisions(this, newPosition) && !crashed) {
            this.position = newPosition;
            collisionBox.setLocation((int) position.getX(), (int) position.getY());
        } else if (!crashed) {
            // Give a little push to the car
            this.position = new Point2D(position.getX() - velocityX / 2, position.getY() - velocityY / 2);
            collisionBox.setLocation((int) position.getX(), (int) position.getY());
            acceleration = 0;
            // Disable the acceleration for a moment
            crashed = true;
            // And an audio queue
            // AudioPlayer.playSound("/assets/sounds/crash.wav");
            // 50/50 to be crash1.mp3 and crash2.mp3
            int crashSound = Math.random() < 0.5 ? 0 : 1;
            gp.playSound(crashSound);
        } else if (crashedDelay > 10) {
            crashed = false;
            crashedDelay = 0;
        } else {
            crashedDelay++;
        }
    }

    public void update(CollisionDetector cd) {
        updateVelocity();
        // this.position.move(velocityX, velocityY);
        // The position movement is borked, let's try another way
        // Now with the collision detection
        Point2D newPosition = new Point2D(position.getX() + velocityX, position.getY() + velocityY);
        move(cd, newPosition);
        if (isTurning) { // Add this condition to reset the turning flag
            isTurning = false;
        }
        direction = Direction.getDirection(turning);
    }

    // Placeholder for loading the character sprites
    public void getImagePlayer() {
    }

    @Override
    protected BufferedImage loadImage(String path) throws IOException {
        BufferedImage originalImage = read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        int newHeight = (int) (carWidth * (double) originalImage.getHeight() / originalImage.getWidth());
        BufferedImage resizedImage = new BufferedImage(carWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, carWidth, newHeight, null);
        g2d.dispose();
        return resizedImage;
    }

    protected boolean allImagesAreSet() {
        return up1 != null &&
                down1 != null &&
                left1 != null &&
                right1 != null &&
                upLeft1 != null &&
                upRight1 != null &&
                downLeft1 != null &&
                downRight1 != null;
    }

    protected void drawImage(Graphics2D g2, BufferedImage image) {
        g2.drawImage(image, (int) position.getX(), (int) position.getY(), null);
    }

    // Placeholder for drawing the character
    public void draw(Graphics2D g2) {
        // Maybe optimize this later
        if (allImagesAreSet()) {
            // Add specific drawing code for PlayerCharacter
            switch (direction) {
                case UP:
                    drawImage(g2, up1);
                    break;
                case UP_RIGHT:
                    drawImage(g2, upRight1);
                    break;
                case RIGHT:
                    drawImage(g2, right1);
                    break;
                case DOWN_RIGHT:
                    drawImage(g2, downRight1);
                    break;
                case DOWN:
                    drawImage(g2, down1);
                    break;
                case DOWN_LEFT:
                    drawImage(g2, downLeft1);
                    break;
                case LEFT:
                    drawImage(g2, left1);
                    break;
                case UP_LEFT:
                    drawImage(g2, upLeft1);
                    break;
            }
        } else {
            // Apply rotation
            Graphics2D g2d = (Graphics2D) g2.create();
            // g2d.rotate(Math.toRadians(turning), position.getX() + (double) collisionBox.width / 2, position.getY() + (double) collisionBox.height / 2);
            // Placeholder for drawing the character, draw the diagonal rectangle
            collisionBox.setRotation(turning);
            collisionBox.draw(g2d);
            // Draw a line indicating the forward direction
            int lineLength = 30; // Length of the forward direction line
            Vector2D forwardVector = Vector2D.fromAngle(turning);
            double endX = position.getX() + forwardVector.x * lineLength;
            double endY = position.getY() + forwardVector.y * lineLength;

            g2d.setColor(Color.RED); // Color of the forward direction line
            g2d.drawLine((int) position.getX() + collisionBox.width / 2, (int) position.getY() + collisionBox.height / 2, (int) endX, (int) endY);
            g2d.dispose();
        }
    }

    public boolean tileIsSlow() {
        return tm.getTile(position) != null && tm.getTile(position).isSlow();
    }

    private void updateVelocity() {
        if (acceleration == 0) {
            accelerationDelay = 0;
        }

        // Now, check the tile it's on and adjust the speed accordingly
        // if the tile isSlow, then reduce the speed by half (maybe 1/3)
        double acceleration = this.acceleration;
        if (tileIsSlow()) {
            acceleration = acceleration / 3;
        }

        Vector2D forwardVector = Vector2D.fromAngle(turning);

        this.velocityX = forwardVector.x * acceleration;
        this.velocityY = forwardVector.y * acceleration;
    }

    public void accelerate() {
        if (acceleration < maxSpeed && accelerationDelay == 0) {
            this.acceleration += (double) this.accelerationSpeed / 3;
        }
        accelerationDelay++;
        if (accelerationDelay > 100) {
            accelerationDelay = 0;
        }
    }

    public void decelerate() {
        if (acceleration > 0) {
            this.acceleration -= 1;
        } else if (acceleration < 0) {
            this.acceleration += 1;
        }
        // Acceleration is now a double, so we need to find if it's stuck between -1 and 1.
        if (acceleration < 1 && acceleration > -1) {
            acceleration = 0;
        }
    }

    public void carBrake() {
        if (acceleration > maxSpeed * -0.5 && accelerationDelay == 0) {
            this.acceleration -= (double) (this.accelerationSpeed / 3) / 2;
        }
        accelerationDelay++;
        if (accelerationDelay > 100) {
            accelerationDelay = 0;
        }
    }

    public void turn(float angle) {
        this.isTurning = true;
        this.turningOld = this.turning;
        this.turning = normalizeAngle(this.turning + (angle * turnSpeed));
    }

    private double normalizeAngle(double angle) {
        angle = angle % 360;
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }

    public Point2D getPosition() {
        return this.position;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void spriteIncrement() {
        spriteCounter++;
        if (spriteCounter % 10 == 0) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
        }
    }

    public int getLaps() {
        return lapsCompleted;
    }

    public void completeLap() {
        lapsCompleted++;
    }
}
