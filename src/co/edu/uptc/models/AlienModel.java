package co.edu.uptc.models;

import co.edu.uptc.pojos.AlienPojo;
import co.edu.uptc.utils.DirectEnum;
import co.edu.uptc.utils.Util;

public class AlienModel {
    private AlienPojo alienPojo = new AlienPojo();
    private DirectEnum movementDirection;
    private int horizontalLimit;
    private int speed;

    public AlienModel(){
        movementDirection = Util.randomDirection();
    }

    public void move() {
        if (movementDirection == DirectEnum.LEFT) {
            moveLeft();
        } else if (movementDirection == DirectEnum.RIGHT) {
            moveRight();
        }
    }

    private void moveLeft() {
        alienPojo.setX(alienPojo.getX() - speed);
        if (alienPojo.getX() <= (-alienPojo.getSize())) {
            leftReubication();
        }
    }

    private void leftReubication(){
        alienPojo.setX(horizontalLimit);
    }

    private void moveRight() {
        alienPojo.setX(alienPojo.getX() + speed);
        if (alienPojo.getX() >= horizontalLimit) {
            rightReubication();
        }
    }

    private void rightReubication(){
        alienPojo.setX(-alienPojo.getSize());
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

    public void setSpeed(int movement) {
        this.speed = movement;
    }
    
    
}
