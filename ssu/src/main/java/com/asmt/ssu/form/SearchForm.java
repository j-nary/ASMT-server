package com.asmt.ssu.form;

import com.asmt.ssu.domain.School;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class SearchForm {

    private int minimumPrice;
    private int maximumPrice;
    private List<String> searchKeywordList = new ArrayList<>();
    private String sortMethod;
    private boolean showZeroPriceItems;
    private School school;

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
}
