/*
 *“Un ascensor en el que caben cuatro personas atiende las llamadas
 * que se le hacen desde varios pisos. En estos pisos llegan personas
 * que quieren subir o bajar a otros pisos. Desarrollar el
 *  código del proceso ascensor y el código de los procesos persona.”
 * */
package models;

public class Persona implements Runnable{

    //Uso los casos donde las personas deben bajar y subir.
    public enum Direccion { SUBIR, BAJAR }
    private final Ascensor ascensor;
    private final int id;
    private final int pisoOrigen;
    private final int pisoDestino;
    private boolean estaAdentro = false;

    public Persona(int id, int pisoOrigen, int pisoDestino, Ascensor ascensor){
        if (pisoOrigen == pisoDestino) {
            throw new IllegalArgumentException("Origen y destino no pueden ser iguales");
        }
        this.id = id;
        this.pisoOrigen = pisoOrigen;
        this.pisoDestino = pisoDestino;
        this.ascensor = ascensor;
    }


    public int getId()            { return id; }
    public int getPisoOrigen()    { return pisoOrigen; }
    public int getPisoDestino()   { return pisoDestino; }
    public Direccion getDireccion(){ return pisoDestino > pisoOrigen ? Direccion.SUBIR : Direccion.BAJAR; }

    public void run() {
        while (true){
            if (ascensor.pisoActual == pisoOrigen && !estaAdentro){
                ascensor.subir();
                estaAdentro = true;
                System.out.println("persona " + this.id + " se SUBE en piso " + pisoOrigen + " personas actuales: " + ascensor.numPersonas);
            }
            if (ascensor.pisoActual == pisoDestino && estaAdentro){
                ascensor.bajar();
                System.out.println("persona " + this.id + " se BAJA en piso " + pisoDestino + " personas actuales: " + ascensor.numPersonas);
                break;
            }
        }
    }

    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", origen=" + pisoOrigen +
                ", destino=" + pisoDestino +
                ", dir=" + getDireccion() +
                '}';
    }


}
