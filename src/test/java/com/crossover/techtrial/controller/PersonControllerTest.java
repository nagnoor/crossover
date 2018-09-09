/**
 *
 */
package com.crossover.techtrial.controller;

import com.crossover.techtrial.com.crossover.techtrial.model.PersonModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.crossover.techtrial.repositories.PersonRepository;

import java.net.URI;

/**
 * @author kshah
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PersonControllerTest {

    MockMvc mockMvc;

    @Mock
    private PersonController personController;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    PersonRepository personRepository;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    @After
    public void after() {
        personRepository.deleteAll();
    }

    @Test
    public void testPanelShouldBeRegistered() {
        PersonModel p = new PersonModel("Test", "person1@gmail.com", "12345");
        HttpEntity<Object> person = getHttpEntity(p);
        ResponseEntity<PersonModel> response = template.postForEntity(
                "/api/person", person, PersonModel.class);
        Assert.assertEquals("Test", response.getBody().getName());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    public void getAllPersons_test() {


        PersonModel person1 = new PersonModel("Person1", "person1@gmail.com", "12345");
        PersonModel person2 = new PersonModel("Person2", "person2@gmail.com", "12346");

        HttpEntity<Object> person_1 = getHttpEntity(person1);
        ResponseEntity<PersonModel> response1 = template.postForEntity(
                "/api/person", person_1, PersonModel.class);

        HttpEntity<Object> person_2 = getHttpEntity(person2);
        ResponseEntity<PersonModel> response2 = template.postForEntity(
                "/api/person", person_2, PersonModel.class);

        ResponseEntity<PersonModel[]> list = template.getForEntity("/api/person", PersonModel[].class);

        Assert.assertEquals(HttpStatus.OK, list.getStatusCode());
        Assert.assertEquals(2, list.getBody().length);

    }


    @Test
    public void getPersonById_test() {
        PersonModel person1 = new PersonModel("Person", "person1@gmail.com", "12345");

        HttpEntity<Object> person_1 = getHttpEntity(person1);
        ResponseEntity<PersonModel> response1 = template.postForEntity(
                "/api/person", person_1, PersonModel.class);


        String url = "/api/person/" + response1.getBody().getId();
        URI uri = URI.create(url);
        ResponseEntity<PersonModel> res = template.getForEntity(uri, PersonModel.class);

        Assert.assertEquals(HttpStatus.OK, res.getStatusCode());
        Assert.assertEquals("Person", res.getBody().getName());

    }

    @Test
    public void getPersonById_test_negative() {
        PersonModel person1 = new PersonModel("Person", "person1@gmail.com", "12345");

        HttpEntity<Object> person_1 = getHttpEntity(person1);
        ResponseEntity<PersonModel> response1 = template.postForEntity(
                "/api/person", person_1, PersonModel.class);


        personRepository.deleteById(response1.getBody().getId());
        String url = "/api/person/" + response1.getBody().getId();
        ResponseEntity<PersonModel> res = template.getForEntity(url, PersonModel.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());

    }


    private HttpEntity<Object> getHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<Object>(body, headers);
    }


}
