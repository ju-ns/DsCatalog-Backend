package com.devsuperior.demo.services;

import com.devsuperior.demo.dto.CategoryDTO;
import com.devsuperior.demo.dto.ProductDTO;
import com.devsuperior.demo.entities.Category;
import com.devsuperior.demo.entities.Product;
import com.devsuperior.demo.projection.ProductProjetion;
import com.devsuperior.demo.repositories.CategoryRepository;
import com.devsuperior.demo.repositories.ProductRepository;
import com.devsuperior.demo.services.exceptions.DatabaseException;
import com.devsuperior.demo.services.exceptions.ResourceNotFoundException;
import com.devsuperior.demo.util.Utils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;



    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(String name, String categoryId, Pageable pageable){

        List<Long> categoryIds = Arrays.asList();
        if (!"0".equals(categoryId)){
            categoryIds = Arrays.stream(categoryId.split(",")).map(Long::parseLong).toList();
        }

        Page<ProductProjetion> page =  repository.searchProducts(categoryIds, name, pageable);
        List<Long> productsIds = page.map((x) -> x.getId()).toList();

        List<Product> entities = repository.searchProductsWithCategories(productsIds);
        entities = Utils.replace(page.getContent(), entities);
        List<ProductDTO> dtos = entities.stream().map(product -> new ProductDTO(product, product.getCategories())).toList();

        Page<ProductDTO> pageDto = new PageImpl<>(dtos, page.getPageable(), page.getTotalPages());
        return pageDto;
    }


    @Transactional(readOnly = true)
    public ProductDTO findByID(Long id) {
        Optional<Product> entity = repository.findById(id);
        Product obj = entity.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ProductDTO(obj);
    }


    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ProductDTO(entity, entity.getCategories());
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setDate(dto.getDate());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());

        entity.getCategories().clear();
        for(CategoryDTO categoryDTO : dto.getCategories()){
            Category category = categoryRepository.getReferenceById(categoryDTO.getId());
            entity.getCategories().add(category);
        }
    }

    @Transactional
    public ProductDTO update(ProductDTO dto, Long id) {
        try{
            Product entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new ProductDTO(entity);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException("id not found" + id);
        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if(!repository.existsById(id)){
            throw  new ResourceNotFoundException("Resource not found");
        }
        try{
            repository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("referential integrity failure");
        }

    }

}
