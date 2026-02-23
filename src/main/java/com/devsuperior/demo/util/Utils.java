package com.devsuperior.demo.util;

import com.devsuperior.demo.projection.IdProjection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {


    public static <T> List<? extends IdProjection<T>> replace(List<? extends IdProjection<T>> ordereded, List<? extends IdProjection<T>> unoreded) {

        Map<T, IdProjection<T>> map = new HashMap<>();
        for(IdProjection<T> obj : unoreded){
            map.put(obj.getId(), obj);
        }
        List<IdProjection<T>> result = new ArrayList<>();
        for(IdProjection<T> obj: ordereded){
            result.add(map.get(obj.getId()));
        }
        return result;
    }
}
