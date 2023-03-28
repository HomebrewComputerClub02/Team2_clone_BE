package com.homebrewtify.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlaylistCover{
    private String id;  //playlist id
    private String name;
    private String coverImgUrl;
}
