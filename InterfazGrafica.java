import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 * Clase que representa la interfaz gráfica de la aplicación.
 * Permite al usuario interactuar con el sistema a través de menús y cuadros de entrada.
 */
public class InterfazGrafica 
{
    // Instancia de la clase Mazo
    private Mazo baraja = new Mazo();
    // Botón para tomar una carta
    private JButton tomarCarta; 

    /**
     * Constructor de la clase InterfazGrafica.
     * Inicializa la interfaz gráfica.
     */
    public InterfazGrafica()
    {

    }

    /**
     * Muestra el menú inicial donde el usuario puede elegir entre jugar contra el computador
     * o jugar en modo multijugador.
     * 
     * @return El índice de la opción seleccionada en el menú (0 para jugar contra el computador,
     *         1 para multijugador).
     */
    public int menuInicial(){
        String[] opciones = {"Jugar contra el computador", "Multijugador"};
        int seleccion = JOptionPane.showOptionDialog(null,"Elige una opción","Menú" ,JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, opciones, opciones[0]);
        return seleccion; 
    }

    /**
     * Solicita al usuario su nombre mediante un cuadro de entrada. Si el nombre ingresado no es válido,
     * muestra un mensaje de error y pide de nuevo el nombre.
     * 
     * @return El nombre ingresado por el usuario.
     */
    public String solicitarNombre(){
        String nombre = "nombre por defecto";
        boolean nombreCorrecto = false;

        while (!nombreCorrecto){
            try{
                nombre = JOptionPane.showInputDialog(null,"Ingresa tu nombre");
                if (nombre != null){
                    nombreCorrecto = true;}
                else {
                    JOptionPane.showMessageDialog(null,"No se ingresó ningun nombre","ERROR",JOptionPane.ERROR_MESSAGE);   
                }
            }

            catch(Exception e){
                JOptionPane.showMessageDialog(null,"El nombre solo debe contener letras");
            }
        }

        return nombre;
    }
}
