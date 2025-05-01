import java.util.ArrayList;
import java.util.List;

/**
 * Representa a un jugador en el juego UNO.
 */
public class Jugador {
    private String nombre;
    private List<Carta> baraja;

    /**
     * Constructor que crea un nuevo jugador con su nombre y una baraja vacía.
     * @param nombre Nombre del jugador
     */
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.baraja = new ArrayList<>();
    }

    /**
     * Verifica si el jugador todavía tiene cartas en su baraja.
     * @return true si tiene cartas, false si no
     */
    public boolean poseeCartas() {
        return !baraja.isEmpty();
    }

    /**
     * Agrega una carta a la baraja del jugador.
     * @param carta Carta a agregar
     */
    public void tomarCarta(Carta carta) {
        baraja.add(carta);
    }

    /**
     * Elimina y devuelve la carta seleccionada de la baraja del jugador.
     * @param indiceCarta Índice de la carta a tirar
     * @return Carta que fue retirada de la baraja
     */
    public Carta tirarCarta(int indiceCarta) {
        return baraja.remove(indiceCarta);
    }

    /**
     * Devuelve el nombre del jugador.
     * @return Nombre del jugador
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Devuelve la lista de cartas que tiene el jugador.
     * @return Baraja del jugador
     */
    public List<Carta> getBaraja() {
        return this.baraja;
    }

    /**
     * Establece una nueva lista de cartas para el jugador.
     * @param barajaNueva Nueva baraja a asignar
     */
    public void setBaraja(List<Carta> barajaNueva) {
        this.baraja = barajaNueva;
    }
}
