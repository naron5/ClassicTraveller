package test;


import org.testng.*;

import org.testng.annotations.*;

import decoder.UniversalPlanetaryProfile;
import decoder.Starport;

public class UPPTests {

	@Test
	public void sizeTest() {
		UniversalPlanetaryProfile upp = new UniversalPlanetaryProfile(
				Starport.none, 1, 0, 0, 0, 0, 0, 0, false, false, false);
		String rv = upp.getSizeString();
		Assert.assertEquals("1000 miles (1600 km)",rv);
	}
}
