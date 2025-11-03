package com.company.inventory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;

import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl implements ICategoryService {
    
    @Autowired
    private ICategoryDao categoryDao;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> search() {
        CategoryResponseRest response = new CategoryResponseRest();
        try {
            List<Category> category = (List<Category>) categoryDao.findAll();
            response.getCategoryResponseRest().setCategory(category);
            response.setMetadata("Respuesta ok", "00", "respuesta exitosa");
        } catch (Exception e) {
            response.setMetadata("Respuesta ok", "-1", "Respuesta al consultar");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> searchById(Long id) {
        CategoryResponseRest response = new CategoryResponseRest();
        List<Category> list = new ArrayList<>();

        try {
            Optional<Category> category = categoryDao.findById(id);

            if (category.isPresent()) {
                list.add(category.get());
                response.getCategoryResponseRest().setCategory(list);
                response.setMetadata("Respuesta ok", "00", "ERROR CATEGORIA ENCONTRADA");
            } else {
            	response.setMetadata("Respuesta NO OK", "-1", "Error CATEGORIA no encontrada.");
                return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            response.setMetadata("Respuesta ok", "-1", "ERROR POR ID");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }
}
