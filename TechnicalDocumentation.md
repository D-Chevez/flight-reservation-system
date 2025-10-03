# 📘 Documentación Técnica (archivo por archivo y función por función)
> Proyecto: **Flight Reservation System**  
> Paquete base: `org.kodigo`  
> Alcance: **src/main/java/org/kodigo/**

---

## 🗺️ Descripción general del sistema
Sistema de reservas de vuelos con interfaz **CLI**. Separa el dominio en cuatro módulos principales:
- **Flights**: aeropuertos, vuelos y asientos.
- **Passengers**: gestión de pasajeros.
- **Bookings**: creación de reservas, gestionde asientos y precios.
- **Check-in**: emisión de boarding pass validando **ventanas de tiempo** y **duplicidad**.

Principios y patrones aplicados:
- **MVC** a nivel de capas (modelo/servicios/CLI).
- **SOLID** (SRP/OCP especialmente).
- **Patrones**: *Repository*, *Factory*, *Strategy* (asignación de asientos), *Decorator* (pricing), *Chain of Responsibility* (validaciones).

---

# 1) Módulo: Flights (`org.kodigo.flights`)

## 1.1 Descripción general
Administra aeropuertos, vuelos y disponibilidad de asientos. Central para validar rutas (origen ≠ destino) y disponibilidad.

### 1.2 Entidades

#### 1.2.1 `Airport`
- **Rol**: Representa un aeropuerto válido (código, nombre, ciudad/país opcional).
- **Razón de diseño**: Entidad simple para validar rutas y evitar strings mágicos en vuelos (**SRP**).

#### 1.2.2 `Flight`
- **Rol**: Modela un vuelo: `id`, `origin:Airport`, `destination:Airport`, `departureTime`, `arrivalTime`, `seatMap`.
- **Razón de diseño**: Mantener cohesión de datos del vuelo (**SRP**). Evitar mezclar reglas de negocio aquí (**Anemia aceptada** con servicios fuertes).

#### 1.2.3 `SeatMap`
- **Rol**: Gestiona asientos del vuelo (ocupados/libres).
- **Patrón**: ***Strategy hook*** para políticas de asignación a través de interfaz `SeatAssignmentPolicy` desde Bookings.

### 1.3 Repositorios

#### 1.3.1 `FlightRepository` (interfaz)
- **Rol**: Abstracción de persistencia para vuelos.
- **Por qué**: *Repository Pattern* desacopla la infraestructura de la lógica.
- **Métodos**:
    - `save(Flight flight)` → persiste/actualiza.
    - `findById(String id)` → consulta y retornar el valor.
    - `findAll()` →  consulta y retorna lista de valores.
    - `deleteById(String id)` -> persiste/elimina.

#### 1.3.2 `InMemoryFlightRepository` (implementación)
- **Rol**: Persistencia en memoria con `Map<String, Flight>`.
- **Ventaja**: Ideal para demo/tests; mantiene contrato de `IFlightRepository` (**OCP**).
- **Métodos**: // TODO Documentar metodos

### 1.4 Servicios

#### 1.4.1 `FlightService`
- **Rol**: Lógica de negocio para vuelos.
- **Responsabilidades**:
    - `createFlight(Airport origin, Airport destination, ...)`
        - **Qué hace**: valida origen ≠ destino, existencia de aeropuertos, disponibilidad horaria.
        - **Por qué**: centralizar reglas de vuelo (**SRP**); fácil de testear.
        - **Patrones**: colabora con repos y validadores (*Chain* liviana si hay varias reglas).
    - `getFlight(String id)` → consulta por id.
    - `listFlights()` → listado general.

### 1.5 Validadores (si existen como clases separadas)
- `OriginDestinationValidator` → asegura `origin != destination`.
- `AirportExistsValidator` → contra catálogo de aeropuertos.
- **Patrón**: *Chain of Responsibility* para encadenar reglas sin `if-else` gigantes (**OCP**).

---

# 2) Módulo: Passengers (`org.kodigo.passengers`)

## 2.1 Descripción general
Administra el ciclo de vida del pasajero y su estado (activo/inactivo).

### 2.2 Entidades

#### 2.2.1 `Passenger`
- **Rol**: Datos del pasajero (`id`, `name`, `email`, `status`).
- **Métodos**: getters/setters; posibles invariantes en `email`/`status`.

### 2.3 Repositorios

#### 2.3.1 `PassengerRepository` (interfaz)
- Métodos: `save`, `findById`, `findAll`, `deleteById`.

#### 2.3.2 `InMemoryPassengerRepository`
- Implementación con `Map<String, Passenger>` y validaciones de unicidad/email.

### 2.4 Servicios / Decorators

#### 2.4.1 `PassengerService`
- `createPassenger(...)` → altas con validación de email y estado por defecto.
- `updatePassenger(...)` → cambios de datos, comprueba transiciones válidas de estado.
- **Diseño**: **SRP**, lógica de pasajero sin conocimiento de vuelos/reservas.

#### 2.4.2 `PassengerServiceDecorator` (si existe)
- **Patrón**: *Decorator* para añadir comportamientos (ej.: logging, auditoría) sin tocar `PassengerService` (**OCP**).

---

# 3) Módulo: Bookings (`org.kodigo.bookings`)

## 3.1 Descripción general
Núcleo del negocio: creación de reservas con **código único**, **asignación de asientos** (estrategia) y **pricing** (decorator con impuestos).

### 3.2 Entidades

#### 3.2.1 `Booking`
- **Rol**: Representa la reserva (`id`, `code`, `passengerId`, `flightId`, `seat`, `price`).
- **Métodos**: getters/setters; no contiene reglas complejas (reglas viven en el servicio).

### 3.3 Repositorios

#### 3.3.1 `BookingRepository` (interfaz)
- `save(Booking)` / `findById(String)` / `findAll()` / `deleteById(String)` / `existsByCode(String)`.

#### 3.3.2 `InMemoryBookingRepository`
- Implementa contratos y verifica **colisión de `code`** (clave del dominio).

### 3.4 Servicios y Fábricas

#### 3.4.1 `BookingService`
- **Métodos**:
    - `createBooking(passengerId, flightId, Optional<String> seat)`
        - **Flujo**:
            1) **Validaciones** encadenadas (*Chain*): pasajero activo, vuelo válido, asiento disponible.
            2) **Generación de código** con `CodeGenerator` (único).
            3) **Asignación de asiento** con `SeatAssignmentPolicy` (por defecto: primero libre).
            4) **Cálculo de precio** con `PricingService` (+ IVA mediante *Decorator*).
            5) Persistencia en `BookingRepository`.
        - **Por qué**: Orquesta transacción de alta con alta cohesión (reglas en un punto) y bajo acoplamiento (estrategias/validadores inyectables).
    - `getBooking(String id)` / `listBookings()`.

#### 3.4.2 `BookingServiceFactory`
- **Rol**: *Factory* para construir `BookingService` con todas sus dependencias (repos, validadores, políticas y pricing).
- **Por qué**: simplifica wiring en CLI y estandariza composición (**SRP/OCP**).

### 3.5 Validadores (Chain of Responsibility)

- `PassengerActiveValidator`
    - **Qué hace**: verifica que el pasajero exista y esté activo.
    - **Por qué**: evita reservas a usuarios inhabilitados (**Regla de integridad de negocio**).

- `FlightSeatValidator`
    - **Qué hace**: verifica que el asiento solicitado esté disponible en el `SeatMap`.
    - **Por qué**: separa la verificación de disponibilidad de la política de asignación (**SRP**).

- `FlightExistsValidator` (si aplica)
    - **Qué hace**: confirma existencia del vuelo y estado operativo.

> **Encadenamiento**: cada validador implementa `setNext(Validator next)` y `validate(Context ctx)`.
> Permite agregar/quitar reglas sin modificar el resto (**OCP**).

### 3.6 Pricing (Decorator)

- `PricingService` (interfaz) → `BigDecimal price(BookingContext ctx)`.
- `BasePricingService` → precio base según tarifa/ruta/fecha.
- `TaxPricingDecorator` → añade **IVA 13%**.
- **Por qué**: *Decorator* permite sumar capas (impuestos, fees) sin duplicar lógica ni romper **SRP/OCP**.

### 3.7 Políticas de asignación (Strategy)

- `SeatAssignmentPolicy` (interfaz): `String assign(SeatMap map, Optional<String> requested)`.
- `FirstFreeSeatPolicy` (implementación): retorna `requested` si está libre; si no, `map.firstFree()`.
- **Por qué**: *Strategy* habilita políticas futuras (prioridad por ventana/pasillo, upgrades, etc.).

### 3.8 Utilidades

- `CodeGenerator`
    - **Qué hace**: produce códigos únicos (p. ej. `FLT-XXXXX`).
    - **Cómo**: genera candidatos pseudoaleatorios y consulta `BookingRepository.existsByCode` hasta no colisionar.
    - **Por qué**: encapsula invariante de unicidad y evita que el servicio duplique lógica (**SRP**).

---

# 4) Módulo: Check-in (`org.kodigo.checkin`)

## 4.1 Descripción general
Gestiona el **boarding pass** validando **ventanas de tiempo**, **duplicados** y **consistencia** con la reserva.

### 4.2 Entidades

#### 4.2.1 `BoardingPass`
- **Rol**: Comprobante de embarque (reserva, pasajero, vuelo, asiento, hora de emisión).

### 4.3 Repositorios

#### 4.3.1 `CheckInRepository` (interfaz)
- `save(BoardingPass)` / `findByBookingId(String)` / `existsByBookingId(String)`.

#### 4.3.2 `InMemoryCheckInRepository`
- Implementación en memoria con `Map<String, BoardingPass>`.

### 4.4 Servicios

#### 4.4.1 `CheckInService`
- **Métodos**:
    - `checkIn(String bookingId)`
        - **Flujo**:
            1) `WindowTimeValidator` → verifica que la reserva esté dentro de la ventana permitida.
            2) `DuplicateCheckInValidator` → evita crear dos boarding pass para la misma reserva.
            3) `PassengerMatchesValidator` → consistencia pasajero-reserva (si aplica).
            4) Genera y persiste `BoardingPass`.
        - **Por qué**: concentra reglas de check-in y las desacopla mediante *Chain*.

### 4.5 Validadores
- `WindowTimeValidator`, `DuplicateCheckInValidator`, `PassengerMatchesValidator`
- **Patrón**: *Chain of Responsibility* como en Bookings.

---

# 5) Ejecución principal (`org.kodigo.Main`)

## 5.1 Descripción
Punto de entrada de la **CLI**. Muestra menú y delega en servicios.

## 5.2 Archivo: `Main.java`
- **Funciones/Métodos** (típicos):
    - `public static void main(String[] args)`
        - **Qué hace**: inicializa repos y servicios (puede usar `BookingServiceFactory`), muestra el menú principal y enruta acciones.
        - **Por qué**: aislar la UI de consola del dominio: **SRP** (UI) + **DIP** (inyectar servicios).
    - `showMenu()` → imprime opciones (Vuelos / Pasajeros / Reservas / Check-in / Salir).
    - `handleFlightsMenu() / handlePassengersMenu() / handleBookingsMenu() / handleCheckInMenu()`
        - **Qué hacen**: orquestan inputs por sección y llaman a los servicios respectivos.
        - **Por qué**: separar casos de uso por contexto, mejorar legibilidad y testeo (métodos pequeños).