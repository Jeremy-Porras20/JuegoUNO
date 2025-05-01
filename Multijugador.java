/**
 * Clase que representa la interfaz gráfica del modo multijugador del juego UNO.
 * Permite a dos jugadores interactuar con el mazo y gestionar turnos.
 */
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;
public class Multijugador extends JFrame {
    private JuegoUNO juego = new JuegoUNO();
    private Jugador jugador1;
    private Jugador jugador2;
    private JPanel panelMazo;
    private JLabel cartaVisible;
    private JPanel panelCartas = new JPanel();
    private int contador = 0;
    private JButton tomar;
    private JButton botonTomar;
    private JLabel etiquetaTurno;
    private int turnoActual = 0;

    /**
     * Constructor de la clase Multijugador.
     * @param jugador1 Primer jugador
     * @param jugador2 Segundo jugador
     */
    public Multijugador(Jugador jugador1, Jugador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;

        inicializarVentana();
        repartirCartas();

        panelMazo = new JPanel();
        cartaVolteada(panelMazo);
        cartaVisible(panelMazo);
    }

    /**
     * Inicializa la ventana gráfica del juego.
     */
    public void inicializarVentana() {
        setTitle("Mi mano de UNO");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        etiquetaTurno = new JLabel("", SwingConstants.LEFT);
        etiquetaTurno.setFont(new Font("Arial", Font.BOLD, 15));
        etiquetaTurno.setForeground(Color.BLACK);
        etiquetaTurno.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(etiquetaTurno, BorderLayout.PAGE_START);
    }

    /**
     * Reparte cartas iniciales a los jugadores.
     */
    public void repartirCartas() {
        ArrayList<Jugador> listaJugadores = juego.getListaJugadores();
        listaJugadores.add(jugador1);
        listaJugadores.add(jugador2);
        juego.repartirCartasIniciales();
    }

    /**
     * Crea y configura el botón para tomar una carta del mazo.
     * @param jugador Jugador que tomará la carta
     */
    public void botonTomar(Jugador jugador) {
        botonTomar = new JButton("Tomar Carta");
        botonTomar.setPreferredSize(new Dimension(200, 75));
        botonTomar.setBackground(Color.WHITE);
        botonTomar.setBorder(new LineBorder(Color.GRAY, 5));

        botonTomar.addActionListener(e -> {
                    juego.repartirCartas(jugador, 1);
                    mazoJugador(jugador);
                    panelMazo.revalidate();
                    panelMazo.repaint();
            });

        panelMazo.add(botonTomar);
        add(panelMazo, BorderLayout.NORTH);
    }

    /**
     * Crea y configura el botón para tomar las cartas acumuladas por penalización.
     * @param jugador Jugador que tomará las cartas acumuladas
     */
    public void botonTomarCartasAcomuladas(Jugador jugador) {
        tomar = new JButton("tomar las cartas\nque me corresponden");
        tomar.setPreferredSize(new Dimension(100, 100));
        tomar.setBackground(Color.WHITE);
        tomar.setBorder(new LineBorder(Color.GRAY, 5));

        tomar.addActionListener(e -> {
                    juego.repartirCartas(jugador, contador);
                    contador = 0;
                    //System.out.println("contador: " + contador);
                    tomar.setVisible(false);
                    panelMazo.revalidate();
                    panelMazo.repaint();
                    siguienteTurno();
            });

        tomar.setVisible(false);
        panelMazo.add(tomar);
        add(panelMazo, BorderLayout.NORTH);
    }

    /**
     * Devuelve el jugador que tiene el turno actual.
     * @return Jugador en turno
     */
    public Jugador jugadorActual() {
        turnoActual = (turnoActual + 1) % juego.getListaJugadores().size();
        return juego.getListaJugadores().get(turnoActual);
    }

    /**
     * Avanza al siguiente turno y actualiza la interfaz.
     */
    public void siguienteTurno() {
        Jugador jugador = jugadorActual();
        etiquetaTurno.setText("Turno de: " + jugador.getNombre());
        panelCartas.removeAll();
        panelMazo.remove(botonTomar);
        panelMazo.remove(tomar);
        mazoJugador(jugador);
        botonTomar(jugador);
        botonTomarCartasAcomuladas(jugador);
        tomar.setVisible(contador > 0);
        revalidate();
        repaint();
    }

    /**
     * Inicia el primer turno del juego.
     */
    public void iniciarPrimerTurno() {
        Jugador jugador = jugadorActual();
        etiquetaTurno.setText("Turno de: " + jugador.getNombre());
        mazoJugador(jugador);
        botonTomar(jugador);
        botonTomarCartasAcomuladas(jugador);
    }

