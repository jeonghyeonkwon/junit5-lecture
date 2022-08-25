package site.metacoding.junitproject.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import site.metacoding.junitproject.dto.BookResponse;
import site.metacoding.junitproject.dto.BookSaveRequest;
import site.metacoding.junitproject.repository.BookRepository;
import site.metacoding.junitproject.util.MailSenderStub;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class BookServiceTest {

    //이렇게 주입하면 서비스만 테스트하고 싶은데 repository까지 테스트 해야 된다. 그래서 mockito같은 걸 쓴다
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void 책등록하기_테스트(){
        //given


        BookSaveRequest dto = new BookSaveRequest();
        dto.setTitle("junit5");
        dto.setAuthor("실습자");

        //가짜 객체
        MailSenderStub mailSenderStub = new MailSenderStub();
        BookService bookService = new BookService(bookRepository,mailSenderStub);
        BookResponse saved = bookService.책등록하기(dto);

        assertEquals(dto.getTitle(),saved.getTitle());
        assertEquals(dto.getAuthor(),saved.getAuthor());

    }

}