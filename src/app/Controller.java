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
    ComboBox<Cidade> cidadeOrigemComboBox;

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
    ListView<String> listViewDistancias;

    @FXML
    ListView<Cidade> listViewCidades;

    AlgoritmoGenetico algoritmoGenetico;

    public Controller() {
        baseDados = new BaseDados();
    }

    @FXML
    public void newDistancia() {
        baseDados.addDistancia(cidadeInicialComboBox.getValue(), cidadeFinalComboBox.getValue(), Integer.valueOf(distancia.getText()));
        updateListViewDistancias();
    }

    @FXML
    public void calculaMenorDistancia() {
        if (cidadeOrigemComboBox.getValue() == null) {
            showModal("Selecione a Origem");
            return;
        }

        if (baseDados.distancias.size() == 0) {
            showModal("Sem distâncias cadastradas");
            return;
        }

        algoritmoGenetico = new AlgoritmoGenetico(baseDados, Integer.valueOf(populacaoInicial.getText()), cidadeOrigemComboBox.getValue());

        for (int i = 0; i < Integer.valueOf(this.epocas.getText()); i++) {
            algoritmoGenetico.geraProximaGeracao();
        }
        Individuo individuo = algoritmoGenetico.getIndividuoMaisAptos(1)[0];
        console.appendText("Menor Percurso: " + individuo.fitness + "\n");

        String nomesCidade = algoritmoGenetico.baseDados.cromossomoToNomeCidade(individuo.cromossomo);
        console.appendText("Melhor Caminho: " + nomesCidade + "\n");
    }

    @FXML
    public void addCidade() {
        if (baseDados.findCidadeByName(nomeCidade.getText()) == null) {
            Cidade cidade = baseDados.addCidade(nomeCidade.getText());
            updateCidadesView(cidade);
        } else {
            showModal("Cidade já existe.");
        }
    }

    @FXML
    public void geraDadosInciais() {
        baseDados.cidades.clear();
        baseDados.distancias.clear();
        baseDados.geraDadosIniciais();
        for (Map.Entry<String, Cidade> entry : baseDados.cidades.entrySet()) {
            updateCidadesView(entry.getValue());
        }

        updateListViewDistancias();
    }

    @FXML
    public void limparConsole() {
        console.clear();
    }

    private void updateListViewDistancias() {
        listViewDistancias.getItems().clear();
        for (Map.Entry<String, Double> entry : baseDados.distancias.entrySet()) {
            listViewDistancias.getItems().add(baseDados.cidades.get(entry.getKey().substring(0,1)).nome + " <-> " + baseDados.cidades.get(entry.getKey().substring(1,2)).nome  + " - " + entry.getValue());
        }
    }

    public void showModal(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem);
    }

    private void updateCidadesView(Cidade cidade) {
        cidadeInicialComboBox.getItems().add(cidade);
        cidadeFinalComboBox.getItems().add(cidade);
        cidadeOrigemComboBox.getItems().add(cidade);
        listViewCidades.getItems().add(cidade);
    }
}
