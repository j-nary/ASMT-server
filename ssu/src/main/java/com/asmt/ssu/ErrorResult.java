package com.asmt.ssu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResult {
    @Schema(example = "BAD_REQUEST")
    private String errorCode;
    @Schema(example = "입력이 잘못되었습니다.[Field error in object 'searchForm' on field 'minimumPrice': rejected value [null]; codes [NotNull.searchForm.minimumPrice,NotNull.minimumPrice,NotNull.java.lang.Integer,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [searchForm.minimumPrice,minimumPrice]; arguments []; default message [minimumPrice]]; default message [널이어서는 안됩니다]]")
    private String errorMessage;
}
