package com.pp.api.unit.service;

import com.pp.api.repository.BlockUserRepository;
import com.pp.api.repository.UserRepository;
import com.pp.api.service.BlockUserService;
import com.pp.api.util.JwtAuthenticationUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.pp.api.util.JwtAuthenticationUtil.getAuthenticatedUserId;
import static org.mockito.Mockito.*;

@ExtendWith(value = MockitoExtension.class)
public class BlockUserServiceTest {

    private MockedStatic<JwtAuthenticationUtil> jwtAuthenticationUtil;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BlockUserRepository blockUserRepository;

    @InjectMocks
    private BlockUserService blockUserService;

    @BeforeEach
    void setUp() {
        jwtAuthenticationUtil = mockStatic(JwtAuthenticationUtil.class);
    }

    @AfterEach
    void tearDown() {
        jwtAuthenticationUtil.close();
    }

    @Test
    void 특정_유저를_차단한다() {
        Long blockerId = 1L;
        Long blockedId = 2L;

        when(userRepository.existsById(blockedId))
                .thenReturn(true);
        when(getAuthenticatedUserId())
                .thenReturn(blockerId);

        blockUserService.block(blockedId);

        verify(blockUserRepository).block(blockerId, blockedId);
    }

    @Test
    void 특정_유저를_차단_해제한다() {
        Long unblockerId = 1L;
        Long unblockedId = 2L;

        when(userRepository.existsById(unblockedId))
                .thenReturn(true);
        when(getAuthenticatedUserId())
                .thenReturn(unblockerId);

        blockUserService.unblock(unblockedId);

        verify(blockUserRepository).unblock(unblockerId, unblockedId);
    }
}
