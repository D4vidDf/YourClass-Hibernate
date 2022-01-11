package com.d4viddf.Tablas;

import javax.persistence.*;

@Entity
@Table(name = "asignaturas")
public class Asignaturas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @Column(name = "nombre", nullable = false)
    public String nombre;
    @Column(name = "curso", nullable = false)
    public String curso;

    public Asignaturas() {
    }

    public Asignaturas(String nombre, int id, String curso) {
        this.nombre = nombre;
        this.id = id;
        this.curso = curso;
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
     * @return int
     */
    public int getId() {
        return this.id;
    }


    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param curso
     */
    public void setCurso(String curso) {
        this.curso = curso;
    }

    /**
     * @return String
     */
    public String getCurso() {
        return curso;
    }

}
