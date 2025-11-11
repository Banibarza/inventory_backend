package com.company.inventory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;

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
            response.getCategoryResponse().setCategory(category);
            response.setMetadata("Respuesta OK", "00", "Consulta exitosa");
        } catch (Exception e) {
            response.setMetadata("Respuesta NO OK", "-1", "Error al consultar categorías");
            e.printStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
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
                response.getCategoryResponse().setCategory(list);
                response.setMetadata("Respuesta OK", "00", "Categoría encontrada");
            } else {
                response.setMetadata("Respuesta NO OK", "-1", "Categoría no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            response.setMetadata("Respuesta NO OK", "-1", "Error al buscar categoría por ID");
            e.printStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoryResponseRest> save(Category category) {
        CategoryResponseRest response = new CategoryResponseRest();
        List<Category> list = new ArrayList<>();

        try {
            Category categorySaved = categoryDao.save(category);

            if (categorySaved != null) {
                list.add(categorySaved);
                response.getCategoryResponse().setCategory(list);
                response.setMetadata("Respuesta OK", "00", "Categoría guardada correctamente");
            } else {
                response.setMetadata("Respuesta NO OK", "-1", "Categoría no guardada");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            response.setMetadata("Respuesta NO OK", "-1", "Error al guardar categoría");
            e.printStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoryResponseRest> update(Category category, Long id) {
        CategoryResponseRest response = new CategoryResponseRest();
        List<Category> list = new ArrayList<>();

        try {
            Optional<Category> categorySearch = categoryDao.findById(id);

            if (categorySearch.isPresent()) {
                categorySearch.get().setName(category.getName());
                categorySearch.get().setDescription(category.getDescription());
                Category categoryToUpdate = categoryDao.save(categorySearch.get());

                if (categoryToUpdate != null) {
                    list.add(categoryToUpdate);
                    response.getCategoryResponse().setCategory(list);
                    response.setMetadata("RESPUESTA OK", "00", "Categoría actualizada correctamente");
                } else {
                    response.setMetadata("RESPUESTA NO OK", "-1", "Categoría no actualizada");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }

            } else {
                response.setMetadata("RESPUESTA NO OK", "-1", "Categoría no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            response.setMetadata("RESPUESTA NO OK", "-1", "Error al actualizar categoría");
            e.printStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 🔽 MÉTODO deleteById() AGREGADO SEGÚN TU IMAGEN 🔽
    @Override
    @Transactional
    public ResponseEntity<CategoryResponseRest> deleteById(Long id) {
        CategoryResponseRest response = new CategoryResponseRest();

        try {
            categoryDao.deleteById(id);
            response.setMetadata("Respuesta OK", "00", "Registro eliminado");
        } catch (Exception e) {
            response.setMetadata("Respuesta NO OK", "-1", "Error al eliminar");
            e.printStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
