package com.example.contacts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/contacts")
public class ContactsController {

    @Autowired private ContactsRepository contactsRepository;

    @PostMapping
    public ResponseEntity<Void> createContact(@RequestBody Contact contact) {
        if (!contact.getFirstName().isEmpty()) {
            contactsRepository.save(contact.getFirstName(), contact.getLastName(), contact.getFirstNumber(), contact.getSecondNumber(),
                    contact.getFirstAddress(), contact.getSecondAddress(), contact.getEmail(), contact.getActive());
            return ResponseEntity.ok().build();
        } else {
            System.out.println("Received a null contact. Not saving to the db.");
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public List<Map<String, Object>> getAllContacts() {
        return contactsRepository.findAll();
    }

    @GetMapping("/names")
    public List<Map<String, Object>> getOnlyNames(@RequestParam Boolean active) {
        return contactsRepository.findNames(active);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        Contact contact = contactsRepository.findById(id);

        if (contact == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(contact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @RequestBody Contact contactDetails) {
        Contact contact = contactsRepository.findById(id);

        if (contact == null) {
            return ResponseEntity.notFound().build();
        }

        contact.setFirstName(contactDetails.getFirstName());
        contact.setLastName(contactDetails.getLastName());
        contact.setFirstNumber(contactDetails.getFirstNumber());
        contact.setSecondNumber(contactDetails.getSecondNumber());
        contact.setFirstAddress(contactDetails.getFirstAddress());
        contact.setSecondAddress(contactDetails.getSecondAddress());
        contact.setEmail(contactDetails.getEmail());
        contact.setActive(contactDetails.getActive());

        System.out.println("Editing " + contact.getFirstName());

        contactsRepository.updateContact(contact);
        return ResponseEntity.ok(contact);
    }

    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable Long id) {
        Contact contact = contactsRepository.findById(id);
        contactsRepository.deleteContact(contact);
    }

}
