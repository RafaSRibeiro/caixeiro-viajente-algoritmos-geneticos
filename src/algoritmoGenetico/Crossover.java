package algoritmoGenetico;

public class Crossover {

    int j = 0;
    int alternaPais = 2;

    String cromossomo[];

    Individuo individuoAtmp;
    Individuo individuoBtmp;
    Individuo individuoA;
    Individuo individuoB;

    public String[] run(Individuo individuoA, Individuo individuoB) {
        this.individuoA = individuoA;
        this.individuoB = individuoB;
        cromossomo = new String[individuoA.cromossomo.length];
        int i;
        String cromossomoAuxiliar;
        for (i = 0; i < cromossomo.length; i += 2) {
            alternaPais(alternaPais);
            cromossomoAuxiliar = individuoAtmp.cromossomo[i];
            j = i;
            while (Utils.contain(cromossomo, cromossomoAuxiliar)) {
                cromossomoAuxiliar = individuoBtmp.cromossomo[j];
                incJ();
            }

            cromossomo[i] = cromossomoAuxiliar;
            cromossomoAuxiliar = individuoAtmp.cromossomo[i + 1];
            j = i + 1;
            while (Utils.contain(cromossomo, cromossomoAuxiliar)) {
                cromossomoAuxiliar = individuoBtmp.cromossomo[j];
                incJ();
            }
            cromossomo[i + 1] = cromossomoAuxiliar;

        }
        return cromossomo;
    }

    private void alternaPais(int alternaPais) {
        if (alternaPais % 2 == 0) {
            individuoAtmp = individuoA;
            individuoBtmp = individuoB;
        } else {
            individuoAtmp = individuoB;
            individuoBtmp = individuoA;
        }
        alternaPais++;
    }

    private void incJ() {
        if (j + 1 < cromossomo.length) {
            j++;
        } else {
            j = 0;
        }
    }
}
