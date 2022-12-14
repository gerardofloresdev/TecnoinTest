package com.tecnoin.products.services.imp;

import com.tecnoin.products.entities.ProductsEntity;
import com.tecnoin.products.models.dto.ProductDTO;
import com.tecnoin.products.models.dto.ResponseDTO;
import com.tecnoin.products.models.enums.StatusCodes;
import com.tecnoin.products.repositories.ProductsRepository;
import com.tecnoin.products.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ProductsServiceImp implements ProductsService {
    @Autowired
    ProductsRepository productsRepository;

    @Override
    public ResponseEntity<ResponseDTO> getProducts(int page, int size) {
        ResponseDTO response = new ResponseDTO();

        try {
            PageRequest pr = PageRequest.of(page,size);

            Page<ProductsEntity> products = productsRepository.findAll(pr);

            response.setCode(StatusCodes.OK);
            response.setData(products);
        } catch (Exception e) {
            e.printStackTrace();

            response.setCode(StatusCodes.ERROR);
            response.setError("An error occurred when obtaining the list of products");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDTO> createProduct(ProductDTO product) {
        ResponseDTO response = new ResponseDTO();

        try {
            String code = product.getCode();
            String name = product.getName();

            if (code == null ? true : code.contentEquals("")) {
                response.setCode(StatusCodes.BAD_REQUEST);
                response.setError("The code parameter is requerid");

                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            if (name == null ? true : name.contentEquals("")) {
                response.setCode(StatusCodes.BAD_REQUEST);
                response.setError("The name parameter is requerid");

                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            ProductsEntity entity = new ProductsEntity();
            entity.setCode(code);
            entity.setName(name);

            productsRepository.save(entity);

            response.setCode(StatusCodes.OK);
        } catch (Exception e) {
            e.printStackTrace();

            response.setCode(StatusCodes.ERROR);
            response.setError("An error occurred while trying to create the product");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDTO> updateProduct(Long id, ProductDTO product) {
        ResponseDTO response = new ResponseDTO();

        try {
            Optional<ProductsEntity> tmpProduct = productsRepository.findById(id);

            if (!tmpProduct.isPresent()) {
                response.setCode(StatusCodes.ERROR);
                response.setError("The product does not exist");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            String code = product.getCode();
            String name = product.getName();

            ProductsEntity entity = tmpProduct.get();
            if (code != null) {
                entity.setCode(code);
            }
            if (name != null) {
                entity.setName(name);
            }

            productsRepository.save(entity);

            response.setCode(StatusCodes.OK);
        } catch (Exception e) {
            e.printStackTrace();

            response.setCode(StatusCodes.ERROR);
            response.setError("An error occurred while trying to update the product");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDTO> deleteProduct(Long id) {
        ResponseDTO response = new ResponseDTO();

        try {
            boolean exists = productsRepository.existsById(id);

            if (!exists) {
                response.setCode(StatusCodes.ERROR);
                response.setError("The product does not exist");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            productsRepository.deleteById(id);

            response.setCode(StatusCodes.OK);
        } catch (Exception e) {
            e.printStackTrace();

            response.setCode(StatusCodes.ERROR);
            response.setError("An error occurred while trying to delete the product");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
