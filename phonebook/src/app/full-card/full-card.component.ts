import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-full-card',
  template: `
  <app-card-header [firstName]="this.firstName" [lastName]="this.lastName" (editContact)="onEditClick()" />
  <app-card-body [firstNumber]="this.firstNumber" [secondNumber]="this.secondNumber" [firstAddress]="this.firstAddress" [secondAddress]="this.secondAddress" [email]="this.email" />
  `,
  styleUrls: ['./full-card.component.css']
})
export class FullCardComponent {
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

  @Output() editContact = new EventEmitter<void>();
    
   onEditClick() {
      /* console.log("Dico di modificare il contatto a main"); */
      this.editContact.emit();
    }
  
}
