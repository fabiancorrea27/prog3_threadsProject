package co.edu.uptc.presenters;

import java.util.List;

import co.edu.uptc.pojos.AlienPojo;

public interface ContractPlay {
    public interface  Model {
        public void setPresenter(Presenter presenter);
        public void start();
        public void stop();
        public List<AlienPojo> getAliensPojo();
        
        
    }
    public interface View {
        public void setPresenter(Presenter presenter);
        public void begin();

    }

    public interface Presenter {
        public void setModel(Model model);
        public void setView(View view);
        public void begin();

        // model
        public List<AlienPojo> getAliensPojo();
        public void start();
        public void stop();
        
    }


}
