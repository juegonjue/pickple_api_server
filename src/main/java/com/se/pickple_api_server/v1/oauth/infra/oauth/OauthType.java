package com.se.pickple_api_server.v1.oauth.infra.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OauthType {
    @JsonProperty("naver")
    NAVER,

    @JsonProperty("google")
    GOOGLE,
    FACEBOOK,

    @JsonProperty("kakao")
    KAKAO,

}
