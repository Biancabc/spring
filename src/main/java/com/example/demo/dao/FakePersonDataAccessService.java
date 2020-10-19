package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao {


    Person p1 = new Person(UUID.randomUUID(), "Bianca");
    Person p2 = new Person(UUID.randomUUID(), "Diana");
    Person p3 = new Person(UUID.randomUUID(), "Adelina");
    Person p4 = new Person(UUID.randomUUID(), "Gabriel");
    public List<Person> DB = new ArrayList<Person>(){{
        add(p1);
        add(p2);
        add(p3);
        add(p4);
    }};

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return DB;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream()
                .filter(person->person.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> deletedId = selectPersonById(id);
        if(!deletedId.isPresent()){
            return 0;
        }
        DB.remove(deletedId.get());
        return 1;
    }

    @Override
    public int updatePersonById(UUID id, Person update) {
        return selectPersonById(id)
                .map(person -> {
                    int indexOfPersonToUpdate = DB.indexOf(person);
                    if(indexOfPersonToUpdate>=0) {
                        DB.set(indexOfPersonToUpdate, new Person(id, update.getName()));
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }
}
