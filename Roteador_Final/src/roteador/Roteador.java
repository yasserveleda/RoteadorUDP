package roteador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Roteador {

    public static void main(String[] args) throws IOException, InterruptedException {
        /* Lista de endereço IPs dos vizinhos */
        ArrayList<String> ip_list = new ArrayList<>();
        
        /* Le arquivo de entrada com lista de IPs dos roteadores vizinhos. */
        try ( BufferedReader inputFile = new BufferedReader(new FileReader("IPVizinhos.txt"))) {
            String ip;
            
            while( (ip = inputFile.readLine()) != null){
                ip_list.add(ip);
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Roteador.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        /* Cria instâncias da tabela de roteamento e das threads de envio e recebimento de mensagens. */
        TabelaRoteamento tabela = new TabelaRoteamento();
        Thread sender = new Thread(new MessageSender(tabela, ip_list));
        Thread receiver = new Thread(new MessageReceiver(tabela));
        
        sender.start();
        receiver.start();
        
        new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ArrayList<Vizinho> vizinhos = tabela.retornaListaVizinhos();
                //Percorre a lista dos IPs vizinhos
                for(Vizinho v : vizinhos){
                    //Decrementa o contador dos IPs vizinhos
                    v.decrementaContador();
                }
            }
        },5000, 5000);
        
        //Procura vizinhos que estão off
        new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                tabela.procuraVizinhoOff();
            }
        },30000, 30000);
        
    }
    
}
