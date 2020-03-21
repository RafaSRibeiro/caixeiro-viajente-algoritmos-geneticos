package app;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Distancia;

import java.util.ArrayList;

public class Controller {

    ArrayList<Distancia> distancias = new ArrayList<Distancia>();

    @FXML
    TextField cidadeInicial;

    @FXML
    TextField cidadeFinal;

    @FXML
    TextField distancia;

    @FXML
    TextArea console;

    @FXML
    public void newDistancia() {
        Distancia distancia = new Distancia(cidadeInicial.toString(), cidadeInicial.toString(), Integer.valueOf(this.distancia.toString()));
        distancias.add(distancia);
    }
}
