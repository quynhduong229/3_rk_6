package com.example.airplanned.adapter;

import static com.example.airplanned.api.ApiClientFactory.GetPostApi;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.airplanned.R;
import com.example.airplanned.activity.ArchivedPostPageActivity;
import com.example.airplanned.model.Comment;
import com.example.airplanned.model.GlobalVariables;
import com.example.airplanned.model.Post;
import com.example.airplanned.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The purpose of this class is to allow the post objects to connect to the layouts
 * @author Saiyara Iftekharuzzaman
 */
public class PostAdapter extends BaseAdapter {

    Context pContext;
    LayoutInflater inflater;
    List<Post> postList;
    ArrayList<Post> arrayPostList;
    int i;

    /**
     * Post adapter constructor
     * @param context
     * curret context
     * @param postList
     * list of posts to display
     */
    public PostAdapter(Context context, List<Post> postList,int i) {
        pContext = context;
        this.postList = postList;
        inflater = LayoutInflater.from(pContext);
        this.arrayPostList = new ArrayList<>();
        this.arrayPostList.addAll(postList);
        this.i=i;

    }

    /**
     * defines xml variables for post adapter
     */
    public class ViewHolder{
        //textViews
        TextView pTitleTv, pDateTv, pDescriptionTv, pPublisherTv, pCommentsTv, pLikeCounterTv;

        //images
        ImageView pImage, pLiked;

        //edit text
        EditText pcInputEt;

        //button
        Button pCommentBtn, pDeleteBtn;

    }

    /**
     * gets the size of the post list
     * @return
     * size
     */
    @Override
    public int getCount() {
        return postList.size();
    }

    /**
     * gets the specific post from index
     * @param i
     * index
     * @return
     * post object
     */
    @Override
    public Object getItem(int i) {
        return postList.get(i);
    }

    /**
     * this method returns the item id by index
     * @param i
     * index
     * @return
     * id
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * this method returns the view
     * and sets the view if the view is empty
     * @param position
     * post row's position
     * @param view
     * post view
     * @param parent
     * parent
     * @return
     * view
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;


        if (view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.post, null);

            //locate the views in post.xml
            //textview
            holder.pPublisherTv = view.findViewById(R.id.post_publisher);
            holder.pLikeCounterTv = view.findViewById(R.id.tv_pg_likeCounter);
            holder.pTitleTv = view.findViewById(R.id.post_title);
            holder.pDescriptionTv = view.findViewById(R.id.post_description);
            holder.pDateTv = view.findViewById(R.id.post_date);




            //comment box
            holder.pCommentsTv = view.findViewById(R.id.post_comments_box);

            //images
            //holder.pImage = (ImageView) view.findViewById(R.id.post_image);
            holder.pLiked = (ImageView) view.findViewById(R.id.btn_pg_likeImage);
            //editText
            holder.pcInputEt =(EditText) view.findViewById(R.id.ed_pg_commentInput);
            //button
            holder.pCommentBtn =(Button) view.findViewById(R.id.btn_pg_commentAdd);
            holder.pDeleteBtn = view.findViewById(R.id.btn_pg_delete);

            //allows users to delete only if it is their own post
            if(i == 0) {
                holder.pDeleteBtn.setVisibility(View.GONE);
            }
            else if(i == 1) {
                holder.pDeleteBtn.setVisibility(View.VISIBLE);
                holder.pcInputEt.setVisibility(View.GONE);
                holder.pCommentBtn.setVisibility(View.GONE);

                /**
                 * delete button functionality
                 */
                holder.pDeleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetPostApi().deletePost(postList.get(position).getPostId()).enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                postList.remove(postList.get(position));
                                Toast.makeText(pContext.getApplicationContext(), " successfully deleted", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Toast.makeText(pContext.getApplicationContext(), " post delete failure", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                });
            }


            /**
             *  if someone likes the picture it should change the image to a liked and
             *  update like counter
             */
            holder.pLiked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(pContext.getApplicationContext(), "liked post", Toast.LENGTH_SHORT).show();
                    holder.pLiked.setImageResource(R.drawable.heartfill);

                    //update like counter
                    int cLike = postList.get(position).getLikesCounter();
                    postList.get(position).setLikesCounter(cLike+1);
                    holder.pLikeCounterTv.setText(String.valueOf(postList.get(position).getLikesCounter()));

                    //post call to update the post.
                }
            });

            //if this is clicked it will check if the pcInputEt is empty and update the
            //comment box display
            /**
             * comments
             */
            holder.pCommentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GlobalVariables globalVariables = (GlobalVariables) pContext;

                    //gets comment
                    String commentMessage = holder.pcInputEt.getText().toString();
                    //gets user
                    User a = globalVariables.getCurrent();

                    //makes a new comment
                    Comment newComment = new Comment(commentMessage,a.getEmailId());

                    postList.get(position).addComments(newComment);

                    //Post UD = postList.get(position); --> postUpdate method


                   // ctd(newComment);
