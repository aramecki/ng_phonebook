import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Contact } from '../contact';
import { ContactService } from '../contact.service';

@Component({
  selector: 'app-new-card',
  templateUrl: './new-card.component.html',
  styleUrls: ['./new-card.component.css']
})
export class NewCardComponent implements OnInit {

  contact = new Contact('', {active: true});

  constructor(private contactService: ContactService) {}

  file: string = 'assets/images/person.png';

  @Input() isEditingContact: boolean = false;

  @Input()
  id: number = 0;
  @Input()
  firstName: string = '';
  @Input()
  lastName: string = '';
  @Input()
  firstNumber: string = '';
  @Input()
  secondNumber: string = '';
  @Input()
  firstAddress: string = '';
  @Input()
  secondAddress: string = '';
  @Input()
  email: string = '';

  @Output() isContactSaved = new EventEmitter<void>();

  ngOnInit(): void {
    if (this.isEditingContact) {
      console.log("modifico " + this.firstName + this.lastName + this.id);
      this.contact.id = this.id;
      this.contact.firstName = this.firstName;
      this.contact.lastName = this.lastName;
      this.contact.firstNumber = this.firstNumber;
      this.contact.secondNumber = this.secondNumber;
      this.contact.firstAddress = this.firstAddress;
      this.contact.secondAddress = this.secondAddress;
      this.contact.email = this.email;
    }
  }

  //Regexes have been searched online, sry

  checkNumbers(event: KeyboardEvent): boolean {
    const regex = /^\d$/;
    const key = event.key;
    if (!regex.test(key)) {
      event.preventDefault();
      return false;
    }
    return true;
  }

  checkText(event: KeyboardEvent): boolean {
    const regex = /^[a-zA-Z0-9_ ]$/;
    const key = event.key;
    if (!regex.test(key)) {
      event.preventDefault();
      return false;
    }
    return true;
  }

  checkEmail(event: KeyboardEvent): boolean {
    const regex = /^[a-zA-Z0-9._,@]$/;
    const key = event.key;
    if (!regex.test(key)) {
      event.preventDefault();
      return false;
    }
    return true;
  }

  saveContact() {
    this.contactService.createContact(this.contact).subscribe({
      next: data => {
        console.log('received data ' + data);
        this.onConfirmClick();
      },
      error: error => console.log(error),
    });
  }

  updateContact(contactId: number, contactData: Contact) {
    this.contactService.updateContact(contactId, contactData).subscribe({
      next: data => {
        console.log('received data on editing ' + data);
        this.onConfirmClick();
      },
      error: error => console.log('errore durante la modifica: ' + error),
    })
  }

  deactivateContact() {
    this.contact.active = false;
    this.updateContact(this.id, this.contact);
  }


  onSubmit() {
    if (this.isFormValid()) {
      if (this.isEditingContact) {
        this.updateContact(this.id, this.contact);
        /* console.log('Hai salvato le modifiche del contatto. '); */
      } else {
        /* console.log('Dati utente prima dell\'invio:', this.contact); */
        this.saveContact();
      }
    }
  }

  isFormValid(): boolean {
    let firstNameField = document.getElementById('firstName') as HTMLInputElement;

    if (firstNameField.value === '' || this.firstName.length > 12 || this.lastName.length > 12 || this.firstNumber.length > 12 || this.secondNumber.length > 12 || this.firstAddress.length > 20 || this.secondAddress.length > 20 || this.email.length > 30) {
      return false;
    } else {
      return true;
    }
  }

  onConfirmClick() {
    console.log('Dico di ricaricare');
    this.isContactSaved.emit();
  }

  onFileChange(event: any) {
    const files = event.target.files as FileList;

    if (files.length > 0) {
      const _file = URL.createObjectURL(files[0]);
      this.file = _file;
      this.resetInput();   
    }
  }

  resetInput(){
    const input = document.getElementById('contactImg') as HTMLInputElement;
    if (input) {
      input.value = '';
    }
  }

}
