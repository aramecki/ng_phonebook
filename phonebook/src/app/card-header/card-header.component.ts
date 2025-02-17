import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-card-header',
  templateUrl: './card-header.component.html',
  styleUrls: ['./card-header.component.css']
})
export class CardHeaderComponent {
  @Input() firstName: string = '';
  @Input() lastName: string = '';

  @Output() editContact = new EventEmitter<void>();
  
  onEditClick() {
    console.log("Dico di modificare il contatto a full-card");
    this.editContact.emit();
  }
}
