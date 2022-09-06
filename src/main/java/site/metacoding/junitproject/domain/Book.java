package site.metacoding.junitproject.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.metacoding.junitproject.dto.response.BookResponse;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Book {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 20,nullable = false)
    private String author;

    @Builder
    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public Book update(String title, String author) {
        this.title = title;
        this.author = author;
        return this;
    }
    public BookResponse toDto(){
        return  BookResponse.builder()
                .id(this.id)
                .title(this.title)
                .author(this.author)
                .build();


    }
}
