package co.edu.uptc.models;

import co.edu.uptc.pojos.BulletPojo;

public class BulletModel {
    private BulletPojo bulletPojo = new BulletPojo();
    private boolean running;
    private int speed;

    

    public void move() {
        if (bulletPojo.getY() >= (-bulletPojo.getSize())) {
            bulletPojo.setY(bulletPojo.getY() - speed);
        } else {
            stopMovement();
        }

    }

    public void startMovement() {
        bulletPojo.setVisible(true);
        running = true;
    }

    public void stopMovement() {
        bulletPojo.setVisible(false);
        running = false;
    }

    public void setSpeed(int movement) {
        this.speed = movement;
    }

    public BulletPojo getBulletPojo() {
        return bulletPojo;
    }

    public boolean isRunning() {
        return running;
    }
}
