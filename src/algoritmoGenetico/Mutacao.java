package algoritmoGenetico;

public class Mutacao {

    private BaseDados baseDados;

    public Mutacao(BaseDados _baseDados) {
        baseDados = _baseDados;
    }

    public Individuo[] run(Individuo[] individuos) {
        Individuo[] novoIndividuos = new Individuo[individuos.length];

        for (int i = 0; i < individuos.length; i++) {
            novoIndividuos[i] = geraMutacao(individuos[i]);
        }

        return novoIndividuos;
    }

    public Individuo geraMutacao(Individuo individuo) {
        int local1;
        do
            local1 = Utils.rand(individuo.cromossomo.length);
        while (local1 == 0 || local1 == individuo.cromossomo.length- 1);

        int local2;
        do
            local2 = Utils.rand(individuo.cromossomo.length);
        while (local2 == 0 || local2 == individuo.cromossomo.length - 1 || local2 == local1);

        String novoCromossomo[] = individuo.cromossomo;
        String tmp = novoCromossomo[local1];
        novoCromossomo[local1] = novoCromossomo[local2];
        novoCromossomo[local2] = tmp;
        return baseDados.newIndividuo(novoCromossomo);
    }

}
