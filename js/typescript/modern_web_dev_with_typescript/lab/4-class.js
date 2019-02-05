var Employee = /** @class */ (function () {
    function Employee(firstName, middleInitial, lastName) {
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.fullName = firstName + " " + middleInitial + " " + lastName;
    }
    Employee.prototype.getFullName = function () {
        return this.fullName;
    };
    return Employee;
}());
function sayHello(person) {
    return "Hello, " + person.getFullName();
}
var user = new Employee("Alain", "R", "Chautard");
// type checking is bypassed for constructor if constructor params don't have explicit type declarations
// let user = new Employee(1, 2, 3);
console.log(sayHello(user));
