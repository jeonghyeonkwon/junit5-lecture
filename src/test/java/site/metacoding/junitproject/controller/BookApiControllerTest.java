package site.metacoding.junitproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import site.metacoding.junitproject.domain.Book;
import site.metacoding.junitproject.dto.request.BookSaveRequest;
import site.metacoding.junitproject.repository.BookRepository;
import site.metacoding.junitproject.service.BookService;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

// 통합테스트 (C, S, R)
// 컨트롤러만 테스트하는 것이 아님
//@RequiredArgsConstructor
@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookApiControllerTest {
    // 이렇게 조회 하면 안됨
//    private final BookService bookService;
//    @Autowired
//    private BookService bookService;

    @Autowired
    private TestRestTemplate rt;
    @Autowired
    private BookRepository bookRepository;

    private static ObjectMapper om;
    private static HttpHeaders httpHeaders;

    @BeforeAll
    public static void init(){
        om = new ObjectMapper();
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }
    @BeforeEach // 각 테스트 시작전에 한번씩 실행
    public void initData(){
        //given
        String title = "junit5";
        String author = "메타 코딩";

        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        // when (테스트 실행)

        Book savedBook = bookRepository.save(book);
    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    public void deleteBook_test(){
        //given
        Long id = 1L;
        //when
        HttpEntity<String> request = new HttpEntity<>(null,httpHeaders);
        ResponseEntity<String> response = rt.exchange("/api/v1/book/"+id,HttpMethod.DELETE, request, String.class);
        //then
        System.out.println(response.getStatusCode());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void updateBook_test() throws Exception{
        // given
        Long id = 1L;
        BookSaveRequest bookSaveRequest = new BookSaveRequest();
        bookSaveRequest.setTitle("spring");
        bookSaveRequest.setAuthor("메타코딩");

        String body = om.writeValueAsString(bookSaveRequest);

        // when
        HttpEntity<String> request = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<String> response = rt.exchange("/api/v1/book/"+id, HttpMethod.PUT, request, String.class);

        //then
        System.out.println("updateBook_test() : " + response.getStatusCode());
        System.out.println(response.getBody());
        DocumentContext dc = JsonPath.parse(response.getBody());
        String title = dc.read("$.body.title");
        assertThat(title).isEqualTo("spring");
    }



    @Sql("classpath:db/tableInit.sql")
    @Test
    public void getBookOne_test(){ // 1. getBookOne_test 시작전에 BeforeEach를 시작하는데
        // given
        Long id = 1L;
        // when
        HttpEntity<String> request = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> response = rt.exchange("/api/v1/book/"+id, HttpMethod.GET, request, String.class);

        System.out.println(response.getBody());
        // then
        DocumentContext dc = JsonPath.parse(response.getBody());
        int code = dc.read("$.code");
        String title = dc.read("$.body.title");
        assertThat(code).isEqualTo(1);
        assertThat(title).isEqualTo("junit5");
    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    public void getBookList_test(){
        // given

        // when
        HttpEntity<String> request = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> response = rt.exchange("/api/v1/book", HttpMethod.GET, request, String.class);
        // then
        System.out.println(response.getBody());
        DocumentContext dc = JsonPath.parse(response.getBody());
        int code = dc.read("$.code");
        String title = dc.read("$.body.bookList[0].title");
        assertThat(code).isEqualTo(1);
        assertThat(title).isEqualTo("junit5");
    }
    @Test
    public void saveBoot_test() throws JsonProcessingException {
        // given
        BookSaveRequest bookSaveRequest = new BookSaveRequest();
        bookSaveRequest.setTitle("스프링1강");
        bookSaveRequest.setAuthor("메타코딩");
        //json 형식으로 변환해 주는 것
        String body = om.writeValueAsString(bookSaveRequest);
        //when
        HttpEntity<String> request = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<String> response = rt.exchange("/api/v1/book", HttpMethod.POST, request, String.class);

        // then
        DocumentContext dc = JsonPath.parse(response.getBody());


        String title = dc.read("$.body.title");
        String author = dc.read("$.body.author");
        assertThat(title).isEqualTo("스프링1강");
        assertThat(author).isEqualTo("메타코딩");
    }

}