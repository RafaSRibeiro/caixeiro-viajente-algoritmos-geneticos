package algoritmoGenetico;

public class AlgoritmoGenetico {

    public Individuo[] populacao;

    public BaseDados baseDados;

    int quantidadePopulacaoInicial = 40;

    public AlgoritmoGenetico(BaseDados baseDados, int quantidadePopulacaoInicial) {
        this.quantidadePopulacaoInicial = quantidadePopulacaoInicial;
        populacao = new Individuo[quantidadePopulacaoInicial];
        this.baseDados = baseDados;
        int i;
        for (i = 0; i < quantidadePopulacaoInicial; i++) {
            populacao[i] = new Individuo(baseDados);
        }
    }

    public Individuo getIndividuoMaisApto(Individuo individuos[]) {
        return individuos[getIndiceMaisApto(individuos)];
    }

    public int getIndiceMaisApto(Individuo individuos[]) {
        Double menorDistancia = 0.0, distanciaAuxiliar;
        int indiceIndividuoMenorDistancia = -1, i, len = individuos.length;
        Individuo individuo;

        for (i = 0; i < len; i++) {
            individuo = individuos[i];
            if (individuo == null)
                continue;
            if (!individuo.isGeneExists()) {
                distanciaAuxiliar = individuo.getDistanciaPercurso(baseDados);
                if (indiceIndividuoMenorDistancia == -1 || distanciaAuxiliar < menorDistancia) {
                    indiceIndividuoMenorDistancia = i;
                    menorDistancia = distanciaAuxiliar;
                }
            }
        }
        assert indiceIndividuoMenorDistancia != -1;
        return indiceIndividuoMenorDistancia;
    }

    public Individuo[] torneio() {
        int quantidadeIndividuosTorneio = 9;
        Individuo individuosRetorno[] = new Individuo[2];
        Individuo individuos[] = new Individuo[quantidadeIndividuosTorneio];

        int i;
        for (i = 0; i < quantidadeIndividuosTorneio; i++) {
            individuos[i] = populacao[Utils.rand(quantidadePopulacaoInicial)];
        }

        int indiceIndividuoRetorno;
        indiceIndividuoRetorno = getIndiceMaisApto(individuos);
        individuosRetorno[0] = populacao[indiceIndividuoRetorno];
        individuos[indiceIndividuoRetorno] = null;

        individuosRetorno[1] = getIndividuoMaisApto(individuos);

        return individuosRetorno;

    }

    public void geraProximaGeracao() {
        Individuo proximaPopulacao[] = new Individuo[quantidadePopulacaoInicial];
        int i;
        proximaPopulacao[0] = getIndividuoMaisApto(populacao);
        for (i = 1; i < quantidadePopulacaoInicial; i++) {
            proximaPopulacao[i] = new Individuo(torneio(), baseDados);
        }
        populacao = proximaPopulacao;
    }


}
