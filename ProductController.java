package com.sb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sb.model.Product1;
import com.sb.repository.ProductRepository;
import com.sb.service.ProductService;

@RestController
@RequestMapping("/ptapi")

public class ProductController {

	@Autowired
	ProductService ps;
	@Autowired
	ProductRepository productrepository;
	@PostMapping("/create")
	public ResponseEntity<Product1> createProduct(@Valid @RequestBody Product1 product) {
		try {

			Product1 t = ps.saveOrUpdate(product);
			return new ResponseEntity<>(t, HttpStatus.CREATED);

		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/readAll")
	public ResponseEntity<List<Product1>> getAllProducts(@RequestParam(required=false) String name) {
		try {
			List<Product1> Products = new ArrayList<Product1>();

			if (name == null)
				productrepository.findAll().forEach(Products::add);
			else
				productrepository.findByNameContaining(name).forEach(Products::add);

			if (Products.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(Products, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}