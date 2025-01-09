package model.adt;

import exceptions.ExpressionException;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public interface MyIDictionary<K, V> {
    Map<K, V> getContent();

    void setContent(Map<K, V> map);

    ArrayList<V> getValues();

    void insert(K key, V value);
    void remove(K key) throws ExpressionException;
    boolean contains(K key);
    V get(K key) throws ExpressionException;
    String toString();

    MyIDictionary<K, V> deepCopy();

    public Set<K> getKeys();
}