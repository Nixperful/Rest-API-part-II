/*
 * Copyright (C) 2016 Pivotal Software, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.arsw.myrestaurant.restcontrollers;

import edu.eci.arsw.myrestaurant.model.Order;
import edu.eci.arsw.myrestaurant.services.OrderServicesException;
import edu.eci.arsw.myrestaurant.services.RestaurantOrderServices;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */

@Service
@RestController
@RequestMapping(value = "/orders")
public class OrdersAPIController {
    private final RestaurantOrderServices restaurantServices;
    
    @Autowired
    public OrdersAPIController(RestaurantOrderServices restaurantServices) {
        this.restaurantServices = restaurantServices;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> manejadorGetRecursoOrders(){
        try {   
                Set<Order> ordenes=  new HashSet<>();
                Set<Integer> pedidos;
                Order ord;
                pedidos=restaurantServices.getTablesWithOrders();
                for(Integer table : pedidos) {
                    ord=restaurantServices.getTableOrder(table);
                    ordenes.add(ord);
                }
                return new ResponseEntity<>(ordenes,HttpStatus.ACCEPTED);
        } catch (Exception ex) {
                Logger.getLogger(OrdersAPIController.class.getName()).log(Level.SEVERE, null, ex);
                return new ResponseEntity<>("Error al momento de hacer la peticion",HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/{idmesa}")
    public ResponseEntity<?> manejadorGetOrder(@PathVariable String idmesa) {
        Order ord=restaurantServices.getTableOrder(Integer.parseInt(idmesa));
        if (ord!=null){
            return new ResponseEntity<>(ord,HttpStatus.ACCEPTED);
        }else{
             return new ResponseEntity<>("Error 404.",HttpStatus.NOT_FOUND);
        }
                
    }
    
    
    @RequestMapping(method = RequestMethod.POST)	
    public ResponseEntity<?> manejadorPostRecursoOrder(@RequestBody Order order){
        try {
                restaurantServices.addNewOrderToTable(order);
                return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (OrderServicesException ex) {
                Logger.getLogger(OrdersAPIController.class.getName()).log(Level.SEVERE, null, ex);
                return new ResponseEntity<>("Error ya existe la orden. ",HttpStatus.FORBIDDEN);            
        }        
    }	 
    
    @GetMapping("/{idmesa}/total")
    public ResponseEntity<?> manejadorGetTotal(@PathVariable String idmesa) {
        try {
            return new ResponseEntity<>(restaurantServices.calculateTableBill(Integer.parseInt(idmesa)),HttpStatus.ACCEPTED);
        } catch (OrderServicesException ex) {
            Logger.getLogger(OrdersAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error 404.",HttpStatus.NOT_FOUND);
        }            
    }
    
    @RequestMapping(value = "/{idmesa}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateOrder(@PathVariable("idmesa") int id, @RequestBody Order order) {
        Order currentOrder = restaurantServices.getTableOrder(id);
         
        if (currentOrder == null) {
            return new ResponseEntity(new OrderServicesException("Unable to upate. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        currentOrder.setOrderAmountsMap(order.getOrderAmountsMap());
        currentOrder.setTableNumber(order.getTableNumber());
       
        return new ResponseEntity<>(currentOrder, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/order/{idmesa}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOrder(@PathVariable("id") int id) {
        Order order = restaurantServices.getTableOrder(id);
        try {
            restaurantServices.releaseTable(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (OrderServicesException ex) {
            Logger.getLogger(OrdersAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(new OrderServicesException("Unable to upate. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        
    }
}
