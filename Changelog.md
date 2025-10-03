# Changelog

## [1.1] 
### Cambiado
- **Retornos inmutables en repos**
    - **Qué cambia:** los métodos de lectura en repositorios (`findAll()` y similares) ahora devuelven **colecciones inmutables** mediante `List.copyOf(...)`/`Map.copyOf(...)`.
    - **Impacto:** si alguna parte del código intentaba modificar directamente la colección devuelta, ahora lanzará `UnsupportedOperationException`.
    - **Motivación:** evitar efectos colaterales y proteger el estado interno del repositorio (**encapsulación** y **defensive copying**).
    - **Migración:** cuando necesites mutar la lista, hacé una copia explícita:
      ```java
      // Antes (mutando el retorno del repo) — ❌
      repo.findAll().add(nuevo);
  
      // Después — ✅
      var items = new ArrayList<>(repo.findAll());
      items.add(nuevo);
      ```

- **`Optional` en consumo + excepciones claras**
    - **Qué cambia:** se reemplazan chequeos manuales de `Optional` por `orElseThrow(...)` al consumir repos desde servicios (p. ej., `FlightService`, `BookingService`, `PassengerService`, `CheckInService`).
    - **Impacto:** si la entidad no existe, ahora se lanza una excepción explícita (p. ej., `IllegalArgumentException`) con **mensaje consistente** en el formato:
      ```
      [Contexto] – [Causa] – [Dato]
      // Ejemplos:
      "Flight – not found – {flightId}"
      "Booking – not found – {bookingId}"
      ```
    - **Motivación:** simplificar el flujo con **guard clauses**, reducir `if (isPresent)` repetidos y mejorar la trazabilidad de errores.
    - **Migración:** capturá la excepción donde corresponda (CLI/controlador) y mostrá el mensaje al usuario; evitá `opt.get()` sin verificar.
      ```java
      // Antes — ❌
      var opt = repo.findById(id);
      if (opt.isPresent()) { entidad = opt.get(); } else { /* manejar */ }
  
      // Después — ✅
      var entidad = repo.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Entidad – not found – " + id));
      ```

### Notas de compatibilidad
- Estos cambios pueden considerarse **breaking** si código externo mutaba las colecciones retornadas o dependía de `null`/`isPresent()` en rutas específicas. La migración propuesta evita rupturas y mejora la robustez del flujo.

### Interno
- PR: *Higiene & Consistencia* (constantes, Optional, guard clauses, retornos inmutables).
- Sin cambios funcionales ni nuevos endpoints; sólo limpieza y consistencia de código.
