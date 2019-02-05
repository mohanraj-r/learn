abstract class Person {
  firstName: string;
  middleInitial: string;
  lastName: string;
  getFullName(): string {
    return this.firstName + " " + this.middleInitial + " " + this.lastName;
  }
}

class Employee extends Person {
  constructor(public firstName, public middleInitial, public lastName) {
    super();
  }
  getFullName(): string {
    return this.firstName + " " + this.lastName;
  }
}

function sayHello(person: Person): string {
  return "Hello, " + person.getFullName();
}

class Greeter<T> {
  constructor(public greeting: T) {}

  greet(): T {
    // return this.greeting + person.getFullName();
    return this.greeting;
  }
}

let user = new Employee("Alain", "R", "Chautard");
let greeter = new Greeter<string>("Hello");

console.log(sayHello(user));
console.log(greeter.greet() + " " + user.getFullName());
