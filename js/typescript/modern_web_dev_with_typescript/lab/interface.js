function sayHello(person) {
    return "Hello " + person.firstName + " " + person.lastName;
}
var p = {
    lastName: "foo",
    firstName: "bar"
};
console.log(sayHello(p));
