/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package unitarias;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;

/**
 *
 * @author leoto
 */
public class AlgoritmosBasicosTest {
    
    public AlgoritmosBasicosTest() {
    }

    @Test
    public void testTipoNumero() {
        int a =5;
        String respuesta=AlgoritmosBasicos.tipoNumero(a);
        assertEquals("POSITIVO", respuesta);
    }
    
    @Test
    public void testNumeroNegativo() {
        int a =-5;
        String respuesta=AlgoritmosBasicos.tipoNumero(a);
        assertEquals("NEGATIVO", respuesta);
    }
    @Disabled
    @Test
    public void testBusquedaLineal(){
        int [] a ={1,5,6,8,11,23,125};
        int buscado =6;
        assertTrue(AlgoritmosBasicos.busquedaLineal(a, buscado));
    }
    
        @Test
    public void testBusquedaLinealFalse(){
        int [] a ={1,5,6,8,11,23,125};
        int buscado =7;
        assertFalse(AlgoritmosBasicos.busquedaLineal(a, buscado));
    }
}
