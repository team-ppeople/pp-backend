package com.pp.api.controller;

import com.pp.api.controller.dto.UpdateUserRequest;
import com.pp.api.service.UsersService;
import com.pp.api.service.command.UpdateUserCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

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
        SecurityContextHolder.getContext().getAuthentication();
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


}
