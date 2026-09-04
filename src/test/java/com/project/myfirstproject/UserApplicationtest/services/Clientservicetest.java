package com.project.myfirstproject.UserApplicationtest.services;

import com.project.myfirstproject.repository.Clientrentryrepo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class Clientservicetest {


@Autowired
private Clientrentryrepo clientrentryrepo;

    @Disabled
    @Test
     public void testfindByClientName(){

        assertNotNull(clientrentryrepo.findByClientName("mohan"));
    }


    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,4,7"
    })
    public void test(int a, int b, int expected){

        assertEquals(expected, a+b);
    }

}
