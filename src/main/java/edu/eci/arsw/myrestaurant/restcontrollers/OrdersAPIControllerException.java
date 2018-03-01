/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.myrestaurant.restcontrollers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Nicolás
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrdersAPIControllerException extends RuntimeException {

	public OrdersAPIControllerException(String userId) {
		super("could not find user '" + userId + "'.");
	}
}