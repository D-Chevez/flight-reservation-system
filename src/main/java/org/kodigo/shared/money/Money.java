package org.kodigo.shared.money;

import java.math.BigDecimal;
import java.util.Objects;

public final class Money implements Comparable<Money> {
    private final BigDecimal amount;
    private final String currency; // ISO 4217, ej. "USD"

    public static Money of(BigDecimal amount, String currency) {
        if (amount == null || currency == null || currency.isBlank()) throw new IllegalArgumentException("Invalid money");
        return new Money(amount, currency.toUpperCase());
    }

    private Money(BigDecimal amount, String currency) { this.amount = amount; this.currency = currency; }

    public BigDecimal amount() { return amount; }
    public String currency() { return currency; }

    public Money add(Money other) {
        requireSameCurrency(other);
        return new Money(this.amount.add(other.amount), currency);
    }
    public Money subtract(Money other){
        requireSameCurrency(other);
        return new Money(this.amount.subtract(other.amount), currency);
    }

    private void requireSameCurrency(Money other){ if(!this.currency.equals(other.currency)) throw new IllegalArgumentException("Currency mismatch"); }

    @Override
    public int compareTo(Money o)
    {
        requireSameCurrency(o);
        return amount.compareTo(o.amount);
    }
    @Override
    public boolean equals(Object o)
    {
        if(this==o) return true;
        if(!(o instanceof Money m)) return false;
        return amount.compareTo(m.amount)==0 && currency.equals(m.currency);
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(amount.stripTrailingZeros(), currency);
    }

    @Override
    public String toString()
    {
        return amount + " " + currency;
    }
}