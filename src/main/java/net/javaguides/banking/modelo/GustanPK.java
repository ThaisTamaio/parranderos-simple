package net.javaguides.banking.modelo;
import java.io.Serializable;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class GustanPK implements Serializable {
 
    @ManyToOne
    @JoinColumn(name = "id_bebedor", referencedColumnName = "id")
    private Bebedor id_bebedor;

    @ManyToOne
    @JoinColumn(name = "id_bebida", referencedColumnName = "id")
    private Bebida id_bebida;

    public GustanPK()
    {
        super();
    }
    
    public GustanPK(Bebedor id_bebedor, Bebida id_bebida)
    {
        super();
        this.id_bebedor = id_bebedor;
        this.id_bebida = id_bebida;
    } 
    
    public Bebedor getBebedor() {
        return id_bebedor;
    }

    public void setBebedor(Bebedor id_bebedor) {
        this.id_bebedor = id_bebedor;
    }

    public Bebida getBebida() {
        return id_bebida;
    }

    public void setBebida(Bebida id_bebida) {
        this.id_bebida = id_bebida;
    } 

}