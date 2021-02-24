package com.task.notice.Service;

import com.task.notice.Config.ResponseResult;
import com.task.notice.Model.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public interface NoticeService {

    ResponseResult addNotice(String noticeTitle, String noticeContents, String userName, String tester) throws Exception;

    Page<Notice> findNoticeList(Pageable pageable);

    ResponseResult updateNotice(String noticeKey, String noticeTitle, String noticeContents, String userName) throws Exception;

    ResponseResult deleteNotice(String noticeKey) throws Exception;
}
