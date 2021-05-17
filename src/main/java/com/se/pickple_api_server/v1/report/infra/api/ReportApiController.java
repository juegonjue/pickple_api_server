package com.se.pickple_api_server.v1.report.infra.api;

import com.se.pickple_api_server.v1.common.infra.dto.SuccessResponse;
import com.se.pickple_api_server.v1.report.application.dto.ReportCreateDto;
import com.se.pickple_api_server.v1.report.application.service.ReportCreateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Api(tags = "신고 관리")
public class ReportApiController {

    private final ReportCreateService reportCreateService;

    @ApiOperation(value = "신고하기")
    @PostMapping(path = "/report")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN')")
    @ResponseStatus(value = HttpStatus.CREATED)
    public SuccessResponse<Long> create(@RequestBody @Validated ReportCreateDto.Request request) {
        return new SuccessResponse(HttpStatus.CREATED.value(),"신고 성공", reportCreateService.create(request));
    }
}
