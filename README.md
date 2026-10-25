# Práctica 11: Nómina Mensual en Java

**Curso:** Programación II | **Universidad Mariano Gálvez de Guatemala**

## Estructura del Proyecto

```text
src/
 ├── aplicacion/
 │    └── Main.java
 └── modelo/
      ├── Empleado.java
      ├── EmpleadoFijo.java
      └── EmpleadoPorHora.java
```

## 12. Organización y creación de objetos

* **Paquetes e importaciones:** El código se divide en el paquete `modelo` para las clases de los trabajadores y `aplicacion` para la clase `Main`. En `Main.java` se usa `import` para traer `ArrayList`, `Scanner` y las clases del paquete `modelo`.
* **Clases e instanciación:** Cada clase está en su propio archivo `.java`. En el método `registrarEmpleado`, se usa `new` para crear una instancia independiente de `EmpleadoFijo` o `EmpleadoPorHora` y guardarla en la lista del mes.
* **Constructores y `this`:** Las clases usan constructores con parámetros sin tipo de retorno para inicializar sus datos. Se emplea `this` como en `this.nombre = nombre;` para diferenciar el atributo de la clase del parámetro recibido.
* **Parámetro vs. Argumento:**
  * **Parámetro:** Son las variables definidas en el constructor: `public EmpleadoFijo(String dpi, String nombre, double sueldoMensual, double bonificacion)`.
  * **Argumento:** Son los valores reales que se envían al crear el objeto en `Main.java`: `new EmpleadoFijo(dpi, nombre, sueldo, bonificacion)`.

## 13. Encapsulación, abstracción y herencia

* **Encapsulación:** Todos los atributos del programa son `private`. Para consultar los datos desde fuera de las clases se utilizan únicamente métodos `get`.
* **Abstracción:** `Empleado` es una clase `abstract` con los métodos abstractos `calcularPago()` y `obtenerTipo()`. No se puede instanciar directamente y sirve como base para los datos comunes.
* **Herencia y sobrescritura:** `EmpleadoFijo` y `EmpleadoPorHora` heredan con `extends Empleado`, usan `super(dpi, nombre)` en su constructor para enviar los datos a la clase padre y sobrescriben los cálculos con `@Override`.
* **Polimorfismo:** Ambos tipos de empleados se guardan juntos en un `ArrayList<Empleado>`. Al imprimir el reporte, se recorre la lista llamando a `emp.calcularPago()`, y Java ejecuta automáticamente la suma o multiplicación según el tipo de objeto sin usar `if` o `switch`.

## 14. Métodos, ámbito y alcance

* **Modularidad:** El método `main` solo inicia el programa. Las tareas están separadas en métodos para mostrar menús, validar entradas, registrar empleados y calcular el total.
* **Ámbito y alcance en el código:**
  1. **Atributo:** `private String dpi;` en `Empleado.java`. Pertenece al objeto y se puede usar en toda la clase `Empleado`.
  2. **Parámetro:** `int tipo` en `registrarEmpleado(int tipo)`. Recibe un dato externo y solo existe mientras se ejecuta ese método.
  3. **Variable local:** `double sumaTotal = 0.0;` en `calcularTotalNomina`. Es temporal, solo funciona dentro de ese método y desaparece al retornar el resultado.

## 15. Uso de arreglos, colecciones y comprobación

### Elementos clave en el código
* **Arreglo de meses:** `private static final String[] meses = {"Enero", "Febrero", ..., "Diciembre"};`
* **Consulta del mes:** `meses[numeroMes - 1]` para mostrar el nombre directamente en títulos según el índice.
* **Agregar a la colección:** `empleadosMesActual.add(nuevo);`
* **Tamaño de la colección:** `empleadosMesActual.size()` para mostrar el total de empleados.
* **Recorrido de la colección:**
  ```java
  for (Empleado emp : empleadosMesActual) {
      sumaTotal += emp.calcularPago();
  }
  ```

### Caso de prueba (Mes 3 - Marzo)
1. Se ingresa el mes `3` y el sistema muestra **MARZO** consultando `meses[2]`.
2. Se registra a **Ana López** como empleada fija con sueldo de Q 4000 y bonificación de Q 300. Su pago calculado es **Q 4300.00**.
3. Se registra a **Luis Pérez** como empleado por hora con tarifa de Q 25 y 120 horas. Su pago calculado es **Q 3000.00**.
4. Al imprimir la nómina, el reporte muestra ambos registros:

```text
NÓMINA DEL MES: MARZO
--------------------------------------------------------------------
DPI             Nombre                 Tipo             Pago (Q)
--------------------------------------------------------------------
1234567890101   Ana López              Fijo              4300.00
9876543210101   Luis Pérez             Por hora          3000.00
--------------------------------------------------------------------
Total de trabajadores: 2
Total de nómina: Q 7300.00
```