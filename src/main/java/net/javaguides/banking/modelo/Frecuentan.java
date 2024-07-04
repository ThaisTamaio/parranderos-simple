package net.javaguides.banking.modelo;
import java.sql.Date;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="frecuentan")
public class Frecuentan {

    @EmbeddedId
    private FrecuentanPK pk;

    public Frecuentan(){;}

    public Frecuentan(Bebedor id_bebedor, Bar id_bar, Date fecha_visita, String horario)
    {
        this.pk = new FrecuentanPK(id_bebedor, id_bar, fecha_visita, horario);
    }

    public FrecuentanPK getPk() {
        return pk;
    }

    public void setPk(FrecuentanPK pk) {
        this.pk = pk;
    }

    @Override
    public String toString() 
    {
        return this.pk.getBebedor().getId()+"|"+this.pk.getBar().getId()+"|"+this.pk.getFecha_visita()+"|"+this.pk.getHorario();
    }

}