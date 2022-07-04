var projectName = '';
var dataId = '';
var inquiryName = '';
var inquiryExplain = '';
var belongType = '';
var proId = '';
var questionnaireId = '';

var str = '';

$(function () {
    console.log("发送邮件界面");
    questionnaireId = getQueryString("questionnaireId");
    header();
    $('#belongProject').css('cursor', 'not-allowed');
});
function email() {
    var email = $("#email").val();
    var emailContent =  $("#emailContent").val()
    if (email === '') {
        layer.msg('请输入要发送的email')
        return;
    }
    var url = '/questionnaire/sendEmail/'+email+'/'+questionnaireId+'/'+emailContent;
    commonAjaxGet(false, url, function (result) {
        if (result.code == "666") {
            layer.msg(result.message);
            setTimeout(function () {
                window.location.href = 'questionnairesList.html?id=' + proId;
            }, 400)
        } else if (result.code == "333") {
            layer.msg(result.message);
        } else {
            layer.msg("发送异常")
        }
    });
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}



