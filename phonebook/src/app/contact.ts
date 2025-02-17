export class Contact {
    id?: number;
    firstName: string;
    lastName?: string;
    firstNumber?: string;
    secondNumber?: string;
    firstAddress?: string;
    secondAddress?: string;
    email?: string;
    imagePath?: string;
    active?: boolean;

    constructor(firstName: string, init?: Partial<Contact>) {
        this.firstName = firstName;
        Object.assign(this, init);
    }
}