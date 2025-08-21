package org.kodigo;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1. Vuelos");
            System.out.println("2. Pasajeros");
            System.out.println("3. Reservas");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    menuVuelos(sc);
                    break;
                case 2:
                    menuPasajeros(sc);
                    break;
                case 3:
                    menuReservas(sc);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida. Intenta de nuevo.");
            }

        } while (opcion != 0);

        sc.close();
    }

    //Acá reemplazaria los System.out.println por la lógica de los vuelos

    private static void menuVuelos(Scanner sc) {
        int opcion;
        do {
            System.out.println("\n===== SUBMENÚ VUELOS =====");
            System.out.println("1. Listar vuelos");
            System.out.println("2. Registrar vuelo");
            System.out.println("3. Modificar vuelo");
            System.out.println("4. Eliminar vuelo");
            System.out.println("0. Volver al menú principal");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> System.out.println("Listando vuelos...");
                case 2 -> System.out.println("Registrando vuelo...");
                case 3 -> System.out.println("Modificando vuelo...");
                case 4 -> System.out.println("Eliminando vuelo...");
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    //Acá reemplazaria los System.out.println por la lógica de los pasajeros

    private static void menuPasajeros(Scanner sc) {
        int opcion;
        do {
            System.out.println("\n===== SUBMENÚ PASAJEROS =====");
            System.out.println("1. Ver pasajeros");
            System.out.println("2. Buscar pasajero");
            System.out.println("3. Actualizar pasajero");
            System.out.println("4. Eliminar pasajero");
            System.out.println("0. Volver al menú principal");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> System.out.println("Mostrando lista de pasajeros...");
                case 2 -> System.out.println("Buscando pasajero...");
                case 3 -> System.out.println("Actualizando pasajero...");
                case 4 -> System.out.println("Eliminando pasajero...");
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    //Acá reemplazaria los System.out.println por la lógica de las reservas

    private static void menuReservas(Scanner sc) {
        int opcion;
        do {
            System.out.println("\n===== SUBMENÚ RESERVAS =====");
            System.out.println("1. Crear reserva");
            System.out.println("2. Ver reservas");
            System.out.println("3. Cancelar reserva");
            System.out.println("0. Volver al menú principal");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();



            switch (opcion) {
                case 1 -> System.out.println("Creando reserva...");
                case 2 -> System.out.println("Mostrando reservas...");
                case 3 -> System.out.println("Cancelando reserva...");
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }
}


