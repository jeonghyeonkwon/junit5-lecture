package site.metacoding.junitproject.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.metacoding.junitproject.domain.Book;
import site.metacoding.junitproject.dto.response.BookResponse;
import site.metacoding.junitproject.dto.request.BookSaveRequest;
import site.metacoding.junitproject.repository.BookRepository;
import site.metacoding.junitproject.util.MailSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@Slf4j
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

        given(bookRepository.save(any())).willReturn(dto.toEntity());
        given(mailSender.send()).willReturn(true);
//        doReturn(dto.toEntity()).when(bookRepository).save(dto.toEntity());
//        doReturn(true).when(mailSender).send();
        // when
        BookResponse saved = bookService.책등록하기(dto);
        //then
//        assertEquals(dto.getTitle(),saved.getTitle());
//        assertEquals(dto.getAuthor(),saved.getAuthor());
            assertThat(dto.getTitle()).isEqualTo(saved.getTitle());
            assertThat(dto.getAuthor()).isEqualTo(saved.getAuthor());
    }

    @Test
    public void 책목록보기_테스트(){
        //given
        List<Book> bookList =new ArrayList<>();
        bookList.add(new Book(1L,"제목1","저자1"));
        bookList.add(new Book(2L,"제목2","저자2"));


        //stub

        when(bookRepository.findAll()).thenReturn(bookList);

        //when
        List<BookResponse> list = bookService.책목록보기();

        //print

        list.forEach((data)->log.info("id : {} title : {} author : {}",data.getId(),data.getTitle(),data.getAuthor()));
        //then
        assertThat(list.get(0).getId()).isEqualTo(1L);
        assertThat(list.get(0).getTitle()).isEqualTo("제목1");
        assertThat(list.get(0).getAuthor()).isEqualTo("저자1");
    }


    @Test
    public void 책한건보기_테스트() {
        //given
        Long id = 1L;

        Optional<Book> book = Optional.of(new Book(1L, "제목", "내용"));
        //stub
        given(bookRepository.findById(any())).willReturn(book);
        //when
        BookResponse findBook = bookService.책한건보기(id);
        //then

        assertThat(findBook.getId()).isEqualTo(id);

    }
    @Test
    public void 책수정하기_테스트(){
        //given
        Long id = 1L;
        BookSaveRequest dto = new BookSaveRequest();
        dto.setTitle("spring");
        dto.setAuthor("실습자");

        //stub
        Optional<Book> bookOP = Optional.of(new Book(1L, "변경전 제목", "변경전 저자"));
        given(bookRepository.findById(any())).willReturn(bookOP);

        //when
        BookResponse updateBook = bookService.책수정하기(id, dto);

        //then
        assertThat(updateBook.getId()).isEqualTo(id);
        assertThat(updateBook.getTitle()).isEqualTo(dto.getTitle());
        assertThat(updateBook.getAuthor()).isEqualTo(dto.getAuthor());

    }


}