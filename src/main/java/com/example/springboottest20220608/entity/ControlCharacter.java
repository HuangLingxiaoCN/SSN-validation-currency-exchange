package com.example.springboottest20220608.entity;

import java.util.Map;

import static java.util.Map.entry;

public class ControlCharacter {
    private Map<String, String> controlCharacters = Map.ofEntries(
            entry("0", "0"),
            entry("1", "1"),
            entry("2", "2"),
            entry("3", "3"),
            entry("4", "4"),
            entry("5", "5"),
            entry("6", "6"),
            entry("7", "7"),
            entry("8", "8"),
            entry("9", "9"),
            entry("10", "A"),
            entry("11", "B"),
            entry("12", "C"),
            entry("13", "D"),
            entry("14", "E"),
            entry("15", "F"),
            entry("16", "H"),
            entry("17", "J"),
            entry("18", "K"),
            entry("19", "L"),
            entry("20", "M"),
            entry("21", "N"),
            entry("22", "P"),
            entry("23", "R"),
            entry("24", "S"),
            entry("25", "T"),
            entry("26", "U"),
            entry("27", "V"),
            entry("28", "W"),
            entry("29", "X"),
            entry("30", "Y")
    );

    public String getControlCharacter(int controlNumber) {
        return controlCharacters.get(""+controlNumber);
    }
}
