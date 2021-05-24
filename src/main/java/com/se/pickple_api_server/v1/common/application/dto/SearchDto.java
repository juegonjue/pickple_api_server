package com.se.pickple_api_server.v1.common.application.dto;

import com.se.pickple_api_server.v1.account.domain.type.AccountType;
import com.se.pickple_api_server.v1.apply.domain.type.ReviewState;
import com.se.pickple_api_server.v1.common.infra.dto.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

public class SearchDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("검색")
    static public class Request {

        @ApiModelProperty(notes = "검색할 키워드")
        private String keyword;

        @NotNull
        private PageRequest pageRequest;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static public class Account extends Request {
        @ApiModelProperty(notes = "멤버 타입으로 찾기")
        private AccountType type;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static public class Apply extends Request {

        @ApiModelProperty(notes = "후기 상태로 찾기")
        private ReviewState reviewState;

        @ApiModelProperty(notes = "계약 상태로 찾기")
        private Integer isContracted;

    }
}
