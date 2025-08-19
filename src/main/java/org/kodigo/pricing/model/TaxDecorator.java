package org.kodigo.pricing.model;

import org.kodigo.shared.money.Money;
import java.math.BigDecimal;

public final class TaxDecorator implements IPrice {

    private final BasePrice next;
    private final BigDecimal taxRate; // ej. 0.13

    public TaxDecorator(BasePrice next, BigDecimal taxRate){ this.next = next; this.taxRate = taxRate; }

    @Override public Money amount() {
        var base = next.amount();
        var tax = base.amount().multiply(taxRate);
        return base.add(Money.of(tax, base.currency()));
    }
}