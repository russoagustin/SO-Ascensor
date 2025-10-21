package views;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import models.Ascensor;
import models.Persona;

public class AscensorGUI extends JPanel implements Runnable {

    private final Ascensor ascensor;
    private final List<Persona> personas;
    private static final int CANT_PISOS = 10;
    private static final int ANCHO = 350;
    private static final int ALTO = 600;
    private static final int ALTURA_PISO = ALTO / CANT_PISOS;

    public AscensorGUI(Ascensor ascensor, List<Persona> personas) {
        this.ascensor = ascensor;
        this.personas = personas;
        new Thread(this).start(); // hilo para repintar constantemente
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(10); // repintar cada 10ms 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Apariencia del EDIFICIO
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(50, 0, 200, ALTO);

        // pisos
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < CANT_PISOS; i++) {
            int y = ALTO - (i + 1) * ALTURA_PISO;
            g.drawLine(50, y, 250, y);
            g.drawString("Piso " + i, 10, y + ALTURA_PISO / 2);
        }

        // ascensor
        int pisoActual = ascensor.getPisoActual();
        int yAscensor = ALTO - (pisoActual + 1) * ALTURA_PISO + 10;
        g.setColor(Color.GRAY);
        g.fillRect(120, yAscensor, 60, ALTURA_PISO - 20);

        // personas (esperando o dentro)
        for (Persona p : personas) {
            g.setColor(new Color(50, 100, 180));
            if (ascensor.estaDentro(p)) {
                // persona dentro del ascensor
                g.fillRect(130 + (p.getId() % 4) * 12, yAscensor + 15, 10, 10);
            } else if (p.isViajeTerminado()) {
                // persona ya bajó en su destino
                int y = ALTO - (p.getPisoDestino() + 1) * ALTURA_PISO + ALTURA_PISO / 2;
                g.setColor(new Color(0, 200, 0)); // verde = ya llegó
                g.fillRect(270, y, 10, 10);
            } else {
                // persona esperando su turno en su piso de origen
                int y = ALTO - (p.getPisoOrigen() + 1) * ALTURA_PISO + ALTURA_PISO / 2;
                g.setColor(new Color(50, 100, 180));
                g.fillRect(270, y, 10, 10);
            }
        }

        // información
        g.setColor(Color.BLACK);
        g.drawString("Piso actual: " + pisoActual, 60, 20);
        g.drawString("Personas en ascensor: " + ascensor.getNumPersonas(), 60, 35);
    }

    
}
