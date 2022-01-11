package com.d4viddf.Tablas;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "imparten")
public class Imparten {
    @Id
    public int profesor;
    @Id
    public int asignatura;
    @Id
    public int alumno;
    @Id
    public String curso;

    public Imparten() {
    }

    public Imparten(String curso, int profesor, int asignatura, int alumno) {
        this.curso = curso;
        this.profesor = profesor;
        this.asignatura = asignatura;
        this.alumno = alumno;
    }


    /**
     * @return String
     */
    public String getCurso() {
        return this.curso;
    }


    /**
     * @param curso
     */
    public void setCurso(String curso) {
        this.curso = curso;
    }


    /**
     * @return int
     */
    public int getProfesor() {
        return this.profesor;
    }


    /**
     * @param profesor
     */
    public void setProfesor(int profesor) {
        this.profesor = profesor;
    }


    /**
     * @return int
     */
    public int getAsignatura() {
        return this.asignatura;
    }


    /**
     * @param asignatura
     */
    public void setAsignatura(int asignatura) {
        this.asignatura = asignatura;
    }


    /**
     * @return int
     */
    public int getAlumno() {
        return this.alumno;
    }


    /**
     * @param alumno
     */
    public void setAlumno(int alumno) {
        this.alumno = alumno;
    }

}
