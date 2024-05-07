package co.edu.uptc.models;

import co.edu.uptc.pojos.BulletPojo;

public class BulletModel {
    private BulletPojo bulletPojo = new BulletPojo();
    private boolean running;
    private int movement;
    private int verticalLimit;

    public void startMovement() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    move();
                }
            }
        });
        thread.start();
    }

    private void move() {
        if (bulletPojo.getY() >= (verticalLimit + bulletPojo.getSize())) {
            bulletPojo.setY(bulletPojo.getY() + movement);
        } else {
            bulletPojo.setVisible(false);
            running = false;
        }
    }

    public void setMovement(int movement) {
        this.movement = movement;
    }

    public void setVerticalLimit(int verticalLimit) {
        this.verticalLimit = verticalLimit;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public BulletPojo getBulletPojo() {
        return bulletPojo;
    }
}
