package me.right42.boot.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final BookStore bookStore;

    public Book findById(String id){
        return bookStore.getBook(id);
    }

    @Slf4j
    @Component
    private static class BookStore {
        private final Map<String, Book> store = new HashMap<>();

        @PostConstruct
        protected void postConstruct(){
            store.put("1", Book.builder()
                    .titie("testBook")
                    .writer("tester")
                    .build());
        }

        public Book getBook(String bookId) {
            log.info("bookId : {}", bookId);
            return this.store.get(bookId);
        }
    }

}
