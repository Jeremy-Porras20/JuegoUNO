import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;

/**
 * Representa una carta en el juego UNO.
 */
public class Carta {
    private String color;
    private String valor;
    private JButton botonCarta;

    /**
     * Constructor que crea una carta con un color y un valor específico.
     * @param color Color de la carta
     * @param valor Valor de la carta
     */
    public Carta(String color, String valor) {
        this.color = color;
        this.valor = valor;
        this.botonCarta = crearBoton();
    }

    /**
     * Crea el botón gráfico que representa la carta en la interfaz.
     * @return JButton configurado para la carta
     */
    private JButton crearBoton() {
        JButton boton = new JButton(valor + " " + color);
        boton.setPreferredSize(new Dimension(100, 150));
        boton.setBackground(Color.WHITE);

        Color colorCarta = obtenerColor(this.color);
        boton.setBorder(new LineBorder(colorCarta, 5));

        return boton;
    }

    /**
     * Convierte un nombre de color en un objeto Color.
     * @param color Nombre del color
     * @return Color correspondiente
     */
    private Color obtenerColor(String color) {
        return switch (color) {
            case "rojo" -> Color.RED;
            case "verde" -> Color.GREEN;
            case "azul" -> Color.BLUE;
            case "naranja" -> Color.ORANGE;
            case "" -> Color.BLACK;
            default -> Color.WHITE;
        };
    }

    /**
     * Devuelve el botón de la carta.
     * @return JButton de la carta
     */
    public JButton getBoton() {
        return botonCarta;
    }

    /**
     * Devuelve el color de la carta.
     * @return Color de la carta
     */
    public String getColor() {
        return color;
    }

    /**
     * Establece un nuevo color para la carta.
     * @param color Nuevo color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Devuelve el valor de la carta.
     * @return Valor de la carta
     */
    public String getValor() {
        return valor;
    }

    /**
     * Establece un nuevo valor para la carta.
     * @param valor Nuevo valor
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * Representación de la carta en forma de texto.
     * @return Cadena de texto con color y valor
     */
    @Override
    public String toString() {
        return color + " " + valor;
    }
}
