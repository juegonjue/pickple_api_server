package com.se.pickple_api_server.v1.account.infra.api;

import com.se.pickple_api_server.v1.account.domain.usecase.AccountCreateUseCase;
import com.se.pickple_api_server.v1.account.domain.usecase.AccountSignInUseCase;
import com.se.pickple_api_server.v1.account.infra.dto.AccountCreateDto;
import com.se.pickple_api_server.v1.account.infra.dto.AccountSignInDto;
import com.se.pickple_api_server.v1.common.infra.dto.SuccessResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Api(tags = "사용자 관리")
public class AccountApiController {

    private final AccountCreateUseCase accountCreateUseCase;
    private final AccountSignInUseCase accountSignInUseCase;


    @PostMapping(path="/signup")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "존재하지 않는 사용자"),
            @ApiResponse(code = 400, message = "비밀번호 불일치")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "회원 가입")
    public SuccessResponse<Long> signUp(@RequestBody @Validated AccountCreateDto.Request request, HttpServletRequest httpServletRequest) {
        return new SuccessResponse<>(HttpStatus.CREATED.value(), "회원가입에 성공했습니다.",
                accountCreateUseCase.signUp(request));
    }

    @PostMapping(path = "/signin")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "비밀번호 불일치")
    })
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "로그인")
    public SuccessResponse<AccountSignInDto.Response> singIn(@RequestBody @Validated AccountSignInDto.Request request) {
        return new SuccessResponse<>(HttpStatus.OK.value(), "성공적으로 로그인 되었습니다.",
                accountSignInUseCase.signIn(request.getId(), request.getPw()));
    }
}