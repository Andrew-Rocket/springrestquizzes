package engine.repos;

import engine.entities.Completion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CompRepo extends JpaRepository<Completion, Integer> {
    @Query(
            value = "SELECT * FROM Completion c WHERE c.user_id = ?1 ORDER BY completed_at DESC",
            countQuery = "SELECT count(*) FROM Completion",
            nativeQuery = true)
    Page<Completion> findAllCompletionsWithPagination(int userId, Pageable pageable);
}

