package com.d4viddf.Tablas;

import javax.persistence.Id;
import java.io.Serializable;

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
}
