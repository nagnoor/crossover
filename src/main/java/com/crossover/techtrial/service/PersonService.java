/**
 *
 */
package com.crossover.techtrial.service;

import java.util.List;

import com.crossover.techtrial.dto.PersonDTO;
import com.crossover.techtrial.model.Person;

/**
 * PersonService interface for Persons.
 *
 * @author cossover
 */
public interface PersonService {
    public List<PersonDTO> getAll();

    public PersonDTO save(PersonDTO p);

    public PersonDTO findById(Long personId);

}
