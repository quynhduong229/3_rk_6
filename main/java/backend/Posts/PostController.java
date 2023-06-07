package backend.Posts;

import backend.Users.User;
import backend.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author Wyatt Duberstein and Asher Gust
 *
 */
@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    private final int FAIL = 0;
    private final int SUCCESS = 1;

    /**
     * Fetches a list of all posts and their attributes
     * @return list of all posts
     */
    @GetMapping(path = "/posts/get")
    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    /**
     * Fetches a post by id
     * @param id id of post
     * @return post object
     */
    @GetMapping(path = "/posts/get/id/{id}")
    public Post getPostById(@PathVariable int id){
        return postRepository.findById(id);
    }

    /**
     * Fetches a post by date
     * @param date date of post
     * @return post object
     */
    @GetMapping(path = "/posts/get/date/{date}")
    public List<Post> getAllPostsByDate(@PathVariable String date) {
        return postRepository.findByDateContaining(date);
    }

    /**
     * Creates a new post
     * @param post post to be inserted into database
     * @return success/fail message
     */
    @PostMapping(path = "/posts/post")
    public int createPost(@RequestBody Post post){
        if (post == null)
            return FAIL;
        postRepository.save(post);
        return SUCCESS;
    }

    /**
     * Creates a new post
     * @param post post to be inserted into database
     * @return success/fail message
     */
    @PostMapping(path = "/posts/post/user/{userId}")
    public int createPostUser(@RequestBody Post post, @PathVariable int userId){
        if (post == null)
            return FAIL;
        User user = userRepository.findById(userId);
        if (user == null)
            return FAIL;
        user.addPost(post);
        post.setEmailId(user);
        postRepository.save(post);
        userRepository.save(user);
        return SUCCESS;
    }

    /**
     * Updates an existing post
     * @param id id of post
     * @param request new post object
     * @return newly inserted post object
     */
    @PutMapping(path = "/posts/put/{id}")
    public Post updatePost(@PathVariable int id, @RequestBody Post request){
        Post post = postRepository.findById(id);
        if(post == null)
            return null;
        postRepository.save(request);
        return postRepository.findById(id);
    }

    /**
     * Deletes a post
     * @param id id of post
     * @return success/fail message
     */
    @DeleteMapping(path = "/posts/delete/{id}")
    public int deletePost(@PathVariable int id){
        Post post = postRepository.findById(id);
        if (post == null)
            return FAIL;
        postRepository.delete(post);
        return SUCCESS;
    }
}
