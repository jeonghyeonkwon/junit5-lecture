package site.metacoding.junitproject.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import site.metacoding.junitproject.dto.BookResponse;
import site.metacoding.junitproject.dto.BookSaveRequest;
import site.metacoding.junitproject.repository.BookRepository;
import site.metacoding.junitproject.util.MailSender;
import site.metacoding.junitproject.util.MailSenderStub;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    //이렇게 주입하면 서비스만 테스트하고 싶은데 repository까지 테스트 해야 된다. 그래서 mockito같은 걸 쓴다

//    @Autowired
//    private BookRepository bookRepository;
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MailSender mailSender;
    @Test
    public void 책등록하기_테스트(){
        //given


        BookSaveRequest dto = new BookSaveRequest();
        dto.setTitle("junit5");
        dto.setAuthor("실습자");

        //가짜 객체
//        MailSenderStub mailSenderStub = new MailSenderStub();
//        BookService bookService = new BookService(bookRepository,mailSenderStub);

        when(bookRepository.save(dto.toEntity())).thenReturn(dto.toEntity());
        when(mailSender.send()).thenReturn(true);

        // when
        BookResponse saved = bookService.책등록하기(dto);
        //then
//        assertEquals(dto.getTitle(),saved.getTitle());
//        assertEquals(dto.getAuthor(),saved.getAuthor());
            assertThat(dto.getTitle()).isEqualTo(saved.getTitle());
            assertThat(dto.getAuthor()).isEqualTo(saved.getAuthor());
    }

}