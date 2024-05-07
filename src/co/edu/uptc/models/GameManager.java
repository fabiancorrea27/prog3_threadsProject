package co.edu.uptc.models;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.pojos.AlienPojo;
import co.edu.uptc.pojos.BulletPojo;
import co.edu.uptc.pojos.CanonPojo;
import co.edu.uptc.presenters.ContractPlay;
import co.edu.uptc.presenters.ContractPlay.Presenter;
import co.edu.uptc.views.DirectEnum;

public class GameManager implements ContractPlay.Model{
    ContractPlay.Presenter presenter;
    List<AlienModel> alienList = new ArrayList<AlienModel>();
    List<BulletModel> bulletList = new ArrayList<BulletModel>();
    CanonModel canonModel = new CanonModel();

    public GameManager(){
        
    }

    private void setAlienLocation(AlienModel alien){
        // Check alien direction movement and set x coordenate accordingly
        if(alien.getMovementDirection() == DirectEnum.LEFT){
            alien.getAlienPojo().setX(alien.getHorizontalLimit());
        } else if(alien.getMovementDirection() == DirectEnum.RIGHT){
            alien.getAlienPojo().setX(-alien.getAlienPojo().getSize());
        }
        // Put y coordenate on random position
        alien.getAlienPojo().setY((int) (Math.random() * 300));
    }

    // private void createBullets(){
    //     for(int i = 0; i < 5; i++){
    //         BulletModel bullet = new BulletModel();
    //         bullet.setVerticalLimit(600);
    //         bulletList.add(bullet);
    //     }
    // }

    @Override
    public void setPresenter(Presenter presenter) {
       this.presenter = presenter;
    }

    @Override
    public void start() {
        createAliens();
        for(AlienModel alien : alienList) {
            alien.startMovement();
        }
    }

    private void createAliens(){
        for(int i = 0; i < 5; i++){
            AlienModel alien = new AlienModel();
            alien.getAlienPojo().setSize(20);
            alien.setHorizontalLimit(800);
            alien.setMovement(10);
            setAlienLocation(alien);
            alienList.add(alien);
        }
    }

    @Override
    public void stop() {
       for(AlienModel alien : alienList) {
     alien.stopMovement();
       }
    }

    @Override
    public List<AlienPojo> getAliensPojo() {
        List<AlienPojo> alienPojoList = new ArrayList<AlienPojo>();
        for(AlienModel alien : alienList) {
            alienPojoList.add(alien.getAlienPojo());
        }
        return alienPojoList;
    }

    @Override
    public List<BulletPojo> getBulletsPojo() {
        List<BulletPojo> bulletPojoList = new ArrayList<BulletPojo>();
        for(BulletModel bullet : bulletList) {
            bulletPojoList.add(bullet.getBulletPojo());
        }
        return bulletPojoList;
    }

    @Override
    public CanonPojo getCanonPojo() {
       return canonModel.getCanonPojo();
    }
}
