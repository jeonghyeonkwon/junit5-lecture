package site.metacoding.junitproject.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/*
* DB와 관련된 컴포넌트만 메모리에 로딩 (즉 컨트롤러와 서비스는 메모리에 뜨지 않는다)
* */
@DataJpaTest
@Slf4j
/*
* 기본적으로 DataJpaTest는 내장 데이터 베이스를 사용하는데 그걸 다른 DB로 대체하고 싶으면 밑에 어노테이션 추가
* */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    // DI
    @Autowired
    private BookRepository bookRepository;

    //1 책 등록
    @Test
    public void 책등록_test(){
        log.info("책 등록_test 실행");
    }
    //2 책 목록보기

    //3 책 한건보기

    //4 책 수정

    //5 책 삭제

}