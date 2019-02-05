var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var Person = /** @class */ (function () {
    function Person() {
    }
    Person.prototype.getFullName = function () {
        return this.firstName + " " + this.middleInitial + " " + this.lastName;
    };
    return Person;
}());
var Employee = /** @class */ (function (_super) {
    __extends(Employee, _super);
    function Employee(firstName, middleInitial, lastName) {
        var _this = _super.call(this) || this;
        _this.firstName = firstName;
        _this.middleInitial = middleInitial;
        _this.lastName = lastName;
        return _this;
    }
    Employee.prototype.getFullName = function () {
        return this.firstName + " " + this.lastName;
    };
    return Employee;
}(Person));
function sayHello(person) {
    return "Hello, " + person.getFullName();
}
var Greeter = /** @class */ (function () {
    function Greeter(greeting) {
        this.greeting = greeting;
    }
    Greeter.prototype.greet = function () {
        // return this.greeting + person.getFullName();
        return this.greeting;
    };
    return Greeter;
}());
var user = new Employee("Alain", "R", "Chautard");
var greeter = new Greeter("Hello");
console.log(sayHello(user));
console.log(greeter.greet() + " " + user.getFullName());
