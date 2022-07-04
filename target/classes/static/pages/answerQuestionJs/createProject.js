/**
 * Created by Amy on 2018/8/6.
 */
var flag = true;
var projectName = '';
var projectContent = '';

var prodectData={
    createdBy: "",
    createdId: null,
    creationDate: "",
    id: 0,
    isDelete: null,
    lastUpdateDate: null,
    lastUpdatedBy: null,
    lastUpdatedId: null,
    projectContent: "",
    projectName: "",
    userId: "",
}

$(function () {
    isLoginFun();
    proId = getQueryString("proId");
    if (proId != null && proId != '') {
        document.getElementById("ctl01_ContentPlaceHolder1_spanTitle").innerHTML="修改项目";
        queryProject(proId);
    }


    header();
    $("#ctl01_lblUserName").text(getCookie('userName'));
});

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}

//点击“立即创建”，创建项目
function createProject() {
    projectName = $("#projectName").val();
    projectContent = $("#inputIntro").val();
    createProjectRight();
}

function createProjectRight() {
    if (flag == true) {
        if (projectName.trim() == '') {
            layer.msg('请完整填写项目名称')
        } else if (projectContent.trim() == '') {
            layer.msg('请完整填写项目描述')
        } else {
            // var userName = getCookie("userName");

            prodectData.projectName=projectName;
            prodectData.projectContent=projectContent;
            if(prodectData.id!=''){
                var url = '/project/modifyProjectInfo';
            }else{
                var url = '/project/addProjectInfo';
            }
            commonAjaxPost(false, url, prodectData, function (result) {
                console.log(result)
                if (result.code == "666") {
                    layer.msg(result.message);
                    setTimeout(function () {
                        window.location.href = "myQuestionnaires.html";
                    }, 700)
                } else if (result.code == "333") {
                    layer.msg(result.message);
                } else {
                    layer.msg("请求异常")
                }

            });
        }
    }
}


function queryProject(id) {
    var url = '/project/queryById/' + id;
    commonAjaxGet(false, url, function (result) {
        console.log(result);
        result;
        if (result.code == "666") {
            $("#projectName").val(result.data.projectName);
            $("#inputIntro").val(result.data.projectContent);
            prodectData=result.data;
        } else if (result.code == "333") {
            layer.msg(result.message);
        } else {
            layer.msg("请求异常")
        }
    });
}
