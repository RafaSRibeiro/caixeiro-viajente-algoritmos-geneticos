package algoritmoGenetico;

import model.Cidade;

import java.util.HashMap;
import java.util.Map;

public class BaseDados {

    public HashMap<String, Double> distancias = new HashMap<String, Double>();

    public HashMap<String, Cidade> cidades = new HashMap<String, Cidade>();

    String possiveis = new String("");

    int quantidadeCidades;

    public void geraDadosIniciais() {
        Cidade cidade1 = addCidade("Tubarao");
        Cidade cidade2 = addCidade("Laguna");
        Cidade cidade3 = addCidade("Braço do Norte");
        Cidade cidade4 = addCidade("Gravatal");
        Cidade cidade5 = addCidade("Florianopolis");
        Cidade cidade6 = addCidade("São Martinho");
        Cidade cidade7 = addCidade("Jaguaruna");
        Cidade cidade8 = addCidade("Criciuma");

        // c1
        addDistancia(cidade1, cidade2, 10);
        addDistancia(cidade1, cidade3, 20);
        addDistancia(cidade1, cidade4, 36);
        addDistancia(cidade1, cidade5, 21);
        addDistancia(cidade1, cidade6, 52);
        addDistancia(cidade1, cidade7, 70);
        addDistancia(cidade1, cidade8, 27);

        // c2
        addDistancia(cidade2, cidade3, 20);
        addDistancia(cidade2, cidade4, 11);
        addDistancia(cidade2, cidade5, 57);
        addDistancia(cidade2, cidade6, 89);
        addDistancia(cidade2, cidade7, 45);
        addDistancia(cidade2, cidade8, 104);

        // c3
        addDistancia(cidade3, cidade4, 68);
        addDistancia(cidade3, cidade5, 82);
        addDistancia(cidade3, cidade6, 31);
        addDistancia(cidade3, cidade7, 50);
        addDistancia(cidade3, cidade8, 39);

        // c4
        addDistancia(cidade4, cidade5, 44);
        addDistancia(cidade4, cidade6, 12);
        addDistancia(cidade4, cidade7, 64);
        addDistancia(cidade4, cidade8, 93);

        // c5
        addDistancia(cidade5, cidade6, 53);
        addDistancia(cidade5, cidade7, 12);
        addDistancia(cidade5, cidade8, 84);

        // c6
        addDistancia(cidade6, cidade7, 48);
        addDistancia(cidade6, cidade8, 43);

        // c7
        addDistancia(cidade7, cidade8, 48);

    }

    public void addDistancia(Cidade cidadeInicial, Cidade cidadeFinal, double distancia) {
        distancias.put(getIniciaisCidade(cidadeInicial, cidadeFinal), distancia);
        distancias.put(getIniciaisCidade(cidadeFinal, cidadeInicial), distancia);
    }

    public Cidade addCidade(String nome) {
        int newId = cidades.size();
        Cidade cidade = new Cidade(String.valueOf(newId), nome);
        cidades.put(cidade.id, cidade);
        distancias.put(getIniciaisCidade(cidade, cidade), 0.0);
        quantidadeCidades = cidades.size();
        possiveis = "";
        for (Map.Entry<String, Cidade> entry : cidades.entrySet()) {
            possiveis += entry.getValue().id;
        }

        return cidade;
    }

    public Cidade findCidadeByName(String nome) {
        for (Map.Entry<String, Cidade> entry : cidades.entrySet()) {
            if (entry.getValue().nome.equals(nome))
                return entry.getValue();
        }
        return null;
    }

    public String getIniciaisCidade(Cidade cidadeInicial, Cidade cidadeFinal) {
        return cidadeInicial.id + cidadeFinal.id;
    }

    public Double getDistanciaByCromossomo(String cromossomo) {
        Double distancia;
        distancia = distancias.get(cromossomo);
        return distancia;
    }

    public Individuo geraIndividuo(Cidade cidadeOrigem) {
        Individuo individuo = new Individuo();
        int quantidadeCidades = this.quantidadeCidades;
        individuo.cromossomo = new String[quantidadeCidades + 1];

        individuo.cromossomo[0] = cidadeOrigem.id;
        for (int i = 1; i < quantidadeCidades; i++) {
            String gene = Utils.at(this.possiveis, Utils.rand(quantidadeCidades));
            while (Utils.contain(individuo.cromossomo, gene)) {
                gene = Utils.at(this.possiveis, Utils.rand(quantidadeCidades));
            }
            individuo.cromossomo[i] = gene;
        }
        individuo.cromossomo[quantidadeCidades] = individuo.cromossomo[0];
        individuo.fitness = getDistanciaPercurso(individuo);
        return individuo;
    }

    public Double getDistanciaPercurso(Individuo individuo) {
        Double distancia = 0.0;
        String cromossomo;
        for (int i = 0; i < individuo.cromossomo.length - 1; i++) {
            cromossomo = individuo.cromossomo[i] + individuo.cromossomo[i + 1];
            distancia += getDistanciaByCromossomo(cromossomo);
        }
        return distancia;
    }

    public Individuo newIndividuo(String cromossomo[]) {
        Individuo individuo = new Individuo();
        individuo.cromossomo = cromossomo;
        individuo.fitness = getDistanciaPercurso(individuo);
        return individuo;
    }

    public String cromossomoToNomeCidade(String cromossomo[]) {
        String nomesCidades = new String("");
        for (int i = 0; i < cromossomo.length; i++) {
            nomesCidades += " -> " + cidades.get(cromossomo[i]);
        }
        return nomesCidades;
    }

}