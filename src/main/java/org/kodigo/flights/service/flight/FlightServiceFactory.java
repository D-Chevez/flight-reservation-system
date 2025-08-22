package org.kodigo.flights.service.flight;

import org.kodigo.flights.model.Flight;
import org.kodigo.flights.repository.flight.IFlightRepository;
import org.kodigo.flights.repository.flight.InMemoryFlightRepository;
import org.kodigo.flights.service.airport.IAirportService;
import org.kodigo.shared.codegen.CodeGenerator;
import org.kodigo.shared.codegen.ICodeGenerator;

import java.math.BigDecimal;

public class FlightServiceFactory {
    public static IFlightService build(IAirportService airportService) {
        IFlightRepository repo = new InMemoryFlightRepository();

        var exists = (java.util.function.Predicate<String>) code -> repo.findByCode(code).isPresent();
        ICodeGenerator codeGen = new CodeGenerator(5, "FLT", "-", exists, 9999);

        return new InMemoryFlightService(repo, airportService, codeGen);
    }
}
