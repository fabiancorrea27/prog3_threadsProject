package co.edu.uptc.models;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.pojos.AlienPojo;
import co.edu.uptc.pojos.BulletPojo;
import co.edu.uptc.pojos.CanonPojo;
import co.edu.uptc.presenters.ContractPlay;
import co.edu.uptc.presenters.ContractPlay.Presenter;
import co.edu.uptc.views.DirectEnum;
import co.edu.uptc.pojos.ObjectPojo;

public class GameManager implements ContractPlay.Model {
    private ContractPlay.Presenter presenter;
    private List<AlienModel> alienList = new ArrayList<AlienModel>();
    private List<BulletModel> bulletList = new ArrayList<BulletModel>();
    private CanonModel canonModel = new CanonModel();
    private int horizontalLimit, verticalLimit;
    private final Object bulletLock = new Object();

    public GameManager() {

    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void start() {
        createAndAddAliens();
        createAndAddCanon();
        createAndAddBullets();
        for (AlienModel alien : alienList) {
            alien.startMovement();
        }
        canonModel.startMovement();
        checkAlienStrikeThread();
    }

    private void createAndAddAliens() {
        for (int i = 0; i < 5; i++) {
            AlienModel alien = createAlien();
            alienList.add(alien);
        }
    }

    // Create a Alien with a initial position
    private AlienModel createAlien() {
        AlienModel alien = new AlienModel();
        alien.getAlienPojo().setSize(50);
        alien.setSpeed(10);
        alien.getAlienPojo().setX(-alien.getAlienPojo().getSize());
        alien.getAlienPojo().setY(-alien.getAlienPojo().getSize());
        return alien;
    }

    private void createAndAddCanon() {
        CanonModel canon = new CanonModel();
        canon.getCanonPojo().setSize(60);
        canon.setSpeed(10);
        this.canonModel = canon;
    }

    private void createAndAddBullets() {
        for (int i = 0; i < 5; i++) {
            BulletModel bullet = new BulletModel();
            bullet.setSpeed(10);
            bullet.getBulletPojo().setSize(60);
            bulletList.add(bullet);
        }
    }

    @Override
    public void stop() {
        for (AlienModel alien : alienList) {
            alien.stopMovement();
        }
    }

    @Override
    public List<AlienPojo> getAliensPojo() {
        List<AlienPojo> alienPojoList = new ArrayList<AlienPojo>();
        for (AlienModel alien : alienList) {
            alienPojoList.add(alien.getAlienPojo());
        }
        return alienPojoList;
    }

    @Override
    public List<BulletPojo> getBulletsPojo() {
        List<BulletPojo> bulletPojoList = new ArrayList<BulletPojo>();
        for (BulletModel bullet : bulletList) {
            bulletPojoList.add(bullet.getBulletPojo());
        }
        return bulletPojoList;
    }

    @Override
    public CanonPojo getCanonPojo() {
        return canonModel.getCanonPojo();
    }

    @Override
    public void setHorizontalLimit(int horizontalLimit) {
        this.horizontalLimit = horizontalLimit;
        putAlienXCoordenate();
    }

    @Override
    public void setVerticalLimit(int verticalLimit) {
        this.verticalLimit = verticalLimit;
        putAlienYCoordinate();
        putCanonCoordenates();
    }

    private void putAlienYCoordinate() {
        // Set aliens y-coordinate on random position in the first half
        for (AlienModel alien : alienList) {
            alien.getAlienPojo().setY(calculeteAlienYCoordinate());
        }
    }

    private int calculeteAlienYCoordinate() {
        return ((int) (Math.random() * (verticalLimit / 2)));
    }

    private void putAlienXCoordenate() {
        for (AlienModel alien : alienList) {
            // Check aliens direction movement and set x coordenate accordingly
            alien.setHorizontalLimit(horizontalLimit);
            alien.getAlienPojo().setX(calculeteAlienXCoordinate(alien));
        }
    }

    private int calculeteAlienXCoordinate(AlienModel alien) {
        int coordinate = 0;
        if (alien.getMovementDirection() == DirectEnum.LEFT) {
            coordinate = alien.getHorizontalLimit();
        } else if (alien.getMovementDirection() == DirectEnum.RIGHT) {
            coordinate = -alien.getAlienPojo().getSize();
        }
        return coordinate;
    }

    private void putCanonCoordenates() {
        canonModel.setHorizontalLimit(horizontalLimit);
        // Set canon x-coordinate at the middle
        canonModel.getCanonPojo()
                .setX((canonModel.getHorizontalLimit() / 2) - (canonModel.getCanonPojo().getSize() / 2));
        // Set canon y-coordinate at the bottom
        canonModel.getCanonPojo().setY(verticalLimit - canonModel.getCanonPojo().getSize());
    }

    @Override
    public void moveCanon(DirectEnum directEnum) {
        canonModel.move(directEnum);
    }

    @Override
    public void shootBullet() {
        boolean shot = false;
        for (int i = 0; i < bulletList.size() && !shot; i++) {
            if (!bulletList.get(i).isRunning()) {
                bulletList.get(i).getBulletPojo().setX(canonModel.getCanonPojo().getX());
                bulletList.get(i).getBulletPojo().setY(canonModel.getCanonPojo().getY());
                bulletList.get(i).startMovement();
                shot = true;
            }
        }
    }

    private void checkAlienStrikeThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                    }
                    checkAlienShot();
                    
                }
            }
        });
        thread.setName("Alien Strake Thread");
        thread.start();
    }

    private void checkAlienShot() {
        boolean collision = false;
        for (BulletModel bulletModel : bulletList) {
            synchronized(bulletLock){
            if (bulletModel.isRunning()) {
                for (int i = 0; i < alienList.size() && !collision; i++) {
                    collision = checkCollision(bulletModel, alienList.get(i));
                }
            }
            bulletLock.notifyAll();
        }
        }
    }

    // Check if there is a collision between the bullet and the alien
    private boolean checkCollision(BulletModel bulletModel, AlienModel alienModel) {
        boolean collision = false;
        Point[] bulletCorners = objectCorners(bulletModel.getBulletPojo());
        Point[] alienCorners = objectCorners(alienModel.getAlienPojo());
        if (checkPointsCollision(bulletCorners, alienCorners)) {
            collision = true;
            alienList.remove(alienModel);
            alienList.add(createNewAlient());   
            bulletModel.stopMovement();
        }
        return collision;
    }

    private AlienModel createNewAlient() {
        AlienModel alien = createAlien();
        alien.setHorizontalLimit(horizontalLimit);
        alien.getAlienPojo().setX(calculeteAlienXCoordinate(alien));
        alien.getAlienPojo().setY(calculeteAlienYCoordinate());
        alien.startMovement();
        return alien;
    }

    private boolean checkPointsCollision(Point[] bulletCorners, Point[] alienCorners) {
        boolean overlapX = (bulletCorners[0].getX() < alienCorners[3].getX()
                && bulletCorners[3].getX() > alienCorners[0].getX());
        boolean overlapY = (bulletCorners[0].getY() < alienCorners[3].getY()
                && bulletCorners[3].getY() > alienCorners[0].getY());
        return (overlapX && overlapY);
    }

    private Point[] objectCorners(ObjectPojo objectPojo) {
        Point[] corners = new Point[4];
        corners[0] = new Point(objectPojo.getX(), objectPojo.getY());
        corners[1] = new Point(objectPojo.getX() + objectPojo.getSize(), objectPojo.getY());
        corners[2] = new Point(objectPojo.getX(), objectPojo.getY() + objectPojo.getSize());
        corners[3] = new Point(objectPojo.getX() + objectPojo.getSize(), objectPojo.getY() + objectPojo.getSize());
        return corners;
    }
}