//                    GetPostApi().createComment(newComment).enqueue(new Callback<Comment>() {
//                        @Override
//                        public void onResponse(Call<Comment> call, Response<Comment> response) {
//                            System.out.print(response.body());
//                            Toast.makeText(pContext.getApplicationContext(), "commented", Toast.LENGTH_SHORT).show();
//                            newComment.setId(response.body().getId());
//                            globalVariables.setNewestComment(response.body());
//
//                            ctp(position,globalVariables.getNewestComment());
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<Comment> call, Throwable t) {
//                            Toast.makeText(pContext.getApplicationContext(), "failure", Toast.LENGTH_SHORT).show();
//                        }
//                    });



                    //postUpdate(position,UD);

                    //updates comments display
                   // holder.pCommentsTv.setText(postList.get(position).getComments().toString());
                    holder.pCommentsTv.setText(Post.printableComments(postList.get(position).getComments()));
                    holder.pcInputEt.setText("");

                }
            });

            view.setTag(holder);

        }
        else{
            holder = (ViewHolder)view.getTag();

        }

        //set the results into text views
        holder.pTitleTv.setText(postList.get(position).getPostTitle());
        holder.pDateTv.setText(postList.get(position).getPostDate());
        holder.pLikeCounterTv.setText(String.valueOf(postList.get(position).getLikesCounter()));
        holder.pDescriptionTv.setText(postList.get(position).getDescription());
        if(postList.get(position).getPublisher()!=null) {
            holder.pPublisherTv.setText(postList.get(position).getPublisher());
        }
        holder.pLiked.setImageResource(R.drawable.heart);

//        if(postList.get(position).getComments()!= null) {
//            String display = "";
//            for (int i = 0; i < postList.get(position).getComments().size(); i++) {
//                display += postList.get(position).getComments().get(i).printableComment() + "\n";
//            }
//            System.out.print(display);
//            holder.pCommentsTv.setText(display);
//        }

        //holder.pCommentsTv.setText(postList.get(position).getComments().toString());
        holder.pCommentsTv.setText(Post.printableComments(postList.get(position).getComments()));







        //click whole post
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //from here is where you do the click thing
                Toast.makeText(pContext.getApplicationContext(), postList.get(position).getPostTitle(),Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    /**
     * this method filters the search from the user input
     * @param charText
     * user input character
     */
    public void pfilter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        postList.clear();
        if(charText.length()==0) {
            postList.addAll(arrayPostList);
        }

        else{
            for (Post post: arrayPostList){
                if ((post.getPostTitle().toLowerCase(Locale.getDefault()).contains(charText))){
                    postList.add(post);
                }
            }
        }

        notifyDataSetChanged();
    }

    /**
     * sets comment to post
     * @param position
     * @param newComment
     */
    public void ctp(int position, Comment newComment){
        int check = newComment.getId();
        GetPostApi().assignCommentToPost(postList.get(position).getPostId(), check).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                System.out.print(response.body());
                if(response.body() == null){
                    Toast.makeText(pContext.getApplicationContext(), "null", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(pContext.getApplicationContext(), "failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * comment to whole database in general
     * @param newComment
     */
    public void ctd(Comment newComment){
        //posts comment to database
        GetPostApi().createComment(newComment).enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                Toast.makeText(pContext.getApplicationContext(), "commented", Toast.LENGTH_SHORT).show();
                newComment.setId(response.body().getId());

            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Toast.makeText(pContext.getApplicationContext(), "failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * update post
     */
    public void postUpdate(int position,Post UD){
        int a = postList.get(position).getPostId();
        Post test = postList.get(position);
        GetPostApi().updatePost(postList.get(position).getPostId(),UD).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                System.out.print(response.body());
                Toast.makeText(pContext.getApplicationContext(), "update", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                System.out.print(t.getMessage());
                Toast.makeText(pContext.getApplicationContext(), " post update failure", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
