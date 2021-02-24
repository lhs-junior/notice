package com.task.notice.Controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.task.notice.Config.ResponseResult;
import com.task.notice.Model.Notice;
import com.task.notice.Model.User;
import com.task.notice.Repository.NoticeRepository;
import com.task.notice.Repository.UserRepository;
import com.task.notice.Service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.IntStream;

@Controller
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/notice")
    public String NoticePage(@PageableDefault Pageable pageable, Model model){
        Page<Notice> noticeList = noticeService.findNoticeList(pageable);
        model.addAttribute("noticeList", noticeList);
        //System.out.println(noticeList.toString());
        return "Notice";
    }

    /* ==========================
    [ 임시방편 사용자 등록 및 게시글 작성]
     */
    @GetMapping("/insert")
    @ResponseBody
    public String UserInsert(){
        User user = userRepository.save(User.builder()
                .name("test")
                .password("1234")
                .id("tester")
                .createdDate(LocalDateTime.now())
                .build()
        );

        IntStream.rangeClosed(1,10).forEach(index ->
                noticeRepository.save(Notice.builder()
                .title("제목 부분입니다." + index)
                .content("내용들 입니다." + index)
                .noticeKey(UUID.randomUUID().toString())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .user(user)
                .build()
                ));
        return "success";
    }

    /* ==========================
    [ 공지사항 저장]
     */
    @PostMapping("saveNotice")
    @ResponseBody
    public ResponseResult addNotice(@RequestParam(value = "parameter", required = false, defaultValue = "")String parameter) {
        JsonObject jsonObject   =    JsonParser.parseString(parameter).getAsJsonObject();
        String noticeKey        =    jsonObject.get("noticeKey").getAsString();
        String noticeTitle      =    jsonObject.get("title").getAsString();
        String noticeContents   =    jsonObject.get("contents").getAsString();
        String userName         =    jsonObject.get("userName").getAsString();
        String userId           =    jsonObject.get("userId").getAsString();
        ResponseResult responseResult = new ResponseResult();


        if (noticeKey.equals("")){
            try {
                responseResult = noticeService.addNotice(
                        noticeTitle,
                        noticeContents,
                        userName,
                        userId
                );
            } catch (Exception e) {
                responseResult.setIsSuccess("false");
                responseResult.setErrorMsg(e.getMessage());
            }
        }else{
            try {
                responseResult = noticeService.updateNotice(
                        noticeKey,
                        noticeTitle,
                        noticeContents,
                        userName
                );
            } catch (Exception e) {
                responseResult.setIsSuccess("false");
                responseResult.setErrorMsg(e.getMessage());
            }
        }
        return responseResult;
    }
    /* ==========================
    [ 공지사항 삭제]
     */
    @PostMapping("deleteNotice")
    @ResponseBody
    public ResponseResult deleteNotice(@RequestParam(value = "parameter", required = false, defaultValue = "")String parameter) {
        JsonObject jsonObject   =    JsonParser.parseString(parameter).getAsJsonObject();
        String noticeKey     =    jsonObject.get("noticeKey").getAsString();
        ResponseResult responseResult = new ResponseResult();

        try {
            responseResult = noticeService.deleteNotice(noticeKey);
        } catch (Exception e) {
            responseResult.setIsSuccess("false");
            responseResult.setErrorMsg(e.getMessage());
        }
        return responseResult;
    }
}
