package app;

import algoritmoGenetico.AlgoritmoGenetico;
import algoritmoGenetico.BaseDados;
import algoritmoGenetico.Individuo;
import algoritmoGenetico.Utils;
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
        if (cidadeInicialComboBox.getValue() == cidadeFinalComboBox.getValue()) {
            showModal("A Cidade inicial dever ser diferente da final.");
            return;
        }
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

        if (baseDados.cidades.size() < BaseDados.MIN_CIDADES) {
            showModal("É preciso cadastrar no mínimo " + BaseDados.MIN_CIDADES + " cidades.");
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
        if (baseDados.cidades.size() == BaseDados.MAX_CIDADES) {
            showModal("Número máximo de cidades cadastrada.");
            return;
        }
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
        double[][] matriz = baseDados.matrizTempos();

        String linha;
        listViewTempos.getItems().clear();

        linha = " |" + Utils.alinha(" ", "E", baseDados.tamanhoColuna);
        for (int i = 0; i < baseDados.cidades.size(); i++) {
            linha = linha + " |" + Utils.alinha(baseDados.cidades.get(String.valueOf(i)).nome, "E", baseDados.tamanhoColuna);
        }
        listViewTempos.getItems().add(linha);

        for (int i = 0; i < baseDados.cidades.size(); i++) {
            linha = " |" + Utils.alinha(baseDados.cidades.get(String.valueOf(i)).nome, "E", baseDados.tamanhoColuna);
            for (int j = 0; j < baseDados.cidades.size(); j++) {
                if (matriz[i][j] == Double.MAX_VALUE)
                    linha = linha + " |" + Utils.alinha("Infinito", "D", baseDados.tamanhoColuna);
                else
                    linha = linha + " |" + Utils.alinha(String.valueOf(matriz[i][j]), "D", baseDados.tamanhoColuna);
            }
            listViewTempos.getItems().add(linha);
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
