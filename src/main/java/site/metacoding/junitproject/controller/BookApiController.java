package site.metacoding.junitproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import site.metacoding.junitproject.dto.response.BookListResponse;
import site.metacoding.junitproject.dto.response.BookResponse;
import site.metacoding.junitproject.dto.request.BookSaveRequest;
import site.metacoding.junitproject.dto.response.CMRespDto;
import site.metacoding.junitproject.service.BookService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class BookApiController {

    private final BookService bookService;

    // 1. 책 등록
    // key=value&key=value
    // {"key":value,"key":value}
    @PostMapping("/v1/book")
    public ResponseEntity saveBook(@RequestBody @Valid BookSaveRequest bookSaveRequest, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            Map<String,String> errorMap = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMap.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            log.error("{}",errorMap);
            throw new RuntimeException(errorMap.toString());
        }
        BookResponse bookRespDto = bookService.책등록하기(bookSaveRequest);

        CMRespDto<?> cmRespDto = CMRespDto.builder()
                .code(1)
                .msg("글 저장 성공")
                .body(bookRespDto)
                .build();
        return new ResponseEntity<>(cmRespDto,HttpStatus.CREATED);
    }
    // 2 책 목록보기
    @GetMapping("/api/v1/book")
    public ResponseEntity getBookList(){

        BookListResponse bookList = bookService.책목록보기();
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 목록보기 성공").body(bookList).build(),HttpStatus.OK);

    }
    // 3. 책 한건보기
    @GetMapping("/api/v1/book/{id}")
    public ResponseEntity<?> getBookOne(@PathVariable Long id) {
        BookResponse bookResponse = bookService.책한건보기(id);
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 한건보기 성공").body(bookResponse).build(),HttpStatus.OK);
    }
    // 4. 책 삭제하기
    @DeleteMapping("/api/v1/book/{id}")
    public ResponseEntity deleteBook(Long id){

        bookService.책삭제하기(id);
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 삭제하기 성공").body(null).build(),HttpStatus.NO_CONTENT);
    }
    // 5. 책 수정하기
    @PutMapping("/api/v1/book/{id}")
    public ResponseEntity updateBook(@PathVariable Long id, @RequestBody @Valid BookSaveRequest bookSaveRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();
            for(FieldError fe: bindingResult.getFieldErrors()){
                errorMap.put(fe.getField(),fe.getDefaultMessage());
            }
            throw new RuntimeException(errorMap.toString());
        }

        BookResponse bookResponse = bookService.책수정하기(id, bookSaveRequest);
        return new ResponseEntity(CMRespDto.builder().code(1).msg("글 수정하기 성공").body(bookResponse).build(),HttpStatus.OK);
    }
}
