package site.metacoding.junitproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.metacoding.junitproject.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
