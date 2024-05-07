package co.edu.uptc.models;

import co.edu.uptc.pojos.CanonPojo;
import co.edu.uptc.views.DirectEnum;

public class CanonModel {
    private CanonPojo canonPojo = new CanonPojo();
    private int horizontalLimit;
    private boolean running = false;
    private int movement;

    public void move(DirectEnum direction) {
        if (running) {
            if (direction == DirectEnum.LEFT) {
                left();
            } else if (direction == DirectEnum.RIGHT) {
                right();
            }
        }
    }

    private void left() {
        if (canonPojo.getX() <= 0) {
            canonPojo.setX(canonPojo.getX() - movement);
        }
    }

    private void right() {
        if (canonPojo.getX() >= (horizontalLimit - canonPojo.getSize())) {
            canonPojo.setX(canonPojo.getX() + movement);
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

    
}
