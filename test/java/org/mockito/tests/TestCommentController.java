package org.mockito.tests;

import static org.junit.Assert.assertEquals;
import backend.Comments.Comment;
import backend.Comments.CommentController;
import backend.Comments.CommentRepository;
import backend.Users.User;
import backend.Users.UserType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * @author asher
 */
public class TestCommentController {

    @InjectMocks
    CommentController commentController;

    @Mock
    CommentRepository commentRepository;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCommentById(){
        User user = new User("Bob", "bob@gmail.com", "Password@123", UserType.BASIC);
        when(commentRepository.findById(1)).thenReturn(new Comment("Message", user));

        Comment comment = commentController.getCommentById(1);

        //Check Comment Information
        assertEquals("Message", comment.getMessage());
        assertEquals(user.getEmailId(), comment.getEmailId());
    }

    @Test
    public void getAllCommentsTest(){
        List<Comment> list = new ArrayList<>();
        User user = new User("Bob", "bob@gmail.com", "Password@123", UserType.BASIC);
        Comment comment1 = new Comment("Message", user);
        comment1.setId(1);
        Comment comment2 = new Comment("Message", user);
        comment2.setId(2);
        Comment comment3 = new Comment("Message", user);
        comment3.setId(3);

        list.add(comment1);
        list.add(comment2);
        list.add(comment3);

        when(commentRepository.findAll()).thenReturn(list);

        List<Comment> commentList = commentController.getAllComments();

        assertEquals(3, commentList.size());
        assertEquals(1, commentList.get(0).getId());
        assertEquals(2, commentList.get(1).getId());
        assertEquals(3, commentList.get(2).getId());
    }
}
