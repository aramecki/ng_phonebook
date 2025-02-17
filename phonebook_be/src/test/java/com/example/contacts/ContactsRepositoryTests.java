package com.example.contacts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ContactsRepositoryTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ContactsRepository contactsRepository;

    @BeforeEach
    void init() {

        contactsRepository = new ContactsRepository(jdbcTemplate);

//        jdbcTemplate.execute("DELETE FROM contacts");
        jdbcTemplate.execute("DROP TABLE contacts");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS contacts (id INT AUTO_INCREMENT PRIMARY KEY, firstName VARCHAR(255) NOT NULL, lastName VARCHAR(255), firstNumber VARCHAR(255), secondNumber VARCHAR(255), firstAddress VARCHAR(255), secondAddress VARCHAR(255), email VARCHAR(255), active BOOL NOT NULL);\n");
        jdbcTemplate.execute("INSERT INTO contacts (id, firstName, lastName, active) VALUES (1, 'Luca', 'Johnson', true)");
        jdbcTemplate.execute("INSERT INTO contacts (id, firstName, lastName, active) VALUES (2, 'Lorenzo', 'Clinton', false)");
    }

    @Test
    void testFindAllContacts() {
        List<Map<String, Object>> results = contactsRepository.findAll();
        assertThat(results).hasSize(2);
        assertThat(results.get(0).get("id")).isEqualTo(1);
        assertThat(results.get(1).get("id")).isEqualTo(2);

        System.out.println("testFindAllContacts() success");
    }

    @Test
    void testFindActiveContacts() {
        List<Map<String, Object>> results = contactsRepository.findNames(true);
        assertThat(results).hasSize(1);
        assertThat(results.get(0).get("firstName")).isEqualTo("Luca");

        System.out.println("testFindActiveContacts() success");
    }

    @Test
    void testFindDeactivatedContacts() {
        List<Map<String, Object>> results = contactsRepository.findNames(false);
        assertThat(results).hasSize(1);
        assertThat(results.get(0).get("firstName")).isEqualTo("Lorenzo");
        assertThat(results.get(0).get("lastName")).isEqualTo("Clinton");

        System.out.println("testFindDeactivatedContacts() success");
    }

    @Test
    void testFindSpecificContact() {
        Contact results = contactsRepository.findById(2L);
        assertThat(results).isNotNull();
        assertThat(results.getFirstName()).isEqualTo("Lorenzo");
        assertThat(results.getLastName()).isEqualTo("Clinton");

        System.out.println("testFindSpecificContact() success");
    }

    @Test
    void createNewContact() {
        contactsRepository.save("Gerry", "Scotti", "1234", "5678", "Via Roma 11", "Via Milano 22","gerry@scotti.com", true);

        Contact results = contactsRepository.findById(3L);
        assertThat(results).isNotNull();
        assertThat(results.getFirstName()).isEqualTo("Gerry");
        assertThat(results.getLastName()).isEqualTo("Scotti");
        assertThat(results.getFirstNumber()).isEqualTo("1234");
        assertThat(results.getSecondNumber()).isEqualTo("5678");
        assertThat(results.getFirstAddress()).isEqualTo("Via Roma 11");
        assertThat(results.getSecondAddress()).isEqualTo("Via Milano 22");
        assertThat(results.getEmail()).isEqualTo("gerry@scotti.com");
        assertThat(results.getActive()).isEqualTo(true);

        System.out.println("createNewContact() success");
    }

    @Test
    void updateSpecificContact() {
        Contact contact = contactsRepository.findById(1L);
        contact.setLastName("Vercetti");
        contact.setEmail("luca@vercetti.com");
        contact.setActive(false);

        contactsRepository.updateContact(contact);

        Contact updatedContact = contactsRepository.findById(1L);
        assertThat(updatedContact).isNotNull();
        assertThat(updatedContact.getLastName()).isEqualTo("Vercetti");
        assertThat(updatedContact.getEmail()).isEqualTo("luca@vercetti.com");
        assertThat(updatedContact.getActive()).isEqualTo(false);

        System.out.println("updateSpecificContact() success");
    }

    @Test
    void deleteSpecificContact() {
        Contact contact = contactsRepository.findById(2L);
        contactsRepository.deleteContact(contact);

        Contact deletedContact = contactsRepository.findById(2L);
        assertThat(deletedContact).isNull();
        System.out.println("deleteSpecificContact() success");
    }
}
