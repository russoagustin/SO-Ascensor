package views;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import models.Ascensor;
import models.Persona;

public class AscensorGUI extends JPanel implements Runnable {

    private final Ascensor ascensor;
    private final List<Persona> personas;
    private static final int CANT_PISOS = 10;
    private static final int ANCHO = 350;
    private static final int ALTO = 600;
    private static final double ESCALA = 1.3;
    private static final int ALTURA_PISO = ALTO / CANT_PISOS;

    public AscensorGUI(Ascensor ascensor, List<Persona> personas) {
        this.ascensor = ascensor;
        this.personas = personas;

        setPreferredSize(new Dimension((int)(ANCHO * ESCALA), (int)(ALTO * ESCALA)));
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

        // Aplicamos la escala global
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(ESCALA, ESCALA);

        // Fondo celeste (cielo)
        g2d.setColor(new Color(135, 206, 235)); // celeste cielo
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Franja de tierra en la base
        g2d.setColor(new Color(139, 69, 19)); // marrón tierra
        g2d.fillRect(0, ALTO , getWidth(), 20); // 20 píxeles de alto en la base

        

        // Apariencia del EDIFICIO
        g2d.setColor(new Color(176, 174, 245));
        g2d.fillRect(50, 0, 200, ALTO);

        // Eje del ascensor (franja negra vertical)
        int ejeX = 120; // misma posición X que el ascensor
        int ejeAncho = 60; // mismo ancho que el ascensor
        g2d.setColor(Color.GRAY);
        g2d.fillRect(ejeX, 0, ejeAncho, ALTO);

        g2d.setColor(Color.BLACK);
        g2d.drawRect(ejeX, 0, ejeAncho, ALTO - 1); // borde negro


        // pisos
        g2d.setColor(Color.BLACK);
        for (int i = 0; i < CANT_PISOS; i++) {
            int y = ALTO - (i + 1) * ALTURA_PISO;
            g2d.drawLine(50, y, 250, y);

            Graphics2D g2 = (Graphics2D) g2d;
            Font fuente = new Font("Arial", Font.BOLD, 12); // fuente más grande y en negrita
            g2.setFont(fuente);
            g2.setColor(Color.BLACK);
            g2.drawString(""+i, 20, y + ALTURA_PISO / 2);
        }

        // ascensor
        int pisoActual = ascensor.getPisoActual();
        int yAscensor = ALTO - (pisoActual + 1) * ALTURA_PISO + 10;
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(121, yAscensor, 60-1, ALTURA_PISO - 20);

        int[] contadorPorPiso = new int[CANT_PISOS];

        for (Persona p : personas) {
            if (ascensor.estaDentro(p)) {
                int x = 130 + (p.getId() % 4) * 12;
                int y = yAscensor + 15;

                // cabeza
                g2d.setColor(new Color(50, 100, 180));
                g2d.fillOval(x, y, 10, 10);

                // cuerpo
                g2d.fillOval(x - 1, y + 8, 12, 12);

                // número de destino dentro del cuerpo
                g2d.setFont(new Font("Arial", Font.PLAIN, 9));
                g2d.setColor(Color.WHITE);
                g2d.drawString("" + p.getPisoDestino(), x + 2, y + 18);
            } else if (p.isViajeTerminado()) {
                int piso = p.getPisoDestino();
                int y = ALTO - (piso + 1) * ALTURA_PISO + ALTURA_PISO / 2 - 15;
                int desplazamiento = contadorPorPiso[piso] * 14; // 14 px entre personas
                int x = 190 + desplazamiento;

                g2d.setColor(new Color(0, 150, 20));
                // cabeza
                g2d.fillOval(x, y, 10, 10);

                // cuerpo
                g2d.fillOval(x - 1, y + 8, 12, 12);

                // número de destino
                g2d.setFont(new Font("Arial", Font.PLAIN, 9));
                g2d.setColor(Color.WHITE);
                g2d.drawString("" + piso, x + 2, y + 18);

                contadorPorPiso[piso]++;
            } else {
                int y = ALTO - (p.getPisoOrigen() + 1) * ALTURA_PISO + ALTURA_PISO / 2;

                // cabeza
                g2d.setColor(new Color(50, 100, 180));
                g2d.fillOval(200, y, 10, 10);

                // cuerpo
                g2d.fillOval(199, y + 8, 12, 12);

                // número de destino
                g2d.setFont(new Font("Arial", Font.PLAIN, 9));
                g2d.setColor(Color.WHITE);
                g2d.drawString("" + p.getPisoDestino(), 202, y + 18);
            }
        }

        // Estilos del letrero
        // cartel de información
        Font fuente = new Font("Arial", Font.BOLD, 16); // fuente más grande y en negrita
        g2d.setFont(fuente);

        // fondo gris del cartel
        g2d.setColor(Color.GRAY);
        g2d.fillRect(320, 200, 280, 100); // posición y tamaño del cartel

        // texto blanco
        g2d.setColor(Color.WHITE);
        g2d.drawString("Piso actual: " + pisoActual, 340, 230);
        g2d.drawString("Personas en ascensor: " + ascensor.getNumPersonas(), 360, 260);
        
    }
}
