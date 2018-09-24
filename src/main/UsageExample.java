package main;

import main.controllers.DB_Manager;
import main.mocks.Person;

public class UsageExample {
    public static void main(String[] args) {

        Person harry = new Person(1, "Harry", "Potter", true);
        Person islam = new Person(2, "Islam", "Murtazaev", true);
        Person hermione = new Person(3, "Hermione", "Granger", true);
       // DB_Manager.save(harry);
        //DB_Manager.save(hermione);
        new DB_Manager().save(hermione);
        new DB_Manager().delete(3);
        new DB_Manager().save(islam);
    }
}
