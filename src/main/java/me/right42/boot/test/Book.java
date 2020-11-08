package me.right42.boot.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
@AllArgsConstructor
public class Book {

    private final String titie;

    private final String writer;
}
