package org.kodigo.flights.service.airport;

import org.kodigo.flights.repository.airport.IAirportRepository;
import org.kodigo.flights.repository.airport.InMemoryAirportRepository;
import org.kodigo.flights.service.airport.validation.AirportCityRequiredValidator;
import org.kodigo.flights.service.airport.validation.AirportNameRequiredValidator;
import org.kodigo.flights.service.airport.validation.AirportCodeUniquenessValidator;
import org.kodigo.flights.service.airport.validation.AirportRequiredExistValidator;
import org.kodigo.shared.codegen.CodeGenerator;
import org.kodigo.shared.codegen.ICodeGenerator;

public class AirportServiceFactory {
    public static IAirportService build() {
        IAirportRepository repo = new InMemoryAirportRepository();

        var onCreate = new AirportCodeUniquenessValidator(repo);
        var onCreate2 = new AirportNameRequiredValidator();
        var onCreate3 = new AirportCityRequiredValidator();
        var onCreate4 = new AirportNameRequiredValidator();

        onCreate.linkWith(onCreate2).linkWith(onCreate3).linkWith(onCreate4);

        var onUpdate = new AirportRequiredExistValidator(repo);

        var exists = (java.util.function.Predicate<String>) code -> repo.findByCode(code).isPresent();
        ICodeGenerator codeGen = new CodeGenerator(5, "ARPT", "-", exists, 9999);

        return new InMemoryAirportService(repo, onCreate, onUpdate, codeGen);
    }
}
