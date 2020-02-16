package com.hari.microservices.core.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.hari.microservices.util.exceptions.InvalidInputException;
import com.hari.microservices.util.exceptions.NotFoundException;
import com.hari.microservices.util.http.ServiceUtil;
import com.hari.microservices.util.logger.Logger;

import microservices.core.api.product.Product;
import microservices.core.api.product.ProductService;

@RestController
public class ProductServiceImpl implements ProductService {

    private static final Logger LOG = Logger.getInstance(ProductServiceImpl.class);

    private final ServiceUtil serviceUtil;

    @Autowired
    public ProductServiceImpl(ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
    }

    @Override
    public Product getProduct(int productId) {
        LOG.debug("/product return the found product for productId={}", productId);

        if (productId < 1) throw new InvalidInputException("Invalid productId: " + productId);

        if (productId == 13) throw new NotFoundException("No product found for productId: " + productId);

        return new Product(productId, "name-" + productId, 123, serviceUtil.getServiceAddress());
    }
}
