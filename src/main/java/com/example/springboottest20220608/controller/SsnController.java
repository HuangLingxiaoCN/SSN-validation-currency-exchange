package com.example.springboottest20220608.controller;

import com.example.springboottest20220608.entity.Ssn;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static java.util.Map.entry;

@RestController
@RequestMapping("/api/ssn")
public class SsnController {

    private Ssn ssn;

    // all control characters
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

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello Spring Boot!";
    }

    @PostMapping
    public String ssnValidation(@RequestBody Ssn ssn) {

        String ssnStr = "";

        // validate the ssn number:
        // 1. length is 11
        // 2. the control character is correct
        if(ssn.getSsn().length() == 11) {

            char[] ssnArr = ssn.getSsn().toCharArray();
            for(int i = 0; i < ssnArr.length; i++) {
                if(i != 6 && i != 10)
                    ssnStr = ssnStr + ssnArr[i];
            }

            // calculate the control number and character
            double ssnNumber = Double.parseDouble(ssnStr);
            double decimalPart = (ssnNumber/31)%1;
            double resultDouble = decimalPart * 31;
            int controlNumber = (int) (resultDouble - resultDouble%1);

            String controlChar = controlCharacters.get(controlNumber+"");

            // check if the calculated controlChar matches the control character in ssn
            if(!controlChar.equals(Character.toString(ssnArr[ssnArr.length - 1])))
                return ssn.toString() +
                        "\nssn validation: false" +
                        "\nCalculated control character: " + controlChar +
                        "\nReceived control character: " + ssnArr[ssnArr.length - 1];

            return ssn.toString() +
                    "\nssn validation: true" +
                    "\nCalculated control character: " + controlChar +
                    "\nReceived control character: " + ssnArr[ssnArr.length - 1];

        } else {
            return ssn + "\n" + "FALSE!";
        }


    }
}
