package com.devsuperior.demo.util;

import com.devsuperior.demo.entities.Product;
import com.devsuperior.demo.projection.ProductProjetion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {


    public static List<Product> replace(List<ProductProjetion> ordereded, List<Product> unoreded) {

        Map<Long, Product> map = new HashMap<>();
        for(Product obj : unoreded){
            map.put(obj.getId(), obj);
        }
        List<Product> result = new ArrayList<>();
        for(ProductProjetion obj: ordereded){
            result.add(map.get(obj.getId()));
        }
        return result;
    }
}
