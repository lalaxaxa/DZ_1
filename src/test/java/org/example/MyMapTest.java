package org.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyMapTest {
    private MyMap<Store, Product> map;

    @Before
    public void setUp() throws Exception {
        map = new MyHashMap<>();
    }

    @Test
    public void whenPut100elementsThenSizeBecome100() {
        for (int i = 0; i < 100; i++) {
            map.put(new Store("Store" + i), new Product(i));
        }
        assertEquals(100, map.size());
    }

    @Test
    public void whenPut100elementsWith10DifferentKeyThenSize10() {
        for (int i = 0; i < 100; i++) {
            int x = i % 10;
            map.put(new Store("Store" + x), new Product(x));
        }
        assertEquals(10, map.size());
    }
    @Test
    public void removeReturnOldValueOnlyOnce() {
        for (int i = 0; i < 10; i++) {
            map.put(new Store("Store" + i), new Product(i));
        }
        assertEquals(10, map.size());
        Store deleteElement = new Store("Store4");
        assertEquals(4, map.remove(deleteElement).id);
        assertEquals(9, map.size());
        assertEquals(null, map.remove(deleteElement));
    }
    @Test
    public void countOfKeysMustBeEqualsCountOfValuesAndCountOfEntrySet() {
        for (int i = 0; i < 100; i++) {
            map.put(new Store("Store" + i), new Product(i));
        }
        assertEquals(100, map.size());
        assertEquals(100, map.keySet().size());
        assertEquals(100, map.values().size());
        assertEquals(100, map.entrySet().size());
    }
    @Test
    public void methodGetMustBeReturnRightValue () {
        for (int i = 0; i < 100; i++) {
            map.put(new Store("Store" + i), new Product(i));
        }
        Store key = new Store("Store57");
        assertEquals(57, map.get(key).id);
    }


    @Test
    public void checkIsEmptyAndClear() {
        assertTrue(map.isEmpty());
        for (int i = 0; i < 10; i++) {
            map.put(new Store("Store" + i), new Product(i));
        }
        assertFalse(map.isEmpty());
        map.clear();
        assertTrue(map.isEmpty());
    }

    @Test
    public void checkContainsKey() {
        for (int i = 0; i < 10; i++) {
            map.put(new Store("Store" + i), new Product(i));
        }
        assertTrue(map.containsKey(new Store("Store5")));
        assertFalse(map.containsKey(new Store("Store55")));
    }

    @Test
    public void checkContainsValue() {
        for (int i = 0; i < 10; i++) {
            map.put(new Store("Store" + i), new Product(i));
        }
        assertTrue(map.containsValue(new Product(5)));
        assertFalse(map.containsValue(new Product(55)));
    }

}