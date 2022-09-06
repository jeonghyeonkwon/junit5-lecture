package site.metacoding.junitproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.metacoding.junitproject.dto.response.BookResponse;
import site.metacoding.junitproject.dto.request.BookSaveRequest;
import site.metacoding.junitproject.dto.response.CMRespDto;
import site.metacoding.junitproject.service.BookService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookApiController {

    private final BookService bookService;

    // 1. 책 등록
    // key=value&key=value
    // {"key":value,"key":value}
    @PostMapping("/v1/book")
    public ResponseEntity saveBook(@RequestBody BookSaveRequest bookSaveRequest){
        BookResponse bookRespDto = bookService.책등록하기(bookSaveRequest);
        CMRespDto<?> cmRespDto = CMRespDto.builder()
                .code(1)
                .msg("글 저장 성공")
                .body(bookRespDto)
                .build();
        return new ResponseEntity<>(cmRespDto,HttpStatus.CREATED);
    }
    // 2 책 목록보기
    public ResponseEntity<?> getBookList(){
        return null;
    }
    // 3. 책 한건보기
    public ResponseEntity<?> getBookOne(){
        return null;
    }
    // 4. 책 삭제하기
    public ResponseEntity<?> deleteBook(){
        return null;
    }
    // 5. 책 수정하기
    public ResponseEntity<?> updateBook(){
        return null;
    }
}
