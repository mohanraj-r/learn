interface Person {
  firstName: string;
  lastName: string;
}

function sayHello(person: Person) {
  return `Hello ${person.firstName} ${person.lastName}`;
}

let p: Person = {
  lastName: "foo",
  firstName: "bar"
};

console.log(sayHello(p));
