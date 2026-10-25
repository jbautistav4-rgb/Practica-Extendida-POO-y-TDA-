package modelo;

public abstract class Empleado {
    // Atributos privados (Encapsulamiento)
    private String dpi;
    private String nombre;

    // Constructor con parámetros y uso de 'this'
    public Empleado(String dpi, String nombre) {
        this.dpi = dpi;
        this.nombre = nombre;
    }

    // Métodos de acceso (Getters)
    public String getDpi() {
        return dpi;
    }

    public String getNombre() {
        return nombre;
    }

    // Métodos abstractos que las clases hijas están obligadas a sobrescribir
    public abstract double calcularPago();
    public abstract String obtenerTipo();
}