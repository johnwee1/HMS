package menus;

import repository.AppointmentRepository;

public abstract class Menu {
    AppointmentRepository repo;
    String id;

    public Menu(AppointmentRepository repo, String id){
        this.repo = repo;
        this.id = id;
    }

    public abstract void userInterface();
}
