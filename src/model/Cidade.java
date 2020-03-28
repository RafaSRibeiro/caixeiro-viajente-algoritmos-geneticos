package model;

public class Cidade {

    public String id;

    public String nome;

    public Cidade(String _id, String _nome) {
        id = _id;
        nome = _nome;
    }

    public String toString() {
        return this.nome;
    }
}
