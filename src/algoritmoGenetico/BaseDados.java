package algoritmoGenetico;

import model.Cidade;

import java.util.HashMap;
import java.util.Map;

public class BaseDados {

    public HashMap<String, Double> distancias = new HashMap<String, Double>();

    public HashMap<String, Cidade> cidades = new HashMap<String, Cidade>();

    String possiveis = new String("");

    int quantidadeCidades;

    public BaseDados() {
        addCidade("Tubarao");
        addCidade("Laguna");
        addCidade("Braço do Norte");
        addCidade("Gravatal");
        addCidade("Florianopolis");
        addCidade("São Martinho");
//        addCidade("Jaguaruna");
//        addCidade("Criciuma");

        // c1
        addDistancia("Tubarao", "Laguna", 10);
        addDistancia("Tubarao", "Braço do Norte", 20);
        addDistancia("Tubarao", "Gravatal", 36);
        addDistancia("Tubarao", "Florianopolis", 21);
        addDistancia("Tubarao", "São Martinho", 52);
//        addDistancia("Tubarao", "Jaguaruna", 70);
//        addDistancia("Tubarao", "Criciuma", 27);

        // c2
        addDistancia("Laguna", "Braço do Norte", 20);
        addDistancia("Laguna", "Gravatal", 11);
        addDistancia("Laguna", "Florianopolis", 57);
        addDistancia("Laguna", "São Martinho", 89);
//        addDistancia("Laguna", "Jaguaruna", 45);
//        addDistancia("Laguna", "Criciuma", 104);

        // c3
        addDistancia("Braço do Norte", "Gravatal", 68);
        addDistancia("Braço do Norte", "Florianopolis", 82);
        addDistancia("Braço do Norte", "São Martinho", 31);
//        addDistancia("Braço do Norte", "Jaguaruna", 50);
//        addDistancia("Braço do Norte", "Criciuma", 39);

        // c4
        addDistancia("Gravatal", "Florianopolis", 44);
        addDistancia("Gravatal", "São Martinho", 12);
//        addDistancia("Gravatal", "Jaguaruna", 64);
//        addDistancia("Gravatal", "Criciuma", 93);

        // c5
        addDistancia("Florianopolis", "São Martinho", 53);
//        addDistancia("Florianopolis", "Jaguaruna", 12);
//        addDistancia("Florianopolis", "Criciuma", 84);

        // c6
//        addDistancia("São Martinho", "Jaguaruna", 48);
//        addDistancia("São Martinho", "Criciuma", 43);

        // c7
//        addDistancia("Jaguaruna", "Criciuma", 48);

    }

    public void addDistancia(String cidadeInicial, String cidadeFinal, double distancia) {
        addCidade(cidadeInicial);
        addCidade(cidadeFinal);
        distancias.put(getIniciaisCidade(cidades.get(cidadeInicial), cidades.get(cidadeFinal)), distancia);
        distancias.put(getIniciaisCidade(cidades.get(cidadeFinal), cidades.get(cidadeInicial)), distancia);
    }

    public void addCidade(String nome) {
        Cidade cidade = new Cidade(nome);
        cidades.put(nome, cidade);
        distancias.put(getIniciaisCidade(cidade, cidade), 0.0);

        quantidadeCidades = cidades.size();
        possiveis = "";
        for (Map.Entry<String, Cidade> entry : cidades.entrySet()) {
            possiveis += entry.getValue().sigla;
        }

    }

    public String getIniciaisCidade(Cidade cidadeInicial, Cidade cidadeFinal) {
        return cidadeInicial.sigla + cidadeFinal.sigla;
    }

    public Double getDistanciaByCromossomo(String cromossomo) {
        Double distancia;
        distancia = distancias.get(cromossomo);
        return distancia;
    }

}