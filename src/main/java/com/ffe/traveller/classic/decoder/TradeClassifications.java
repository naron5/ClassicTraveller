package com.ffe.traveller.classic.decoder;

public enum TradeClassifications {
	Agricultural("Agricultural System"),
    NonAgricultural("Non-Agricultural System"),
    Industrial("Industrial System"),
    NonIndustrial("Non-Industrial System"),
    Rich("Rich System"),
    Poor("Poor System"),
    Farming("Farming Colony"),
    Mining("Mining Colony"),
    Colony("Colony"),
    Research("Research Facility"),
    Military("Military Base");


    private final String value;
    public String getValue() {return value;}
    private TradeClassifications(String value) {this.value = value;}
}
