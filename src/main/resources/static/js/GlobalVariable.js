let setCookie = function (name, value, exp) {
    let date = new Date();
    date.setTime(date.getTime() + exp * 24 * 60 * 60 * 1000);
    document.cookie = name + '=' + value + ';expires = ' + date.toUTCString() + ';path=/';
};

let getCookie = function (name) {
    let value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
    return value? value[2] : null;
};

let deleteCookie = function (name) {
    let expireDate = new Date();
    expireDate.setDate(expireDate.getDate() - 1);

    document.cookie = name + "=" + "; expires=" + expireDate.toUTCString() + "; path=/";
};

let getContextPath = function () {
    let context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
    return window.location.origin + context;
};

function POSTAjax(MappingName, Parameter){
    let result = "";
    $.ajax({
        type    :   "POST",
        url     :   getContextPath() + "/" + MappingName,
        data    :   Parameter,
        cache   :   false,
        async   :   false,
        success :   function (data){
            if(data != "" || data != null){
                //console.log(data);
                result = data;
            }
        },
        error   :   function (data){
            result = data;
        }
    });

    return result;
}