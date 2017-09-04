package com.example.demo.dao;

import com.example.demo.model.Music;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MusicMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Music record);

    void insertBatch(List<Music> musics);

    int insertSelective(Music record);

    Music selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Music record);

    int updateByPrimaryKey(Music record);
}