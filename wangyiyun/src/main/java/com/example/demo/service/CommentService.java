package com.example.demo.service;

import com.example.demo.model.Comment;

import java.util.List;

public interface CommentService {


    int insertComment(Comment record);

    void insertBatch(List<Comment> comments);

}
