package modelo;

/**
 * Representa a un jugador registrado en el juego.
 *
 * @author Sofi
 */
public class Jugador {

    private int id;
    private String nombre;
    private String avatar;

    // Para un jugador nuevo, que todavía no está en la base
    public Jugador(String nombre, String avatar) {
        setNombre(nombre);
        setAvatar(avatar);
    }

    // Para un jugador que se leyó de la base (ya tiene id)
    public Jugador(int id, String nombre, String avatar) {
        this(nombre, avatar);
        this.id = id;
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
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
