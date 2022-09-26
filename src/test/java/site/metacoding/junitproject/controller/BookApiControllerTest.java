package site.metacoding.junitproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import site.metacoding.junitproject.dto.request.BookSaveRequest;
import site.metacoding.junitproject.service.BookService;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

// 통합테스트 (C, S, R)
// 컨트롤러만 테스트하는 것이 아님
//@RequiredArgsConstructor
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookApiControllerTest {
    // 이렇게 조회 하면 안됨
//    private final BookService bookService;
//    @Autowired
//    private BookService bookService;

    @Autowired
    private TestRestTemplate rt;

    private static ObjectMapper om;
    private static HttpHeaders httpHeaders;
    @BeforeAll
    public static void init(){
        om = new ObjectMapper();
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
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