package co.edu.uptc.views;

import javax.swing.JFrame;

import co.edu.uptc.presenters.ContractPlay;
import co.edu.uptc.presenters.ContractPlay.Presenter;

public class Dashboard extends JFrame implements ContractPlay.View {

    private ContractPlay.Presenter presenter;
    @Override
    public void setPresenter(Presenter presenter) {
       this.presenter = presenter;
    }

    @Override
    public void begin() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'begin'");
    }
    
}
