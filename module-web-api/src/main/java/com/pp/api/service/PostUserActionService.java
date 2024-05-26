package com.pp.api.service;

import com.pp.api.repository.PostUserActionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostUserActionService {

    private final PostUserActionRepository postUserActionRepository;

    public void deleteUserPostThumbsUpByUserId(Long userId) {
        postUserActionRepository.deleteUserPostThumbsUpByUserId(userId);
    }

}
