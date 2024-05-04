package com.pp.api.service;

import com.pp.api.repository.NoticeRepository;
import com.pp.api.service.command.FindNoticesByNoOffsetQuery;
import com.pp.api.service.domain.NoticeOfList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public List<NoticeOfList> findNotices(FindNoticesByNoOffsetQuery query) {
        return noticeRepository.find(
                        query.getLastId(),
                        query.getLimit()
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
