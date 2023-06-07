package backend.Comments;


import backend.Posts.Post;
import backend.Posts.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author Wyatt Duberstein and Asher Gust
 *
 */
@RestController
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    private final int FAIL = 0;
    private final int SUCCESS = 1;

    /**
     * Fetches a complete list of comments with all of their attrubutes
     * @return list of comments
     */
    @GetMapping(path = "/comments/get")
    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    /**
     * Fetches a comment by id
     * @param id id of comment
     * @return comment object
     */
    @GetMapping(path = "/comments/get/id/{id}")
    public Comment getCommentById(@PathVariable int id){
        return commentRepository.findById(id);
    }

    /**
     * Create a commend
     * @param comment comment to be inserted into database
     * @return success/fail message
     */
    @PostMapping(path = "/comments/post")
    public Comment createComment(@RequestBody Comment comment){
        if (comment == null)
            return null;
        commentRepository.save(comment);
        return comment;
    }

    /**
     * Updates a comment
     * @param id id of comment
     * @param request newly created comment object to be inserted into database
     * @return newly inserted comment object
     */
    @PutMapping(path = "/comments/put/{id}")
    public Comment updateComment(@PathVariable int id, @RequestBody Comment request){
        Comment comment = commentRepository.findById(id);
        if(comment == null)
            return null;
        commentRepository.save(request);
        return commentRepository.findById(id);
    }

    /**
     * Assigns a comment to a post by id
     * @param postsId id of post
     * @param commentsId id of comment
     * @return success/fail message
     */
    @PutMapping(path = "/posts/{postsId}/comments/{commentsId}")
    public int assignCommentToPost(@PathVariable int postsId, @PathVariable int commentsId){
        Post post = postRepository.findById(postsId);
        Comment comment = commentRepository.findById(commentsId);
        if (post == null || comment == null) return FAIL;
        post.addComment(comment);
        postRepository.save(post);
        return SUCCESS;
    }

    /**
     * Deletes a comment
     * @param id id of comment
     * @return success/fail message
     */
    @DeleteMapping(path = "/comments/delete/{id}")
    public int deleteComment(@PathVariable int id){
        Comment comment = commentRepository.findById(id);
        if (comment == null)
            return FAIL;
        commentRepository.delete(comment);
        return SUCCESS;
    }
}
