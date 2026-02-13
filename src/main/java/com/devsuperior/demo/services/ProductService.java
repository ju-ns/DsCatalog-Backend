package com.devsuperior.demo.services;

import com.devsuperior.demo.dto.CategoryDTO;
import com.devsuperior.demo.dto.ProductDTO;
import com.devsuperior.demo.entities.Category;
import com.devsuperior.demo.entities.Product;
import com.devsuperior.demo.entities.Product;
import com.devsuperior.demo.repositories.CategoryRepository;
import com.devsuperior.demo.repositories.ProductRepository;
import com.devsuperior.demo.services.exceptions.DatabaseException;
import com.devsuperior.demo.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    public List<ProductDTO> findAll(){
        List<Product> list = repository.findAll();
        return list.stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());

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
        return new ProductDTO(entity);
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setDate(entity.getDate());
        entity.setPrice(entity.getPrice());
        entity.setImgUrl(entity.getImgUrl());

        entity.getCategories().clear();
        for(CategoryDTO categoryDTO : dto.getCategories()){
            Category category = categoryRepository.getReferenceById(categoryDTO.getId());
            entity.getCategories().add(category);
        }
    }

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

    public Page<ProductDTO> findAllPaged(Pageable pageable) {
        Page<Product> list = repository.findAll(pageable);
        return list.map(ProductDTO::new);
    }
}
