PassengerService - Servicio de Gestión de Pasajeros
# Descripción
Clase de servicio que maneja la lógica de negocio para operaciones CRUD de pasajeros, actuando como intermediario entre los controladores y el repositorio de persistencia.

🔧 Dependencias
Passenger (Modelo)

passengerRepository (Persistencia)

List, Optional (Java Utils)

# Métodos Principales
Crear Pasajero
java
Passenger createPassenger(String fullName, String passport)
Valida unicidad del pasaporte

Crea y persiste nuevo pasajero

Lanza excepción si el pasaporte ya existe

Listar Pasajeros
java
List<Passenger> listPassengers()
Retorna lista completa de pasajeros

Lista vacía si no hay registros

Gestión de Estado
java
void suspendPassenger(String passport)   // Suspende pasajero
void reactivatePassenger(String passport) // Reactiva pasajero
Buscan por pasaporte

Actualizan estado del pasajero

Lanzan excepción si no encuentra el pasaporte

Actualizar Contactos
java
void updateContacts(String passport, String email, String phone)
Modifica email y teléfono

Valida existencia del pasajero

Búsqueda y Eliminación
java
Optional<Passenger> findByPassport(String passport) // Búsqueda segura
void deletePassenger(String passport)               // Eliminación
findByPassport retorna Optional (evita excepciones)

deletePassenger valida existencia antes de eliminar 

# Comportamiento
Lanza IllegalArgumentException en operaciones con pasaporte inexistente

Mantiene separación clara entre lógica de negocio y persistencia

Sigue patrón Repository para operaciones de base de datos

