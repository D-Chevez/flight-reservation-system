package org.kodigo.bookings.service.pricing;

import org.kodigo.shared.money.Money;

public interface IPricingService {
    Money calculateTotal(Money baseFare);
}
