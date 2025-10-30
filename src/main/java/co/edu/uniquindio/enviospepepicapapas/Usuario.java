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
}
