package org.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MyHashMap<K, V> implements MyMap<K, V> {
    public static final int INITIAL_CAPACITY = 16;
    public static final double LOAD_FACTOR = 0.75;
    private Entry<K, V>[] array = new Entry[INITIAL_CAPACITY];
    private int size = 0;
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

    private V put(K key, V value, Entry[] dst) {
        int position = getElementPosition(key, dst.length);
        Entry<K, V> existedElement = dst[position];
        if(existedElement == null){
            Entry entry = new Entry<>(key, value, null);
            dst[position] = entry;
            return null;
        }else {
            while (true){
                if(key.hashCode() == existedElement.key.hashCode() && key.equals(existedElement.key)){
                    V oldValue = existedElement.value;
                    existedElement.value = value;
                    return oldValue;
                }
                if(existedElement.next == null){
                    Entry entry = new Entry<>(key, value, null);
                    existedElement.next = entry;
                    return null;
                }
                existedElement = existedElement.next;
            }
        }
    }

    @Override
    public V get(Object key) {
        int position = getElementPosition(key, array.length);
        Entry<K, V> existedElement = array[position];
        while(existedElement != null){
            if(key.hashCode() == existedElement.key.hashCode() && key.equals(existedElement.key)){
                return existedElement.value;
            }
            existedElement = existedElement.next;
        }
        return null;
    }

    @Override
    public V remove(Object key) {
        int position = getElementPosition(key, array.length);
        Entry<K, V> existedElement = array[position];
        if(existedElement != null && key.hashCode() == existedElement.key.hashCode() && key.equals(existedElement.key)){
            V oldValue = existedElement.value;
            array[position] = existedElement.next;
            size--;
            return oldValue;
        }else {
            while(existedElement != null){
                Entry<K, V> nextElement = existedElement.next;
                if(nextElement == null){
                    return null;
                }
                if(key.hashCode() == nextElement.key.hashCode() && key.equals(nextElement.key)){
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
        array  = new Entry[INITIAL_CAPACITY];
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
        for(Entry entry: array){
            Entry<K, V> existedElement = entry;
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
        for(Entry entry: array){
            Entry<K, V> existedElement = entry;
            while (existedElement != null){
                result.add(existedElement.value);
                existedElement = existedElement.next;
            }
        }
        return result;
    }

    private int getElementPosition(Object key, int arrayLength){
        return Math.abs(key.hashCode() % arrayLength);
    }

    private void increaseArray(){
        Entry<K, V>[] newArray = new Entry[array.length * 2];
        for(Entry entry: array){
            Entry<K, V> existedElement = entry;
            while (existedElement != null){
                put(existedElement.key, existedElement.value, newArray);
                existedElement = existedElement.next;
            }
        }
        array = newArray;
    }


    private class Entry<K, V>{
        private K key;
        private V value;
        private Entry next;

        public Entry(K key, V value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
