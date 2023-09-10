package com.services.pricehistory.application.rest.validator;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author Omar Al-Shalabi
 */
@Component
public class InstrumentValidator {

    private static final int ISIN_LENGTH = 12;

    /**
     * Checker Method to validate ISIN based on the following rules:
     * 1- ISIN length of 12 characters
     * 2- ISIN starts with 2 letters
     * 3- The rest of ISIN is a combination of letters, and digits
     * @param isin ISIN to be checked
     * @return boolean representing the validation of ISIN
     */
    public boolean isIsinValid(@NonNull final String isin) {
        return isin.length() == ISIN_LENGTH && Character.isLetter(isin.charAt(0)) &&
                Character.isLetter(isin.charAt(1)) && isIsinAplhanumeric(isin);
    }

    private boolean isIsinAplhanumeric(final String isin) {
        boolean check = true;
        for (char c : isin.toCharArray()) {
            check &= Character.isLetter(c) || Character.isDigit(c);
        }
        return check;
    }
}
