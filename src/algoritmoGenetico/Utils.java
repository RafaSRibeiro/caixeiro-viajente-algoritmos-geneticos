package algoritmoGenetico;

import java.util.Random;

public class Utils {

    public static int rand(int v) {
        Random r = new Random();
        return r.nextInt(v);
    }

    public static <T> boolean contain(T individuos[], T individuo) {
        int i, max = individuos.length;
        T aux;
        for (i = 0; i < max; i++) {
            aux = individuos[i];
            if (aux != null && aux.equals(individuo)) {
                return true;
            }
        }
        return false;
    }

    public static String at(String str, int index) {
        return str.substring(index, index + 1);
    }

    public static String alinha(String string, String direcao, int tamanho) {
        tamanho = tamanho - string.length();
        for (int i = 0; i < tamanho; i++) {
            if (direcao.equals("D"))
                string = " " + string;
            else
                string = string + " ";
        }
        return string;
    }
}