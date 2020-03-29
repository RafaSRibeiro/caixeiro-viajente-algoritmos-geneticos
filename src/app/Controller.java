package app;

import algoritmoGenetico.AlgoritmoGenetico;
import algoritmoGenetico.BaseDados;
import algoritmoGenetico.Individuo;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    TextField tempo;

    @FXML
    TextField epocas;

    @FXML
    TextField populacaoInicial;

    @FXML
    TextField taxaConvergencia;

    @FXML
    Slider percentualMutação;

    @FXML
    TextField nomeCidade;

    @FXML
    TextArea console;

    @FXML
    ListView<String> listViewTempos;

    @FXML
    ListView<Cidade> listViewCidades;

    AlgoritmoGenetico algoritmoGenetico;

    public Controller() {
        baseDados = new BaseDados();
    }

    @FXML
    public void newTempo() {
        baseDados.addTempo(cidadeInicialComboBox.getValue(), cidadeFinalComboBox.getValue(), Integer.valueOf(tempo.getText()));
        updateListViewTempos();
    }

    @FXML
    public void calculaMenorTempo() {
        if (cidadeOrigemComboBox.getValue() == null) {
            showModal("Selecione a Origem");
            return;
        }

        if (baseDados.tempos.size() == 0) {
            showModal("Sem Tempos cadastradas");
            return;
        }

        algoritmoGenetico = new AlgoritmoGenetico(
                baseDados,
                Integer.valueOf(populacaoInicial.getText()),
                cidadeOrigemComboBox.getValue(),
                Integer.valueOf(taxaConvergencia.getText()),
                Double.valueOf(percentualMutação.getValue()));

        algoritmoGenetico.run(Integer.valueOf(this.epocas.getText()));
        Individuo individuo = algoritmoGenetico.getIndividuoMaisAptos();
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
        baseDados.tempos.clear();
        baseDados.geraDadosIniciais();
        for (Map.Entry<String, Cidade> entry : baseDados.cidades.entrySet()) {
            updateCidadesView(entry.getValue());
        }

        updateListViewTempos();
    }

    @FXML
    public void limparConsole() {
        console.clear();
    }

    private void updateListViewTempos() {
        listViewTempos.getItems().clear();
        for (Map.Entry<String, Double> entry : baseDados.tempos.entrySet()) {
            listViewTempos.getItems().add(baseDados.cidades.get(entry.getKey().substring(0,1)).nome + " <-> " + baseDados.cidades.get(entry.getKey().substring(1,2)).nome  + " - " + entry.getValue());
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
