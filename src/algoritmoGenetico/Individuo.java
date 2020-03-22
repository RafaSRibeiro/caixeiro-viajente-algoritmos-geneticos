package algoritmoGenetico;

public class Individuo {

    public String cromossomo[];

    private int tamanhoGenes;

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
    }

    public Individuo(Individuo individuos[], BaseDados baseDados) {
        Individuo pai = individuos[0];
        Individuo mae = individuos[1];
        assert pai.cromossomo.length == mae.cromossomo.length;
        tamanhoGenes = pai.tamanhoGenes;
        cromossomo = crossover(pai, mae);
        mutacao(baseDados);
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

    public void mutacao(BaseDados baseDados) {
        int local1 = Utils.rand(tamanhoGenes);
        int local2 = Utils.rand(tamanhoGenes);
        while (local2 == local1) {
            local2 = Utils.rand(tamanhoGenes);
        }
        String tmp = cromossomo[local1];
        cromossomo[local1] = cromossomo[local2];
        cromossomo[local2] = tmp;
    }

    private String[] crossover(Individuo individuoA, Individuo individuoB) {
//        String[] child = new String[individuoA.cromossomo.length];
//        Double crossPoint = Math.random() * individuoA.cromossomo.length;//make a crossover point
//        for (int i = 0; i < individuoA.cromossomo.length; ++i) {
//            if (i < crossPoint)
//                child[i] = individuoA.cromossomo[i];
//            else
//                child[i] = individuoB.cromossomo[i];
//        }
//        return child;

        String lst[] = new String[individuoA.tamanhoGenes];
        int i, max = lst.length;
        int rand_point = Utils.rand(tamanhoGenes);

        int cout_pais = 2;
        String tmp;
        int j;
        for (i = 0; i < max; i += 2) {
            if (cout_pais % 2 == 0) {// pai
                tmp = individuoA.cromossomo[i];
                j = i;
                while (Utils.contain(lst, tmp)) {// pega o da mae
                    tmp = individuoB.cromossomo[j];
                    if (j + 1 < max) {
                        j++;
                    } else {
                        j = 0;
                    }

                }

                lst[i] = tmp;
                tmp = individuoA.cromossomo[i + 1];
                j = i + 1;
                while (Utils.contain(lst, tmp)) {
                    tmp = individuoB.cromossomo[j];
                    if (j + 1 < max) {
                        j++;
                    } else {
                        j = 0;
                    }

                }
                lst[i + 1] = tmp;

            } else {// mae
                tmp = individuoB.cromossomo[i];
                j = i;
                while (Utils.contain(lst, tmp)) {// pega o da pai
                    tmp = individuoA.cromossomo[j];
                    if (j + 1 < max) {
                        j++;
                    } else {
                        j = 0;
                    }
                }
                lst[i] = tmp;

                tmp = individuoB.cromossomo[i + 1];
                j = i + 1;
                while (Utils.contain(lst, tmp)) {
                    tmp = individuoA.cromossomo[j];
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
