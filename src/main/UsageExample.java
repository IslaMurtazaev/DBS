package main;

import main.controllers.DB_Manager;
import main.mocks.Person;
import main.mocks.Pet;

public class UsageExample {
    public static void main(String[] args) {
        DB_Manager db_manager = new DB_Manager();

        Person harry = (Person) db_manager.save(new Person("Harry", "Potter", true));
        Person ron = (Person) db_manager.save(new Person("Ronald", "Weasley", true));
        Person hermione = (Person) db_manager.save(new Person("Hermione", "Granger", true));
        Person navil = (Person) db_manager.save(new Person("Navil", "Longbuttom", false));

        Person theBoyWhoLived = (Person) db_manager.get(harry.getId(), new Person());
        System.out.println(theBoyWhoLived);


        Pet hedwig = (Pet) db_manager.save(new Pet(harry.getId(), "Hedwig", "Owl"));
        Pet scabbers = (Pet) db_manager.save(new Pet(ron.getId(), "Scabbers", "Rat"));

        System.out.println(db_manager.get(hedwig.getId(), new Pet()));

        db_manager.delete(navil.getId(), new Person());

        hermione.setLastName("Weasley");
        db_manager.update(hermione.getId(), hermione);

        System.out.println(hedwig.get(new Person()));
        System.out.println(scabbers.get(new Person()));
    }
}
