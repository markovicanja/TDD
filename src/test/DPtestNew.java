package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import formatting.TxtFormatter;
import music.Composition;

class DPtestNew {

	// Strategy, Iterator, Facade
	@Test
	void printCompositionTest() {
		Composition composition = new Composition();
		composition.parse("test.txt");
		
		String expectedOutput = "Note: 1/4 E4Pause: 1/8Chord: 1/4 [D#4 E4 F4]Pause: 1/4Note: 1/4 F#4";
		String output = composition.printComposition();
		assertEquals(expectedOutput, output);
	}
	
	// Decorator
	@Test
	void printTxtFormat() {
		Composition composition = new Composition();
		composition.parse("test.txt");
		
		TxtFormatter txtFormatter = new TxtFormatter(composition, "testOutput.txt");
		String expectedOutput = "u [Yui]|I";
		String output = txtFormatter.toString();
		assertEquals(expectedOutput, output);
	}

}