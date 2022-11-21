package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TDD {
	
	private User testedUser;
	
	@BeforeEach
	void setUp() {
		testedUser = User.getInstance();
	}

	@Test
	void itShouldBeUniqueObject() {
		User user = User.getInstance();
		assertSame(testedUser, user);
	}

}
