package co.edu.uptc.models;

import co.edu.uptc.pojos.AlienPojo;
import co.edu.uptc.views.DirectEnum;

public class AlienModel {
    private AlienPojo alienPojo;
    private DirectEnum direction;
    private boolean running;
    private int horizontalLimit;
    private int movement;

    public void startAlienMovement() {
        Thread thread = new Thread(() -> {
            while(running){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                move();
            }
        });
        thread.start();
    }

    private void move() {
        if (direction == DirectEnum.LEFT) {
            left();
        } else {
            right();
        }
    }

    private void left() {
        alienPojo.setX(alienPojo.getX() - movement);
        if (alienPojo.getX() <= (alienPojo.getSize() / 2)) {
            direction = DirectEnum.RIGHT;
        }
    }

    private void right() {
        alienPojo.setX(alienPojo.getX() + movement);
        if (alienPojo.getX() >= (horizontalLimit - (alienPojo.getSize() / 2))) {
            direction = DirectEnum.LEFT;
        }
    }

}
