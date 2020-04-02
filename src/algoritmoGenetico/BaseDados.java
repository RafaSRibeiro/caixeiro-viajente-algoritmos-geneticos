package algoritmoGenetico;

import model.Cidade;

import java.util.HashMap;
import java.util.Map;

public class BaseDados {

    public static final int MIN_CIDADES = 3;

    public static final int MAX_CIDADES = 10;

    public HashMap<String, Double> tempos = new HashMap<String, Double>();

    public HashMap<String, Cidade> cidades = new HashMap<String, Cidade>();

    String possiveis = new String("");

    int quantidadeCidades;

    public int tamanhoColuna = 0;

    public void geraDadosIniciais() {
        Cidade cidade1 = addCidade("Tubarao");
        Cidade cidade2 = addCidade("Laguna");
        Cidade cidade3 = addCidade("Braço do Norte");
        Cidade cidade4 = addCidade("Gravatal");
        Cidade cidade5 = addCidade("Florianopolis");
        Cidade cidade6 = addCidade("São Martinho");
        Cidade cidade7 = addCidade("Jaguaruna");
        Cidade cidade8 = addCidade("Criciuma");
        Cidade cidade9 = addCidade("São Martinho");

        // c1
        addTempo(cidade1, cidade2, 10);
        addTempo(cidade1, cidade3, 20);
        addTempo(cidade1, cidade4, 36);
        addTempo(cidade1, cidade5, 21);
        addTempo(cidade1, cidade6, 52);
        addTempo(cidade1, cidade7, 70);
        addTempo(cidade1, cidade8, 27);
        addTempo(cidade1, cidade9, 60);

        // c2
        addTempo(cidade2, cidade3, 20);
        addTempo(cidade2, cidade4, 11);
        addTempo(cidade2, cidade5, 57);
        addTempo(cidade2, cidade6, 89);
        addTempo(cidade2, cidade7, 45);
        addTempo(cidade2, cidade8, 100);
        addTempo(cidade2, cidade9, 104);

        // c3
        addTempo(cidade3, cidade4, 68);
        addTempo(cidade3, cidade5, 82);
        addTempo(cidade3, cidade6, 31);
        addTempo(cidade3, cidade7, 50);
        addTempo(cidade3, cidade8, 39);
        addTempo(cidade3, cidade9, 98);

        // c4
        addTempo(cidade4, cidade5, 44);
        addTempo(cidade4, cidade6, 12);
        addTempo(cidade4, cidade7, 64);
        addTempo(cidade4, cidade8, 93);
        addTempo(cidade4, cidade9, 152);

        // c5
        addTempo(cidade5, cidade6, 53);
        addTempo(cidade5, cidade7, 12);
        addTempo(cidade5, cidade8, 84);
        addTempo(cidade5, cidade9, 100);

        // c6
        addTempo(cidade6, cidade7, 48);
        addTempo(cidade6, cidade8, 43);
        addTempo(cidade6, cidade9, 50);

        // c7
        addTempo(cidade7, cidade8, 48);
        addTempo(cidade7, cidade9, 55);

        // c8
        addTempo(cidade8, cidade9, 55);

    }

    public void addTempo(Cidade cidadeInicial, Cidade cidadeFinal, double tempo) {
        tempos.put(getIniciaisCidade(cidadeInicial, cidadeFinal), tempo);
        tempos.put(getIniciaisCidade(cidadeFinal, cidadeInicial), tempo);
    }

    public Cidade addCidade(String nome) {
        int newId = cidades.size();
        Cidade cidade = new Cidade(String.valueOf(newId), nome);
        cidades.put(cidade.id, cidade);
        tempos.put(getIniciaisCidade(cidade, cidade), 0.0);
        quantidadeCidades = cidades.size();
        possiveis = "";
        for (Map.Entry<String, Cidade> entry : cidades.entrySet()) {
            possiveis += entry.getValue().id;
        }
        if (tamanhoColuna < cidade.nome.length())
            tamanhoColuna = cidade.nome.length();
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

    public Double getTempoByCromossomo(String cromossomo) {
        Double tempo;
        tempo = tempos.get(cromossomo) == null ? Double.MAX_VALUE : tempos.get(cromossomo);
        return tempo;
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
        individuo.fitness = getTempoPercurso(individuo);
        return individuo;
    }

    public Double getTempoPercurso(Individuo individuo) {
        Double tempo = 0.0;
        String cromossomo;
        for (int i = 0; i < individuo.cromossomo.length - 1; i++) {
            cromossomo = individuo.cromossomo[i] + individuo.cromossomo[i + 1];
            tempo += getTempoByCromossomo(cromossomo);
        }
        return tempo;
    }

    public Individuo newIndividuo(String cromossomo[]) {
        Individuo individuo = new Individuo();
        individuo.cromossomo = new String[cromossomo.length];
        for (int i = 0; i < cromossomo.length; i++)
            individuo.cromossomo[i] = cromossomo[i];
        individuo.fitness = getTempoPercurso(individuo);
        return individuo;
    }

    public String cromossomoToNomeCidade(String cromossomo[]) {
        String nomesCidades = new String("");
        for (int i = 0; i < cromossomo.length; i++) {
            nomesCidades += " -> " + cidades.get(cromossomo[i]);
        }
        return nomesCidades;
    }

    public double[][] matrizTempos() {
        double[][] matriz = new double[cidades.size()][cidades.size()];
        for (int i = 0; i < cidades.size(); i++) {
            for (int j = 0; j < cidades.size(); j++) {
                matriz[i][j] = tempos.get("" +i + j) == null ? Double.MAX_VALUE : tempos.get("" +i + j);
            }
        }
        return matriz;
    }

}