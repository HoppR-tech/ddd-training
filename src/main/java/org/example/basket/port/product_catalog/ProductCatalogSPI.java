package org.example.basket.port.product_catalog;

import org.example.basket.model.ProductRef;

public interface ProductCatalogSPI {

    boolean exist(ProductRef productRef);

}
