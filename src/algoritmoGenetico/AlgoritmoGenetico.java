package algoritmoGenetico;

public class AlgoritmoGenetico {

    public Individuo[] populacao;

    public BaseDados baseDados;

    int quantidade = 40;

    public AlgoritmoGenetico(BaseDados baseDados) {
        populacao = new Individuo[quantidade];
        this.baseDados = baseDados;
        int i;
        for (i = 0; i < quantidade; i++) {
            populacao[i] = new Individuo(baseDados);
        }
    }

    public Individuo getIndividuoMaisApto(Individuo lst[]) {
        return lst[getIndiceMaisApto(lst)];
    }

    public int getIndiceMaisApto(Individuo lst[]) {
        Double menor = 0.0, aux;
        int local_menor = -1, i, len = lst.length;
        Individuo tmp;

        for (i = 0; i < len; i++) {
            tmp = lst[i];
            if (tmp == null) continue;
            if (!tmp.isGeneExists()) {
                aux = tmp.get_distancia_percurso(baseDados);
                if (local_menor == -1 || aux < menor) {
                    local_menor = i;
                    menor = aux;
                }
            }
        }
        assert local_menor != -1;

        return local_menor;

    }

    public Individuo[] torneio() {
        int qnt_torneio = 9;
        Individuo to_return[] = new Individuo[2];
        Individuo lst[] = new Individuo[qnt_torneio];

        int i;
        // seleciona qnt_torneio Individuos aleatoriamente
        for (i = 0; i < qnt_torneio; i++) {
            lst[i] = populacao[Utils.rand(quantidade)];
        }

        // seleciona os dois mais aptos para retornar

        int i1;
        i1 = getIndiceMaisApto(lst);
        to_return[0] = populacao[i1];
        lst[i1] = null;

        to_return[1] = getIndividuoMaisApto(lst);

        return to_return;

    }

    public void proxima_geracao() {
        Individuo prox_populacao[] = new Individuo[quantidade];
        int i;
        // preserva o mais apto
        prox_populacao[0] = getIndividuoMaisApto(populacao);

        for (i = 1; i < quantidade; i++) {
            prox_populacao[i] = new Individuo(torneio(), baseDados);
        }

        populacao = prox_populacao;

    }


}
