package co.edu.uptc.models;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.pojos.AlienPojo;
import co.edu.uptc.pojos.BulletPojo;
import co.edu.uptc.pojos.CanonPojo;
import co.edu.uptc.presenters.ContractPlay;
import co.edu.uptc.presenters.ContractPlay.Presenter;
import co.edu.uptc.views.DirectEnum;

public class GameManager implements ContractPlay.Model {
    private ContractPlay.Presenter presenter;
    private List<AlienModel> alienList = new ArrayList<AlienModel>();
    private List<BulletModel> bulletList = new ArrayList<BulletModel>();
    private CanonModel canonModel = new CanonModel();
    private int horizontalLimit, verticalLimit;

    public GameManager() {

    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void start() {
        createAliens();
        createCanon();
        createBullets();
        for (AlienModel alien : alienList) {
            alien.startMovement();
        }
        canonModel.startMovement();
    }

    private void createAliens() {
        for (int i = 0; i < 5; i++) {
            AlienModel alien = new AlienModel();
            alien.getAlienPojo().setSize(50);
            alien.setSpeed(10);
            alien.getAlienPojo().setX(-alien.getAlienPojo().getSize());
            alien.getAlienPojo().setY(-alien.getAlienPojo().getSize());
            alienList.add(alien);
        }
    }

    private void createCanon() {
        CanonModel canon = new CanonModel();
        canon.getCanonPojo().setSize(60);
        canon.setSpeed(10);
        this.canonModel = canon;
    }

    private void createBullets() {
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
            alien.getAlienPojo().setY((int) (Math.random() * (verticalLimit / 2)));
        }
    }

    private void putAlienXCoordenate() {
        for (AlienModel alien : alienList) {
            // Check aliens direction movement and set x coordenate accordingly
            alien.setHorizontalLimit(horizontalLimit);
            if (alien.getMovementDirection() == DirectEnum.LEFT) {
                alien.getAlienPojo().setX(alien.getHorizontalLimit());
            } else if (alien.getMovementDirection() == DirectEnum.RIGHT) {
                alien.getAlienPojo().setX(-alien.getAlienPojo().getSize());
            }
        }
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
        for(int i = 0; i < bulletList.size() && !shot; i++) {
            if(!bulletList.get(i).isRunning()){
                bulletList.get(i).getBulletPojo().setX(canonModel.getCanonPojo().getX());
                bulletList.get(i).getBulletPojo().setY(canonModel.getCanonPojo().getY());
                bulletList.get(i).startMovement();
                shot = true;
            }
        }
    }
}
