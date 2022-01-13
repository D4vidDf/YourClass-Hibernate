package com.d4viddf.Tablas;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Table (name = "profesores")
public class Profesores {
    @Id
    public int cod_prof;
    @Column(name = "departamentos")
    public int departamento;
    @Column(name = "dni")
    public String DNI;
    @Column(name = "nombre")
    public String nombre;
    @Column(name = "apellidos")
    public String apellidos;
    @Column(name = "fecha_nacimiento")
    public LocalDate fecha_nacimiento;

    
    /** 
     * @return int
     */
    public int getDepartamento() {
        return this.departamento;
    }

    
    /** 
     * @param departamento
     */
    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }

    
    /** 
     * @return int
     */
    public int getCod_prof() {
        return this.cod_prof;
    }

    
    /** 
     * @param cod_prof
     */
    public void setCod_prof(int cod_prof) {
        this.cod_prof = cod_prof;
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
    public LocalDate getFecha_nacimiento() {
        return this.fecha_nacimiento;
    }

    
    /** 
     * @param fecha_nacimiento
     */
    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public Profesores(int departamento, int cod_prof, String DNI, String nombre, String apellidos,
            LocalDate fecha_nacimiento) {
        this.departamento = departamento;
        this.cod_prof = cod_prof;
        this.DNI = DNI;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public Profesores() {
    }

}
