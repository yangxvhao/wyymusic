package com.example.wangyiyun.service;

import com.example.wangyiyun.model.Music;

import java.util.List;


public interface MusicService {
    int insertMusic(Music music);

    void insertBatch(List<Music> musics);
}
