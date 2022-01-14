package com.d4viddf.Tablas;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
@Embeddable
public class ImpartenId implements Serializable {
    public int profesor;
    public int asignatura;
    public int alumno;
    public String curso;

    public ImpartenId(int profesor, int asignatura, int alumno, String curso) {
        this.profesor = profesor;
        this.asignatura = asignatura;
        this.alumno = alumno;
        this.curso = curso;
    }

    public ImpartenId() {
    }

    public int getProfesor() {
        return profesor;
    }

    public void setProfesor(int profesor) {
        this.profesor = profesor;
    }

    public int getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(int asignatura) {
        this.asignatura = asignatura;
    }

    public int getAlumno() {
        return alumno;
    }

    public void setAlumno(int alumno) {
        this.alumno = alumno;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}
