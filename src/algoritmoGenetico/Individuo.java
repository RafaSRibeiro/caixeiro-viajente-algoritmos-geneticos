package algoritmoGenetico;

public class Individuo {

    public String genes[];

    private int tamanhoGenes;

    public Individuo(BaseDados baseDados) {
        int quantidadeCidades = baseDados.quantidadeCidades;
        genes = new String[quantidadeCidades];
        tamanhoGenes = quantidadeCidades;
        int i, max = quantidadeCidades;

        int randomicoMaximo = baseDados.quantidadeCidades;
        String tmp;
        for (i = 0; i < max; i++) {
            tmp = Utils.at(baseDados.possiveis, Utils.rand(randomicoMaximo));
            while (Utils.contain(genes, tmp)) {
                tmp = Utils.at(baseDados.possiveis, Utils.rand(randomicoMaximo));
            }
            genes[i] = tmp;
        }
    }

    public Individuo(Individuo individuos[], BaseDados baseDados) {
        Individuo pai = individuos[0];
        Individuo mae = individuos[1];
        assert pai.genes.length == mae.genes.length;
        tamanhoGenes = pai.tamanhoGenes;
        genes = crossover(pai, mae);
        mutacao(baseDados);
    }

    public Double get_distancia_percurso(BaseDados baseDados) {
        int i, max = tamanhoGenes - 1;
        Double count = 0.0;
        String cromossomo;
        for (i = 0; i < max; i++) {
            cromossomo = genes[i] + genes[i + 1];
            count += baseDados.getDistanciaByCromossomo(cromossomo);
        }
        return count;
    }

    public boolean isGeneExists() {
        int i, j;
        String tmp;
        for (i = 0; i < tamanhoGenes; i++) {
            tmp = genes[i];
            for (j = i + 1; j < tamanhoGenes; j++) {
                if (genes[j].equals(tmp))
                    return true;
            }
        }
        return false;
    }

    public void mutacao(BaseDados baseDados) {
        int local1 = Utils.rand(tamanhoGenes);
        int local2 = Utils.rand(tamanhoGenes);
        while (local2 == local1) {
            local2 = Utils.rand(tamanhoGenes);
        }
        String tmp = genes[local1];
        genes[local1] = genes[local2];
        genes[local2] = tmp;
    }

    private String[] crossover(Individuo a, Individuo b) {
        String lst[] = new String[a.tamanhoGenes];
        int i, max = lst.length;
        int rand_point = Utils.rand(tamanhoGenes);

        int cout_pais = 2;
        String tmp;
        int j;
        for (i = 0; i < max; i += 2) {
            if (cout_pais % 2 == 0) {// pai
                tmp = a.genes[i];
                j = i;
                while (Utils.contain(lst, tmp)) {// pega o da mae
                    tmp = b.genes[j];
                    if (j + 1 < max) {
                        j++;
                    } else {
                        j = 0;
                    }

                }

                lst[i] = tmp;
                tmp = a.genes[i + 1];
                j = i + 1;
                while (Utils.contain(lst, tmp)) {
                    tmp = b.genes[j];
                    if (j + 1 < max) {
                        j++;
                    } else {
                        j = 0;
                    }

                }
                lst[i + 1] = tmp;

            } else {// mae
                tmp = b.genes[i];
                j = i;
                while (Utils.contain(lst, tmp)) {// pega o da pai
                    tmp = a.genes[j];
                    if (j + 1 < max) {
                        j++;
                    } else {
                        j = 0;
                    }
                }
                lst[i] = tmp;

                tmp = b.genes[i + 1];
                j = i + 1;
                while (Utils.contain(lst, tmp)) {
                    tmp = a.genes[j];
                    if (j + 1 < max) {
                        j++;
                    } else {
                        j = 0;
                    }
                }
                lst[i + 1] = tmp;
            }
            cout_pais++;

        }
        return lst;
    }

}
