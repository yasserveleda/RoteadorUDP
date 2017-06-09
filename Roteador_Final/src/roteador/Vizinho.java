/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roteador;

import java.util.concurrent.Semaphore;

/**
 *
 * @author 12205008
 */
public class Vizinho {
    private String ip;
    private boolean chave;
    private int contador;
    private Semaphore m;
    
    public Vizinho(String ip, int contador) {
        this.ip = ip;
        this.chave = false;
        this.contador = contador;
        m = new Semaphore(1);
    }
    
    public void decrementaContador(){
        m.tryAcquire();
        if(contador > 0){
            contador--;
        }
        m.release();
    }
    
    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        m.tryAcquire();
        this.contador = contador;
        m.release();
    }

    @Override
    public String toString() {
        return "Vizinho{" + "ip=" + ip + ", chave=" + chave + "contador" + contador+'}';
    }
    
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
