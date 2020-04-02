package algoritmoGenetico;

import model.Cidade;

import java.util.Stack;

public class AlgoritmoGenetico {

    public Individuo[] populacao;

    public BaseDados baseDados;

    public Mutacao mutacao;

    private Convergencia convergencia;

    public AlgoritmoGenetico(
            BaseDados baseDados,
            int quantidadePopulacaoInicial,
            Cidade cidadeOrigem,
            int taxaConvergencia,
            Double percentualMutacao) {
        populacao = new Individuo[quantidadePopulacaoInicial];
        this.baseDados = baseDados;
        this.convergencia = new Convergencia(taxaConvergencia);
        this.mutacao = new Mutacao(baseDados, percentualMutacao);
        for (int i = 0; i < quantidadePopulacaoInicial; i++) {
            populacao[i] = baseDados.geraIndividuo(cidadeOrigem);
        }
        ordenaPorFitness();
    }

    public void run(int epocas) {
        for (int i = 0; i < epocas; i++) {
            geraProximaGeracao();
            if (convergencia.atingiuConvergencia(getIndividuoMaisAptos()))
                return;
        }
    }

    public Individuo getIndividuoMaisAptos() {
        return getIndividuosMaisAptos(1)[0];
    }

    public Individuo[] getIndividuosMaisAptos(int limit) {
        Stack<Individuo> populacaoAux = new Stack<Individuo>();
        for (int i = 0; i < populacao.length; i++) {
            populacaoAux.push(populacao[i]);
        }

        Individuo[] novaPopulacao = new Individuo[limit];
        for (int i = 0; (populacaoAux.size() > 0) && (i < limit); i++) {
            novaPopulacao[i] = populacaoAux.firstElement();
            populacaoAux.remove(novaPopulacao[i]);
        }
        return novaPopulacao;
    }

    private void ordenaPorFitness() {
        Individuo individuo;
        Stack<Individuo> populacaoAux = new Stack<Individuo>();
        for (int i = 0; i < populacao.length; i++) {
            populacaoAux.push(populacao[i]);
        }

        Stack<Individuo> populacaoOrdenada = new Stack<Individuo>();
        while (populacaoAux.size() > 0) {
            Double menorTempo = Double.MAX_VALUE;
            Individuo individuoMenorMenor = populacaoAux.get(0);
            for (int i = 0; i < populacaoAux.size(); i++) {
                individuo = populacaoAux.get(i);
                if (individuo.fitness < menorTempo) {
                    individuoMenorMenor = individuo;
                    menorTempo = individuo.fitness;
                }
            }
            populacaoAux.remove(individuoMenorMenor);
            populacaoOrdenada.add(individuoMenorMenor);
        }

        Individuo[] novaPopulacao = new Individuo[populacao.length];
        for (int i = 0; (populacaoOrdenada.size() > 0) && (i < populacao.length); i++) {
            novaPopulacao[i] = populacaoOrdenada.firstElement();
            populacaoOrdenada.remove(novaPopulacao[i]);
        }
        populacao = novaPopulacao;
    }

    public void geraProximaGeracao() {
        Individuo proximaPopulacao[] = new Individuo[populacao.length];

        Individuo populacaoMaisApta[] = getIndividuosMaisAptos(populacao.length / 2);
        System.arraycopy(populacaoMaisApta, 0, proximaPopulacao, 0, populacaoMaisApta.length);
        Individuo populacaoMutacao[] = mutacao.run(populacaoMaisApta);
        System.arraycopy(populacaoMutacao, 0, proximaPopulacao, populacaoMaisApta.length, populacaoMutacao.length);

        populacao = proximaPopulacao;
        ordenaPorFitness();
    }

}
