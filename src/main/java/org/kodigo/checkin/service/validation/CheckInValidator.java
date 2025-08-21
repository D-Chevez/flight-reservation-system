package org.kodigo.checkin.service.validation;

public interface CheckInValidator {
    void validate(CheckInValidationContext ctx);
    CheckInValidator linkWith(CheckInValidator next);
}