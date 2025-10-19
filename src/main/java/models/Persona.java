/*
 *“Un ascensor en el que caben cuatro personas atiende las llamadas
 * que se le hacen desde varios pisos. En estos pisos llegan personas
 * que quieren subir o bajar a otros pisos. Desarrollar el
 *  código del proceso ascensor y el código de los procesos persona.”
 * */
package models;


public class Persona implements Runnable{

    //Uso los casos donde las personas deben bajar y subir.
    private final Ascensor ascensor;
    private final int id;
    private final int pisoOrigen;
    private final int pisoDestino;
    private boolean adentro = false;


    public Persona(int id, int pisoOrigen, int pisoDestino, Ascensor ascensor){
        if (pisoOrigen == pisoDestino) {
            throw new IllegalArgumentException("Origen y destino no pueden ser iguales");
        }
        this.id = id;
        this.pisoOrigen = pisoOrigen;
        this.pisoDestino = pisoDestino;
        this.ascensor = ascensor;
    }

    public void run() {
        try{
            ascensor.esperarPiso(this.pisoOrigen);

            ascensor.subir(this.id);
            adentro = true;

            ascensor.esperarPiso(this.pisoDestino);

            ascensor.bajar(this.id);
            adentro = false;
            
            System.out.println("Persona: " + this.id + " completó viaje " + this.pisoOrigen + " -> " + this.pisoDestino);

        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

}
