package net.javaguides.banking.modelo;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="gustan")
public class Gustan {

    @EmbeddedId
    private GustanPK pk;

    public Gustan(){;}

    public Gustan(Bebedor id_bebedor, Bebida id_bebida)
    {
        super();
        this.pk = new GustanPK(id_bebedor, id_bebida);
    }

    public GustanPK getPk() {
        return pk;
    }

    public void setPk(GustanPK pk) {
        this.pk = pk;
    }

    @Override
    public String toString() 
    {
        return this.pk.getBebedor().getId()+"|"+this.pk.getBebida().getId();
    }
    
}