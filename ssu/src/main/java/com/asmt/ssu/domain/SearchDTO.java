package com.asmt.ssu.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter @Setter
public class SearchDTO {
    @Schema(description = "가게 이름", example = "존맛집")
    private String placeName;
    @Schema(description = "가게 주소", example = "서울 동작구 사당로 6-1")
    private String placeAddress;
    @Schema(description = "가게 평점", nullable = true, example = "4.34")
    private float placeRating;
    @Schema(description = "가게 네이버플레이스 주소", example = "https://map.naver.com/v5/entry/place/35688740")
    private String placeLink;
    @Schema(description = "학교부터 거리", example = "352")
    private int placeDistance;

    @Schema(description = "소속 학교", example = "ssu")
    private School school;


    @Schema(description = "메뉴 아이디", example = "84118")
    private Long menuId;
    @Schema(description = "메뉴 이름", example = "제계치2")
    private String menuName;
    @Schema(description = "메뉴 가격", example = "150000")
    private int menuPrice;

    @Schema(description = "메뉴 이미지", nullable = true, example = "https://search.pstatic.net/common/?autoRotate=true&quality=95&type=f320_320&src=https://ldb-phinf.pstatic.net/20200116_202/1579159139861EGFkk_JPEG/570619_286_20170526164736.jpg")
    private String menuImg;

    @Schema(description = "북마크 여부", nullable = true, example = "true")

    private boolean isBookmarked;

    public SearchDTO(String placeName, String placeAddress, float placeRating, String placeLink, int placeDistance, School school, Long menuId, String menuName, int menuPrice, String menuImg, boolean isBookmarked) {
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.placeRating = placeRating;
        this.placeLink = placeLink;
        this.placeDistance = placeDistance;
        this.school = school;
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuImg = menuImg;
        this.isBookmarked= isBookmarked;

    }

    public SearchDTO(Menu menu){
        this.placeName = menu.getPlace().getPlaceName();
        this.placeAddress = menu.getPlace().getPlaceAddress();
        this.placeRating = menu.getPlace().getPlaceRating();
        this.placeLink = menu.getPlace().getPlaceLink();
        this.placeDistance = menu.getPlace().getPlaceDistance();
        this.school = menu.getPlace().getSchool();
        this.menuId = menu.getId();
        this.menuName = menu.getMenuName();
        this.menuPrice = menu.getMenuPrice();
        this.menuImg = menu.getMenuImg();
    }

    public SearchDTO(Menu menu, boolean isBookmarked){
        this.placeName = menu.getPlace().getPlaceName();
        this.placeAddress = menu.getPlace().getPlaceAddress();
        this.placeRating = menu.getPlace().getPlaceRating();
        this.placeLink = menu.getPlace().getPlaceLink();
        this.placeDistance = menu.getPlace().getPlaceDistance();
        this.school = menu.getPlace().getSchool();
        this.menuId = menu.getId();
        this.menuName = menu.getMenuName();
        this.menuPrice = menu.getMenuPrice();
        this.menuImg = menu.getMenuImg();
        this.isBookmarked = isBookmarked;
    }
}
