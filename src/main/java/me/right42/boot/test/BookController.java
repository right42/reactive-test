package me.right42.boot.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private int count = 0;

    private void addCountSync(int count){
        synchronized (this){
            this.count += count;
        }
    }

    @GetMapping("/add-count")
    public Response<Integer> addCount(){
        addCountSync(1);
        return new Response<>(count);
    }

    @GetMapping("/counts")
    public Response<Integer> getCount(){
        System.out.println(count);
        return new Response<>(count);
    }

    @GetMapping("/books/{bookId}")
    public Response<Book> getBook(@PathVariable String bookId){
        return new Response<>(bookService.findById(bookId));
    }

    @Getter
    @AllArgsConstructor
    private static class Response<T> {
        T body;
    }
}
