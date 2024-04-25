package com.pp.api.facade;

import com.pp.api.controller.dto.FindNoticesRequest;
import com.pp.api.controller.dto.FindNoticesResponse;
import com.pp.api.controller.dto.FindNoticesResponse.NoticeResponse;
import com.pp.api.service.NoticeService;
import com.pp.api.service.command.FindNoticesByNoOffsetQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindNoticesFacade {

    private final NoticeService noticeService;

    public FindNoticesResponse findNotices(FindNoticesRequest request) {
        FindNoticesByNoOffsetQuery query = FindNoticesByNoOffsetQuery.of(
                request.lastId(),
                request.limit() != null ? request.limit() : 20
        );

        List<NoticeResponse> noticeResponses = noticeService.findNoticeOfList(query)
                .stream()
                .map(notice -> new NoticeResponse(
                        notice.id(),
                        notice.title(),
                        notice.content(),
                        notice.createdDate()
                ))
                .toList();

        return new FindNoticesResponse(noticeResponses);
    }

}
