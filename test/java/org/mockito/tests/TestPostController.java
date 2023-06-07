package org.mockito.tests;

import backend.Posts.Post;
import backend.Posts.PostController;
import backend.Posts.PostRepository;
import backend.Users.User;
import backend.Users.UserType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author asher
 */
public class TestPostController {

    @InjectMocks
    PostController postController;

    @Mock
    PostRepository postRepository;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getPostByIdTest() {
        User user = new User("Bob", "bob@gmail.com", "Password@123", UserType.BASIC);
        new Post("Title", "Description", "11/1/2021", user);
        when(postRepository.findById(1)).thenReturn( new Post("Title", "Description", "11/1/2021", user));

        Post post = postRepository.findById(1);

        //Check post info
        assertEquals("Title", post.getTitle());
        assertEquals("Description", post.getDescription());
        assertEquals("11/1/2021", post.getDate());
        assertEquals(user.getEmailId(), post.getEmailId());

    }

    @Test
    public void getAllPostsTest(){
        List<Post> list = new ArrayList<>();
        User user = new User("Bob", "bob@gmail.com", "Password@123", UserType.BASIC);
        Post post1 =  new Post("Title", "Description", "11/1/2021", user);
        post1.setId(1);
        Post post2 =  new Post("Title", "Description", "11/1/2021", user);
        post2.setId(2);
        Post post3 =  new Post("Title", "Description", "11/1/2021", user);
        post3.setId(3);

        list.add(post1);
        list.add(post2);
        list.add(post3);

        when(postRepository.findAll()).thenReturn(list);

        List<Post> postsList = postController.getAllPosts();

        assertEquals(3, postsList.size());
        assertEquals(1, postsList.get(0).getId());
        assertEquals(2, postsList.get(1).getId());
        assertEquals(3, postsList.get(2).getId());
    }

    @Test
    public void getAllPostsByDateTest(){
        List<Post> list = new ArrayList<>();
        User user = new User("Bob", "bob@gmail.com", "Password@123", UserType.BASIC);
        Post post1 =  new Post("Title", "Description", "11/1/2021", user);
        post1.setId(1);
        Post post2 =  new Post("Title", "Description", "11/1/2021", user);
        post2.setId(2);
        Post post3 =  new Post("Title", "Description", "11/1/2021", user);
        post3.setId(3);
        Post post4 =  new Post("Title", "Description", "11/2/2021", user);
        post4.setId(4);
        Post post5 =  new Post("Title", "Description", "11/2/2021", user);
        post5.setId(5);
        Post post6 =  new Post("Title", "Description", "11/2/2021", user);
        post6.setId(6);

        list.add(post1);
        list.add(post2);
        list.add(post3);
        list.add(post4);
        list.add(post5);
        list.add(post6);

        List<Post> list2 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getDate().equals("11/2/2021")) list2.add(list.get(i));
        }

        when(postRepository.findByDateContaining("11/2/2021")).thenReturn(list2);

        List<Post> postsList = postController.getAllPostsByDate("11/2/2021");

        assertEquals(3, postsList.size());
        assertEquals(4, postsList.get(0).getId());
        assertEquals(5, postsList.get(1).getId());
        assertEquals(6, postsList.get(2).getId());
    }
}
