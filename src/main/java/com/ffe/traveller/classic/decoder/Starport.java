package com.ffe.traveller.classic.decoder;

/**
 * @author markknights
 *         <p/>
 *         Bringing Traveller into the Applications world!
 */


public enum Starport {
    A("Class A Starport"),
    B("Class B Starport"),
    C("Class C Starport"),
    D("Class D Starport"),
    E("Class E Starport"),
    X("Class X Starport"),
    Y("No Spaceport"),
    H("Primitive Spaceport"),
    G("Poor quality Spaceport"),
    F("Good quality Spaceport"),
    none("No Starport");

    private final String value;
    public String getValue() {return value;}
    private Starport(String value) {this.value = value;}

}