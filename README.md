# Phonebook WebApp with Angular and Spring Boot
Simple phonebook interface to get comfortable with Angular and Spring Boot.
## How to install
In order to try the app you have to follow the steps below:

 1. Download or clone the content of this repository.
 2. Configure a MySQL database named "contacts". (username: "root", password: "pass")
 3. Open the "phonebook_be" folder with your favorite IDE and start the app.
 4. Open the "phonebook" folder with your favorite IDE.
 5. Give the command `npm install`, then `ng serve`.
 
 Now everything should be ready to use and accessible  on your local host.
 
## How to use
### Create a new contact
To create a new contact the user have to click on the "Add new contact" element on the bottom right of the interface, a new component will be loaded. Here,  there are all the fields that can be filled as the user wishes, the only required is the "First Name" in order to avoid creating useless empty contacts.
Every field has a maximum number of characters based on common-sense (or at least I think so). This is checked in the html and then in the TS too. A simple regex checks the inserted text for characters to avoid SQL injections even if this webapp is intended for loacal use.
The interface shows on the bottom center a blue "Confirm"  button to save the form and a top right "x" icon to go back to the contacts selection interface.
 > NOTE: The picture selection is not working as intended. It will only show a preview of the selected image but it isn't actually saved in the backend.
### Edit a contact
Everything said above about creating a new contact is valid for this function too. In fact the same component is designed to perform both the action of creating and editing a contact.
But in this interface there are two main differences with the creation one. The first one is that here the fields are already compiled with the infos of the selected contact. The second is that on the top left of the interface a bin icon is appeared letting the user to move the contact in the trash bin by setting to false a value in the database table.
After a contact deletion the main page is reloaded.
### Access the trash bin
To access the bin the user only has to click on the "Show bin" text on the bottom left of the main page.
To go back to the main page there is a "x" icon on the top right.
### Permanently delete a contact
To permanently delete a contact the user has to access the trash bin as showed above and just click on the trash bin icon on the right of the name.
### Restore a contact
To restore a contact moved to the trash bin the user has to click on the reload icon on the right of the name.

## How to test
In order to test the database backend functions you have to:
1. Download or clone the content of this repository.
 2. Configure a MySQL database named "contacts_test". (username: "root", password: "pass")
 3. Open the "phonebook_be" folder with your favorite IDE and navigate to "src/test/java/com.example.contacts".
 4. Open the file "ContactsRepositoryTests" and from here play a single test or the whole group. 

## More info about what I used to write the webapp
### Frontend
Angular 16.2.16 with Typescript.

### Backend
Spring Boot with Java 17, Maven and jdbc to manage the MySQL database.

 > NOTE: I know I didn't actually used the Router. The reason is that I just liked this way of playing by loading or unloading components in a dynamic way.  Just input a non-valid route in the browser address bar, please.