package com.example.springboottest20220608;

import com.example.springboottest20220608.entity.ControlCharacter;
import com.example.springboottest20220608.entity.Ssn;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class SSNValidationCurrencyExchangeTests {

    private static ControlCharacter controlCharacter;

    @BeforeAll
    public static void setup() {
        controlCharacter = new ControlCharacter();
        System.out.println("Start testing ...");
    }

    @AfterAll
    public static void teardown() {
        System.out.println("End testing ...");
    }

    @Test
    public void ssnController_length() {
        Ssn ssn = new Ssn("100598-657Y", "358");
        assertEquals(11, ssn.getSsn().length());
    }

    @Test
    public void ssnController_lengthException() {
        assertThrows(AssertionError.class, () -> {
            Ssn ssn = new Ssn("100598-657YX", "358");
            assertEquals(11, ssn.getSsn().length());
        });
    }

    @Test
    public void ssnController_controlChar() {
        Ssn ssn = new Ssn("100598-657Y", "358");

        String ssnStr = "";
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
        assertEquals('Y', controlChar);
    }


}
