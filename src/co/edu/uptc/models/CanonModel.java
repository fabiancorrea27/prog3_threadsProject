package co.edu.uptc.models;

import co.edu.uptc.pojos.CanonPojo;
import co.edu.uptc.utils.DirectEnum;

public class CanonModel {
    private CanonPojo canonPojo = new CanonPojo();
    private int horizontalLimit;
    private boolean running;
    private int speed;

    public void move(DirectEnum direction) {
        if (direction == DirectEnum.LEFT) {
            left();
        } else if (direction == DirectEnum.RIGHT) {
            right();
        }
    }

    private void left() {
        if (running && (canonPojo.getX() >= 0)) {
            canonPojo.setX(canonPojo.getX() - speed);
            if (canonPojo.getX() < 0) {
                canonPojo.setX(0);
            }
        }
    }

    private void right() {
        if (running && (canonPojo.getX() <= (horizontalLimit - canonPojo.getSize()))) {
            canonPojo.setX(canonPojo.getX() + speed);
            if (canonPojo.getX() > (horizontalLimit - canonPojo.getSize())) {
                canonPojo.setX(horizontalLimit - canonPojo.getSize());
            }
        }
    }

    public void startMovement() {
        running = true;
    }

    public void stopMovement() {
        running = false;
    }

    public CanonPojo getCanonPojo() {
        return canonPojo;
    }

    public void setHorizontalLimit(int horizontalLimit) {
        this.horizontalLimit = horizontalLimit;
    }

    public void setSpeed(int movement) {
        this.speed = movement;
    }

    public int getHorizontalLimit() {
        return this.horizontalLimit;
    }
}
