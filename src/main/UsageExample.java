package main;

import main.controllers.DB_Manager;
import main.mocks.Person;

public class UsageExample {
    public static void main(String[] args) {

        Person harry = new Person(1, "Harry", "Potter", true);
        Person danyshman = new Person(2, "Danyshman", "Azamatov", true);
        Person islam = new Person(4, "Islam", "Murtazaev", true);
        Person hermione = new Person(3, "Hermione", "Granger", true);
//

//        new DB_Manager().save(harry);
//        new DB_Manager().save(danyshman);
//        new DB_Manager().save(hermione);

//        new DB_Manager().delete(danyshman.getId(), danyshman);
        new DB_Manager().retrieve(3, harry);
        System.out.println(harry.getId());
        System.out.println(hermione.getFirstName());
    }
}
