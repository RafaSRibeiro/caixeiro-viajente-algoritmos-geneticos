package algoritmoGenetico;

public class Mutacao {

    public static Individuo[] mutacao(BaseDados baseDados, Individuo[] individuos) {
        Individuo[] novoIndividuos = new Individuo[individuos.length];

        for (int i = 0; i < individuos.length; i++) {
            int local1 = Utils.rand(individuos[i].cromossomo.length);
            int local2 = Utils.rand(individuos[i].cromossomo.length);
            while (local2 == local1) {
                local2 = Utils.rand(individuos[i].cromossomo.length);
            }
            String novoCromossomo[] = individuos[i].cromossomo;
            String tmp = novoCromossomo[local1];
            novoCromossomo[local1] = novoCromossomo[local2];
            novoCromossomo[local2] = tmp;
            novoIndividuos[i] = new Individuo(baseDados, novoCromossomo);
        }

        return novoIndividuos;
    }
}
