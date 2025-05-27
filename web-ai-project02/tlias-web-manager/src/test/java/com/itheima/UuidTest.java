package com.itheima;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UuidTest {

    @Test
    public void UuidTest(){
        //UUid-36位不重复字符串
        for (int i = 0; i < 10; i++) System.out.println(UUID.randomUUID().toString());
    }
}

