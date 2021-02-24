
(function ($) {
    "use strict";


    /*==================================================================
    [ Validate ]*/
    var input = $('.validate-input .input100');

    // $('.validate-form').on('submit',function(){
    $("#loginBtn").on("click", function (){

        let parameter = {};
        let id = $("#id");
        let password = $("#pass");

        if(id.val() == ""){
            id.parent().addClass("alert-validate");
            return false;
        }
        if(password.val() == ""){
            password.parent().addClass("alert-validate");
            return false;
        }

        parameter.id        = id.val();
        parameter.password  = password.val();
        parameter.name      = "GUEST";

        let jsonString = JSON.stringify(parameter);
        let login = POSTAjax("login", {"parameter": jsonString});
        if(login != ""){
            if(login.isSuccess == "true"){
                setCookie("userId", id.val(), 30);
                setCookie("name", "GUEST", 30);

                location.replace(getContextPath() + "/notice?page=1&size=10");

            }else{
                $("#modalSmBody").html("로그인 실패 !<br/> 이유 : " + login.errorMsg);
                $("#modalSm").modal("show");
            }
        }

    });

    $('.input100').each(function(){
        $(this).focus(function(){
            hideValidate(this);
        });
    });

    function hideValidate(input) {
        let thisAlert = $(input).parent();

        $(thisAlert).removeClass('alert-validate');
    }

})(jQuery);