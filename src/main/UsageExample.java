package main;

import main.controllers.DB_Manager;
import main.mocks.Person;
import main.mocks.Pet;

public class UsageExample {
    public static void main(String[] args) {

        Person harry = new Person("Harry", "Potter", true);
        Person ron = new Person("Ronald", "Weasley", true);
        Person hermione = new Person("Hermione", "Granger", true);
        Person navil = new Person("Navil", "Longbuttom", false);

        DB_Manager db_manager = new DB_Manager();

        db_manager.save(harry);
        db_manager.save(ron);
        db_manager.save(hermione);

        Person theBoyWhoLived = (Person) db_manager.retrieve(1, new Person());
        System.out.println("The one who survived the killing curse "+ theBoyWhoLived);

        Pet hedwig = new Pet(1, "Hedwig", "Owl");
        Pet scabbers = new Pet(2, "Scabbers", "Rat");

        db_manager.save(hedwig);
        System.out.println(db_manager.retrieve(hedwig.getId(), new Pet()));

        db_manager.delete(4, new Person());

        hermione.setLastName("Weasley");
        db_manager.update(3, hermione);

        System.out.println(hedwig.get(new Person()));
        System.out.println(scabbers.get(new Person()));
    }
}
