package co.edu.uptc.views;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import co.edu.uptc.pojos.AlienPojo;
import co.edu.uptc.utils.Util;

public class WorkPanel extends JPanel {
    private Dashboard dashboard;
    // private boolean running;
    private BufferedImage image;

    public WorkPanel(){
        image = Util.alienImage();
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
            for (AlienPojo alienPojo : dashboard.getAliensPojo()) {
                g.drawImage(image, alienPojo.getX(), alienPojo.getY(), null);
            }
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
