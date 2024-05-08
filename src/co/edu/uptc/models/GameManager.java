package co.edu.uptc.models;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.pojos.AlienPojo;
import co.edu.uptc.pojos.BulletPojo;
import co.edu.uptc.pojos.CanonPojo;
import co.edu.uptc.pojos.ObjectPojo;
import co.edu.uptc.presenters.ContractPlay;
import co.edu.uptc.presenters.ContractPlay.Presenter;
import co.edu.uptc.utils.ConfigValue;
import co.edu.uptc.utils.DirectEnum;
import co.edu.uptc.utils.Util;

public class GameManager implements ContractPlay.Model {
    private ContractPlay.Presenter presenter;
    private List<AlienModel> alienList = new ArrayList<AlienModel>();
    private List<BulletModel> bulletList = new ArrayList<BulletModel>();
    private CanonModel canonModel = new CanonModel();
    private int horizontalLimit, verticalLimit;
    private boolean running = false;
    private int aliensAmount, aliensEliminatedAmount;

    public GameManager() {

    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void start() {
        running = true;
        createAndAddObjects();
        aliensAmount = alienList.size();
        createAliensMovementThread();
        createBulletsMovementThread();
        canonModel.startMovement();
        checkAlienStrikeThread();
    }

    private void createAndAddObjects() {
        createAndAddAliens();
        createAndAddCanon();
        createAndAddBullets();
    }

    private void createAndAddAliens() {
        int aliensAmount = Integer.parseInt(ConfigValue.getProperty("aliensAmount"));
        for (int i = 0; i < aliensAmount; i++) {
            AlienModel alien = createAlien();
            alienList.add(alien);
        }
    }

    // Create a Alien with a initial position
    private AlienModel createAlien() {
        int alienMinSize = Integer.parseInt(ConfigValue.getProperty("alienMinSize"));
        int alienMaxSize = Integer.parseInt(ConfigValue.getProperty("alienMaxSize"));
        int alienMinSpeed = Integer.parseInt(ConfigValue.getProperty("alienMinSpeed"));
        int alienMaxSpeed = Integer.parseInt(ConfigValue.getProperty("alienMaxSpeed"));
        AlienModel alien = new AlienModel();
        alien.getAlienPojo().setSize((int) (Math.random() * (alienMaxSize - alienMinSize + 1)) + alienMinSize);
        alien.setSpeed((int) (Math.random() * (alienMaxSpeed - alienMinSpeed + 1)) + alienMinSpeed);
        alien.getAlienPojo().setX(-alien.getAlienPojo().getSize());
        alien.getAlienPojo().setY(-alien.getAlienPojo().getSize());
        return alien;
    }

    private void createAndAddCanon() {
        int canonSize = Integer.parseInt(ConfigValue.getProperty("canonSize"));
        int canonSpeed = Integer.parseInt(ConfigValue.getProperty("canonSpeed"));
        CanonModel canon = new CanonModel();
        canon.getCanonPojo().setSize(canonSize);
        canon.setSpeed(canonSpeed);
        this.canonModel = canon;
    }

    private void createAndAddBullets() {
        int bulletsAmount = Integer.parseInt(ConfigValue.getProperty("bulletsAmount"));
        int bulletSpeed = Integer.parseInt(ConfigValue.getProperty("bulletSpeed"));
        int bulletSize = Integer.parseInt(ConfigValue.getProperty("bulletSize"));
        for (int i = 0; i < bulletsAmount; i++) {
            BulletModel bullet = new BulletModel();
            bullet.setSpeed(bulletSpeed);
            bullet.getBulletPojo().setSize(bulletSize);
            bulletList.add(bullet);
        }
    }

    private void createAliensMovementThread() {
        int threadDelay = Integer.parseInt(ConfigValue.getProperty("aliensThreadDelay"));
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                   Util.sleep(threadDelay);
                    
                    for (int i = 0; i < alienList.size(); i++) {
                        alienList.get(i).move();
                    }
                }
            }
        });
        thread.setName(ConfigValue.getProperty("aliensThreadName"));
        thread.start();
    }

    private void createBulletsMovementThread() {
        int threadDelay = Integer.parseInt(ConfigValue.getProperty("bulletsThreadDelay"));
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                   Util.sleep(threadDelay);
                    for (BulletModel bulletModel : bulletList) {
                        if (bulletModel.getBulletPojo().isVisible()) {
                            bulletModel.move();
                        }
                    }
                }
            }
        });
        thread.setName(ConfigValue.getProperty("bulletsThreadName"));
        thread.start();
    }

    @Override
    public List<AlienPojo> getAliensPojo() {

        List<AlienPojo> alienPojoList = new ArrayList<AlienPojo>();
        for (int i = 0; i < alienList.size(); i++) {
            alienPojoList.add(alienList.get(i).getAlienPojo());
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
        updateAlienXCoordenate();
    }

    @Override
    public void setVerticalLimit(int verticalLimit) {
        this.verticalLimit = verticalLimit;
        updateAlienYCoordinate();
        updateCanonCoordenates();
    }

    private void updateAlienYCoordinate() {
        // Set aliens y-coordinate on random position in the first half
        for (AlienModel alien : alienList) {
            alien.getAlienPojo().setY(calculateAlienYCoordinate());
        }
    }

    private int calculateAlienYCoordinate() {
        return ((int) (Math.random() * (verticalLimit / 2)));
    }

    private void updateAlienXCoordenate() {
        for (AlienModel alien : alienList) {
            // Check aliens direction movement and set x coordenate accordingly
            alien.setHorizontalLimit(horizontalLimit);
            alien.getAlienPojo().setX(calculateAlienXCoordinate(alien));
        }
    }

    private int calculateAlienXCoordinate(AlienModel alien) {
        int coordinate = 0;
        if (alien.getMovementDirection() == DirectEnum.LEFT) {
            coordinate = alien.getHorizontalLimit();
        } else if (alien.getMovementDirection() == DirectEnum.RIGHT) {
            coordinate = -alien.getAlienPojo().getSize();
        }
        return coordinate;
    }

    private void updateCanonCoordenates() {
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
            // Verify if the bullet is already used
            if (!bulletList.get(i).isRunning()) {
                // Set x-coordinate of bullet to the middle of the canon
                bulletList.get(i).getBulletPojo()
                        .setX(canonModel.getCanonPojo().getX() + (canonModel.getCanonPojo().getSize() / 2)
                                - (bulletList.get(i).getBulletPojo().getSize() / 2));

                // Set y-coordinate of the bullet to the same position of the canon
                bulletList.get(i).getBulletPojo().setY(canonModel.getCanonPojo().getY());
                bulletList.get(i).startMovement();
                shot = true;
            }
        }
    }

    private void checkAlienStrikeThread() {
        int threadDelay = Integer.parseInt(ConfigValue.getProperty("alienStrikeThreadDelay"));
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                   Util.sleep(threadDelay);
                    checkAlienShot();

                }
            }
        });
        thread.setName(ConfigValue.getProperty("alienStrikeThreadName"));
        thread.start();
    }

    private void checkAlienShot() {
        boolean collision = false;
        for (BulletModel bulletModel : bulletList) {
            if (bulletModel.isRunning()) {
                for (int i = 0; i < alienList.size() && !collision; i++) {
                    collision = checkCollision(bulletModel, alienList.get(i));
                }
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
            collisionEvent(bulletModel, alienModel);
        }
        return collision;
    }

    // Actions to do when the collision is detected
    private void collisionEvent(BulletModel bulletModel, AlienModel alienModel) {
        alienList.remove(alienModel);
        alienList.add(createNewAlient());
        bulletModel.stopMovement();
        aliensEliminatedAmount++;
        presenter.updateAliensEliminated(aliensEliminatedAmount);

    }

    private AlienModel createNewAlient() {
        AlienModel alien = createAlien();
        alien.setHorizontalLimit(horizontalLimit);
        alien.getAlienPojo().setX(calculateAlienXCoordinate(alien));
        alien.getAlienPojo().setY(calculateAlienYCoordinate());
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

    @Override
    public int getAliensAmount() {
        return aliensAmount;
    }

}
