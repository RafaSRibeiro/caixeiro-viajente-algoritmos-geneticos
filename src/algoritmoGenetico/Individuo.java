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

    public Individuo(BaseDados baseDados, String cromossomo[]) {
        int quantidadeCidades = baseDados.quantidadeCidades;
        tamanhoGenes = quantidadeCidades;
        this.cromossomo = cromossomo;
        fitness = getDistanciaPercurso(baseDados);
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


}
