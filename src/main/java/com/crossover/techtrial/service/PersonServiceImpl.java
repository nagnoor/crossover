/**
 *
 */
package com.crossover.techtrial.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.crossover.techtrial.dto.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.repositories.PersonRepository;

/**
 * @author crossover
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;

    /* (non-Javadoc)
     * @see com.crossover.techtrial.service.PersonService#getAll()
     */
    @Override
    public List<PersonDTO> getAll() {
        List<Person> personList = new ArrayList<>();
        personRepository.findAll().forEach(personList::add);
        return personList.stream().map(person -> new PersonDTO(person.getName(),
                person.getEmail(),
                person.getRegistrationNumber(),
                person.getId()
        )).collect(Collectors.toList());
    }

    public PersonDTO save(PersonDTO p) {
        Person person = new Person();
        person.setRegistrationNumber(p.getRegistrationNumber());
        person.setName(p.getName());
        person.setEmail(p.getEmail());
        Person dbPerson = personRepository.save(person);
        PersonDTO personDTO = new PersonDTO();
        personDTO.setRegistrationNumber(dbPerson.getRegistrationNumber());
        personDTO.setName(dbPerson.getName());
        personDTO.setEmail(dbPerson.getEmail());
        personDTO.setId(dbPerson.getId());
        return personDTO;
    }

    @Override
    public PersonDTO findById(Long personId) {
        Optional<Person> dbPerson = personRepository.findById(personId);
        PersonDTO personDTO = null;
        if (dbPerson.isPresent()) {
            personDTO = new PersonDTO();
            personDTO.setRegistrationNumber(dbPerson.get().getRegistrationNumber());
            personDTO.setName(dbPerson.get().getName());
            personDTO.setEmail(dbPerson.get().getEmail());
            personDTO.setId(dbPerson.get().getId());
        }

        return personDTO;
    }


}
