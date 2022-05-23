package com.taller1SM.service;

import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import com.taller1SM.model.prod.Location;
import com.taller1SM.model.prod.Product;
import com.taller1SM.model.prod.Productcategory;
import com.taller1SM.model.prod.Productcosthistory;
import com.taller1SM.model.prod.Productinventory;
import com.taller1SM.repositories.LocationRepository;
import com.taller1SM.repositories.ProductRepository;
import com.taller1SM.repositories.ProductinventoryRepository;

@Service
public class ProductinventoryImp implements ProductinventoryService {

	private ProductinventoryRepository productinventoryRepository;
	private ProductRepository productRepository;
	private LocationRepository locationRepository;

	public ProductinventoryImp(ProductinventoryRepository productinventoryRepository,
			ProductRepository productRepository, LocationRepository locationRepository) {

		this.productinventoryRepository = productinventoryRepository;
		this.productRepository = productRepository;
		this.locationRepository = locationRepository;
	}

	public Productinventory savePIR(Productinventory productinventory, Integer productId, Integer locationId) {


		if (productinventory.getQuantity() == null) {
			throw new NullPointerException("CANTIDAD ES NULA.");
		}
		if (productinventory.getQuantity() < 0) {
			throw new IllegalArgumentException("CANTIDAD ES MENOR A CERO.");
		}

		productinventory.setProduct(productRepository.findById(productId).get());
		productinventory.setLocation(locationRepository.findById(locationId).get());

		return productinventoryRepository.save(productinventory);

	}

	public Productinventory editPIR(Integer id, Productinventory productinventory, Integer productId, Integer locationId) {

	
		if (productinventory.getQuantity()== null) {
			throw new RuntimeException("no valid");

		} 
		
		/*
		if (productinventory.getQuantity() < 0) {
			throw new IllegalArgumentException("Cantidad mayor a 0");

		}
*/
		
		productinventory.setLocation(locationRepository.findById(locationId).get());
		productinventory.setProduct(productRepository.findById(productId).get());
		
		
		Productinventory inventoryModify = productinventoryRepository.findById(id).get();
		inventoryModify.setLocation(locationRepository.findById(locationId).get());
		inventoryModify.setProduct(productRepository.findById(productId).get());
		inventoryModify.setQuantity(productinventory.getQuantity());
		return productinventoryRepository.save(inventoryModify);

	}// fin metodo

	@Override
	public Iterable<Productinventory> findAll() {
		return productinventoryRepository.findAll();
	}

	@Override
	public Optional<Productinventory> findById(Integer id) {

		return productinventoryRepository.findById(id);
	}

}
