package com.example.demo.api;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/person")
@RestController
public class PersonController {

    private final PersonService personService;

    @GetMapping("/")
    public String welcome()
    {
        return "<html><body>"
                + "<h1>WELCOME</h1>"
                + "</body></html>";
    }

    @Autowired
    public PersonController(PersonService personService){
        this.personService = personService;
    }

    @PostMapping
    public void addPerson(@RequestBody Person person){
        personService.addPerson(person);
    }

    @GetMapping
    public String getAllPeople(){
        List<Person> persons = personService.getAllPeople();
        String s = new String();
        for(Person person: persons){
            s += "<html><body>"
                    + "<h1>Person Name:" + person.getName() + "</h1>"
                    + "<h1>Person Id:" + person.getId() + "</h1>"
                    + "</body></html>";
        }
        System.out.println(s);
        return s;
    }

    @GetMapping(path = "{id}")
    public Person getPersonById(@PathVariable("id") UUID id){
        return personService.getPersonById(id)
                .orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public void deletePerson(@PathVariable("id") UUID id){
        personService.deletePerson(id);
    }

    @PutMapping(path = "{id}")
    public void updatePerson(@PathVariable("id") UUID id, @RequestBody Person personToUpdate ){
        personService.updatePerson(id, personToUpdate);
    }
}
