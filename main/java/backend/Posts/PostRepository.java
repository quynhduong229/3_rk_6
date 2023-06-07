package backend.Posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findById(int id);
    List<Post> findByDateContaining(String date);

    @Transactional
    void deleteById(int id);
    void delete(Post post);
}
