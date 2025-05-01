import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal que gestiona la lógica del juego UNO.
 */
public class JuegoUNO 
{
    /** Lista de jugadores en la partida */
    private ArrayList<Jugador> jugadores; 

    /** Mazo de cartas del juego */
    private Mazo mazo;

    /** Pila de cartas que han sido lanzadas */
    private ArrayList<Carta> lanzamientos;

    /** Indica si se permite lanzar cualquier color (no utilizado actualmente) */
    private boolean permitirCualquierColor = false;

    /**
     * Constructor que inicializa el mazo, la pila de lanzamientos y la lista de jugadores.
     */
    public JuegoUNO()
    {
        this.mazo = new Mazo();
        this.lanzamientos = new ArrayList<>();
        this.jugadores = new ArrayList<>();
    }

    /**
     * Reparte 5 cartas a cada jugador y coloca una carta no especial como inicio de la pila.
     */
    public void repartirCartasIniciales(){
        for (int i = 0; i <= 4; i++){
            for (Jugador jugador : jugadores){
                jugador.tomarCarta(mazo.repartirCarta());
            }
        }

        Carta cartaLanzamiento = mazo.repartirCartaNoEspecial();
        lanzamientos.add(cartaLanzamiento);
    }

    /**
     * Reparte una cantidad específica de cartas a un jugador.
     * @param jugador Jugador que recibe las cartas
     * @param cantidad Número de cartas a repartir
     */
    public void repartirCartas(Jugador jugador,int cantidad){
        for (int i = 0; i < cantidad; i++){
            jugador.tomarCarta(mazo.repartirCarta());
        }
    }

    /**
     * Agrega un jugador a la lista de jugadores.
     * @param jugador Jugador a agregar
     */
    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    /**
     * Valida si una carta es válida justo después de que se haya lanzado una carta especial sin color.
     * @param carta Carta que se desea jugar
     * @return true si es válida, false si no
     */
    public boolean validarCartaDepuesDeEspecial(Carta carta){
        Carta ultimaCarta = lanzamientos.get(lanzamientos.size()-1);
        boolean valida = false;
        if (ultimaCarta.getColor().equals("") && !ultimaCarta.getValor().equals("buscar")){
            if (carta.getColor().equals("verde") || carta.getColor().equals("naranja") || carta.getColor().equals("rojo") || carta.getColor().equals("azul")){
                valida = true;
            }
        }
        return valida;
    }

    /**
     * Busca en la pila una carta que coincida con color o valor con la anterior a la carta de tipo "buscar".
     * @param carta Carta especial "buscar"
     * @return Carta encontrada que coincide, o una carta vacía si no se encuentra
     */
    public Carta cartaBuscar(Carta carta){
        Carta elegida = new Carta("","");
        Carta ultima = lanzamientos.get(lanzamientos.size()-2);
        System.out.println("ultima:  " + ultima.getValor() + "" + ultima.getColor());

        for (int i = lanzamientos.size()-3; i >= 0; i--){
            elegida = lanzamientos.get(i);
            if ( elegida.getValor().equals(ultima.getValor()) || elegida.getColor().equals(ultima.getColor()) ){
                System.out.println("elegida:  " +elegida.getValor() + "" + elegida.getColor());
                lanzamientos.remove(elegida);
                return elegida;
            }
        }

        return elegida;
    }

    /**
     * Valida si una carta puede ser jugada sobre la carta superior de la pila.
     * @param carta Carta a validar
     * @return true si es válida, false si no
     */
    public boolean validarCarta(Carta carta){
        Carta cartaArriba = lanzamientos.get(lanzamientos.size()-1);
        String colorArriba = cartaArriba.getColor();
        String colorCarta = carta.getColor();
        String valorArriba = cartaArriba.getValor();
        String valorCarta = carta.getValor();

        boolean cartaValida = false;

        if (colorArriba.equals("verde") || colorArriba.equals("naranja") || colorArriba.equals("rojo") || colorArriba.equals("azul")) {
            if (colorCarta.equals(colorArriba) || valorCarta.equals(valorArriba) || (colorCarta.equals("") && !valorCarta.equals("cancelar"))) {
                cartaValida = true;
            }
            else if (valorCarta.equals("buscar en la pila de cartas")){
                cartaValida = true;
            }
        }

        else if (colorArriba.equals("") && colorCarta.equals("")){
            if (valorArriba.equals("+2")){
                if (valorCarta.equals("+2") || valorCarta.equals("+3") || valorCarta.equals("+4") || valorCarta.equals("cancelar")){
                    cartaValida = true; 
                }
            }
            else if (valorArriba.equals("+3")){
                if (valorCarta.equals("+3") || valorCarta.equals("+4") || valorCarta.equals("cancelar")){
                    cartaValida = true; 
                }
            }
            else if (valorArriba.equals("+4")){
                if (valorCarta.equals("+4") || valorCarta.equals("cancelar")){
                    cartaValida = true;
                }
            }
            else if (valorArriba.equals("cancelar")){
                if (colorCarta.equals("") || colorCarta.equals("verde") || colorCarta.equals("naranja") || colorCarta.equals("rojo") || colorCarta.equals("azul")){
                    cartaValida = true;
                }
            }
        }
        return cartaValida;
    }

    /**
     * Determina el efecto de una carta especial jugada.
     * 
     * @param cartaJugada Carta especial que fue jugada
     * @return Número de cartas que el siguiente jugador debe tomar:
     *         - 0 si es "cancelar" (se omite turno)
     *         - 2 si es "+2"
     *         - 3 si es "+3"
     *         - 4 si es "+4"
     *         - -1 si es "buscar" (buscar en la pila)
     */
    public int accionCartaEspecial(Carta cartaJugada){
        int cartasPorTomar = 0;

        switch (cartaJugada.getValor()) {
            case "+2": cartasPorTomar = 2; break;
            case "+3": cartasPorTomar = 3; break;
            case "+4": cartasPorTomar = 4; break;
            case "buscar": cartasPorTomar = -1; break;
            case "cancelar": cartasPorTomar = 0; break;
        }

        return cartasPorTomar;
    }

    /**
     * Devuelve la lista de jugadores actuales en la partida.
     * @return Lista de jugadores
     */
    public ArrayList<Jugador> getListaJugadores(){
        return jugadores;
    }

    /**
     * Devuelve el mazo de cartas del juego.
     * @return Objeto mazo
     */
    public Mazo getMazo(){
        return mazo;
    }

    /**
     * Devuelve la lista de cartas que han sido lanzadas.
     * @return Pila de lanzamientos
     */
    public ArrayList<Carta> getLanzamientos(){
        return lanzamientos;
    }

}
