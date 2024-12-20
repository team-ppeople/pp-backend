package com.pp.api.service;

import com.pp.api.exception.CanNotBlockMySelfException;
import com.pp.api.exception.UserNotExistsException;
import com.pp.api.repository.BlockUserRepository;
import com.pp.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.pp.api.util.JwtAuthenticationUtil.getAuthenticatedUserId;
import static com.pp.api.util.JwtAuthenticationUtil.isAuthenticatedUser;

@Component
@RequiredArgsConstructor
public class BlockUserService {

    private final UserRepository userRepository;

    private final BlockUserRepository blockUserRepository;

    public void block(Long blockedId) {
        if (isAuthenticatedUser(blockedId)) {
            throw new CanNotBlockMySelfException();
        }

        if (!userRepository.existsById(blockedId)) {
            throw new UserNotExistsException();
        }

        blockUserRepository.block(
                getAuthenticatedUserId(),
                blockedId
        );
    }

    public void unblock(Long blockedId) {
        if (!userRepository.existsById(blockedId)) {
            throw new UserNotExistsException();
        }

        blockUserRepository.unblock(
                getAuthenticatedUserId(),
                blockedId
        );
    }

    public List<Long> findBlockedIds(Long userId) {
        return blockUserRepository.findBlockedIds(userId)
                .stream()
                .map(Long::valueOf)
                .toList();
    }

    public boolean isBlockedUser(Long userId) {
        if (isAuthenticatedUser(userId)) {
            return false;
        }

        return blockUserRepository.isBlocked(
                getAuthenticatedUserId(),
                userId
        );
    }

    public void deleteBlockedUser(Long userId) {
        blockUserRepository.deleteBlockList(userId);
    }

}
