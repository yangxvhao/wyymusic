package com.example.demo.service.impl;

import com.example.demo.dao.CommentMapper;
import com.example.demo.model.Comment;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired(required = false)
    CommentMapper commentMapper;

    @Override
    public int insertComment(Comment comment){return commentMapper.insert(comment);}

    @Override
    public void insertBatch(List<Comment> comments) {
        commentMapper.insertBatch(comments);
    }

}
