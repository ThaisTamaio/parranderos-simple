package net.javaguides.banking.modelo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="bares")
public class Bar {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String nombre;

    private String ciudad;

    private String presupuesto;

    private Integer cant_sedes;

    public Bar(){;}

    public Bar(String nombre, String ciudad, String presupuesto, Integer cant_sedes)
    {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.presupuesto = presupuesto;
        this.cant_sedes = cant_sedes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad){
        this.ciudad = ciudad;
    }

    public String getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(String presupuesto){
        this.presupuesto = presupuesto;
    }

    public Integer getCant_sedes() {
        return cant_sedes;
    }

    public void setCant_sedes(Integer cant_sedes){
        this.cant_sedes = cant_sedes;
    }

    @Override
    public String toString()
    {
        return this.nombre+"|"+this.ciudad+"|"+this.presupuesto+"|"+this.cant_sedes;
    }

    public Bar orElseThrow(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'orElseThrow'");
    }
    
}