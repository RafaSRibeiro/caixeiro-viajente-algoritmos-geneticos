package model;

public class Cidade {

    public String nome;
    public String sigla;

    public Cidade(String nome_) {
        nome = nome_;
        sigla = nome.toUpperCase().substring(0, 1);
    }
}
