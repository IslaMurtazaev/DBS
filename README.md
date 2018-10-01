# DBS

Database Simulator is a CL program to save POJOs in json file.

## Prerequisites

[Install Maven](http://maven.apache.org/install.html) 

## Installing

Inside of project's root folder type:
```
mvn install
```

## Usage

We recommend you to follow these steps:

* Look up ``UsageExample.java`` in main folder 
(we use ``Person.java`` as mock to demonstrate the usage)

* Run ``UsageExample.java`` and find ``Person.json`` in your project folder
(By default DBS saves your instances in JSON file with the same name as their class) 

* Extend ``Savable.java`` in all classes which instances you want to be serialized

* Enjoy using ``DB_Manager.java`` ;) 

## Running the tests

```
mvn test
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Jackson Api](https://www.boraji.com/jackson-api-converting-pojos-to-json-example) - JSON converter

## Authors

* [Islam Murtazaev](https://github.com/IslaMurtazaev)
* [Danyshman Azamatov](https://github.com/Danyshman)
* [Aidar Zamirbekov](https://github.com/AIDARXAN) 
* [Israil Zarbaliev](https://github.com/israil99)
