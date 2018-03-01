package edu.eci.arsw.myrestaurant.test;

import edu.eci.arsw.myrestaurant.model.Order;
import edu.eci.arsw.myrestaurant.model.ProductType;
import edu.eci.arsw.myrestaurant.model.RestaurantProduct;
import edu.eci.arsw.myrestaurant.services.OrderServicesException;
import edu.eci.arsw.myrestaurant.services.RestaurantOrderServicesStub;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class ApplicationServicesTests {

    @Autowired
    RestaurantOrderServicesStub ros; 
        
    /**
     * Diseño de pruebas:
     * 
     * Clases de equivalencia:
     *  
     *      CE1: Descripción. La mesa no existe.
     *           Resultado esperado: No calcula la cuenta de la mesa y retorna un error.
     *          
     *      CE2: Descripción. Consultar el precio de una orden de una mesa existente.
     *           Resultado esperado: Resultado del precio de BillWithTaxesCalculator + TaxesCalculator2016ColTributaryReform.
     *   
     *      CE3: La mesa fue liberada anterior mente.
     *           Resultado esperado: No calcula la cuenta de la mesa y retorna un error.
     *          

     *  
     * Condiciones de frontera:
     * 
     *      CF1: Una peticion de total de una mesa que no existe
     *           Clases de equivalencia relacionadas: CE1
     *           Resultado esperado: Se evidencia un error 404 o que no encontró ninguna mesa con ese nombre. 
     *      CF2: Una Orden de un HotDog de la mesa 4
     *           Clases de equivalencia relacionadas: CE2
     *           Resultado esperado: Se espera que el precio del HotDog para la mesa 4 sea de 5950 . 
     *      CF3: Una Orden de un HotDog, 5 cervesas, 3 Hamburgesas de la mesa 2.
     *           Clases de equivalencia relacionadas: CE2
     *           Resultado esperado: Se espera que el precio del HotDog para la mesa 1 sea de . 
     *      CF4: Una peticion de valor de una orden que ya fue liberada
     *           Clases de equivalencia relacionadas: CE3
     *           Resultado esperado: Se espera que se evise que la mesa ya fue liberada.
     *      
     *         
     * 
     */    
    
    @Test
    public void noDeberiaConsultarMesaNoExistente(){
        try {
            ros.calculateTableBill(10);
            Assert.fail();
        } catch (OrderServicesException ex) {
            Logger.getLogger(ApplicationServicesTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Test
    public void deberiaTenerElValorCorrecto1() throws OrderServicesException{
        Assert.assertEquals(ros.calculateTableBill(4),3570);
    }
    
   @Test
    public void deberiaTenerElValorCorrecto2() throws OrderServicesException{
        Assert.assertEquals(ros.calculateTableBill(5),61981);
    }
    
    @Test
    public void noDeberiaConsultarMesaLibre(){
        try {
            ros.releaseTable(6);
            ros.calculateTableBill(6);
            Assert.fail();
        } catch (OrderServicesException ex) {
            Logger.getLogger(ApplicationServicesTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
