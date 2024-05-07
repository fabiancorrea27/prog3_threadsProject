package co.edu.uptc.views;

import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

import co.edu.uptc.pojos.AlienPojo;
import co.edu.uptc.pojos.BulletPojo;
import co.edu.uptc.pojos.CanonPojo;
import co.edu.uptc.utils.ImagesUtil;

public class WorkPanel extends JPanel {
    private Dashboard dashboard;
    // private boolean running;
    private BufferedImage alienImage;
    private BufferedImage canonImage;
    private List<AlienPojo> aliens;
    private List<BulletPojo> bullets;
    private CanonPojo canon;

    public WorkPanel(){
        alienImage = ImagesUtil.alienImage();
        canonImage = ImagesUtil.canonImage();
        putComponentListener();
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public void loadObjectsPojo(){
        aliens = dashboard.getPresenter().getAliensPojo();
        bullets = dashboard.getPresenter().getBulletsPojo();
        canon = dashboard.getPresenter().getCanonPojo();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
            // System.out.println(aliens.get(0).getX());
            for (AlienPojo alienPojo : aliens) {
                g.drawImage(alienImage, alienPojo.getX(), alienPojo.getY(), null);
            }
            g.drawImage(canonImage, canon.getX(), canon.getY(), canon.getSize(), canon.getSize(), null);
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

    private void putComponentListener(){
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                dashboard.getPresenter().setHorizontalLimit(getWidth());
                dashboard.getPresenter().setVerticalLimit(getHeight());
                System.out.println("Cambiado tamano papu");
            }
        });
    }
}
