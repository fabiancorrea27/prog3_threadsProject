package co.edu.uptc.presenters;

public interface ContractPlay {
    public interface  Model {
        public void setPresenter(Presenter presenter);
        public void start();
        public void stop();
        
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
        public void setLimits(int horizontalLimit, int verticalLimit);
        public void start();
        public void stop();
        
    }


}
