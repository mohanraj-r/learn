package objectStream;

import java.io.*;
import java.nio.file.*;

class ObjectStreamTest {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Employee peter = new Employee("Peter", 40000);
		Manager paul = new Manager("Paul", 105000);
		Manager mary = new Manager("Mary", 180000);
		paul.setAdmin(peter);
		mary.setAdmin(peter);
		Employee[] staff = new Employee[3];

		staff[0] = paul;
		staff[1] = mary;
		staff[2] = peter;

		Path path = Paths.get("employee.dat");
		// save all employee records to the file employee.dat
		try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(path))) {
			out.writeObject(staff);
		}

		try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path))) {
			// retrieve all records into a new array

			Employee[] newStaff = (Employee[]) in.readObject();

			// raise admin's salary
			newStaff[2].raiseSalary(10);

			// print the newly read employee records
			for (Employee e : newStaff)
				System.out.println(e);
		}
	}
}
