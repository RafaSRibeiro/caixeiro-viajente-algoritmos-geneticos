package algoritmoGenetico;

public class Convergencia {

    private Individuo ultimoIndividuo;

    private int taxaConvergencia;

    private int taxaConvergenciaAuxiliar;

    public Convergencia(int taxaConvergencia) {
        this.taxaConvergencia = taxaConvergencia;
    }

    public boolean atingiuConvergencia(Individuo individuo) {
        if (ultimoIndividuo == individuo) {
            taxaConvergenciaAuxiliar++;
        } else {
            ultimoIndividuo = individuo;
            taxaConvergenciaAuxiliar = 0;
        }

        return taxaConvergenciaAuxiliar == taxaConvergencia;
    }


}
