/*
*“Un ascensor en el que caben cuatro personas atiende las llamadas
* que se le hacen desde varios pisos. En estos pisos llegan personas
* que quieren subir o bajar a otros pisos. Desarrollar el
*  código del proceso ascensor y el código de los procesos persona.”
* */
package models;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Ascensor implements Runnable{

    private static final Integer CANT_PISOS = 10;
    private static final Integer MAX_PERSONAS = 4;
    public Integer numPersonas = 0;
    private Integer subir = 1; //vale 1 o -1
    public Integer pisoActual=0;

    Lock lock = new ReentrantLock();
    Condition lleno = lock.newCondition();
    Condition pisoCambiado = lock.newCondition();

    public Ascensor(){

    }

    //INICIO DEL PROCESO
    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                if (pisoActual == CANT_PISOS - 1) {
                subir = -1;
                }

                if (pisoActual == 0) {
                    subir = 1;
                }

                pisoActual = pisoActual + subir;
                pisoCambiado.signalAll();
            } finally {
                lock.unlock();
            }
            
            try{
                Thread.sleep(100);
            }catch(InterruptedException e){}
        }
    }

    public void esperarPiso(Integer piso) throws InterruptedException{
        lock.lock();
        try{
            while (pisoActual != piso) {
                pisoCambiado.await();
            }
        }finally{
            lock.unlock();
        }
    }

    public Integer getPisoActual(){
        lock.lock();
        try{
            return pisoActual;
        }finally{
            lock.unlock();
        }
    }


    public boolean subir(Integer personaId, Integer pisoOrigen) throws InterruptedException {
        lock.lock();
        try{
            while (numPersonas == MAX_PERSONAS){
                System.out.println("ASCENSOR LLENO, Persona: " + personaId + " espera");
                lleno.await();
            }
            if(pisoActual.equals(pisoOrigen)){
                numPersonas++;
                System.out.println("Persona: " + personaId + " se SUBE en piso: " + pisoActual);
                return true;
            }
            return false;

        }finally{
            lock.unlock();
        }

    }


    public void bajar(Integer personaId){
        lock.lock();
        try{
            numPersonas--;
            System.out.println("Persona: " + personaId + " se BAJA en piso: " + pisoActual);
            lleno.signalAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }
}

