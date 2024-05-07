package co.edu.uptc.views;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import co.edu.uptc.pojos.AlienPojo;
import co.edu.uptc.pojos.CanonPojo;
import co.edu.uptc.utils.ImagesUtil;
import co.edu.uptc.utils.Util;

public class WorkPanel extends JPanel {
    private Dashboard dashboard;
    // private boolean running;
    private BufferedImage alienImage;
    private BufferedImage canonImage;

    public WorkPanel(){
        alienImage = ImagesUtil.alienImage();
        canonImage = ImagesUtil.canonImage();
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
            for (AlienPojo alienPojo : dashboard.getPresenter().getAliensPojo()) {
                g.drawImage(alienImage, alienPojo.getX(), alienPojo.getY(), null);
            }
            g.drawImage(canonImage, dashboard.getPresenter().getCanonPojo().getX(), dashboard.getPresenter().getCanonPojo().getY(), null);
    }

    public void threadPaint(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    repaint();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }
}
