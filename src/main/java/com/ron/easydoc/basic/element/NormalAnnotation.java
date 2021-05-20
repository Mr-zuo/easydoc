package com.ron.easydoc.basic.element;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

;

/**
 * @author ron
 * @date 2021年05月20日
 */
@Getter
@Setter
public  class NormalAnnotation  extends Annotation{
    private List<Pair> pairList;


    @Getter
    @Setter
    public static class Pair{
        private String name;
        private String value;
    }

    public String getValue(String name){
        Pair pair = getPair(name);
        return pair != null?pair.getValue():null;
    }

    public Pair getPair(String name){
        for(Pair pair:pairList){
            if(pair.getName().equals(name)){
                return pair;
            }
        }
        return null;
    }
}
