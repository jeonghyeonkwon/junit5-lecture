package site.metacoding.junitproject.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter

public class BookListResponse {
    List<BookResponse> bookList;

    @Builder
    public BookListResponse(List<BookResponse> bookList) {
        this.bookList = bookList;
    }
}
