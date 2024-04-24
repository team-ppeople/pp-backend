package com.pp.api.controller;

import com.pp.api.controller.dto.FindUserProfileResponse;
import com.pp.api.controller.dto.RestResponseWrapper;
import com.pp.api.controller.dto.UpdateUserRequest;
import com.pp.api.facade.FindUserProfileFacade;
import com.pp.api.service.UsersService;
import com.pp.api.service.command.UpdateUserCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    private final FindUserProfileFacade findUserProfileFacade;

    @PreAuthorize(value = "isAuthenticated() && hasAuthority('SCOPE_user.read') && hasAuthority('SCOPE_user.write')")
    @PatchMapping(
            path = "/api/v1/users/{userId}",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> update(
            @PathVariable(name = "userId") Long userId,
            @RequestBody @Valid UpdateUserRequest request
    ) {
        UpdateUserCommand command = UpdateUserCommand.of(
                request.getNickname(),
                request.getProfileImageFileUploadId()
        );

        usersService.update(
                userId,
                command
        );

        return ResponseEntity.ok()
                .build();
    }

    @PreAuthorize(value = "isAuthenticated() && hasAuthority('SCOPE_user.read')")
    @GetMapping(
            path = "/api/v1/users/{userId}/profiles",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> findUserProfile(@PathVariable(name = "userId") Long userId) {
        FindUserProfileResponse response = findUserProfileFacade.findUserProfile(userId);

        return ResponseEntity.ok(RestResponseWrapper.from(response));
    }


}
