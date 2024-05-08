package co.edu.uptc.models;

import co.edu.uptc.pojos.BulletPojo;

public class BulletModel {
    private BulletPojo bulletPojo = new BulletPojo();
    private boolean running;
    private int speed;

    public void threadBullet() {
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
        
        if (bulletPojo.getY() >= (-bulletPojo.getSize())) {
            bulletPojo.setY(bulletPojo.getY() - speed);
        } else {
            bulletPojo.setVisible(false);
            running = false;
        }
        
    }

    public void startMovement(){
        bulletPojo.setVisible(true);
        this.running = true;
        threadBullet();
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
