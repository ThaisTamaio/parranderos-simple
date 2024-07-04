package net.javaguides.banking.modelo;
import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class FrecuentanPK implements Serializable {
 
    @ManyToOne
    @JoinColumn(name = "id_bebedor", referencedColumnName = "id")
    private Bebedor id_bebedor;

    @ManyToOne
    @JoinColumn(name = "id_bar", referencedColumnName = "id")
    private Bar id_bar;

    private Date fecha_visita;

    private String horario;

    public FrecuentanPK()
    {
        super();
    }

    public FrecuentanPK(Bebedor id_bebedor, Bar id_bar, Date fecha_visita, String horario)
    {
        super();
        this.id_bebedor = id_bebedor;
        this.id_bar = id_bar;
        this.fecha_visita = fecha_visita;
        this.horario = horario;
    }

    public Bebedor getBebedor() {
        return id_bebedor;
    }

    public void setBebedor(Bebedor id_bebedor) {
        this.id_bebedor = id_bebedor;
    }

    public Bar getBar() {
        return id_bar;
    }

    public void setBar(Bar id_bar) {
        this.id_bar = id_bar;
    }

    public Date getFecha_visita() {
        return fecha_visita;
    }

    public void setFecha_visita(Date fecha_visita) {
        this.fecha_visita = fecha_visita;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        if(horario.length() > 20)
            horario = horario.substring(0, 20);
        this.horario = horario;
    }

}