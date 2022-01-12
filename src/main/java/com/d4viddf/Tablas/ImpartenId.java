package com.d4viddf.Tablas;

import javax.persistence.Id;

public class ImpartenId {
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
