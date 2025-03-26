
package unitarias;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;

public class CalculadoraTest {
    
    public CalculadoraTest() {
    }

    
    @Test
    public void testSuma(){
        double a= 5,b=5;
        double respuesta =Calculadora.suma(a, b);
        assertEquals(10, respuesta);
    }
    
    @Test
    public void testDivisionZero(){
        assertThrows(java.lang.ArithmeticException.class, ()->{
            Calculadora.division(10, 0);
        });
    }
    
    
}
