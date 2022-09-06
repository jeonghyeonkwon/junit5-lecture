package site.metacoding.junitproject.dto.response;

import lombok.Builder;
import lombok.Getter;
import site.metacoding.junitproject.domain.Book;


@Getter
@Builder
public class BookResponse {

    private Long id;
    private String title;
    private String author;

    public BookResponse(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
}
