package com.example.airplanned.api;

import com.example.airplanned.model.Comment;
import com.example.airplanned.model.Post;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * the purpose of this class is to implement retrofit calls for posts
 * @author Saiyara Iftekharuzzaman
 */
public interface PostApi {

    /**
     * gets all posts
     * @return
     * list of posts
     */
    @GET("/posts/get")
    Call<List<Post>> getPosts();

    /**
     * gets the posts from a specific user
     * @param emailid
     * email id
     * @return
     * a list of posts
     */
    @GET("/users/{emailid}/get/posts")
    Call<List<Post>> getPostbyUser(@Path("emailid")String emailid);

    /**
     * posts new post to everyone's page
     */
    @POST("/posts/post")
    Call<Integer> createPost(@Body Post post);

    /**
    *assign post by user
    */
    @POST("/posts/post/user/{userId}")
    Call<Integer> createPostUser(@Body Post post, @Path("userId") int userId);

    /**
     * update a post
     */
    @PUT("/posts/put/{id}")
    Call<Post> updatePost(@Path("id")int id, @Body Post post);

    /**
     * deletes post
     */
    @DELETE("/posts/delete/{id}")
    Call<Integer> deletePost(@Path("id")int id);

    /////////////////////////////Comments///////////////////////////////////

    /**
     * comments get assigned to post
     */
    @PUT("/posts/{postsId}/comments/{commentsId}")
    Call<Integer> assignCommentToPost(@Path("postsId")int postId,@Path("commentsId") int commentsId);

    /**
     * adds post to database
     * @param comment
     * @return
     */
    @POST("/comments/post")
    Call<Comment> createComment(@Body Comment comment);






    //update like count
    //@PUT("")

    //update comments
    //@PUT("")
}
