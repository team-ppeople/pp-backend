package com.pp.api.service;

import com.pp.api.repository.NoticeRepository;
import com.pp.api.service.command.FindNoticesByNoOffsetQuery;
import com.pp.api.service.domain.NoticeOfList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public List<NoticeOfList> findNotices(@Valid FindNoticesByNoOffsetQuery query) {
        return noticeRepository.find(
                        query.lastId(),
                        query.limit()
                )
                .stream()
                .map(notice ->
                        new NoticeOfList(
                                notice.getId(),
                                notice.getTitle(),
                                notice.getContent(),
                                notice.getCreatedDate(),
                                notice.getUpdatedDate()
                        )
                )
                .toList();
    }

}
