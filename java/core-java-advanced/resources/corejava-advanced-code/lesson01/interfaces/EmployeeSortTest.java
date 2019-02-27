package interfaces;

import java.util.Arrays;
import java.util.Comparator;

public class EmployeeSortTest {
	public static void main(String[] args) {
		Employee[] staff = new Employee[3];

		staff[0] = new Employee("Harry Hacker", 75000);
		staff[1] = new Employee("Carl Cracker", 75000);
		staff[2] = new Employee("Tony Tester", 38000);
		
		Arrays.sort(staff);

		System.out.println(Arrays.toString(staff));
	}
}