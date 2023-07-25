package com.asmt.ssu.controller;

import com.asmt.ssu.domain.School;
import com.asmt.ssu.domain.SearchDTO;
import com.asmt.ssu.service.RankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "Rank", description = "랭킹 조회 / 반영 API")
public class RankController {

    private final RankService rankService;

    @GetMapping("/api/rank")
    @Operation(summary = "랭킹 조회", description = "조회할 수와 학교를 입력받아 일일 랭킹을 조회합니다.")
    public List<SearchDTO> getDailyRanking(@RequestParam int rankCount,
                                           @RequestParam School school){
        return rankService.getDailyRank(rankCount, school);
    }

    @PostMapping("/api/rank")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "랭킹 Hit", description = "메뉴 아이디를 입력받아 클릭했음을 반영합니다.")
    public ResponseEntity<String> addHitDailyRanking(@RequestParam @Positive @Valid Long menuId){
        rankService.addMenuToRank(menuId);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }
}
