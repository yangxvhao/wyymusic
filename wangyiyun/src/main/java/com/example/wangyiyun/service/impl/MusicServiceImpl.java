package com.example.wangyiyun.service.impl;

import com.example.wangyiyun.dao.MusicMapper;
import com.example.wangyiyun.model.Music;
import com.example.wangyiyun.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicServiceImpl implements MusicService{

    @Autowired(required = false)
    MusicMapper musicMapper;

    public int insertMusic(Music music){return musicMapper.insert(music);}

    @Override
    public void insertBatch(List<Music> musics) {
        musicMapper.insertBatch(musics);
    }
}
