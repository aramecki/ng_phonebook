package com.example.contacts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ContactsRepository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public ContactsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //get all contacts
    public List<Map<String, Object>> findAll() {
        return jdbcTemplate.queryForList("SELECT * FROM contacts");
    }

    //get contacts list
    public List<Map<String, Object>> findNames(Boolean active) {
        return jdbcTemplate.queryForList("SELECT id, firstName, lastName FROM contacts WHERE active = ? ORDER BY firstName ASC", active);
    }

    //get contact by id
    public Contact findById(Long id) {
        try {
            Map<String, Object> row = jdbcTemplate.queryForMap("SELECT * FROM contacts WHERE id = ?", id);

            Contact contact = new Contact();
            contact.setId(((Integer) row.get("id")).longValue());
            contact.setFirstName((String) row.get("firstName"));
            contact.setLastName((String) row.get("lastName"));
            contact.setFirstNumber((String) row.get("firstNumber"));
            contact.setSecondNumber((String) row.get("secondNumber"));
            contact.setFirstAddress((String) row.get("firstAddress"));
            contact.setSecondAddress((String) row.get("secondAddress"));
            contact.setEmail((String) row.get("email"));
            contact.setActive((Boolean) row.get("active"));

            return contact;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    //add new contact
    public void save(String firstName, String lastName, String firstNumber, String secondNumber, String firstAddress, String secondAddress, String email, Boolean active
    ) {
        if (firstName.isBlank()) {
            throw new IllegalArgumentException("Null firstName field found");
        }
        if (!Validation.validateText(firstName)) {
            throw new IllegalArgumentException("Invalid firstName field found");
        }

        if (Validation.validateActualText(lastName) && Validation.validateActualNumber(firstNumber) && Validation.validateActualNumber(secondNumber) && Validation.validateActualText(firstAddress) &&
                Validation.validateActualText(secondAddress) && Validation.validateActualEmail(email)){
            jdbcTemplate.update("INSERT INTO contacts (firstName, lastName, firstNumber, secondNumber, firstAddress, secondAddress, email, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                    firstName, lastName, firstNumber, secondNumber, firstAddress, secondAddress, email, active);
        }
    }

    //update existing contact
    public void updateContact(Contact contact) {
        if (contact.getFirstName().isBlank()) {
            throw new IllegalArgumentException("Null firstName field found");
        }
        if (!Validation.validateText(contact.getFirstName())) {
            throw new IllegalArgumentException("Invalid firstName field found");
        }

        if (!Validation.validateActualText(contact.getLastName()) || !Validation.validateActualNumber(contact.getFirstNumber()) || !Validation.validateActualNumber(contact.getSecondNumber()) ||
                !Validation.validateActualText(contact.getFirstAddress()) || !Validation.validateActualText(contact.getSecondAddress()) || !Validation.validateActualEmail(contact.getEmail())) {
            return;
        }

        int rowsAffected = jdbcTemplate.update("UPDATE contacts SET firstName = ?, lastName = ?, firstNumber = ?, secondNumber = ?, firstAddress = ?, secondAddress = ?, email = ?, active = ? WHERE id = ?",
                contact.getFirstName(), contact.getLastName(), contact.getFirstNumber(), contact.getSecondNumber(), contact.getFirstAddress(),
                contact.getSecondAddress(), contact.getEmail(), contact.getActive(), contact.getId());
        System.out.println("Rows : " + rowsAffected);
    }

    //delete a contact
    public void deleteContact(Contact contact) {
        jdbcTemplate.update("DELETE FROM contacts WHERE id = ?", contact.getId());
    }

}
