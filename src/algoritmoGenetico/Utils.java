package algoritmoGenetico;

import java.util.Random;

public class Utils {

    public static int rand(int v) {
        Random r = new Random();
        return r.nextInt(v);
    }

    public static <T> boolean contain(T lst[], T st) {
        int i, max = lst.length;
        T aux;
        for (i = 0; i < max; i++) {
            aux = lst[i];
            if (aux != null && aux.equals(st)) {
                return true;
            }
        }
        return false;
    }

    public static String at(String str, int index) {
        return str.substring(index, index + 1);
    }
}