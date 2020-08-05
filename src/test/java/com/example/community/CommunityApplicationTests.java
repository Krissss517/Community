package com.example.community;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootTest
class CommunityApplicationTests {


    @Test
    void contextLoads() {
        Map<Object,Object> hashMap= new HashMap();
        Map<Object,Object> concurrentHashMap= new ConcurrentHashMap<>();
        Map<Object,Object> hashtable= new Hashtable<>();

    }

}


