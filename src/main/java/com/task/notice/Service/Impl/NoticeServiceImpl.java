package com.task.notice.Service.Impl;

import com.google.gson.JsonObject;
import com.task.notice.Config.ResponseResult;
import com.task.notice.Model.Notice;
import com.task.notice.Model.User;
import com.task.notice.Repository.NoticeRepository;
import com.task.notice.Repository.UserRepository;
import com.task.notice.Service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    @Override
    public ResponseResult addNotice(String noticeTitle, String noticeContents, String userName, String id) throws  Exception{

        User user = userRepository.findById(id);
        if(user == null)
            throw new Exception("잘못된 작성자 계정입니다." + id);

        Notice notice = new Notice();
        notice.setTitle(noticeTitle);
        notice.setContent(noticeContents);
        notice.setNoticeKey(UUID.randomUUID().toString());
        notice.setCreatedDate(LocalDateTime.now());
        notice.setUpdateDate(LocalDateTime.now());
        notice.setUser(user);

        noticeRepository.save(notice);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("noticeKey", notice.getNoticeKey());

        return ResponseResult.createResult(true, "", jsonObject);

    }

    @Override
    public Page<Notice> findNoticeList(Pageable pageable) {
        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber()-1,
                pageable.getPageSize());

        return noticeRepository.findAll(pageable);
    }

    @Override
    public ResponseResult updateNotice(String noticeKey, String noticeTitle, String noticeContents, String userName) throws Exception {
        Notice notice = noticeRepository.findByNoticeKey(noticeKey);
        if(notice == null)
            throw new Exception("수정 할 게시글이 없습니다." + noticeKey);

        notice.setTitle(noticeTitle);
        notice.setContent(noticeContents);
        notice.setUpdateDate(LocalDateTime.now());

        noticeRepository.save(notice);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("noticeKey", notice.getNoticeKey());

        return ResponseResult.createResult(true, "", jsonObject);
    }

    @Override
    @Transactional
    public ResponseResult deleteNotice(String noticeKey) throws Exception {
        noticeRepository.deleteByNoticeKey(noticeKey);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("noticeKey", noticeKey);
        return ResponseResult.createResult(true, "", jsonObject);
    }


    //public String addNotice(String )
}
