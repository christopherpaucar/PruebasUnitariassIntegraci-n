package unitarias;

public class Calculadora {
  
    public static double suma(double a, double b){
        return a+b;
    }
    
    public static double resta(double a, double b){
        return a-b;
    }
    
    public static int division(int a, int b){
        return a/b;
    }
    
    public static double multiplicacion(double a, double b){
        return a*b;
    }
    
    public static void main(String[] args) {
        double a =division(010, 0);
        
        System.out.println(a);
    }
}
