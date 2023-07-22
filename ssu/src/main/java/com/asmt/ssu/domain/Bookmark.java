package com.asmt.ssu.domain;


import lombok.Getter;

import javax.persistence.*;

@Entity  @Getter
public class Bookmark {

    @Id @GeneratedValue
    private Long id;

    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    public Bookmark() {
    }

    public Bookmark(String userId, Menu menu) {
        this.userId = userId;
        this.menu = menu;
    }
}
