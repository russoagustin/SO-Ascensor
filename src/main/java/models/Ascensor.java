/*
*“Un ascensor en el que caben cuatro personas atiende las llamadas
* que se le hacen desde varios pisos. En estos pisos llegan personas
* que quieren subir o bajar a otros pisos. Desarrollar el
*  código del proceso ascensor y el código de los procesos persona.”
* */
package models;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Ascensor implements Runnable{
    private static final Integer CANT_PISOS = 10;
    public Integer numPersonas = 0;
    private Integer subir = 1; //vale 1 o -1
    public Integer pisoActual=0;

    public Ascensor(){

    }


//INICIO DEL PROCESO
    @Override
    public void run() {
        while (true) {
            pisoActual = pisoActual + subir;
            if (pisoActual == CANT_PISOS - 1) {
                subir = -1;
            }
            if (pisoActual == 0) {
                subir = 1;
            }

            //Thread.sleep(1000); // Simula el tiempo de movimiento
        }
    }

    //Monitores
    public void subir(){
/*
            // Mientras NO se cumpla la condición de entrada (piso y espacio)...
            while (pisoActual != pisoOrigen || numPersonas >= CAPACIDAD) {
                // Si la condición de piso no se cumple, esperamos en ascensorDisponible
                if (pisoActual != pisoOrigen) {
                    System.out.println("Persona espera en piso " + pisoOrigen + " (Ascensor no ha llegado).");
                    // Si la condición de espacio no se cumple (ascensor lleno), esperamos en espacioDisponible
                } else if (numPersonas >= CAPACIDAD) {
                    System.out.println("Persona espera en piso " + pisoOrigen + " (Ascensor lleno).");
                }
            }
*/
            // Condición cumplida: Entra en el ascensor
            numPersonas++;
            //System.out.println("Personas actuales: " + numPersonas);
            //System.out.println("--> SUBE. Persona entra en piso " + pisoActual + ". Ocupación: " + numPersonas);

    }


    public void bajar(){
        numPersonas--;
        //System.out.println("Personas actuales: " + numPersonas);
        //System.out.println("Persona baja en piso " + pisoActual + ". Ocupación: " + numPersonas);
    }


}

