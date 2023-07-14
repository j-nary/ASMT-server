package com.asmt.ssu.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter @Setter
public class SearchDTO {
    private String placeName;
    private String placeAddress;
    private float placeRating;
    private String placeLink;
    private int placeDistance;
    private School school;

    private String menuName;
    private int menuPrice;
    private String menuImg;

    public SearchDTO(String placeName, String placeAddress, float placeRating, String placeLink, int placeDistance, School school, String menuName, int menuPrice, String menuImg) {
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.placeRating = placeRating;
        this.placeLink = placeLink;
        this.placeDistance = placeDistance;
        this.school = school;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuImg = menuImg;
    }
}
