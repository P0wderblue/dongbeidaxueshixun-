/**
 * Created by Amy on 2018/8/6.
 */
var flag = true;
var projectName = '';
var projectContent = '';

var excelData = {
    roleType: "",
    studentNumber: "",
    name: "",
    id: 0,
    academy: null,
    major: null,
    classGrade: null,
    sex: null,
    wechat: "",
    qq: "",
    mobile: "",
    mailbox: "",
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}

$(function () {
    isLoginFun();
    var userId = getQueryString("userId");
    queryExcelUserInfo(userId);
    header();
    $("#ctl01_lblUserName").text(getCookie('userName'));
});


function createExcelUserRight() {
    var url = '/excleUser/update';
    commonAjaxPost(false, url, excelData, function (result) {
        console.log(result)
        if (result.code == "666") {
            layer.msg(result.message);
            setTimeout(function () {
                window.location.href = "excelUser.html";
            }, 700)
        } else if (result.code == "333") {
            layer.msg(result.message);
        } else {
            layer.msg("请求异常")
        }

    });




}

//点击“立即创建”，创建项目
function createExcelUser() {
    excelData.studentNumber = $("#studentNumber").val();
    if (excelData.studentNumber.trim() == '') {
        layer.msg('请输入学/师号')
        return
    }
    excelData.roleType = $("#roleType").val();
    excelData.name = $("#name").val();
    excelData.academy = $("#academy").val();
    excelData.major = $("#major").val();
    excelData.classGrade = $("#classGrade").val();
    excelData.sex = $("#sex").val();
    excelData.wechat = $("#wechat").val();
    excelData.qq = $("#qq").val();
    excelData.mobile = $("#mobile").val();
    excelData.mailbox = $("#mailbox").val();
    createExcelUserRight();
}


function queryExcelUserInfo(id) {
    var url = '/excleUser/queryById/' + id;
    commonAjaxGet(false, url, function (result) {

        if (result.code == "666") {
            $("#roleType").val(result.data.roleType);
            $("#studentNumber").val(result.data.studentNumber);
            $("#name").val(result.data.name);
            $("#academy").val(result.data.academy);
            $("#major").val(result.data.major);
            $("#classGrade").val(result.data.classGrade);
            $("#sex").val(result.data.sex);
            $("#wechat").val(result.data.wechat);
            $("#qq").val(result.data.qq);
            $("#mobile").val(result.data.mobile);
            $("#mailbox").val(result.data.mailbox);
            excelData = result.data;
        } else if (result.code == "333") {
            layer.msg(result.message);
        } else {
            layer.msg("请求异常")
        }
    });
}
