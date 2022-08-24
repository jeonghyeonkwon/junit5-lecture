package site.metacoding.junitproject.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import site.metacoding.junitproject.domain.Book;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/*
* DB와 관련된 컴포넌트만 메모리에 로딩 (즉 컨트롤러와 서비스는 메모리에 뜨지 않는다)
* */
@DataJpaTest
@Slf4j
/*
* 기본적으로 DataJpaTest는 내장 데이터 베이스를 사용하는데 그걸 다른 DB로 대체하고 싶으면 밑에 어노테이션 추가
* */
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    // DI
    @Autowired
    private BookRepository bookRepository;

    //@BeforeAll // 테스트 시작전 한번만 실행
    @BeforeEach // 각 테스트 시작전에 한번씩 실행
    public void init(){
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
    /*
    * beforeEach + 메소드1 이런식으로 트랜잭션이 묶인다.
    * */

    @AfterEach
    public void delete(){
//        bookRepository.deleteAll();
    }
    //1 책 등록
    @Test
    public void 책등록_test(){
        // given (데이터 준비)
        String title = "junit5";
        String author = "메타 코딩";

        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        // when (테스트 실행)

        Book savedBook = bookRepository.save(book);

        // then (검증)

        assertEquals(title,savedBook.getTitle());
        assertEquals(author,savedBook.getAuthor());

        log.info("{}",savedBook);
        //트랜잭션 종료(저장된 데이터를 초기화함)
    }
    //2 책 목록보기
    @Test
    public void 책목록보기_test(){


        //given
        String title = "junit5";
        String author = "메타 코딩";

        //when
        List<Book> books  = bookRepository.findAll();

        //then
        assertEquals(title,books.get(0).getTitle());
        assertEquals(author,books.get(0).getAuthor());
        log.info("{}",books.size());

    }
    //3 책 한건보기
    /*
    * id를 찾는 모든 것이 어노테이션 붙이기
    * */
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책한건보기_test(){
        //given
        String title = "junit5";
        String author = "메타 코딩";



        //when
        Book targetBook = bookRepository.findById(1L).get();

        //then

        assertEquals(title,targetBook.getTitle());
        assertEquals(author,targetBook.getAuthor());
    }
    //4 책 삭제
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책삭제_test(){
        /*
        * 단위 테스트는 되지만 전체 테스트 하면 에러가 뜸
        *
        * 1. 테스트 메서드 3개가 있다.(순서 보장이 안된다) - Order() 어노테이션
        * (1) 메서드1
        * (2) 메서드2
        * (3) 메서드3
        *
        * 2. 테스트 메서드 하나 실행 후 종료되면 데이터가 초기화된다 - Transactional()어노테이션
        * (1) 1건
        * (2) 2건
        * -> 트랜젝션 종료 -> 데이터 초기화
        * *** primary key auto_increment 값이 초기화가 안됨
        *
        * 그래서  @Sql("classpath:db/tableInit.sql")
        * */
        //given
        Long id = 1L;

        //when
        bookRepository.deleteById(id);

        //then
        Optional<Book> bookPs = bookRepository.findById(id);
        assertFalse(bookPs.isPresent());
    }
    //5 책 수정

    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책수정_test(){
        //given

        Long id = 1L;
        String title = "junit5";
        String author = "메타코딩";
        Book book = new Book(id,title,author);
        //when
        Book save = bookRepository.save(book);

        //then
        assertEquals(id,save.getId());
        assertEquals(title,save.getTitle());
        assertEquals(author,save.getAuthor());


    }
}