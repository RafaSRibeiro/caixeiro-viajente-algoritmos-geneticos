package app;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Cidade;

import java.util.ArrayList;

public class Controller {

    ArrayList<Cidade> cidades = new ArrayList<Cidade>();

    @FXML
    TextField nomeCidade;

    @FXML
    TextField cidadeInicial;

    @FXML
    TextField cidadeFinal;

    @FXML
    TextField distancia;

    @FXML
    TextArea console;

    @FXML
    public void newCidade() {

    }

    @FXML
    public void newDistancia() {

    }
}
