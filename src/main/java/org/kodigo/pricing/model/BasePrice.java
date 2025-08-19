package org.kodigo.pricing.model;

import org.kodigo.shared.money.Money;

public class BasePrice implements IPrice{
    private final Money value;

    public BasePrice(Money value){ this.value = value; }

    @Override
    public Money amount(){ return value; }
}
