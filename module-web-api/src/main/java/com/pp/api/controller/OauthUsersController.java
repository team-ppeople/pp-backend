package com.pp.api.controller;

import com.pp.api.controller.dto.IsRegisteredOauthUserResponse;
import com.pp.api.controller.validator.AllowedOauthUserClient;
import com.pp.api.service.OauthUsersService;
import com.pp.api.service.command.IsRegisteredOauthUserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
public class OauthUsersController {

    private final OauthUsersService oauthUsersService;

    @PostMapping(
            path = "/api/v1/oauth2/{client}/users/registered",
            consumes = APPLICATION_FORM_URLENCODED_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> isRegistered(
            @PathVariable(name = "client") @AllowedOauthUserClient String client,
            @RequestParam(name = "idToken") String idToken
    ) {
        IsRegisteredOauthUserQuery query = IsRegisteredOauthUserQuery.of(
                client,
                idToken
        );

        boolean isRegistered = oauthUsersService.isRegistered(query);

        return ResponseEntity.ok(IsRegisteredOauthUserResponse.from(isRegistered));
    }


}
