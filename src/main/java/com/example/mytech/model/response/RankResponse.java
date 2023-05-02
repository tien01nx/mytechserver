package com.example.mytech.model.response;

import com.example.mytech.entity.Rank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RankResponse {
    private UserRankResponse user;
    private Rank rank;
}
