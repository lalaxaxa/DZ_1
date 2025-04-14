package org.example;

import java.util.Collection;
import java.util.Set;

public interface MyMap<K, V> {
    V put(K key, V value);
    V get(Object key);
    V remove(Object key);
    int size();
    boolean isEmpty();
    void clear();

    /*boolean containsKey(Object key);
    boolean containsValue(Object value);*/
    Set<K> keySet();
    Collection<V> values();
}
