package main;

import main.controllers.DB_Manager;
import main.mocks.Person;

public class UsageExample {
    public static void main(String[] args) {

        Person harry = new Person(1, "Harry", "Potter", true);
        Person danyshman = new Person(2, "Danyshman", "Azamatov", true);
        Person hermione = new Person(3, "Hermione", "Granger", true);

        Person islam = new Person(4, "Islam", "Murtazaev", true);

        DB_Manager db_manager = new DB_Manager();

//        db_manager.save(islam);
//        db_manager.save(harry);
//        db_manager.save(danyshman);
//        db_manager.save(hermione);

        db_manager.delete(1,harry);

//        new DB_Manager().delete(danyshman.getId(), danyshman);
        new DB_Manager().retrieve(3, harry);
        System.out.println(harry.getId());
        System.out.println(hermione.getFirstName());

    }
}
