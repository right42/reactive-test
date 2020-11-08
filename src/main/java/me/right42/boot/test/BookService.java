package me.right42.boot.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    @Async
    public Book findById(String bookId) {
        log.info("BookService ");
        return bookRepository.findById(bookId);
    }
}
