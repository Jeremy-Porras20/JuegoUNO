import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Representa el mazo de cartas del juego UNO.
 */
public class Mazo {
    private List<Carta> baraja;

    /**
     * Constructor. Crea y baraja el mazo de cartas.
     */
    public Mazo() {
        baraja = new ArrayList<>();
        crearBaraja();
        barajarMazo();
    }

    /**
     * Crea todas las cartas del mazo: numéricas y especiales.
     */
    private void crearBaraja() {
        String[] colores = {"rojo", "azul", "verde", "naranja"};
        String[] valores = {"0", "1", "2", "3", "4", "5", "6", "7", "8"};

        for (int i = 0; i <= 1; i++) {
            for (String color : colores) {
                for (String valor : valores) {
                    Carta nuevaNumerica = new Carta(color, valor);
                    baraja.add(nuevaNumerica);
                }
            }
        }

        String[] valoresEspeciales = {"+2", "+3", "+4", "cancelar", "buscar"};
        for (int i = 0; i <= 3; i++) {
            for (String valor : valoresEspeciales) {
                Carta cartaEspecial = new Carta("", valor);
                baraja.add(cartaEspecial);
            }
        }
    }

    /**
     * Quita y devuelve la última carta de una lista dada.
     * @param baraja Lista de cartas desde donde quitar
     * @return La carta removida o null si está vacía
     */
    public Carta quitarCarta(List<Carta> baraja) {
        if (baraja.isEmpty())
            return null;
        else
            return baraja.remove(baraja.size() - 1);
    }

    /**
     * Devuelve una carta aleatoria del mazo y la elimina de la baraja.
     * @return Carta aleatoria
     */
    public Carta repartirCarta() {
        Random random = new Random();
        int i = random.nextInt(baraja.size());
        return baraja.remove(i);
    }

    /**
     * Reparte una carta no especial (de color) al inicio del juego.
     * @return Carta no especial
     */
    public Carta repartirCartaNoEspecial() {
        Carta carta;
        do {
            carta = repartirCarta();
        } while (carta.getColor().equals(""));
        return carta;
    }

    /**
     * Baraja el mazo aleatoriamente.
     */
    public void barajarMazo() {
        Random random = new Random();
        int cantidadCartas = baraja.size();
        for (int i = 0; i < cantidadCartas; i++) {
            int indiceAleatorio = random.nextInt(baraja.size());
            Carta seleccionada = baraja.remove(indiceAleatorio);
            baraja.add(seleccionada);
        }
    }

    /**
     * Devuelve la lista de cartas que quedan en el mazo.
     * @return Lista de cartas
     */
    public List<Carta> getBaraja() {
        return baraja;
    }
}
