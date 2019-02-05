interface Person {
  firstName: string;
  lastName: string;

  getFullName(): string;
}

class Employee implements Person {
  fullName: string;

  constructor(public firstName: string, public middleInitial: string, public lastName: string) {
    this.fullName = firstName + " " + middleInitial + " " + lastName;
  }

  getFullName() {
    return this.fullName;
  }
}

function sayHello(person: Person): string {
  return "Hello, " + person.getFullName();
}

let user = new Employee("Alain", "R", "Chautard");
// type checking is bypassed for constructor if constructor params don't have explicit type declarations
// let user = new Employee(1, 2, 3);

console.log(sayHello(user));
