package backend.Comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findById(int id);

    @Transactional
    void deleteById(int id);
    void delete(Comment comment);
}
