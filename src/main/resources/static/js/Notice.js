$(document).ready(function() {
    $("input[type=radio]").on("click",function() {
        // Get the storedValue
        let previousValue = $(this).data('storedValue');
        // if previousValue = true then
        //     Step 1: toggle radio button check mark.
        //     Step 2: save data-StoredValue as false to indicate radio button is unchecked.
        if (previousValue) {
            $(this).prop('checked', !previousValue);
            $(this).data('storedValue', !previousValue);
            $("#modifyNotice").hide();
            $("#deleteNotice").hide();
        }
            // If previousValue is other than true
            //    Step 1: save data-StoredValue as true to for currently checked radio button.
        //    Step 2: save data-StoredValue as false for all non-checked radio buttons.
        else{
            $(this).data('storedValue', true);
            $("input[type=radio]:not(:checked)").data("storedValue", false);
            $("#modifyNotice").show();
            $("#deleteNotice").show();
        }
    });

    //게시글 작성, 수정
    $("#saveNotice").on("click", function (){
        let parameter = {};
        let noticeKey = $("input:radio[name='noticeNumber']:checked").val();

        if(noticeKey == "" || noticeKey == undefined)
            parameter.noticeKey = "";
        else
            parameter.noticeKey = noticeKey;

        parameter.title     = $("#notice_title").val();
        parameter.contents  = $("#notice_contents").val();
        parameter.userId    = getCookie("userId");
        parameter.userName  = getCookie("name");

        let jsonString = JSON.stringify(parameter);
        let saveNotice = POSTAjax("saveNotice", {"parameter": jsonString});
        console.log(saveNotice);
        if(saveNotice != ""){
            if(saveNotice.isSuccess == "true"){
                $("#modalSmBody").html("게시글이 작성되었습니다.");
                $("#noticeModal").modal('hide');
                $("#modalSm").modal("show");
            }else{
                $("#modalSmBody").html("게시글이 작성에 실패하였습니다. <br/>이유 : " + saveNotice.errorMsg);
                $("#noticeModal").modal('hide');
                $("#modalSm").modal("show");
            }
        }
    });

    //글 수정 모달 띄우기
    $("#modifyNotice").on("click", function (){
        let noticeKey = $("input:radio[name='noticeNumber']:checked").val();

        if(noticeKey != "" || noticeKey != undefined){
            let tdList = $("input:radio[name='noticeNumber']:checked").parent().nextAll();
            $("#notice_title").val(tdList[0].textContent);
            $("#notice_contents").val(tdList[1].textContent);
            $("#noticeModal").modal("show");
        }else{
            $("#modalSmBody").html("수정 할 게시글을 선택해주세요.");
            $("#modalSm").modal("show");
        }
    });
    
    //글 삭제하기
    $("#deleteNotice").on("click", function (){
        let noticeKey = $("input:radio[name='noticeNumber']:checked").val();
        let parameter = {};
        if(noticeKey != "" || noticeKey != undefined){
            parameter.noticeKey = noticeKey;
            let jsonString = JSON.stringify(parameter);
            let deleteNotice = POSTAjax("deleteNotice", {"parameter": jsonString});
            //console.log(deleteNotice);
            if(deleteNotice != ""){
                if(deleteNotice.isSuccess == "true"){
                    $("#modalSmBody").html("게시글이 삭제 되었습니다.");
                    $("#modalSm").modal("show");
                }else{
                    $("#modalSmBody").html("게시글 삭제에 실패하였습니다. <br/> 이유 : " + deleteNotice.errorMsg);
                    $("#modalSm").modal("show");
                }
            }
        }else{
            $("#modalSmBody").html("삭제 할 게시글을 선택해주세요.");
            $("#modalSm").modal("show");
        }
    });

    //SMALL MODAL CLOSE
    $("#modalSmClose").on("click", function (){
        window.location.reload();
    });
});

