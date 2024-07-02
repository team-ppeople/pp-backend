package com.pp.api.service;

import com.pp.api.entity.User;
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
            return;
        }

        User user = userRepository.findById(blockedId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        blockUserRepository.block(
                getAuthenticatedUserId(),
                user.getId()
        );
    }

    public void unblock(Long blockedId) {
        if (isAuthenticatedUser(blockedId)) {
            return;
        }

        User user = userRepository.findById(blockedId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        blockUserRepository.unblock(
                getAuthenticatedUserId(),
                user.getId()
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

}
