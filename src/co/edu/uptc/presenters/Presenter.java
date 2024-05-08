package co.edu.uptc.presenters;

import java.awt.event.KeyEvent;
import java.util.List;

import co.edu.uptc.models.GameManager;
import co.edu.uptc.pojos.AlienPojo;
import co.edu.uptc.pojos.BulletPojo;
import co.edu.uptc.pojos.CanonPojo;
import co.edu.uptc.presenters.ContractPlay.Model;
import co.edu.uptc.presenters.ContractPlay.View;
import co.edu.uptc.views.Dashboard;
import co.edu.uptc.views.DirectEnum;

public class Presenter implements ContractPlay.Presenter {
    private ContractPlay.Model model;
    private ContractPlay.View view;

    
    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void begin() {
        makeMVP();
        start();
        view.begin();
    }

    private void makeMVP() {
        Dashboard dashboard = new Dashboard();
        dashboard.setPresenter(this);
        setView(dashboard);

        GameManager gameManager = new GameManager();
        gameManager.setPresenter(this);
        setModel(gameManager);
    }

    @Override
    public void canonMovement(int keyCode) {
        if(keyCode == KeyEvent.VK_LEFT){
            model.moveCanon(DirectEnum.LEFT);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            model.moveCanon(DirectEnum.RIGHT);
        }
    }
    
    @Override
    public void shootBullet() {
        model.shootBullet();
    }

    @Override
    public List<AlienPojo> getAliensPojo() {
        return model.getAliensPojo();
    }

    @Override
    public void start() {
        model.start();
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stop'");
    }

    @Override
    public List<BulletPojo> getBulletsPojo() {
        return model.getBulletsPojo();
    }

    @Override
    public CanonPojo getCanonPojo() {
        return model.getCanonPojo();
    }

    @Override
    public void setHorizontalLimit(int horizontalLimit) {
       model.setHorizontalLimit(horizontalLimit);
        
    }

    @Override
    public void setVerticalLimit(int verticalLimit) {
        model.setVerticalLimit(verticalLimit);
    }
}
