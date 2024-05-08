package co.edu.uptc.views;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import co.edu.uptc.presenters.ContractPlay;
import co.edu.uptc.presenters.ContractPlay.Presenter;

public class Dashboard extends JFrame implements ContractPlay.View {

    private ContractPlay.Presenter presenter;
    private WorkPanel workPanel;
    
    public Dashboard(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        putKeyListener();
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
        workPanel.loadObjectsPojo();
        workPanel.threadPaint();
        this.setVisible(true);
    }

    public ContractPlay.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public int horizontalLimit() {
        return getWidth();
    }

    @Override
    public int verticalLimit() {
        return getHeight();
    }

    public void putKeyListener(){
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                presenter.canonMovement(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                presenter.shootBullet();
            }
        });
    }
    
}
