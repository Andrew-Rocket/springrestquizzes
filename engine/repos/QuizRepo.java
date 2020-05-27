package engine.repos;

import engine.entities.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface QuizRepo extends JpaRepository<Quiz, Integer> {
    @Query(
            value = "SELECT * FROM Quiz ORDER BY id",
            countQuery = "SELECT count(*) FROM Quiz",
            nativeQuery = true)
    Page<Quiz> findAllQuizzesWithPagination(Pageable pageable);
}
