package models;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Ascensor implements Runnable {

    private static final int CANT_PISOS = 10;
    private static final int MAX_PERSONAS = 4;

    private int pisoActual = 0;
    private int subir = 1; // 1 = subiendo, -1 = bajando
    private int numPersonas = 0;

    private final Lock lock = new ReentrantLock();
    private final Condition lleno = lock.newCondition();
    private final Condition pisoCambiado = lock.newCondition();

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                if (pisoActual == CANT_PISOS - 1) subir = -1;
                if (pisoActual == 0) subir = 1;

                pisoActual += subir;
                pisoCambiado.signalAll();
            } finally {
                lock.unlock();
            }

            try {
                Thread.sleep(1000); // controla la velocidad de desplazamiento
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void esperarPiso(int piso) throws InterruptedException {
        lock.lock();
        try {
            while (pisoActual != piso) {
                pisoCambiado.await();
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean subir(Persona p) throws InterruptedException {
        lock.lock();
        try {
            while (numPersonas == MAX_PERSONAS) {
                System.out.println("Ascensor lleno persona: " + p.getId() + " espera");
                lleno.await();
            }

            if (pisoActual == p.getPisoOrigen()) {
                numPersonas++;
                System.out.println("Persona: " + p.getId() + " se SUBE en piso: " + pisoActual);
                p.setEstado(EPersonaEstado.DENTRO);
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public void bajar(Persona p) {
        lock.lock();
        try {
            if (p.getEstado().equals(EPersonaEstado.DENTRO)) {
                numPersonas--;
                System.out.println("Persona: " + p.getId() + " se BAJA en piso: " + pisoActual);
                lleno.signalAll();
                p.setEstado(EPersonaEstado.COMPLETADO);
            }
        } finally {
            lock.unlock();
        }
    }

    public int getNumPersonas() {
        lock.lock();
        try {
            return numPersonas;
        } finally {
            lock.unlock();
        }
    }

    public int getPisoActual() {
        lock.lock();
        try {
            return pisoActual;
        } finally {
            lock.unlock();
        }
    }
}
