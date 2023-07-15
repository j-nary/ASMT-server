package com.asmt.ssu.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Hit {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "menu_id")
    private Menu menu;

    private LocalDateTime hitTime;

    @Builder
    public Hit(Menu menu, LocalDateTime hitTime) {
        this.menu = menu;
        this.hitTime = hitTime;
    }

    public Hit() {
    }
}
