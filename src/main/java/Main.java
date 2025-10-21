import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import models.Ascensor;
import views.AscensorGUI;
import models.Persona;


public class Main {
    private static final int ANCHO = 800;
    private static final int ALTO = 710;

    public static void main(String[] args) {
        Ascensor ascensor = new Ascensor();
        new Thread(ascensor).start();

        List<Persona> personas = new ArrayList<>();

        // Procesos Personas con origen y destino fijos
        personas.add(new Persona(0, 0, 5, ascensor)); // del piso 0 al 5
        personas.add(new Persona(1, 3, 8, ascensor)); // del piso 3 al 8
        personas.add(new Persona(2, 6, 2, ascensor)); // del piso 6 al 2
        personas.add(new Persona(3, 9, 1, ascensor)); // del piso 9 al 1
        personas.add(new Persona(4, 4, 7, ascensor)); // del piso 4 al 7
        personas.add(new Persona(5, 3, 5, ascensor)); // del piso 0 al 5

        //  hilos de personas
        for (Persona p : personas) {
            new Thread(p).start();
        }

        // ventana
        JFrame ventana = new JFrame("Simulación Ascensor (Swing)");
        AscensorGUI panel = new AscensorGUI(ascensor, personas);
        ventana.add(panel);
        ventana.pack();
        ventana.setSize(ANCHO+100, ALTO + 160);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);
        ventana.setVisible(true);
    }

}