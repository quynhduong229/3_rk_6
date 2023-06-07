package org.mockito.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import backend.Posts.Post;
import backend.Trips.Trip;
import backend.Users.UserController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import backend.Users.User;
import backend.Users.UserRepository;
import backend.Users.UserType;

/**
 * @author asher and Wyatt
 */
public class TestUserController {

    @InjectMocks
    UserController userController;

    @Mock
    UserRepository repo;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAccountByIdTest() {
        when(repo.findById(1)).thenReturn(new User("John Doe", "jDoe@gmail.com", "123456", UserType.BASIC));

        User user = userController.getUserById(1);

        assertEquals("John Doe", user.getName());
        assertEquals("123456", user.getPassword());
        assertEquals("jDoe@gmail.com", user.getEmailId());
    }

    @Test
    public void getUserByEmailTest() {
        when (repo.findUserByEmailId("jDoe@gmail.com")).thenReturn(new User("John Doe", "jDoe@gmail.com", "123456", UserType.BASIC));

        User user = userController.getUserByEmail("jDoe@gmail.com");

        assertEquals("John Doe", user.getName());
        assertEquals("123456", user.getPassword());
        assertEquals("jDoe@gmail.com", user.getEmailId());
    }

    @Test
    public void getAllAccountTest() {
        List<User> list = new ArrayList<>();
        User acctOne = new User("John Doe", "jDoe@gmail.com", "1234", UserType.BASIC);
        User acctTwo = new User("Alex", "alex@gmail.com", "abcd", UserType.BASIC);
        User acctThree = new User("Steve", "steve@gmail.com", "efgh", UserType.BASIC);

        list.add(acctOne);
        list.add(acctTwo);
        list.add(acctThree);

        when(repo.findAll()).thenReturn(list);

        List<User> acctList = userController.getAllUsers();

        assertEquals(3, acctList.size());
        verify(repo, times(1)).findAll();
    }

    /**
     * @author asher
     */
    @Test
    public void getPostsByUserTest(){
        when(repo.findUserByEmailId("jDoe@gmail.com")).thenReturn(new User("John Doe", "jDoe@gmail.com", "123456", UserType.BASIC));

        User user = userController.getUserByEmail("jDoe@gmail.com");
        Post post1 = new Post("Title", "Description", "10052021", user);
        Post post2 = new Post("Title2", "Description2", "10052021", user);
        Post post3 = new Post("Title3", "Description3", "10052021", user);

        user.addPost(post1);
        user.addPost(post2);
        user.addPost(post3);

        List<Post> list = userController.getPostsByUser("jDoe@gmail.com");

        assertEquals(3, list.size());
        assertEquals(post1, list.get(0));


    }

    /**
     * @author asher
     */
    @Test
    public void getTripsByUserTest(){
        when(repo.findById(1)).thenReturn(new User("John Doe", "jDoe@gmail.com", "123456", UserType.BASIC));

        User user = userController.getUserById(1);
        Trip trip1 = new Trip();
        Trip trip2 = new Trip();
        Trip trip3 = new Trip();

        trip2.setIsArchived(true);
        trip3.setIsArchived(true);

        user.addTrip(trip1);
        user.addTrip(trip2);
        user.addTrip(trip3);

        List<Trip> list = userController.getTripsByUser(1);

        assertEquals(3, list.size());
        assertEquals(trip1, list.get(0));


    }

    /**
     * @author asher
     */
    @Test
    public void getArchivedTripsByUserTest(){
        when(repo.findById(1)).thenReturn(new User("John Doe", "jDoe@gmail.com", "123456", UserType.BASIC));

        User user = userController.getUserById(1);
        Trip trip1 = new Trip();
        Trip trip2 = new Trip();
        Trip trip3 = new Trip();

        trip2.setIsArchived(true);
        trip3.setIsArchived(true);

        user.addTrip(trip1);
        user.addTrip(trip2);
        user.addTrip(trip3);

        List<Trip> list = userController.getArchivedTripsByUser(1);

        assertEquals(2, list.size());
        assertEquals(trip2, list.get(0));


    }

    /**
     * @author asher
     */
    @Test
    public void getUnarchivedTripsByUserTest(){
        when(repo.findById(1)).thenReturn(new User("John Doe", "jDoe@gmail.com", "123456", UserType.BASIC));

        User user = userController.getUserById(1);
        Trip trip1 = new Trip();
        Trip trip2 = new Trip();
        Trip trip3 = new Trip();

        trip2.setIsArchived(true);
        trip3.setIsArchived(true);

        user.addTrip(trip1);
        user.addTrip(trip2);
        user.addTrip(trip3);

        List<Trip> list = userController.getUnarchivedTripsByUser(1);

        assertEquals(1, list.size());
        assertEquals(trip1, list.get(0));


    }

}
