# Requerimientos de CLI

- Crear reservas
- Asignación de asiento.
- Cálculo de precio delegando a pricing.
- Consultar reserva por código.
- Cancelar reserva.
- Listar reservas por estado/fecha (mínimo para reportes).
- Persistencia conmutable: in-memory / archivo.

## Notas
- Realizar validaciones antes de realizar la creacion de una reserva. Como:  
    - El pasajero debe estar activo.
    - El vuelo debe existir.
    - El vuelo debe tener asientos disponibles.
    - No permitir reservas en asientos ocupados.
- El precio de la reserva se debe calcular delegando a pricing.
- Al cancelar una reserva, se debe liberar el asiento asignado.