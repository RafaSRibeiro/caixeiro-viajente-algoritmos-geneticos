package algoritmoGenetico;

import java.util.Arrays;

public class Individuo {

    public String cromossomo[];

    public Double fitness;

    public int tamanhoGenes;

    public Individuo(BaseDados baseDados) {
        int quantidadeCidades = baseDados.quantidadeCidades;
        cromossomo = new String[quantidadeCidades];
        tamanhoGenes = quantidadeCidades;
        int i, max = quantidadeCidades;

        int randomicoMaximo = baseDados.quantidadeCidades;
        String gene;
        for (i = 0; i < max; i++) {
            gene = Utils.at(baseDados.possiveis, Utils.rand(randomicoMaximo));
            while (Utils.contain(cromossomo, gene)) {
                gene = Utils.at(baseDados.possiveis, Utils.rand(randomicoMaximo));
            }
            cromossomo[i] = gene;
        }
        fitness = getDistanciaPercurso(baseDados);
    }

    public Individuo(Individuo individuos[], BaseDados baseDados) {
        Individuo pai = individuos[0];
        Individuo mae = individuos[1];
        assert pai.cromossomo.length == mae.cromossomo.length;
        tamanhoGenes = pai.tamanhoGenes;
//        cromossomo = this.crossover.run(pai, mae);
//        mutacao();
    }

    public Double getDistanciaPercurso(BaseDados baseDados) {
        int i, max = tamanhoGenes - 1;
        Double count = 0.0;
        String cromossomo;
        for (i = 0; i < max; i++) {
            cromossomo = this.cromossomo[i] + this.cromossomo[i + 1];
            count += baseDados.getDistanciaByCromossomo(cromossomo);
        }
        return count;
    }

    public boolean isGeneExists() {
        int i, j;
        String tmp;
        for (i = 0; i < tamanhoGenes; i++) {
            tmp = cromossomo[i];
            for (j = i + 1; j < tamanhoGenes; j++) {
                if (cromossomo[j].equals(tmp))
                    return true;
            }
        }
        return false;
    }

    public void mutacao() {
        int local1 = Utils.rand(tamanhoGenes);
        int local2 = Utils.rand(tamanhoGenes);
        while (local2 == local1) {
            local2 = Utils.rand(tamanhoGenes);
        }
        String tmp = cromossomo[local1];
        cromossomo[local1] = cromossomo[local2];
        cromossomo[local2] = tmp;
    }

}
