package model.adt;


import exceptions.ExpressionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    private Map<K, V> map;

    public MyDictionary() {
        this.map = new HashMap<K, V>();
    }

    public MyDictionary(Map<K, V> map) {
        this.map = map;
    }

    @Override
    public Map<K, V> getContent(){
        return this.map;
    }

    @Override
    public void setContent(Map<K, V> map){
        this.map = map;
    }

    @Override
    public ArrayList<V> getValues() {
        return new ArrayList<>(this.map.values());
    }

    @Override
    public void insert(K key, V value) {
        this.map.put(key, value);
    }

    @Override
    public void remove(K key) throws ExpressionException{
        if (this.map.containsKey(key))
            this.map.remove(key);
        else
            throw new ExpressionException("(Remove) Key not found in dictionary!\n");
    }

    @Override
    public boolean contains(K key) {
        return this.map.containsKey(key);
    }

    @Override
    public V get(K key) throws ExpressionException {
        if (!this.map.containsKey(key)){
            throw new ExpressionException("(Get) Key not found in dictionary!\n");
        }
        return this.map.get(key);
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();

        for (K elem : this.map.keySet()){
            str.append(elem.toString() + "->" + this.map.get(elem).toString() + " | ");
        }

        return str.toString();
    }

    @Override
    public MyIDictionary<K, V> deepCopy() {
        return new MyDictionary<>(this.map);
    }

    @Override
    public Set<K> getKeys() {
        return this.map.keySet();
    }
}