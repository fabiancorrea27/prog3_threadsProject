package co.edu.uptc.models;

import co.edu.uptc.pojos.AlienPojo;
import co.edu.uptc.utils.Util;
import co.edu.uptc.views.DirectEnum;

public class AlienModel {
    private AlienPojo alienPojo = new AlienPojo();
    private DirectEnum movementDirection;
    private boolean running = true;
    private int horizontalLimit;
    private int movement;

    public AlienModel(){
        movementDirection = Util.randomDirection();
    }

    public void startMovement() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                    move();
                }
            }
        });
        thread.start();
    }

    private void move() {
        if (movementDirection == DirectEnum.LEFT) {
            left();
        } else if (movementDirection == DirectEnum.RIGHT) {
            right();
        }
    }

    private void left() {
        alienPojo.setX(alienPojo.getX() - movement);
        if (alienPojo.getX() <= (-alienPojo.getSize())) {
            leftReubication();
        }
    }

    private void leftReubication(){
        alienPojo.setX(horizontalLimit);
    }

    private void right() {
        alienPojo.setX(alienPojo.getX() + movement);
        if (alienPojo.getX() >= horizontalLimit) {
            rightReubication();
        }
    }

    private void rightReubication(){
        alienPojo.setX(-alienPojo.getSize());
    }

    public void stopMovement() {
        running = false;
    }
  
    public AlienPojo getAlienPojo() {
        return alienPojo;
    }

    public void setHorizontalLimit(int horizontalLimit) {
        this.horizontalLimit = horizontalLimit;
    }
    public DirectEnum getMovementDirection() {
        return movementDirection;
    }
    public int getHorizontalLimit() {
        return horizontalLimit;
    }

    public void setMovement(int movement) {
        this.movement = movement;
    }
    
    
}
