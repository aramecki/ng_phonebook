import { Component, Input, Output, EventEmitter } from '@angular/core';
import { ContactService } from '../contact.service';

@Component({
  selector: 'app-contacts-list',
  templateUrl: './contacts-list.component.html',
  styleUrls: ['./contacts-list.component.css']
})
export class ContactsListComponent {

  constructor(private contactService: ContactService) {}

  @Input() id: number = 0;
  @Input() firstName: string = '';
  @Input() lastName: string = '';
  @Input() isShowingTrash: boolean = false;

  @Output() isContactSelected = new EventEmitter<number>();
  @Output() isContactPermDeleted = new EventEmitter<void>();
  @Output() wantsToRestoreContact = new EventEmitter<number>();

  onNameClick() {
    if (!this.isShowingTrash) {
      let lastContact = document.querySelector("li.active") as HTMLLIElement;
      if (lastContact) {
        lastContact.classList.remove("active");
      }

      let activeContact = document.getElementById(`${this.id}`) as HTMLLIElement;
      activeContact.classList.add("active");

      console.log("Dico di cambiare id");
      this.isContactSelected.emit(this.id);
    }
  }

  deleteContact() {
    this.contactService.deleteContact(this.id).subscribe({
      next: data => {
        console.log("deleted permanently" + this.id + data);
        this.isContactPermDeleted.emit();
      },
      error: error => console.log(error),
    })
  }

  restoreContact() {
    console.log("emetto l'id: " + this.id)
    this.wantsToRestoreContact.emit(this.id);
  }
}
