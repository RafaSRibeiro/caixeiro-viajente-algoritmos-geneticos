package app;

import algoritmoGenetico.AlgoritmoGenetico;
import algoritmoGenetico.BaseDados;
import algoritmoGenetico.Individuo;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Cidade;

import javax.swing.*;
import java.util.Map;

public class Controller {

    private BaseDados baseDados;

    @FXML
    ComboBox<Cidade> cidadeInicialComboBox;

    @FXML
    ComboBox<Cidade> cidadeFinalComboBox;

    @FXML
    TextField distancia;

    @FXML
    TextField epocas;

    @FXML
    TextField populacaoInicial;

    @FXML
    TextField nomeCidade;

    @FXML
    TextArea console;

    @FXML
    ListView<String> listView;

    @FXML
    ListView<Cidade> listViewCidades;

    public Controller() {
        baseDados = new BaseDados();
    }

    @FXML
    public void newDistancia() {
        baseDados.addDistancia(cidadeInicialComboBox.getId(), cidadeFinalComboBox.getId(), Integer.valueOf(distancia.getText()));
        updateListView();
    }

    @FXML
    public void calculaMenorDistancia() {
        AlgoritmoGenetico algoritmoGenetico = new AlgoritmoGenetico(baseDados, Integer.valueOf(populacaoInicial.getText()));
        Individuo individuo;

        if (!algoritmoGenetico.isPossuiPopulacao()) {
            showModal("Sem cidades cadastradas");
            return;
        }

        for (int i = 0; i < Integer.valueOf(this.epocas.getText()); i++) {
            algoritmoGenetico.geraProximaGeracao();
        }
        individuo = algoritmoGenetico.getIndividuoMaisAptos(1)[0];
        console.appendText("Menor Percurso: " + individuo.fitness + "\n");
        console.appendText("Melhor Caminho: " + String.join(" ", individuo.cromossomo) + "\n");
    }

    @FXML
    public void addCidade() {
        if (baseDados.findCidadeByName(nomeCidade.getText()) == null) {
            Cidade cidade = baseDados.addCidade(nomeCidade.getText());
            cidadeInicialComboBox.getItems().add(cidade);
            cidadeFinalComboBox.getItems().add(cidade);
            listViewCidades.getItems().add(cidade);
        } else {
            showModal("Cidade já existe.");
        }
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
