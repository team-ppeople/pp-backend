package com.pp.api.repository;

import java.util.Set;

public interface BlockUserRepository {

    void block(Long blockerId, Long blockedId);

    void unblock(Long blockerId, Long blockedId);

    boolean isBlocked(Long blockerId, Long blockedId);

    Set<String> findBlockedIds(Long blockerId);
}