    /**
     * Muestra las cartas del jugador actual en la interfaz.
     * @param jugador Jugador actual
     */
    public void mazoJugador(Jugador jugador){
        panelCartas.removeAll();
        panelCartas.setLayout(new FlowLayout(FlowLayout.LEFT));

        /*int ancho = jugador.getBaraja().size() * 110; 
        panelCartas.setPreferredSize(new Dimension(ancho, 200));

        JScrollPane scroll = new JScrollPane();
        panelCartas.add(scroll, BorderLayout.SOUTH);
         */
        List<Carta> barajaJugador = jugador.getBaraja();

        for (Carta carta : barajaJugador) {
            JButton botonCarta = carta.getBoton();

            for (ActionListener accion : botonCarta.getActionListeners()) {
                botonCarta.removeActionListener(accion);
            }

            botonCarta.addActionListener(e -> 
                    {
                        Carta cartaArriba = juego.getLanzamientos().get(juego.getLanzamientos().size() - 1);
                        boolean validacion = false;
                        if (cartaArriba.getColor().equals("") && contador == 0){
                            boolean esUnicaCancelar = barajaJugador.size() == 1 && carta.getValor().equals("cancelar");
                            validacion = esUnicaCancelar || juego.validarCartaDepuesDeEspecial(carta);
                        }

                        else {
                            validacion = juego.validarCarta(carta);
                        }

                        if (validacion) {

                            int accionEspecial = juego.accionCartaEspecial(carta);

                            if (accionEspecial == -1){
                                Carta cartaEncontrada = juego.cartaBuscar(cartaArriba);
                                if (!cartaEncontrada.getValor().equals("") || juego.getLanzamientos().size() > 1){
                                    accionEspecial = 0;
                                    juego.getLanzamientos().add(cartaEncontrada);

                                    barajaJugador.remove(carta);
                                    revalidate();
                                    repaint();
                                    siguienteTurno();
                                    cartaVisible.setText(cartaEncontrada.getValor() + " " + cartaEncontrada.getColor());

                                    return;

                                }
                                else {
                                    return;
                                }
                            }

                            barajaJugador.remove(carta);

                            if (jugador.getBaraja().isEmpty()) {
                                JOptionPane.showMessageDialog(this, jugador.getNombre() + " ha ganado el juego ");
                                System.exit(0);
                            }

                            contador += accionEspecial;
                            System.out.println("contador: " + contador);
                            //aparicionBotonTomarCartas(carta);
                            panelCartas.remove(botonCarta); 

                            System.out.println("turno: " + turnoActual);

                            juego.getLanzamientos().add(carta);
                            cartaVisible.setText(carta.getValor() + " " + carta.getColor());

                            siguienteTurno();
                            revalidate();
                            repaint();
                            panelCartas.revalidate();
                            panelCartas.repaint();

                        }

                });

            panelCartas.add(botonCarta);
        }
        panelCartas.setBackground(Color.WHITE);
        add(panelCartas, BorderLayout.SOUTH);

    }


    /**
     * Muestra u oculta el botón de tomar cartas según la carta jugada.
     * @param carta Carta jugada
     */
    public void aparicionBotonTomarCartas(Carta carta) {
        if (carta.getValor().equals("+2") || carta.getValor().equals("+3") || carta.getValor().equals("+4")) {
            tomar.setVisible(true);
        }
        if (carta.getValor().equals("cancelar")) {
            contador = 0;
            tomar.setVisible(false);
        }
        panelMazo.revalidate();
        panelMazo.repaint();
    }

    /**
     * Muestra la carta visible en juego.
     * @param panelMazo Panel que contiene el mazo
     */
    public void cartaVisible(JPanel panelMazo) {
        List<Carta> lanzamientos = juego.getLanzamientos();
        Carta carta = lanzamientos.get(lanzamientos.size() - 1);
        cartaVisible = new JLabel(carta.getValor() +" "+ carta.getColor(), SwingConstants.CENTER);
        cartaVisible.setPreferredSize(new Dimension(150, 200));
        cartaVisible.setFont(new Font("Arial", Font.BOLD, 24));
        cartaVisible.setOpaque(true);
        cartaVisible.setBackground(Color.WHITE);
        cartaVisible.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panelMazo.add(cartaVisible);
        add(panelMazo, BorderLayout.WEST);
    }

    /**
     * Muestra la imagen de una carta volteada.
     * @param mazo Panel donde se colocará la carta volteada
     */
    public void cartaVolteada(JPanel mazo) {
        ImageIcon imagenLogo = new ImageIcon("C:\\Users\\jerem\\Downloads\\Tarea_Programada1\\cartaBoltada.jpg");
        Image img = imagenLogo.getImage();
        Image nuevaImg = img.getScaledInstance(150, 200, Image.SCALE_SMOOTH);
        ImageIcon imagenLogoRedimensionado = new ImageIcon(nuevaImg);
        JLabel cartaVolteada = new JLabel(imagenLogoRedimensionado, SwingConstants.CENTER);
        cartaVolteada.setPreferredSize(new Dimension(150, 200));
        cartaVolteada.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        mazo.add(cartaVolteada);
        add(mazo, BorderLayout.WEST);
    }

    /**
     * Devuelve el valor actual del contador de penalizaciones.
     * @return contador
     */
    public int getContador() {
        return contador;
    }
}
