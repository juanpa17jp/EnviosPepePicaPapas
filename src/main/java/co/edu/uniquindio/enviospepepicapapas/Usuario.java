package co.edu.uniquindio.enviospepepicapapas;

public abstract class  Usuario implements Cloneable{
    private int id;
    private String nombre;
    private String email;

    @Override
    public Usuario clone(){
        try{
            return (Usuario) super.clone();
        }catch(CloneNotSupportedException e){
            throw new RuntimeException(e);
        }
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
