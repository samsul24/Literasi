package com.example.maskoki.rest.services;

import com.example.maskoki.models.Auth;
import com.example.maskoki.models.Comment;
import com.example.maskoki.models.Recipes;
import com.example.maskoki.models.User;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiServices {

    @FormUrlEncoded
    @POST("User/login")
    Call<User> loginRequest(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("users/register")
    Call<User> registerRequest(
            @Field("username") String username,
            @Field("name") String name,
            @Field("password") String password
    );

    @GET("recipes")
    Call<List<Recipes>> listRecipes();

    @GET("recipes/{recipeId}/comments")
    Call<List<Comment>> listComments(@Path("recipeId") int recipeId);

    @FormUrlEncoded
    @POST("recipes/{recipeId}/comments")
    Call<Comment> addComment(
            @Path("recipeId") int recipeId,
            @HeaderMap Map<String, String> token,
            @Field("comment") String comment
    );

    @FormUrlEncoded
    @PUT("recipes/{recipeId}/comments/{id}")
    Call<Comment> editComment(
            @Path("recipeId") int recipeId,
            @Path("id") int commentId,
            @HeaderMap Map<String, String> token,
            @Field("comment") String comment
    );

    @DELETE("comments/{id}")
    Call<Comment> deleteComment(
            @HeaderMap Map<String, String> token,
            @Path("id") int commentId
    );
}
