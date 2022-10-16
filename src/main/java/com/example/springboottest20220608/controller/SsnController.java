package com.example.springboottest20220608.controller;

import com.example.springboottest20220608.entity.ControlCharacter;
import com.example.springboottest20220608.entity.Ssn;
import com.example.springboottest20220608.exception.SsnLengthException;
import com.example.springboottest20220608.exception.WrongControlCharacterException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ssn")
public class SsnController {

    private ControlCharacter controlCharacter = new ControlCharacter();

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

            char controlChar = controlCharacter.getControlCharacter(controlNumber).charAt(0);
            // check if the calculated controlChar matches the control character in ssn
            if(controlChar != (ssn.getControlCharacter()))

                // error handling for wrong control character
                throw new WrongControlCharacterException(ssn.getSsn(), controlChar);

            return ssn + "\nssn validation: true";

        } else {
            throw new SsnLengthException(ssn.getSsn());
        }

    }
}
