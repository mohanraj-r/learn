package objectStream;

public class Manager extends Employee {
	private Employee admin;

	/**
	 * Constructs a Manager without an admin
	 * 
	 * @param n
	 *            the employee's name
	 * @param s
	 *            the salary
	 */
	public Manager(String n, double s) {
		super(n, s);
		admin = null;
	}

	/**
	 * Assigns a admin to the manager.
	 * 
	 * @param s
	 *            the admin
	 */
	public void setAdmin(Employee s) {
		admin = s;
	}

	public String toString() {
		return super.toString() + "[admin=" + admin + "]";
	}
}
