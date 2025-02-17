import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import { Contact } from './contact';

@Injectable({
    providedIn: 'root'
})
export class ContactService {

    private URL = "http://localhost:8080/contacts"

    constructor(private httpClient: HttpClient) { }

    getContactsList(): Observable<Contact[]> {
        return this.httpClient.get<Contact[]>(`${this.URL}`);
    }

    getActiveContactsNamesList(): Observable<Contact[]> {
        const params = new HttpParams().set('active', 'true');
        return this.httpClient.get<Contact[]>(`${this.URL}/names`, { params });
    }

    getNoActiveContactsNamesList(): Observable<Contact[]> {
        const params = new HttpParams().set('active', 'false');
        return this.httpClient.get<Contact[]>(`${this.URL}/names`, { params });
    }

    getContactById(id: number): Observable<Contact> {
        return this.httpClient.get<Contact>(`${this.URL}/${id}`);
    }

    createContact(contact: Contact): Observable<Object> {
        return this.httpClient.post(`${this.URL}`, contact);
    }

    updateContact(id: number, contact: Contact): Observable<Object> {
        return this.httpClient.put(`${this.URL}/${id}`, contact);
    }

    deleteContact(id: number): Observable<Object> {
        return this.httpClient.delete(`${this.URL}/${id}`)
    }

}