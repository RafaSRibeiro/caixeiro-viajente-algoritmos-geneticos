package algoritmoGenetico;

import java.util.Stack;

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

    public Individuo[] getIndividuoMaisAptos(int quantidade) {
        Individuo individuo;
        Stack<Individuo> populacaoAux = new Stack<Individuo>();
        for (int i = 0; i < populacao.length; i++) {
            populacaoAux.push(populacao[i]);
        }

        Stack<Individuo> populacaoOrdenada = new Stack<Individuo>();
        while (populacaoAux.size() > 0) {
            Double menorDistancia = Double.POSITIVE_INFINITY;
            Individuo individuoMenorDistancia = populacaoAux.get(0);
            for (int i = 0; i < populacaoAux.size(); i++) {
                individuo = populacaoAux.get(i);
                if (individuo.fitness < menorDistancia) {
                    individuoMenorDistancia = individuo;
                    menorDistancia = individuo.fitness;
                }
            }
            populacaoAux.remove(individuoMenorDistancia);
            populacaoOrdenada.add(individuoMenorDistancia);
        }

        Individuo[] novaPopulacao = new Individuo[quantidade];
        for (int i = 0; (populacaoOrdenada.size() > 0) && (i < quantidade); i++) {
            novaPopulacao[i] = populacaoOrdenada.firstElement();
            populacaoOrdenada.remove(novaPopulacao[i]);
        }
        return novaPopulacao;
    }

    public void geraProximaGeracao() {
        Individuo proximaPopulacao[] = new Individuo[quantidadePopulacaoInicial];
        Individuo populacaoMaisApta[] = getIndividuoMaisAptos(quantidadePopulacaoInicial / 2);
        System.arraycopy(populacaoMaisApta, 0, proximaPopulacao, 0, populacaoMaisApta.length);

        Individuo populacaoMutacao[] = Mutacao.mutacao(baseDados, populacaoMaisApta);
        System.arraycopy(populacaoMutacao, 0, proximaPopulacao, populacaoMaisApta.length, populacaoMutacao.length);
        populacao = proximaPopulacao;
    }


}
