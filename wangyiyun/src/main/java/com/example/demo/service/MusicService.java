package com.example.demo.service;

import com.example.demo.model.Music;

import java.util.List;


public interface MusicService {
    int insertMusic(Music music);

    void insertBatch(List<Music> musics);
}
