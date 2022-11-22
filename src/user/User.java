package user;

import exceptions.ExportForbiddenException;

public class User {
	
	private static User uniqueInstance = null;
	
	private String firstName, lastName, username;
	
	protected User() {
		firstName = "";
		lastName = "";
		username = "";
	}
	
	public static User getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new User();
		}
		return uniqueInstance;
	}

	public boolean setData(String firstName, String lastName, String username) {
		if (username.contains(" ")) return false;
		uniqueInstance.firstName = firstName;
		uniqueInstance.lastName = lastName;
		uniqueInstance.username = username;
		return true;
	}

	public String getFirstName() {
		return uniqueInstance.firstName;
	}

	public String getLastName() {
		return uniqueInstance.lastName;
	}

	public String getUsername() {
		return uniqueInstance.username;
	}

	public void clearData() {
		uniqueInstance.firstName = "";
		uniqueInstance.lastName = "";
		uniqueInstance.username = "";
	}

	public boolean hasData() {
		if (uniqueInstance.firstName.equals("") ||
				uniqueInstance.lastName.equals("") ||
				uniqueInstance.username.equals("")) return false;
		return true;
	}

	public static String getExportPath(String string) throws ExportForbiddenException {
		if (!uniqueInstance.hasData()) throw new ExportForbiddenException();
		else return uniqueInstance.username + "/" + string; 
	}

}
