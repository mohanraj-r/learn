package objectStream;

import java.io.Serializable;

public class Employee implements Serializable {
	private String name;
	private double salary;

	public Employee() {
	}

	/**
	 * Constructs a Manager without an admin
	 * 
	 * @param n
	 *            the employee's name
	 * @param s
	 *            the salary
	 */
	public Employee(String n, double s) {
		name = n;
		salary = s;
	}

	public String getName() {
		return name;
	}

	public double getSalary() {
		return salary;
	}

	/**
	 * Raises the salary of this employee.
	 * 
	 * @byPercent the percentage of the raise
	 */
	public void raiseSalary(double byPercent) {
		double raise = salary * byPercent / 100;
		salary += raise;
	}

	public String toString() {
		return super.toString() + "[name=" + name + ",salary=" + salary + "]";
	}
}
