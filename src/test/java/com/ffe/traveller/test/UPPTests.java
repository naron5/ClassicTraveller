package com.ffe.traveller.test;


import org.testng.*;

import org.testng.annotations.*;

import com.ffe.traveller.classic.decoder.UniversalPlanetaryProfile;
import com.ffe.traveller.classic.decoder.Starport;

import static com.ffe.traveller.classic.decoder.UniversalPlanetaryProfileMaker.CreateUniversalPlanetaryProfile;

public class UPPTests {

	@Test
	public void sizeTest() {
		UniversalPlanetaryProfile upp = CreateUniversalPlanetaryProfile(
                Starport.none, 1, 0, 0, 0, 0, 0);
		String rv = upp.Size();
		Assert.assertEquals("1000 miles (1600 km)",rv);
	}
}
