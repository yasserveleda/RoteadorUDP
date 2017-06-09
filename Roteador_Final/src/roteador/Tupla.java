/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roteador;

/**
 *
 * @author 12205008
 */
public class Tupla {
    private String saida, ip;
    private int metrica;
    
    public Tupla(String ip, int metrica, String saida) {
        this.metrica = metrica;
        this.saida = saida;
        this.ip = ip;
    }
    
    public String getIp(){
        return ip;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public int getMetrica() {
        return metrica;
    }

    public void setMetrica(int metrica) {
        this.metrica = metrica;
    }

    public String getSaida() {
        return saida;
    }

    public void setSaida(String saida) {
        this.saida = saida;
    }
    
    public String toString(){
        return "IP: "+ip+" Métrica: "+metrica+" Sáida: "+saida;
    }
    
    public String message(){
        return "*"+ip+";"+metrica;
    }
    
}
