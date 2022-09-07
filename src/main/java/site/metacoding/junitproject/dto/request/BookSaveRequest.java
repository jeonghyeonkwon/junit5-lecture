package site.metacoding.junitproject.dto.request;


import lombok.Getter;
import lombok.Setter;
import site.metacoding.junitproject.domain.Book;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/*
* Controller에서 Setter가 호출되면서 Dto에 값이 채워짐
* */
@Setter
@Getter
public class BookSaveRequest {
    @NotBlank
    @Size(min = 1,max = 50,message = "1부터 50까지")
    private String title;
    @NotBlank
    @Size(min = 2,max = 20,message = "1부터 20까지")
    private String author;

    public Book toEntity(){
        return Book.builder()
                .title(title)
                .author(author)
                .build();
    }
}
