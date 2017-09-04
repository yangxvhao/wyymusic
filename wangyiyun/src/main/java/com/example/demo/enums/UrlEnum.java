package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum UrlEnum {
    // 主域名
    BASE_URL("http://music.163.com/"),

    // 匹配专辑URL
    ALBUM_URL("http://music\\.163\\.com/discover/toplist\\?id=\\d+"),

    // 匹配歌曲URL
    MUSIC_URL("http://music\\.163\\.com/song\\?id=\\d+"),

    //用户主页
    USER_HOME_URL("http://music.163.com/user/home?id="),

    // 初始地址, 热歌榜
    START_URL("http://music.163.com/discover/toplist?id=3778678"),
        ;

    @Getter
    private String value;
}
