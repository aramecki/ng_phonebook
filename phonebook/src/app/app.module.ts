import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainComponent } from './main/main.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule } from '@angular/common/http';
import { ContactService } from './contact.service';
import { CardComponent } from './card/card.component';
import { CardHeaderComponent } from './card-header/card-header.component';
import { CardBodyComponent } from './card-body/card-body.component';
import { NewCardComponent } from './new-card/new-card.component';
import { ContactsListComponent } from './contacts-list/contacts-list.component';
import { FullCardComponent } from './full-card/full-card.component';
import { NotFoundComponent } from './not-found/not-found.component';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    CardComponent,
    CardHeaderComponent,
    CardBodyComponent,
    NewCardComponent,
    ContactsListComponent,
    FullCardComponent,
    NotFoundComponent
  ],
  imports: [
    BrowserModule,
    NgbModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [
    ContactService
  ],
  bootstrap: [AppComponent],
  exports: [
    MainComponent,
    CardComponent,
    CardHeaderComponent,
    CardBodyComponent,
    NewCardComponent,
    ContactsListComponent,
    FullCardComponent,
    NotFoundComponent
  ]
})
export class AppModule { }
