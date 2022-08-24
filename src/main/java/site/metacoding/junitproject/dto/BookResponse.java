package site.metacoding.junitproject.dto;

import lombok.Getter;
import site.metacoding.junitproject.domain.Book;


@Getter
public class BookResponse {

    private Long id;
    private String title;
    private String author;

    public BookResponse toDto(Book bookPs){
        this.id = bookPs.getId();
        this.title = bookPs.getTitle();
        this.author = bookPs.getAuthor();
        return this;
    }
}
