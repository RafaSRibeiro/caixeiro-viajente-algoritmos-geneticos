package model;

public class Distancia {

    private String cidadeInicial;

    private String cidadeFinal;

    private int distancia;

    public Distancia(String cidadeInicial, String cidadeFinal, int distancia) {
        this.cidadeInicial = cidadeInicial;
        this.cidadeFinal = cidadeFinal;
        this.distancia = distancia;
    }

    public String getCidadeInicial() {
        return cidadeInicial;
    }

    public void setCidadeInicial(String cidadeInicial) {
        this.cidadeInicial = cidadeInicial;
    }

    public String getCidadeFinal() {
        return cidadeFinal;
    }

    public void setCidadeFinal(String cidadeFinal) {
        this.cidadeFinal = cidadeFinal;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }
}
