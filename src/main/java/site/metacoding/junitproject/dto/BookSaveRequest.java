package site.metacoding.junitproject.dto;


import lombok.Setter;
import site.metacoding.junitproject.domain.Book;

/*
* Controller에서 Setter가 호출되면서 Dto에 값이 채워짐
* */
@Setter
public class BookSaveRequest {
    private String title;
    private String author;

    public Book toEntity(){
        return Book.builder()
                .title(title)
                .author(author)
                .build();
    }
}
