package aplicacion;

import java.util.ArrayList;
import java.util.Scanner;
import modelo.Empleado;
import modelo.EmpleadoFijo;
import modelo.EmpleadoPorHora;

public class Main {
    // Arreglo simple predefinido con los 12 meses
    private static final String[] meses = {
        "Enero", "Febrero", "Marzo", "Abril",
        "Mayo", "Junio", "Julio", "Agosto",
        "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };

    // Colección que almacena las 12 listas de empleados (una por cada mes)
    private static final ArrayList<ArrayList<Empleado>> nominasPorMes = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static int numeroMes;

    public static void main(String[] args) {
        inicializarNominasPorMes();
        seleccionarMes();
        ejecutarMenuPrincipal();
    }

    // Crea un ArrayList<Empleado> independiente para cada uno de los 12 meses
    private static void inicializarNominasPorMes() {
        for (int i = 0; i < meses.length; i++) {
            nominasPorMes.add(new ArrayList<>());
        }
    }

    // Devuelve el ArrayList<Empleado> exclusivo del mes que está seleccionado actualmente
    private static ArrayList<Empleado> obtenerNominaActual() {
        return nominasPorMes.get(numeroMes - 1);
    }

    // 2. Selección del mes
    private static void seleccionarMes() {
        System.out.println("CÁLCULO DE NÓMINA");
        numeroMes = leerEnteroEnRango("Ingrese el mes a trabajar (1-12): ", 1, 12);
    }

    // 3. Menú principal
    private static void ejecutarMenuPrincipal() {
        int opcion;
        do {
            limpiarPantalla();
            System.out.println("NÓMINA DEL MES: " + meses[numeroMes - 1].toUpperCase());
            System.out.println("1\tIngreso de nómina");
            System.out.println("2\tImpresión de nómina");
            System.out.println("3\tCambiar de mes");
            System.out.println("4\tSalir");
            opcion = leerEnteroEnRango("Seleccione una opción: ", 1, 4);

            switch (opcion) {
                case 1:
                    ejecutarSubmenuIngreso();
                    break;
                case 2:
                    imprimirNomina();
                    break;
                case 3:
                    limpiarPantalla();
                    seleccionarMes();
                    break;
                case 4:
                    limpiarPantalla();
                    System.out.println("Saliendo del sistema. ¡Hasta pronto!");
                    break;
            }
        } while (opcion != 4);
    }

    // 4. Opción 1: Ingreso de nómina
    private static void ejecutarSubmenuIngreso() {
        int continuar;
        do {
            ArrayList<Empleado> empleadosMesActual = obtenerNominaActual();
            
            limpiarPantalla();
            System.out.println("NÓMINA DEL MES: " + meses[numeroMes - 1].toUpperCase());
            System.out.println("INGRESO DE EMPLEADO A NÓMINA");
            System.out.println("Trabajadores actuales = " + empleadosMesActual.size());
            System.out.println("1\tEmpleado fijo");
            System.out.println("2\tEmpleado por hora");
            
            int tipo = leerEnteroEnRango("Seleccione el tipo de empleado: ", 1, 2);
            registrarEmpleado(tipo);

            // 5. Después de ingresar un empleado
            System.out.println("\nEmpleado agregado correctamente.");
            System.out.println("Trabajadores actuales = " + empleadosMesActual.size());
            System.out.println("\n¿Desea ingresar otro empleado?");
            System.out.println("1\tSí, ingresar otro empleado");
            System.out.println("2\tVolver al menú principal");
            continuar = leerEnteroEnRango("Seleccione una opción: ", 1, 2);

        } while (continuar == 1);
    }

    private static void registrarEmpleado(int tipo) {
        ArrayList<Empleado> empleadosMesActual = obtenerNominaActual();
        
        limpiarPantalla();
        System.out.println("NÓMINA DEL MES: " + meses[numeroMes - 1].toUpperCase());
        
        if (tipo == 1) {
            System.out.println("INGRESO DE EMPLEADO FIJO");
            String dpi = leerDPI("DPI: ");
            String nombre = leerNombre("Nombre: ");
            double sueldo = leerDoubleNoNegativo("Sueldo mensual (Q): ");
            double bonificacion = leerDoubleNoNegativo("Bonificación de este mes (Q): ");

            Empleado nuevo = new EmpleadoFijo(dpi, nombre, sueldo, bonificacion);
            System.out.printf("Pago de %s = Q %.2f%n", nuevo.getNombre(), nuevo.calcularPago());
            empleadosMesActual.add(nuevo);
        } else {
            System.out.println("INGRESO DE EMPLEADO POR HORA");
            String dpi = leerDPI("DPI: ");
            String nombre = leerNombre("Nombre: ");
            double pagoHora = leerDoubleNoNegativo("Pago por hora (Q): ");
            double horas = leerDoubleNoNegativo("Horas trabajadas este mes: ");

            Empleado nuevo = new EmpleadoPorHora(dpi, nombre, pagoHora, horas);
            System.out.printf("Pago de %s = Q %.2f%n", nuevo.getNombre(), nuevo.calcularPago());
            empleadosMesActual.add(nuevo);
        }
    }

    // 6. Opción 2: Impresión de nómina
    private static void imprimirNomina() {
        ArrayList<Empleado> empleadosMesActual = obtenerNominaActual();
        
        limpiarPantalla();
        System.out.println("NÓMINA DEL MES: " + meses[numeroMes - 1].toUpperCase());
        System.out.println("--------------------------------------------------------------------");

        if (empleadosMesActual.isEmpty()) {
            System.out.println("No hay empleados registrados en la nómina de este mes.");
        } else {
            System.out.printf("%-15s %-22s %-12s %12s%n", "DPI", "Nombre", "Tipo", "Pago (Q)");
            System.out.println("--------------------------------------------------------------------");
            
            // Recorrido polimórfico del ArrayList del mes actual
            for (Empleado emp : empleadosMesActual) {
                System.out.printf("%-15s %-22s %-12s %12.2f%n",
                        emp.getDpi(),
                        emp.getNombre(),
                        emp.obtenerTipo(),
                        emp.calcularPago());
            }
            System.out.println("--------------------------------------------------------------------");
        }

        double totalNomina = calcularTotalNomina(empleadosMesActual);
        System.out.println("Total de trabajadores: " + empleadosMesActual.size());
        System.out.printf("Total de nómina: Q %.2f%n", totalNomina);
        
        System.out.print("\nPresione Enter para volver al menú principal...");
        scanner.nextLine();
    }

    // Método con parámetro que devuelve el total del mes seleccionado
    private static double calcularTotalNomina(ArrayList<Empleado> listaEmpleados) {
        double sumaTotal = 0.0; // Variable local
        for (Empleado emp : listaEmpleados) {
            sumaTotal += emp.calcularPago(); // Polimorfismo
        }
        return sumaTotal;
    }

    // --- MÉTODOS DE VALIDACIÓN Y UTILIDADES ---

    private static int leerEnteroEnRango(String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();
            try {
                int valor = Integer.parseInt(entrada);
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.println("Error: Debe ingresar una opción entre " + min + " y " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Error: Entrada inválida. Ingrese un número entero válido.");
            }
        }
    }

    private static String leerDPI(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();
            
            if (!entrada.matches("\\d{13}")) {
                System.out.println("Error: El DPI debe contener exactamente 13 dígitos numéricos sin espacios.");
                continue;
            }
            
            if (existeDPIEnMesActual(entrada)) {
                System.out.println("Error: Ya existe un empleado registrado con ese DPI en el mes de " + meses[numeroMes - 1] + ".");
                continue;
            }
            
            return entrada;
        }
    }

    // Verifica que el DPI no se repita dentro de la nómina del mes actual
    private static boolean existeDPIEnMesActual(String dpiBuscado) {
        ArrayList<Empleado> empleadosMesActual = obtenerNominaActual();
        for (Empleado emp : empleadosMesActual) {
            if (emp.getDpi().equals(dpiBuscado)) {
                return true;
            }
        }
        return false;
    }

    private static String leerNombre(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();
            if (!entrada.isEmpty()) {
                return entrada;
            }
            System.out.println("Error: El nombre no puede estar vacío ni contener solo espacios.");
        }
    }

    private static double leerDoubleNoNegativo(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();
            try {
                double valor = Double.parseDouble(entrada);
                if (Double.isFinite(valor) && valor >= 0) {
                    return valor;
                }
                System.out.println("Error: El monto debe ser un número finito mayor o igual a 0.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Entrada inválida. Ingrese un valor numérico válido.");
            }
        }
    }

    private static void limpiarPantalla() {
        // Imprime líneas en blanco para simular la limpieza en cualquier consola
        for (int i = 0; i < 35; i++) {
            System.out.println();
        }
    }
}