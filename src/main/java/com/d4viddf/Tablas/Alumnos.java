package com.d4viddf.Tablas;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "alumnos")
public class Alumnos {
    @Id
    private int expediente;
    @Column(name = "dni", nullable = false)
    private String DNI;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "apellidos", nullable = false)
    private String apellidos;
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate nacimiento;

    public Alumnos() {
    }

    public Alumnos(int expediente, String DNI, String nombre, String apellidos, LocalDate nacimiento) {
        this.expediente = expediente;
        this.DNI = DNI;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nacimiento = nacimiento;
    }


    /**
     * @return String
     */
    public String getDNI() {
        return this.DNI;
    }


    /**
     * @param DNI
     */
    public void setDNI(String DNI) {
        this.DNI = DNI;
    }


    /**
     * @return String
     */
    public String getNombre() {
        return this.nombre;
    }


    /**
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    /**
     * @return String
     */
    public String getApellidos() {
        return this.apellidos;
    }


    /**
     * @param apellidos
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }


    /**
     * @return LocalDate
     */
    public LocalDate getNacimiento() {
        return this.nacimiento;
    }


    /**
     * @param nacimiento
     */
    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }

    /**
     * @return int
     */
    public int getExpediente() {
        return expediente;
    }

    /**
     * @param expediente
     */
    public void setExpediente(int expediente) {
        this.expediente = expediente;
    }
}
