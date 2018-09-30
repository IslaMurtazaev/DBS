package main;

import main.controllers.DB_Manager;
import main.mocks.Person;

public class UsageExample {
    public static void main(String[] args) {

        Person harry = new Person(1, "Harry", "Potter", true);
        Person ron = new Person(2, "Ronald", "Weasley", true);
        Person hermione = new Person(3, "Hermione", "Granger", true);

        DB_Manager db_manager = new DB_Manager();

        db_manager.save(harry);
        db_manager.save(ron);
        db_manager.save(hermione);

        Person theBoyWhoLived = (Person) db_manager.retrieve(1, new Person());
        System.out.println("The one who survived the killing curse "+ theBoyWhoLived);

        db_manager.delete(1, new Person());

        hermione.setLastName("Weasley");
        db_manager.update(3, hermione);
    }
}
