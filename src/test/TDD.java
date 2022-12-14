package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import exceptions.ExportForbiddenException;
import formatting.TxtFormatter;
import music.Composition;
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
	
	@Test
	void removeUserTest() {
		testedUser.clearData();
		
		assertEquals("", testedUser.getFirstName());
		assertEquals("", testedUser.getLastName());
		assertEquals("", testedUser.getUsername());
	}
	
	@ParameterizedTest
	@CsvSource({
		"Anja, Markovic, anjamarkovic, true",
		"'' , '', '', false",
		"Anja, Markovic, '', false",
		"Anja, '',anjamarkovic, false",
		" '',Markovic, anjamarkovic, false"
	})
	void hasDataTest(String firstName, String lastName, String username, boolean expected) {
		testedUser.setData(firstName, lastName, username);
		boolean output = testedUser.hasData();
		
		assertEquals(expected, output);
	}
	
	@Test
	void shouldAllowExport() {
		Composition composition = new Composition();
		testedUser.setData("Anja", "Markovic", "anjamarkovic");
		
		boolean output = composition.canExport();
		assertTrue(output);
	}
	
	@Test
	void shouldPreventExport() {
		Composition composition = new Composition();
		testedUser.clearData();
		
		boolean output = composition.canExport();
		assertFalse(output);
	}
	
	@Test 
	void shouldReturnUserExportPath() {
		testedUser.setData("Anja", "Markovic", "anjamarkovic");
		String fileName = "";
		try {
			fileName = User.getExportPath("file.txt");
		} catch (ExportForbiddenException e) {
			e.printStackTrace();
		}
		
		assertEquals("anjamarkovic/file.txt", fileName);
	}
	
	@Test
	void shouldThrowExportForbiddenException() {
		testedUser.clearData();
		
		ExportForbiddenException thrown = Assertions.assertThrows(ExportForbiddenException.class, () -> {
			User.getExportPath("file.txt");
		}, "ExportForbiddenException was expected");
		
		assertEquals("Export is forbidden!", thrown.toString());
	}
	
	@Test
	void shouldReturnUsersSignature() {
		testedUser.setData("Anja", "Markovic", "anjamarkovic");
		String signature = testedUser.getSignature();
		
		assertEquals("Anja Markovic", signature);
	}
	
	@Test 
	void shouldReturnSignedText() {
		testedUser.setData("Anja", "Markovic", "anjamarkovic");
		TxtFormatter txtFormatter = new TxtFormatter(new Composition(), "directory");
		
		String expected = "\n\nBy: Anja Markovic";
		assertEquals(expected, txtFormatter.appendSignature());
	}
}
