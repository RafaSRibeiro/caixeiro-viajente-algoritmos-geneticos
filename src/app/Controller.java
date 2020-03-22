package app;

import algoritmoGenetico.AlgoritmoGenetico;
import algoritmoGenetico.BaseDados;
import algoritmoGenetico.Individuo;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.util.Map;

public class Controller {

    private BaseDados baseDados;

    @FXML
    TextField cidadeInicial;

    @FXML
    TextField cidadeFinal;

    @FXML
    TextField distancia;

    @FXML
    TextField epocas;

    @FXML
    TextField populacaoInicial;

    @FXML
    TextArea console;

    @FXML
    ListView<String> listView;

    public Controller() {
        baseDados = new BaseDados();
    }

    @FXML
    public void newDistancia() {
        baseDados.addDistancia(cidadeInicial.getText(), cidadeFinal.getText(), Integer.valueOf(distancia.getText()));
        updateListView();
    }

    @FXML
    public void calculaMenorDistancia() {
        AlgoritmoGenetico algoritmoGenetico = new AlgoritmoGenetico(baseDados, Integer.valueOf(populacaoInicial.getText()));
        Individuo individuo;
        int epocas = 0;

        if (algoritmoGenetico.populacao.length == 0) {
            showModal("Sem cidades cadastradas");
            return;
        }

        while (true) {
            individuo = algoritmoGenetico.getIndividuoMaisApto(algoritmoGenetico.populacao);
            algoritmoGenetico.geraProximaGeracao();
            epocas++;
            if (epocas == Integer.valueOf(this.epocas.getText())) break;
        }
        console.appendText("Menor Percurso: " + individuo.getDistanciaPercurso(algoritmoGenetico.baseDados) + "\n");
        console.appendText("Melhor Caminho: " + String.join(" ", individuo.cromossomo) + "\n");
    }

    private void updateListView() {
        listView.getItems().clear();
        for (Map.Entry<String, Double> entry : baseDados.distancias.entrySet()) {
            listView.getItems().add(entry.getKey() + " - " + entry.getValue());
        }
    }

    public void showModal(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem);
    }
}
