package com.example.wangyiyun.dao;

import com.example.wangyiyun.model.Comment;

import java.util.List;

//@Mapper
public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    void insertBatch(List<Comment> comments);
}