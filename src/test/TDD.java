package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import user.User;

class TDD {
	
	private User testedUser;
	
	@BeforeEach
	void setUp() {
		testedUser = User.getInstance();
	}

	@Test
	void shouldBeUniqueObject() {
		User user = User.getInstance();
		
		assertSame(testedUser, user);
	}
	
	@Test
	void setFirstNameTest() {
		testedUser.setData("Anja", "Markovic", "anjamarkovic");
		
		assertEquals("Anja", testedUser.getFirstName());
	}
	
	@Test
	void setLastNameTest() {
		testedUser.setData("Anja", "Markovic", "anjamarkovic");
		
		assertEquals("Markovic", testedUser.getLastName());
	}
	
	@Test
	void setUserNameTest() {
		testedUser.setData("Anja", "Markovic", "anjamarkovic");
		
		assertEquals("anjamarkovic", testedUser.getUsername());
	}
	
	@ParameterizedTest
	@CsvSource({
		"anjamarkovic, true",
		"anja markovic, false"
	})
	void shoudValidateUsername(String username, boolean expected) {
		boolean isValid = testedUser.setData("Anja", "Markovic", username);
		
		assertEquals(expected, isValid);
	}

}
