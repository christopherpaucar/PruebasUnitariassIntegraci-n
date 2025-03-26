package unitarias;

public class AlgoritmosBasicos {

    public static String tipoNumero(int a) {
        String respuesta = "";
        if (a > 0) {
            respuesta = "POSITIVO";
        } else {
            if (a == 0) {
                respuesta = "NEUTRO";
            } else {
                respuesta = "NEGATIVO";
            }
        }
        return respuesta;
    }

    public static boolean busquedaLineal(int[] numeros, int buscado) {
        boolean respuesta = false;
        for (int i = 0; i < numeros.length; i++) {
            if (numeros[i] == buscado) {
                respuesta = true;
                break;
            }
        }
        return respuesta;
    }

    public static boolean numeroParImpar(int numero) {
        return true;

    }
    
    public static boolean esPrimo(){
        return true;
    }
   
    //https://www.cdc.gov/healthyweight/spanish/assessing/bmi/adult_bmi/index.html#tendencias
    public static String calcularIMC(double pesoKg, double alturaM){
        return "";
    }
    
}
