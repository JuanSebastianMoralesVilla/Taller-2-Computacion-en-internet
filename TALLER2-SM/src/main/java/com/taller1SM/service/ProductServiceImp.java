package com.taller1SM.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.taller1SM.model.prod.Location;
import com.taller1SM.model.prod.Product;
import com.taller1SM.model.prod.Productcategory;
import com.taller1SM.model.prod.Productcosthistory;
import com.taller1SM.model.prod.Productinventory;
import com.taller1SM.model.prod.Productsubcategory;
import com.taller1SM.repositories.ProductRepository;
import com.taller1SM.repositories.ProductSubcategoryRepository;
import com.taller1SM.repositories.ProductcategoryRepository;
@Service
public class ProductServiceImp implements ProductService {

	// relaciones
	private ProductRepository productRepository;

	private ProductcategoryRepository productcategoryRepository;

	private ProductSubcategoryRepository productSubcategoryRepository;

	// constructor

	public ProductServiceImp(ProductRepository productRepository, ProductcategoryRepository productcategoryRepository,
			ProductSubcategoryRepository productSubcategoryRepository) {

		this.productRepository = productRepository;
		this.productcategoryRepository = productcategoryRepository;
		this.productSubcategoryRepository = productSubcategoryRepository;
	}

	// servicio de guardar producto
	@Override
	public Product saveProduct(Product product, Integer prCategoryId, Integer prSubcategoryId) {

		//Optional<Productcategory> productcategory = productcategoryRepository.findById(prCategoryId);

		//Optional<Productsubcategory> productsubcategory = productSubcategoryRepository.findById(prSubcategoryId);

		if (product == null || prSubcategoryId==null) {
			throw new RuntimeException("no valido");

		}
		
		if(!productSubcategoryRepository.existsById(prSubcategoryId)) {
			throw new RuntimeException("no existe la categoria o subcategoria ");
		}

		
		if(product.getProductnumber().length()==0 || product.getSellstartdate().isAfter(product.getSellenddate())) {
			if(product.getSize()<0 || product.getWeight()<0) {
				throw new IllegalArgumentException("Incorrect fields.");
			}
		}
		Productsubcategory subcategory=productSubcategoryRepository.findById(prSubcategoryId).get();
		
		product.setProductsubcategory(subcategory);
		
		return productRepository.save(product);
		
		

	}// fin metodo
	
	// servicio de editar producto

	public Product editProduct(Integer id, Product product, Integer prCategoryId, Integer prSubcategoryId) {
					
		
	       if (product.getSellstartdate().isAfter(product.getSellenddate()) || product.getProductnumber().length() == 0  || product.getWeight() < 0 || product.getSize() < 0) {
				throw new IllegalArgumentException("La fecha de inicio de venta debe ser menor a la fecha de fin, Peso mayor a 0 , Tamaño mayor a ");

			} 
	       
	   	Productsubcategory productsubcategory = productSubcategoryRepository.findById(prSubcategoryId).get();
	    
	   	
	   	
			product.setProductsubcategory(productsubcategory);
			

			Product pmodificar = productRepository.findById(id).get();
			
			
				pmodificar.setProductnumber(product.getProductnumber());
				pmodificar.setSellenddate(product.getSellenddate());
				pmodificar.setSellstartdate(product.getSellstartdate());
				pmodificar.setName(product.getName());
				pmodificar.setSize(product.getSize());
				pmodificar.setWeight(product.getWeight());
				pmodificar.setProductsubcategory(productsubcategory);
				return productRepository.save(pmodificar);

	}
	
	
	@Override
	public Iterable<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Iterable<Productsubcategory> findAllSubcategory() {
		return productSubcategoryRepository.findAll();
	}

	public Optional<Product> findById(Integer id) {
		
		return productRepository.findById(id);
	}
}
