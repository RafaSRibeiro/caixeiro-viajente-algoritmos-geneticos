package app;

import algoritmoGenetico.AlgoritmoGenetico;
import algoritmoGenetico.BaseDados;
import algoritmoGenetico.Individuo;
import algoritmoGenetico.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

    private BaseDados baseDados;

    @FXML
    TextField cidadeInicial;

    @FXML
    TextField cidadeFinal;

    @FXML
    TextField distancia;

    @FXML
    TextArea console;

    @FXML
    ListView<String> listView;

    public Controller() {
        baseDados = new BaseDados();
    }

    @FXML
    public void newDistancia() {

    }

    @FXML
    public void calculaMenorDistancia() {
        AlgoritmoGenetico algoritmoGenetico = new AlgoritmoGenetico(baseDados);
        Individuo individuo;
        int epocas = 0;

        while(true){
            individuo = algoritmoGenetico.getIndividuoMaisApto(algoritmoGenetico.populacao);
            algoritmoGenetico.proxima_geracao();
            epocas++;
            if(epocas == 50)break;
        }
        console.appendText("Menor Percurso: " + individuo.get_distancia_percurso(algoritmoGenetico.baseDados) + "\n");
        console.appendText("Melhor Caminho: " + String.join(" ", individuo.genes) + "\n");
    }
}
