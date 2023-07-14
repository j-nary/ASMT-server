package com.asmt.ssu.domain;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Hit {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "menu_id")
    private Menu menu;

    private LocalDateTime hitTime;
}
