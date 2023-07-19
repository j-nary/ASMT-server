package com.asmt.ssu.form;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Component
@Getter @Setter
public class BookmarkForm {

    @Schema(description = "유저를 구분할수 있는 String값",example = "abcdefghihihihihi")
    @NotBlank
    private String userId;


    @Schema(description = "북마크할 메뉴의 ID",example = "1020")
    @NotNull @PositiveOrZero
    private Long menuId;

    public BookmarkForm() {
    }

    public BookmarkForm(String userId, Long menuId) {
        this.userId = userId;
        this.menuId = menuId;
    }
}
