package org.example;

import java.util.Objects;

public class Product {
    int id;

    public Product(int id) {
        this.id = id;
    }

    //тут equals и hashCode нужны для работы containsValue
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
