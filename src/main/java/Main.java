import models.Ascensor;
import models.Persona;


public class Main {

    public static void main(String[] args) {
        Ascensor ascensor = new Ascensor();
        Thread hiloAscensor = new Thread(ascensor);
        Thread persona1 = new Thread(new Persona(1,0,9,ascensor));
        Thread persona2 = new Thread(new Persona(2,2,5,ascensor));
        Thread persona3 = new Thread(new Persona(3,2,7,ascensor));
        Thread persona4 = new Thread(new Persona(4,3,11,ascensor));
        Thread persona5 = new Thread(new Persona(5,6,9,ascensor));


        hiloAscensor.start();
        persona1.start();
        persona2.start();
        persona3.start();
        persona4.start();
        persona5.start();

    }

}
