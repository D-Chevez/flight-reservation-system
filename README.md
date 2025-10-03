# 📑 Flight Reservation System

**Flight Reservation System** es una aplicación desarrollada en **Java 17** (construida con **Maven**) que simula un sistema de reservación de vuelos en modo **consola (CLI)**.  
Permite gestionar vuelos, pasajeros, reservas y check-in mediante un menú interactivo.

Está diseñado como un prototipo académico/demostrativo con repositorios en memoria, patrones de diseño aplicados y buenas prácticas de programación.

---

## ✨ Características principales

- Gestión de **aeropuertos y vuelos** con validaciones (origen ≠ destino, códigos válidos).
- Administración de **pasajeros** con estado activo/inactivo.
- Sistema de **reservas de vuelos** con:
    - Generación de código único de reserva.
    - Política de asignación de asientos (primer asiento libre por defecto).
    - Cálculo de precio con **IVA (13%)**.
- **Check-in** con emisión de boarding pass y validación de duplicados/tiempos.
- Menú interactivo en **consola (CLI)** para navegar las funcionalidades.

---

## 🚀 Cómo usar este proyecto

### 1️⃣ Clona el repositorio:

```bash
git clone https://github.com/D-Chevez/flight-reservation-system.git
```

### 2️⃣ Compila el proyecto con Maven:

```bash
mvn clean install
```

### 3️⃣ Ejecuta la aplicación:

Desde tu IDE (IntelliJ, Eclipse, etc.), corre la clase principal:

```
org.kodigo.Main
```

O directamente con Maven:

```bash
mvn exec:java -Dexec.mainClass="org.kodigo.Main"
```

### 4️⃣ Interactúa con el sistema:

Navega el menú por consola para crear vuelos, registrar pasajeros, hacer reservas y realizar check-in.

---

## 🛠 Tecnologías utilizadas

✅ Java 17  
✅ Maven  
✅ Git & GitHub para control de versiones

---

## 📐 Buenas prácticas aplicadas

- **Arquitectura MVC**: separación clara de modelos, servicios (lógica) y capa de interacción por consola.
- **Principios SOLID**:
    - _Single Responsibility_: cada servicio/repositorio tiene una única responsabilidad.
    - _Open/Closed_: validadores y políticas se pueden extender sin modificar el código existente.
- **Patrones de diseño implementados**:
    - **Factory** → construcción de servicios completos (BookingServiceFactory).
    - **Chain of Responsibility** → validaciones encadenadas en reservas y check-in.
    - **Strategy** → políticas de asignación de asientos (ej. FirstFreeSeatPolicy).
    - **Decorator** → cálculo de precios con impuestos y extensión de pasajeros.
    - **Repository Pattern** → almacenamiento en memoria desacoplado de la lógica.

---

## 🗂️ Estructura del proyecto

```
flight-reservation-system
│
├── src
│   ├── main
│   │   ├── java/org/kodigo
│   │   │   ├── bookings         # Servicios y validaciones de reservas
│   │   │   ├── checkin          # BoardingPass y validaciones de check-in
│   │   │   ├── flights          # Aeropuertos, vuelos y validaciones
│   │   │   ├── passengers       # Gestión de pasajeros y decorators
│   │   │   └── Main.java        # Menú principal (CLI)
│   │   └── resources            # Archivos de configuración
│   └── test                     # Tests unitarios (pendientes)
│
└── pom.xml                      # Configuración de Maven
```

---

## 🔄 Flujo funcional

1. **Registrar vuelos** → creas aeropuertos y vuelos válidos.
2. **Registrar pasajeros** → se validan como activos antes de reservar.
3. **Reservar vuelo** → genera código único, asigna asiento, calcula precio + IVA.
4. **Check-in** → valida tiempo, evita duplicados y emite boarding pass.
5. **Menú por consola** → navegación interactiva de todo el sistema.

---

---

## 👨‍💻 Autores

- 🚀 **Programador:** [Diego Chevez](https://github.com/D-Chevez)
- 🚀 **Programador:** [Nicole Sanchez](https://github.com/nicolenohemysanchez)
