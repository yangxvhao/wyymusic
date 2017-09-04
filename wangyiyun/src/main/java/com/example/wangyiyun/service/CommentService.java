package com.example.wangyiyun.service;

import com.example.wangyiyun.model.Comment;

import java.util.List;

public interface CommentService {


    int insertComment(Comment record);

    void insertBatch(List<Comment> comments);

}
