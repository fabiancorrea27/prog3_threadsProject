package co.edu.uptc.presenters;

import java.util.List;

import co.edu.uptc.models.GameManager;
import co.edu.uptc.pojos.AlienPojo;
import co.edu.uptc.presenters.ContractPlay.Model;
import co.edu.uptc.presenters.ContractPlay.View;
import co.edu.uptc.views.Dashboard;

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

}
