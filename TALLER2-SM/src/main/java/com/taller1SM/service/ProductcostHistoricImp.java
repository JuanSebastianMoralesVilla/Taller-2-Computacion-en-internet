package com.taller1SM.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.taller1SM.model.prod.Location;
import com.taller1SM.model.prod.Product;
import com.taller1SM.model.prod.Productcosthistory;
import com.taller1SM.repositories.ProductRepository;
import com.taller1SM.repositories.ProductcosthistoryRepository;

@Service
public class ProductcostHistoricImp implements ProductcosthistoryService {

	private ProductRepository productRepository;
	private ProductcosthistoryRepository productcosthistoryRepository;

	public ProductcostHistoricImp(ProductcosthistoryRepository productcosthistoryRepository,
			ProductRepository productRepository) {
		this.productRepository = productRepository;
		this.productcosthistoryRepository = productcosthistoryRepository;

	}

	public Productcosthistory savePHC(Productcosthistory productcosthistory, Integer productId) {

		if (productcosthistory.getModifieddate().isAfter(productcosthistory.getEnddate())) {
			throw new IllegalArgumentException("La fecha de inicio de venta debe ser menor a la fecha de fin");

		}

		if (productcosthistory.getStandardcost() == null) {
			throw new IllegalArgumentException("El costo estandar no puede ser nulo");
		}

		if (productcosthistory.getStandardcost().intValue() < 0) {
			throw new IllegalArgumentException("El costo estandar debe ser mayor a 0");
		}

		Product product = productRepository.findById(productId).get();

		productcosthistory.setProduct(product);
		return productcosthistoryRepository.save(productcosthistory);

	}

	public Productcosthistory editPHC(Integer id, Productcosthistory productcosthistory, Integer productId) {

		if (productcosthistory == null || productId == null || id == null) {
			throw new NullPointerException("id are null.");
		}

		if (productcosthistory.getModifieddate().isAfter(productcosthistory.getEnddate())) {
			throw new IllegalArgumentException("La fecha de inicio de venta debe ser menor a la fecha de fin");
		}

		if (productcosthistory.getStandardcost().intValue() < 0) {
			throw new IllegalArgumentException("costo estandar no debe ser negativo");
		}

		if (!productcosthistoryRepository.existsById(productcosthistory.getId())) {
			throw new IllegalStateException(" no existen datos para modificar");
		}

		if (!productRepository.existsById(productId)) {
			throw new NullPointerException("Product no existe.");
		}
		
		
		Productcosthistory productCHmodified = productcosthistoryRepository.findById(id).get();
		productCHmodified.setModifieddate(productcosthistory.getModifieddate());
		productCHmodified.setEnddate(productcosthistory.getEnddate());
		productCHmodified.setStandardcost(productcosthistory.getStandardcost());
		productCHmodified.setProduct(productcosthistory.getProduct());
		productcosthistoryRepository.save(productCHmodified);
		return productcosthistoryRepository.findById(productCHmodified.getId()).get();
	}

	public Iterable<Productcosthistory> findAll() {
		return productcosthistoryRepository.findAll();
	}

	public Optional<Productcosthistory> findById(Integer id) {

		return productcosthistoryRepository.findById(id);
	}

}
