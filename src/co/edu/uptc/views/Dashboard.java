package co.edu.uptc.views;

import java.util.List;

import javax.swing.JFrame;

import co.edu.uptc.pojos.AlienPojo;
import co.edu.uptc.presenters.ContractPlay;
import co.edu.uptc.presenters.ContractPlay.Presenter;

public class Dashboard extends JFrame implements ContractPlay.View {

    private ContractPlay.Presenter presenter;
    private List<AlienPojo> aliensPojo;
    private WorkPanel workPanel;
    
    public Dashboard(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        initComponents();
    }

    private void initComponents() {
        workPanel = new WorkPanel();
        workPanel.setDashboard(this);
        this.add(workPanel);
    }

    @Override
    public void setPresenter(Presenter presenter) {
       this.presenter = presenter;
    }

    @Override
    public void begin() {
        this.aliensPojo = this.presenter.getAliensPojo();
        workPanel.threadPaint();
        this.setVisible(true);
    }

    public List<AlienPojo> getAliensPojo() {
        return aliensPojo;
    }
    
}
