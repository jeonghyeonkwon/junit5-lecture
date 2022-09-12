package site.metacoding.junitproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.metacoding.junitproject.domain.Book;
import site.metacoding.junitproject.dto.response.BookListResponse;
import site.metacoding.junitproject.dto.response.BookResponse;
import site.metacoding.junitproject.dto.request.BookSaveRequest;
import site.metacoding.junitproject.repository.BookRepository;
import site.metacoding.junitproject.util.MailSender;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final MailSender mailSender;
    // 1. 책 등록
    @Transactional
    public BookResponse 책등록하기(BookSaveRequest dto){
        Book bookPS = bookRepository.save(dto.toEntity());

        if(bookPS!=null){
            if(!mailSender.send()){
                throw new RuntimeException("메일이 전송되지 않았습니다");
            }
        }

        return bookPS.toDto();
    }
    // 2. 책 목록보기

    public BookListResponse 책목록보기(){
        List<BookResponse> dtos = bookRepository.findAll()
                .stream()
                .map(Book::toDto).collect(Collectors.toList());
        BookListResponse bookListResponse = BookListResponse.builder().bookList(dtos).build();
        return bookListResponse;
    }
    // 3. 책 책한건 보기

    public BookResponse 책한건보기(Long id){
        Optional<Book> bookOP = bookRepository.findById(id);
        if(bookOP.isPresent()){
            return bookOP.get().toDto();
        }else{
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다");
        }
    }

    // 4. 책 삭제

    @Transactional
    public void 책삭제하기(Long id){
        bookRepository.deleteById(id);
    }
    // 5. 책 수정

    @Transactional
    public BookResponse 책수정하기(Long id, BookSaveRequest dto){
        Optional<Book> bookOP = bookRepository.findById(id);
        if(bookOP.isPresent()){
            Book bookPS = bookOP.get();
            bookPS.update(dto.getTitle(),dto.getAuthor());
            return bookPS.toDto();
        }else{
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다");
        }
    }
}
