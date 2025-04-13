package org.example;

public class MyHashMap<K, V> implements MyMap<K, V> {
    public static final int INITIAL_CAPACITY = 16;
    private Entry<K, V>[] array = new Entry[INITIAL_CAPACITY];
    private int size = 0;
    @Override
    public V put(K key, V value) {
        int position = getElementPosition(key);
        Entry<K, V> existedElement = array[position];
        if(existedElement == null){
            Entry entry = new Entry<>(key, value, null);
            array[position] = entry;
            size++;
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
                    size++;
                    return null;
                }
                existedElement = existedElement.next;
            }
        }

    }

    @Override
    public V get(Object key) {
        int position = getElementPosition(key);
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
        int position = getElementPosition(key);
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

    private int getElementPosition(Object key){
        return Math.abs(key.hashCode() % array.length);
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
