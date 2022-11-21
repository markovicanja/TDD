package user;

public class User {
	
	private static User uniqueInstance = null;
	
	protected User() {
		
	}
	
	public static User getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new User();
		}
		return uniqueInstance;
	}

}
