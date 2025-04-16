package org.example;

import java.util.*;

public class MyHashMap<K, V> implements MyMap<K, V> {
    public static final int INITIAL_CAPACITY = 16;
    public static final double LOAD_FACTOR = 0.75;
    private Node<K, V>[] array = new Node[INITIAL_CAPACITY];
    private int size = 0;

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
    static private int getElementPosition(int hashKey, int arrayLength){
        return hashKey & (arrayLength - 1);
    }
    private void increaseArray(){
        Node<K, V>[] newArray = new Node[array.length * 2];
        for(Node<K, V> node : array){
            Node<K, V> existedElement = node;
            while (existedElement != null){
                put(existedElement.key, existedElement.value, newArray);
                existedElement = existedElement.next;
            }
        }
        array = newArray;
    }
    @Override
    public V put(K key, V value) {
        if (size >= array.length * LOAD_FACTOR){
            increaseArray();
        }
        V oldValue = put(key, value, array);
        if(oldValue == null){
            size++;
        }
        return oldValue;
    }

    private V put(K key, V value, Node<K, V>[] dst) {
        int hasKey = hash(key);
        int position = getElementPosition(hasKey, dst.length);
        Node<K, V> existedElement = dst[position];
        if(existedElement == null){
            Node<K, V> node = new Node<>(hasKey, key, value, null);
            dst[position] = node;
            return null;
        }else {
            while (true){
                if(hasKey == existedElement.hash && key.equals(existedElement.key)){
                    V oldValue = existedElement.value;
                    existedElement.value = value;
                    return oldValue;
                }
                if(existedElement.next == null){
                    Node<K, V> node = new Node<>(hasKey, key, value, null);
                    existedElement.next = node;
                    return null;
                }
                existedElement = existedElement.next;
            }
        }
    }

    @Override
    public V get(Object key) {
        int hasKey = hash(key);
        int position = getElementPosition(hasKey, array.length);
        Node<K, V> existedElement = array[position];
        while(existedElement != null){
            if(hasKey == existedElement.hash && key.equals(existedElement.key)){
                return existedElement.value;
            }
            existedElement = existedElement.next;
        }
        return null;
    }

    @Override
    public V remove(Object key) {
        int hasKey = hash(key);
        int position = getElementPosition(hasKey, array.length);
        Node<K, V> existedElement = array[position];
        if(existedElement != null && hasKey == existedElement.hash && key.equals(existedElement.key)){
            V oldValue = existedElement.value;
            array[position] = existedElement.next;
            size--;
            return oldValue;
        }else {
            while(existedElement != null){
                Node<K, V> nextElement = existedElement.next;
                if(nextElement == null){
                    return null;
                }
                if(hasKey == existedElement.hash && key.equals(nextElement.key)){
                    V oldValue = nextElement.value;
                    existedElement.next = nextElement.next;
                    size--;
                    return oldValue;
                }
                existedElement = existedElement.next;
            }
        }
        return null;
    }

    @Override
    public void clear() {
        array  = new Node[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> result = new HashSet<>();
        for(Node<K, V> node : array){
            Node<K, V> existedElement = node;
            while (existedElement != null){
                result.add(existedElement.key);
                existedElement = existedElement.next;
            }
        }
        return result;
    }

    @Override
    public Collection<V> values() {
        Collection<V> result = new ArrayList<>();
        for(Node<K, V> node : array){
            Node<K, V> existedElement = node;
            while (existedElement != null){
                result.add(existedElement.value);
                existedElement = existedElement.next;
            }
        }
        return result;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> result = new HashSet<>();
        for(Node<K, V> node : array){
            Node<K, V> existedElement = node;
            while (existedElement != null){
                result.add(existedElement);
                existedElement = existedElement.next;
            }
        }
        return result;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        int hashValue = value.hashCode();

        for(Node<K, V> node : array){
            Node<K, V> existedElement = node;
            while (existedElement != null){
                if(existedElement.value.hashCode() == hashValue && existedElement.value.equals(value)){
                    return true;
                }
                existedElement = existedElement.next;
            }
        }
        return false;
    }



    private static class Node<K, V> implements MyMap.Entry<K, V>{
        private final int hash;
        private final K key;
        private V value;
        private Node<K, V> next;

        public Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            return null;
        }
    }
}
