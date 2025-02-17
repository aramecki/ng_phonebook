import { Component, OnInit, ViewChild } from '@angular/core';
import { Contact } from '../contact';
import { ContactService } from '../contact.service';
import { Router } from '@angular/router';
import { NewCardComponent } from '../new-card/new-card.component';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit{

  @ViewChild(NewCardComponent) card!: NewCardComponent;

  id: number = 0;

  isShowingContact = false;
  isCreatingContact = false;
  isEditingContact = false;
  isShowingTrash = false;
  isRestoringContact = false;

  contacts: Contact[] = [];
  contact = new Contact('',  {active: true});

  constructor(private contactService: ContactService, private router: Router) {}

  ngOnInit(): void {
      this.getContactsList();
  }

  private getContactsList() {
    if (!this.isShowingTrash) {
      this.contactService.getActiveContactsNamesList().subscribe(data => {
        this.contacts = data;
      });
    } else {
      this.contactService.getNoActiveContactsNamesList().subscribe(data => {
        this.contacts = data;
      });
    }
  }

  ShowContact() {
    this.isShowingContact = true;
  }

  refreshList() {
    if (this.isCreatingContact) {
      this.viewContactCreation();
      this.isShowingContact = false;
    } 
    if (this.isEditingContact) {
      this.viewContactEditing();
      this.getContactById();
    }
    console.log("Ricarico...");
    this.getContactsList();
  }

  viewContactCreation() {
    this.isCreatingContact = !this.isCreatingContact;
  }

  viewContactEditing() {
    this.isEditingContact = !this.isEditingContact;
  }

  viewTrash() {
    this.isShowingTrash = true;
    this.refreshList();
    this.isCreatingContact = false;
    this.isEditingContact = false;
    console.log("Dico di mostrare il cestino")
  }

  getContactId(contactId: number) {
    this.id = contactId;
    this.getContactById();
    console.log("ho assegnato ad id il valore " + this.id);
  } 

  private getContactById() {
    this.contactService.getContactById(this.id).subscribe(data => {
      this.contact = data;
      console.log("recuperato" + data);

      if (this.isRestoringContact) {
        console.log("provo a ripristinare: " + this.id)
        this.contact.active = true;
        console.log("il contatto è attivo: " + this.contact.active);
        this.updateContact(this.id, this.contact);
        this.isRestoringContact = false;
        console.log("restore completed");
      }

    })
  }

  deactivateContactCall() {
    if (this.card) {
      this.card.deactivateContact();
      this.isShowingContact = false;
    }
  }

  restoreContact(contactId: number) {
    this.isRestoringContact = true;
    this.getContactId(contactId);
  }

  updateContact(contactId: number, contactData: Contact) {
    this.contactService.updateContact(contactId, contactData).subscribe({
      next: data => {
        console.log("received data on editing " + data);
        this.refreshList();
      },
      error: error => console.log("errore durante la modifica: " + error),
    })
  }

  onExit() {
    if (this.isCreatingContact) {
      this.isCreatingContact = false;
    } else if (this.isEditingContact) {
      this.isEditingContact = false;
    } else if (this.isShowingTrash){
      this.isShowingTrash = false;
      this.refreshList();
    } else {
      this.refreshList();
    }
  }

}
