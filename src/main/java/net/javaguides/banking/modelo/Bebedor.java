package net.javaguides.banking.modelo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="bebedores")
public class Bebedor {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  private String nombre;

  private String ciudad;

  private String presupuesto;
  
  public Bebedor(){;}

  public Bebedor(String nombre, String ciudad, String presupuesto)
  {
    this.nombre = nombre;
    this.ciudad = ciudad;
    this.presupuesto = presupuesto;
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

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getCiudad() {
    return ciudad;
  }

  public void setCiudad(String ciudad) {
    this.ciudad = ciudad;
  }

  public String getPresupuesto() {
    return presupuesto;
  }

  public void setPresupuesto(String presupuesto) {
    this.presupuesto = presupuesto;
  }

  @Override
  public String toString()
  {
    return this.nombre+"|"+this.ciudad+"|"+this.presupuesto;
  }
  
}


