// src/pricing/service/DecoratorPricingService.java
package org.kodigo.bookings.service.pricing;

import org.kodigo.pricing.model.BasePrice;
import org.kodigo.pricing.model.TaxDecorator;
import org.kodigo.shared.money.Money;

import java.math.BigDecimal;

public final class DecoratorPricingService implements IPricingService {
    private final BigDecimal taxRate;

    public DecoratorPricingService(BigDecimal taxRate){ this.taxRate = taxRate; }

    @Override public Money calculateTotal(Money baseFare){
        var priced = new TaxDecorator(new BasePrice(baseFare), taxRate);
        return priced.amount();
    }
}
