import { Component } from '@angular/core';

@Component({
  selector: 'app-not-found',
  template : `
  <div>
    <h4>Not what you were looking for.</h4>
    <img src="assets/images/not-found.gif">
    <p>Please <a routerLink="/">go back</a> to where you should be.</p>
  <div>`,
  styleUrls: ['./not-found.component.css']
})
export class NotFoundComponent {

}
