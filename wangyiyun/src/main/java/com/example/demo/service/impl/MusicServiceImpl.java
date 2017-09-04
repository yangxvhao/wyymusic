package com.example.demo.service.impl;

import com.example.demo.dao.MusicMapper;
import com.example.demo.model.Music;
import com.example.demo.service.MusicService;
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
