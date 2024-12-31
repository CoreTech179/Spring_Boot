package com.example.journalApp;

import com.example.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepositoryObj;

    @Disabled // This will disable this particular function
    @Test
    public void testFindByUsername(){
        assertEquals(4,2+2);
        assertNotNull(userRepositoryObj.findByUserName("ram")); // Iska matlab ham dawa kar rehe he ye NULL nehi hona chahiye
//        assert means --> dawa karna
//        At first this will throw an error because userRepository will only run when the entire Spring_Boot application will run.
//        When we annotate @SpringBootTest annotation this will start the entire application, and then we will test for that particular function.
    }

    @ParameterizedTest // Basically this annotation will make a loop on this function test that is defined inside CSVSource
    @CsvSource({
            "1,1,2", // syntax --> a = 1, b = 1, expected = 2, iteration = 1
            "2,3,5", // similarly, a = 2, b = 3, expected = 5, iteration = 2
            "1,2,4" // similarly,  a = 1, b = 2, expected = 4, iteration = 3
    })
    public void test(int a, int b, int expected){
        assertEquals(expected, a+b);
    }

}
