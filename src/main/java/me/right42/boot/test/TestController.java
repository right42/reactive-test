package me.right42.boot.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private int count = 0;

    @GetMapping("/add-count")
    public Response<Integer> addCount(){
        System.out.println(count);
        return new Response<>(count++);
    }

    @GetMapping("/counts")
    public Response<Integer> getCount(){
        System.out.println(count);
        return new Response<>(count);
    }

    @Getter
    @AllArgsConstructor
    private static class Response<T> {
        T body;
    }
}
