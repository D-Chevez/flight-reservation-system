package org.kodigo.checkin.service.validation;

public abstract class BaseCheckInValidator implements CheckInValidator {
    private CheckInValidator next;

    @Override
    public CheckInValidator linkWith(CheckInValidator next){ this.next = next; return next; }

    protected void checkNext(CheckInValidationContext ctx){ if (next != null) next.validate(ctx); }
}