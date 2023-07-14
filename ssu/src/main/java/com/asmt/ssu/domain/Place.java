package com.asmt.ssu.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Place {
    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private School school;
    private String placeName;

    @Column(columnDefinition = "text")
    private String placeAddress;

    @Column(columnDefinition = "text")
    private String placeLink;

    private Integer placeDistance;

    private float placeRating;
}
