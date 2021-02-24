package com.task.notice.Repository;

import com.task.notice.Model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Notice findByNoticeKey(String noticeKey);

    void deleteByNoticeKey(String noticeKey);
}
