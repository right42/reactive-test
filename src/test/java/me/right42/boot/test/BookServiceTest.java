package me.right42.boot.test;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BookServiceTest {

    @Autowired
    BookService bookService;

    @Test
    void asyncTest(){
        Book byId = bookService.findById("1");

        System.out.println("이게 먼저 실행되나요?");
        assertThat(byId).isNull();
        // 실패
        // Assertions.assertThat(byId.getTitie()).isEqualTo("testBook");
    }

}