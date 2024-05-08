package co.edu.uptc.views;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import co.edu.uptc.presenters.ContractPlay;
import co.edu.uptc.presenters.ContractPlay.Presenter;
import co.edu.uptc.utils.Chronometer;
import co.edu.uptc.utils.ConfigValue;

public class Dashboard extends JFrame implements ContractPlay.View {

    private ContractPlay.Presenter presenter;
    private WorkPanel workPanel;
    private InformationPanel informationPanel;
    private KeyDialog keyDialog;
    private int shotKey;

    public Dashboard() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Integer.parseInt(ConfigValue.getProperty("windowWidth")),
                Integer.parseInt(ConfigValue.getProperty("windowHeight")));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        putKeyListener();
        initComponents();
    }

    private void initComponents() {
        workPanel = new WorkPanel();
        workPanel.setDashboard(this);
        informationPanel = new InformationPanel();
        initDialog();
        this.add(informationPanel, BorderLayout.NORTH);
        this.add(workPanel, BorderLayout.CENTER);

    }

    private void initDialog() {
        keyDialog = new KeyDialog(e -> {
                shotKey = keyDialog.getKeyCode();
                keyDialog.dispose();
                startGameView();
        });
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void begin() {
        keyDialog.setVisible(true);
    }

    public void startGameView() {
        createTimer();
        informationPanel.setAliensAmount(String.valueOf(presenter.getAliensAmount()));
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

    private void putKeyListener() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                presenter.canonMovement(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == shotKey) {
                    presenter.shootBullet();
                }
            }
        });
    }

    private void createTimer() {
        Chronometer chronometer = new Chronometer();
        Thread timer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    chronometer.increaseSeconds();
                    informationPanel.setTimeValue(chronometer.timerString());
                }
            }
        });
        timer.start();
    }

    @Override
    public void updateAliensEliminated(int amount) {
        informationPanel.setAliensEliminatedAmount(String.valueOf(amount));
    }

}
