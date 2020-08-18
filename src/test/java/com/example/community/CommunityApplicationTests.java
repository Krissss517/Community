package com.example.community;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommunityApplicationTests {


    @Test
    void testQuick() {

        int n = 9 - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        int res= (n < 0) ? 1 : n + 1;
        System.out.println(res);

    }

    @Test
    public void test2(){



    }
}

