# Requerimientos de checkin

- Marcar check-in de una reserva CONFIRMED → CHECKED_IN.
- Emitir BoardingPass con: bookingCode, passengerName, flightId, seatNumber, issuedAt.

## Notas
- Pasajero debe estar ACTIVE.
- No permite check-in si CANCELLED o DRAFT.
- Salida por consola (mínimo): imprime boarding pass legible.