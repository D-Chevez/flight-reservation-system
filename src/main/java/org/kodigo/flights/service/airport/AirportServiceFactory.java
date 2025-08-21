package org.kodigo.flights.service.airport;

import org.kodigo.flights.repository.airport.IAirportRepository;
import org.kodigo.flights.repository.airport.InMemoryAirportRepository;
import org.kodigo.flights.service.airport.validation.AirportCodeFormatValidator;
import org.kodigo.flights.service.airport.validation.AirportNameRequiredValidator;
import org.kodigo.flights.service.airport.validation.AirportUniquenessValidator;
import org.kodigo.shared.codegen.CodeGenerator;
import org.kodigo.shared.codegen.ICodeGenerator;

public class AirportServiceFactory {
    public static IAirportService build() {
        IAirportRepository repo = new InMemoryAirportRepository();

        var onCreate = new AirportCodeFormatValidator();
        var onCreate2 = new AirportNameRequiredValidator();
        var onCreate3 = new AirportUniquenessValidator(repo);
        onCreate.linkWith(onCreate2).linkWith(onCreate3);

        var onUpdate = new AirportCodeFormatValidator();
        var onUpdate2 = new AirportNameRequiredValidator();
        onUpdate.linkWith(onUpdate2);

        var exists = (java.util.function.Predicate<String>) code -> repo.findByCode(code).isPresent();
        ICodeGenerator codeGen = new CodeGenerator(5, "ARPT", "-", exists, 9999);

        return new InMemoryAirportService(repo, onCreate, onUpdate, codeGen);
    }
}
