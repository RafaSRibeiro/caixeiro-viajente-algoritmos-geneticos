package algoritmoGenetico;

public class Mutacao {

    private BaseDados baseDados;

    private Double taxaMutacao;

    public Mutacao(BaseDados _baseDados, Double _taxaMutacao) {
        baseDados = _baseDados;
        taxaMutacao = _taxaMutacao;
    }

    public Individuo[] run(Individuo[] individuos) {
        Individuo[] novoIndividuos = new Individuo[individuos.length];

        for (int i = 0; i < individuos.length; i++) {
            novoIndividuos[i] = geraMutacao(individuos[i]);
        }

        return novoIndividuos;
    }

    public Individuo geraMutacao(Individuo individuo) {

        Individuo novoIndividuo = baseDados.newIndividuo(individuo.cromossomo);
        for (int i = 0; i < taxaMutacaoToInt(individuo.cromossomo); i++) {
            int local1;
            do
                local1 = Utils.rand(novoIndividuo.cromossomo.length);
            while (local1 == 0 || local1 == novoIndividuo.cromossomo.length- 1);

            int local2;
            do
                local2 = Utils.rand(novoIndividuo.cromossomo.length);
            while (local2 == 0 || local2 == novoIndividuo.cromossomo.length - 1 || local2 == local1);

            String tmp = novoIndividuo.cromossomo[local1];
            novoIndividuo.cromossomo[local1] = novoIndividuo.cromossomo[local2];
            novoIndividuo.cromossomo[local2] = tmp;
        }
        novoIndividuo.fitness = baseDados.getTempoPercurso(novoIndividuo);
        return novoIndividuo;
    }

    private long taxaMutacaoToInt(String cromossomo[]) {
        return Math.round((taxaMutacao * cromossomo.length) / 100);
    }

}
