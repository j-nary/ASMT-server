package com.asmt.ssu.form;

import com.asmt.ssu.domain.School;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "조회 정보")
@Getter
public class SearchForm {

    @Schema(description = "가격 하한",example = "1000")
    @NotNull
    @PositiveOrZero
    private Integer minimumPrice;

    @Schema(description = "가격 상한",example = "90000")
    @NotNull
    @PositiveOrZero
    private Integer maximumPrice;

    @Schema(description = "검색 키워드 리스트, OR로 처리됩니다.", nullable = true, example = "[\"고구마피자\",\"치킨\",\"와사비\",\"가지덮밥\"]")
    private List<String> searchKeywordList = new ArrayList<>();

    @Schema(description = "정렬 방법", example = "lowPrice", allowableValues = {"lowPrice", "highPrice", "distance"})
    @NotNull
    private String sortMethod;

    @Schema(description = "0원 메뉴 반환 여부", example = "true", allowableValues = {"true", "false"})
    @NotNull
    private boolean showZeroPriceItems;

    @Schema(description = "학교", example = "ssu")
    @NotNull
    private School school;

    @NotNull
    @Schema(description = "유저 고유 키값", example = "SSU-AESPA")
    private String userId;


    @NotNull
    @Schema(description = "페이지, 양수여야 합니다.", example = "1")
    private Integer page;

    public SearchForm() {
    }

    public SearchForm(int minimumPrice, int maximumPrice, List<String> searchKeywordList, String sortMethod, boolean showZeroPriceItems, School school) {
        this.minimumPrice = minimumPrice;
        this.maximumPrice = maximumPrice;
        this.searchKeywordList = searchKeywordList;
        this.sortMethod = sortMethod;
        this.showZeroPriceItems = showZeroPriceItems;
        this.school = school;
    }

    public void processZeroPrice(){
        if (minimumPrice == 0 && showZeroPriceItems){
            this.minimumPrice=1;
        }
    }

    public String makeSortResult() {
        if (sortMethod.equals("lowPrice")){
            return "m.menuPrice asc";
        }
        else if (sortMethod.equals("highPrice")){
            return "m.menuPrice desc";
        }
        else if (sortMethod.equals("distance")){
            return "p.placeDistance asc";
        }
        return null;
    }


}
