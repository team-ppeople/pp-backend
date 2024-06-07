package com.pp.api.controller;

import com.pp.api.controller.dto.FindUserProfileResponse;
import com.pp.api.controller.dto.UpdateUserRequest;
import com.pp.api.facade.FindUserProfileFacade;
import com.pp.api.service.UserService;
import com.pp.api.service.command.UpdateUserCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.pp.api.controller.dto.RestResponseWrapper.empty;
import static com.pp.api.controller.dto.RestResponseWrapper.from;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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
        UpdateUserCommand command = new UpdateUserCommand(
                userId,
                request.nickname(),
                request.profileImageFileUploadId()
        );

        userService.update(command);

        return ok(empty());
    }

    @PreAuthorize(value = "isAuthenticated() && hasAuthority('SCOPE_user.read')")
    @GetMapping(
            path = "/api/v1/users/{userId}/profiles",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> findUserProfile(@PathVariable(name = "userId") Long userId) {
        FindUserProfileResponse response = findUserProfileFacade.findUserProfile(userId);

        return ok(from(response));
    }

    @PreAuthorize(value = "isAuthenticated() && hasAuthority('SCOPE_user.read') && hasAuthority('SCOPE_user.write')")
    @DeleteMapping(path = "/api/v1/users/{userId}")
    public ResponseEntity<?> withdraw(@PathVariable(name = "userId") Long userId) {
        userService.withdraw(userId);

        return ok(empty());
    }


}
