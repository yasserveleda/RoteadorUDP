package roteador;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class TabelaRoteamento {

    private ArrayList<Tupla> tuplas;
    private ArrayList<Vizinho> vizinhos;
    private Semaphore semafaroTabelo;

    public TabelaRoteamento() {
        tuplas = new ArrayList<Tupla>();
        vizinhos = new ArrayList<Vizinho>();
        semafaroTabelo = new Semaphore(1);
    }

    public void update_tabela(String tabela_s, InetAddress IPAddress) {
        semafaroTabelo.tryAcquire();
        System.out.println("- - - - - - UPDATE - - - - - -");
        System.out.println("Vizinho: " + IPAddress);
        System.out.println("Tabela: " + tabela_s + "\n");

        //Declarando IPAddress como String e Removendo a '/'
        String ipsaida = "" + IPAddress;
        ipsaida = ipsaida.replace("/", "");

        Tupla tuplaVizinho = new Tupla(ipsaida, 1, ipsaida);

        removeTuplasVizinho(ipsaida);

        if (!contains(tuplaVizinho)) {
            tuplas.add(tuplaVizinho);
            vizinhos.add(new Vizinho(ipsaida, 3));
        }

        //Seta o contador do vizinho avisando que está conectado
        for (Vizinho v : vizinhos) {
            if (v.getIp().equals(ipsaida)) {
                v.setContador(3);
            }
        }

        //Separa os IPs
        String[] listaIp = tabela_s.split("\\*");
        for (int i = 0; i < listaIp.length - 1; i++) {

            String[] ip_metrica = listaIp[i + 1].split("\\;");
            String metricaString = ip_metrica[1];

            //Remove o que não for dígito 1-9
            metricaString = metricaString.replaceAll("\\D", "");
            int metrica = Integer.parseInt("" + metricaString);
            metrica = metrica + 1;
            Tupla tupla = new Tupla(ip_metrica[0], metrica, ipsaida);
            //Delimitando para 15 numero de saltos
            if (!contains(tupla) && tupla.getMetrica() < 15) {
                tuplas.add(tupla);
            }
        }
        semafaroTabelo.release();
    }

    public String get_tabela_string() {
        semafaroTabelo.tryAcquire();

        String tabela_string = "";
        System.out.println("- - - - - - TABELA ATUALIZADA - - - - - -");
        if (tuplas.isEmpty()) {
            tabela_string = "!";
            System.out.println("Tabela Vazia\n");
        } else {
            for (Tupla tupla : tuplas) {
                tabela_string = tabela_string + tupla.message();
            }
            for (Tupla elemento : tuplas) {
                System.out.println("ip: " + elemento.getIp()
                        + " | métrica: " + elemento.getMetrica()
                        + " | saída: " + elemento.getSaida() + "");
            }
            System.out.println("");
        }

        semafaroTabelo.release();
        System.out.println("- - - - - - GET - - - - - -");
        System.out.println("get_tabela_string: " + tabela_string + "\n");
       
        return tabela_string;
    }

    public ArrayList<Vizinho> retornaListaVizinhos() {
        return vizinhos;
    }

    public boolean contains(Tupla tupla) {
        for (int i = 0; i < tuplas.size(); i++) {
            if (tuplas.get(i).getIp().equalsIgnoreCase(tupla.getIp())) {
                if (tuplas.get(i).getMetrica() > tupla.getMetrica()) {
                    tuplas.remove(i);
                    return false;
                }
                return true;
            }
        }
        //Denifir IP para não ser adicionado na própria tabela de roteamento
        if (tupla.getIp().equalsIgnoreCase("10.32.148.77")) {
            return true;
        } else if (tupla.getIp().equalsIgnoreCase("127.0.0.1")) {
            return true;
        }
        return false;
    }
    
    public void procuraVizinhoOff() {
        for (Vizinho v : vizinhos) {
            if (v.getContador() == 0) {
                //remove o vizinho off encontrado
                removeVizinho(v.getIp());
                //remove tuplas com ipsaida do vizinho
                removeTuplasVizinho(v.getIp());
            }
        }
    }

    public synchronized void removeVizinho(String saida) {
        Tupla tt = null;
        if (!tuplas.isEmpty()) {
            for (int i = 0; i < tuplas.size(); i++) {
                if (tuplas.get(i).getSaida().equalsIgnoreCase(saida)) {
                    tt = tuplas.get(i);
                    tuplas.remove(tuplas.get(i));
                    System.out.println("Removeu ip: " + tt.getIp());
                }
            }
        }

    }

    public void removeTuplasVizinho(String ipVizinho) {
        if (!tuplas.isEmpty()) {
            for (int i = 0; i < tuplas.size(); i++) {
                if (tuplas.get(i).getSaida().equals(ipVizinho)) {
                    tuplas.remove(tuplas.get(i));
                }
            }
        }
    }

}
