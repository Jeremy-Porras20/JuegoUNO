import javax.swing.JOptionPane;

/**
 * Clase principal que ejecuta la aplicación de juego.
 * Controla la selección del modo de juego y gestiona la interacción con el usuario para iniciar una partida.
 */
public class Main
{
    /**
     * Método principal que inicia la aplicación.
     * Presenta al usuario un menú para seleccionar el modo de juego y gestiona las opciones elegidas.
     * 
     * @param args Argumentos de línea de comandos (no utilizados en este caso).
     */
    public static void main(String[] args){
        // Crear una instancia de la interfaz gráfica
        InterfazGrafica interfaz = new InterfazGrafica();

        // Mostrar el menú inicial y obtener la opción seleccionada
        int seleccion = interfaz.menuInicial();

        // Si el usuario selecciona la opción 0 (jugar contra el computador)
        if (seleccion == 0){
            // En esta opción, se muestra un mensaje indicando que la función no está habilitada
            JOptionPane.showMessageDialog(null, "Esta función no está habilitada", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
        // Si el usuario selecciona la opción 1 (modo multijugador)
        else if(seleccion == 1){
            // Solicitar los nombres de los dos jugadores
            String nombre1 = interfaz.solicitarNombre();
            String nombre2 = interfaz.solicitarNombre();

            // Crear instancias de los jugadores con los nombres proporcionados
            Jugador jugador1 = new Jugador(nombre1);
            Jugador jugador2 = new Jugador(nombre2);

            // Crear el objeto del juego multijugador e iniciar la interfaz gráfica
            Multijugador juego = new Multijugador(jugador1, jugador2);
            juego.setVisible(true);

            // Iniciar el primer turno del juego
            juego.iniciarPrimerTurno();
        }
    }
}
