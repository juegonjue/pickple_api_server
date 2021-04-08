package com.se.pickple_api_server.v1.board.domain.entity;

import com.se.pickple_api_server.v1.account.domain.entity.Account;
import com.se.pickple_api_server.v1.tag.domain.entity.Tag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DiscriminatorValue("R")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitmentBoard extends Board {

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer recNumber;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer payment_min;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer payment_max;

    @Column(nullable = false)
    private LocalDateTime workStartDate;

    @Column(nullable = false)
    private LocalDateTime workEndDate;

    @Column(nullable = false, columnDefinition = "datetime default now()")
    private LocalDateTime recStartDate;

    @Column(nullable = false)
    private LocalDateTime recEndDate;

    //
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Tag> tags = new ArrayList<>();

    @Builder
    public RecruitmentBoard(Long boardId, Account writerId, @Size(min = 2, max = 50) String title, @Size(min = 2, max = 2000) String text, BoardType type, Integer hit, Integer isDeleted, Integer recNumber, Integer payment_min, Integer payment_max, LocalDateTime workStartDate, LocalDateTime workEndDate, LocalDateTime recStartDate, LocalDateTime recEndDate, List<Tag> tags) {
        super(boardId, writerId, title, text, type, hit, isDeleted);
        this.recNumber = recNumber;
        this.payment_min = payment_min;
        this.payment_max = payment_max;
        this.workStartDate = workStartDate;
        this.workEndDate = workEndDate;
        this.recStartDate = recStartDate;
        this.recEndDate = recEndDate;
        this.tags = tags;
    }

}

