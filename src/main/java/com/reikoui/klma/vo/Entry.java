package com.reikoui.klma.vo;

import lombok.Data;

@Data
public class Entry<K, V> {


    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    private K key;
    private V value;

}
