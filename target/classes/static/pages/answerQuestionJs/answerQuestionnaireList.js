/**
 * Created by Amy on 2018/8/9.
 */
var proId = '';

var questionnaireId='';

$(function () {
    isLoginFun();
    header();
    projectId = getQueryString("proId");
    questionnaireId = getQueryString("questionnaireId");
    $("#ctl01_lblUserName").text(getCookie('userName'));
    var outTable = new TableInit(projectId);
    outTable.Init();
});
//回车事件
$(document).keydown(function (event) {
    if (event.keyCode == 13) {
        getUserList();
    }
});

$('#userManager').on("keydown", function (event) {
    var keyCode = event.keyCode || event.which;
    if (keyCode == "13") {
        //console.log("1111")
        event.preventDefault();
    }
});

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}


function getUserList() {
    $("#userTable").bootstrapTable('refresh');
}

function TableInit(projectId) {

    var outTableInit = new Object();
    //初始化Table
    outTableInit.Init = function () {
        $('#userTable').bootstrapTable({
            url: httpRequestUrl + '/questionnaireAnswer/queryListPage',         //请求后台的URL（*）
            method: 'POST',                      //请求方式（*）
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortOrder: "asc",                   //排序方式
            queryParamsType: '',
            dataType: 'json',
            paginationShowPageGo: true,
            showJumpto: true,
            pageNumber: 1, //初始化加载第一页，默认第一页
            queryParams: queryParams,//请求服务器时所传的参数
            sidePagination: 'server',//指定服务器端分页
            pageSize: 10,//单页记录数
            pageList: [10, 20, 30, 40],//分页步进值
            search: false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            silent: true,
            showRefresh: false,                  //是否显示刷新按钮
            showToggle: false,
            minimumCountColumns: 2,             //最少允许的列数
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列

            columns: [{
                checkbox: true,
                visible: false
            }, {
                field: 'id',
                title: '序号',
                align: 'center',
                formatter: function (value, row, index) {
                    return index + 1;
                }
            },
                {
                    field: 'questionName',
                    title: '问卷名称',
                    align: 'center',
                    // width: '180px'
                },
                {
                    field: 'questionContent',
                    title: '问卷说明',
                    align: 'center',
                },

                {
                    field: 'createTime',
                    title: '创建时间',
                    align: 'center',
                },
                {
                    field: 'submitIp',
                    title: '答卷ip',
                    align: 'center',
                },{
                    field: 'responseTime',
                    title: '答卷时间',
                    align: 'center',
                },
                {
                    field: 'operation',
                    title: '操作',
                    align: 'center',
                    events: operateEvents,//给按钮注册事件
                    formatter: addFunctionAlty//表格中增加按钮
                }],
            responseHandler: function (res) {
                console.log(res);
                if (res.code === "666") {
                    var userInfo = res.data.list;
                    var NewData = [];
                    if (userInfo.length) {
                        for (var i = 0; i < userInfo.length; i++) {
                            var dataNewObj = {
                                'id': '',
                                'questionName': '',
                                'questionContent': '',
                                'responseTime': '',
                                "createTime": '',
                                'submitIp': ''
                            };

                            dataNewObj.id = userInfo[i].id;
                            dataNewObj.questionName = userInfo[i].questionName;
                            dataNewObj.questionContent = userInfo[i].questionContent;
                            dataNewObj.responseTime = userInfo[i].responseTime;
                            dataNewObj.createTime = userInfo[i].createTime;
                            dataNewObj.submitIp = userInfo[i].submitIp;
                            NewData.push(dataNewObj);
                        }
                        //console.log(NewData)
                    }
                    var data = {
                        total: res.data.totalCount,
                        rows: NewData
                    };

                    return data;
                }

            }

        });
    };

    // 得到查询的参数
    function queryParams(params) {
        var inquiryName = $("#keyWord").val();
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            page: params.pageNumber,
            limit: params.pageSize,
            inquiryName: inquiryName,
            questionnaireId: questionnaireId
        };
        return JSON.stringify(temp);
    }

    return outTableInit;
}


window.operateEvents = {
    //编辑
    'click #btn_count': function (e, value, row, index) {
        id = row.id;
        $.cookie('questionId', id);
    }
};


// 表格中按钮
function addFunctionAlty(value, row, index) {
    var btnText = '';
    btnText += "<button type=\"button\" id=\"btn_look\" onclick=\"previewQuestionnaireAnswer(" + "'" + row.id + "')\" class=\"btn btn-default-g ajax-link\">查看</button>&nbsp;&nbsp;";
    return btnText;
}

//重置密码
function resetPassword(id) {
    alert("重置密码")

}
function changeStatus(id) {

    layer.confirm('您确认要修改此问卷的开启/停止状态吗？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        var url = '/questionnaire/editQuestionnaireStart';
        var data = {
            "id": id
        };
        commonAjaxPost(true, url, data, function (result) {
            if (result.code == "666") {
                layer.msg(result.message, {icon: 1});
                setTimeout(function () {
                    location.reload();
                }, 600);
            } else if (result.code == "333") {
                layer.msg(result.message, {icon: 2});
                setTimeout(function () {
                    window.location.href = 'login.html';
                }, 1000);
            } else {
                layer.msg(result.message, {icon: 2});
            }
        });
    }, function () {
    });
}
//创建项目问卷
function createProjectQuestionnaires(id, value) {
    console.log("创建项目问卷")
    window.location.href = 'createQuestionnaire.html?proId=' + proId + 'questionnaireId=' + null;
}

function previewQuestionnaireAnswer(id) {
    window.location.href = 'previewQuestionnaire.html?issueId=' + null + '&answerId=' + id;
}

function previewQuestionnaire(id){
    window.location.href = 'previewQuestionnaire.html?issueId=' + id + '&issue=' + null;
}


function deleteProjectQuestionnaires(id) {
    layer.confirm('您确认要删除此问卷吗？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        var url = '/questionnaire/delete';
        var data = {
            "id": id
        };
        commonAjaxPost(true, url, data, function (result) {
            if (result.code == "666") {
                layer.msg(result.message, {icon: 1});
                setTimeout(function () {
                    location.reload();
                }, 600);
            } else if (result.code == "333") {
                layer.msg(result.message, {icon: 2});
                setTimeout(function () {
                    window.location.href = 'login.html';
                }, 1000);
            } else {
                layer.msg(result.message, {icon: 2});
            }
        });
    }, function () {
    });
}

