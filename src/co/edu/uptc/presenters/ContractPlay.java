package co.edu.uptc.presenters;

import java.util.List;

import co.edu.uptc.pojos.AlienPojo;
import co.edu.uptc.pojos.BulletPojo;
import co.edu.uptc.pojos.CanonPojo;
import co.edu.uptc.views.DirectEnum;

public interface ContractPlay {
    public interface  Model {
        public void setPresenter(Presenter presenter);
        public void start();
        public void stop();
        public List<AlienPojo> getAliensPojo();
        public List<BulletPojo> getBulletsPojo();
        public CanonPojo getCanonPojo();
        public void setHorizontalLimit(int horizontalLimit);
        public void setVerticalLimit(int verticalLimit);
        public void moveCanon(DirectEnum directEnum);
        public void shootBullet();
    }

    public interface View {
        public void setPresenter(Presenter presenter);
        public void begin();
        public int horizontalLimit();
        public int verticalLimit();
    }
    
    public interface Presenter {
        
        public void setModel(Model model);
        public void setView(View view);
        public void begin();
        // model
        public void shootBullet();
        public void canonMovement(int keyCode);
        public List<AlienPojo> getAliensPojo();
        public List<BulletPojo> getBulletsPojo();
        public CanonPojo getCanonPojo();
        public void setHorizontalLimit(int horizontalLimit);
        public void setVerticalLimit(int verticalLimit);
        public void start();
        public void stop();
        
    }


}
