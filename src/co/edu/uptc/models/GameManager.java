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
    // private void createBullets(){
    // for(int i = 0; i < 5; i++){
    // BulletModel bullet = new BulletModel();
    // bullet.setVerticalLimit(600);
    // bulletList.add(bullet);
    // }
    // }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void start() {
        createAliens();
        createCanon();
        for (AlienModel alien : alienList) {
            alien.startMovement();
        }
    }

    private void createAliens() {
        for (int i = 0; i < 5; i++) {
            AlienModel alien = new AlienModel();
            alien.getAlienPojo().setSize(20);
            alien.setMovement(10);
            alien.getAlienPojo().setX(-alien.getAlienPojo().getSize());
            alien.getAlienPojo().setY(-alien.getAlienPojo().getSize());
            alienList.add(alien);
        }
    }

    private void putAlienYCoordinate() {
        // Put aliens y coordenate on random position
        System.out.println(verticalLimit);
        for (AlienModel alien : alienList) {
                alien.getAlienPojo().setY((int) (Math.random() * (verticalLimit / 2)));
        }
    }

    private void putAlienXCoordenate() {
        for (AlienModel alien : alienList) {
            // Check if the horizontal limit is already added
            if (alien.getHorizontalLimit() == 0) {
                // Check aliens direction movement and set x coordenate accordingly
                if (alien.getMovementDirection() == DirectEnum.LEFT) {
                    alien.getAlienPojo().setX(alien.getHorizontalLimit());
                } else if (alien.getMovementDirection() == DirectEnum.RIGHT) {
                    alien.getAlienPojo().setX(-alien.getAlienPojo().getSize());
                }
            }
        }
    }

    private void createCanon() {
        CanonModel canon = new CanonModel();
        canon.getCanonPojo().setSize(60);
        canon.setHorizontalLimit(horizontalLimit);
        canon.setMovement(10);
        canon.getCanonPojo().setX(canon.getHorizontalLimit() / 2);

        canon.getCanonPojo().setY(verticalLimit - canon.getCanonPojo().getSize());
        this.canonModel = canon;
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
    }
}
