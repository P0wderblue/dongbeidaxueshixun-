/**
 * Created by Amy on 2018/8/9.
 */
var proId = '';
var startTime = '';
var endTime = ' ';
var questionnaireId = '';

$(function () {
    questionnaireId = getQueryString("questionnaireId");
    isLoginFun();
    var url = location.search;
    if (url.indexOf("?") != -1) {
        let projectId = url.substr(4);
        header();
        proId = projectId;
        $("#ctl01_lblUserName").text(getCookie('userName'));
        var oTable = new TableInit(projectId);
        oTable.Init();
    } else {
        console.log("没有参数");
        window.location.href = 'myQuestionnaires.html';
    }

    console.log('回退');
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

function getUserList() {
    $("#userTable").bootstrapTable('refresh');
}

function TableInit(projectId) {

    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#userTable').bootstrapTable({
            url: httpRequestUrl + '/questionnaire/queryListPage',         //请求后台的URL（*）
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
                    field: 'createName',
                    title: '创建人',
                    align: 'center',
                    // width: '180px'
                },
                // {
                //     field: 'projectName',
                //     title: '项目名称',
                //     align: 'center',
                // },
                {
                    field: 'inquiryType',
                    title: '调查群体',
                    align: 'center',
                },
                {
                    field: 'inquiryName',
                    title: '调查名称',
                    align: 'center',
                },
                {
                    field: 'startTime',
                    title: '开始时间',
                    align: 'center',
                },
                {
                    field: 'endTime',
                    title: '结束时间',
                    align: 'center',
                },
                {
                    field: 'isStart',
                    title: '问卷状态',
                    align: 'center',
                },
                {
                    field: 'clickNumber',
                    title: '点击数量',
                    align: 'center',
                },
                {
                    field: 'remark',
                    title: '分析备注',
                    align: 'center',
                },
                {
                    field: 'operation',
                    title: '问卷操作',
                    align: 'center',
                    events: operateEvents,//给按钮注册事件
                    formatter: addFunctionAlty//表格中增加按钮
                },
                {
                    field: 'operation',
                    title: '分享操作',
                    align: 'center',
                    events: operateEvents,//给按钮注册事件
                    formatter: shareUrl//表格中增加按钮
                },
                {
                    field: 'operation',
                    title: '答卷操作',
                    align: 'center',
                    events: operateEvents,//给按钮注册事件
                    formatter: answerOperateEvents//表格中增加按钮
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
                                'createName': '',
                                'projectName': '',
                                'inquiryType': '',
                                "startTime": '',
                                'endTime': '',
                                "isStart": '',
                                'answerNumber': '',
                                'inquiryName': '',
                                'answerUrl': '',
                                'remark': '',
                                'clickNumber': ''
                            };

                            dataNewObj.id = userInfo[i].id;
                            dataNewObj.createName = userInfo[i].createName;
                            dataNewObj.projectName = userInfo[i].projectName;
                            dataNewObj.inquiryName = userInfo[i].inquiryName;
                            if (userInfo[i].inquiryType == 1) {
                                dataNewObj.inquiryType = "老师";
                            } else {
                                dataNewObj.inquiryType = "学生";
                            }
                            dataNewObj.startTime = userInfo[i].startTime;
                            dataNewObj.endTime = userInfo[i].endTime;

                            if (userInfo[i].isStart == 0) {
                                dataNewObj.isStart = "启动";
                            } else {
                                dataNewObj.isStart = "停止";
                            }
                            dataNewObj.answerNumber = userInfo[i].answerNumber;
                            dataNewObj.clickNumber = userInfo[i].clickNumber;
                            dataNewObj.remark = userInfo[i].remark;
                            dataNewObj.answerUrl = "answerQuestionnaire.html?questionnaireIssueId=" + userInfo[i].id;
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
            projectId: projectId
        };
        return JSON.stringify(temp);
    }

    return oTableInit;
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
    // var btnText = '';
    // btnText += "<button type=\"button\" id=\"btn_look\" onclick=\"editProjectQuestionnaires(" + "'" + row.id + "')\" class=\"btn btn-default-g ajax-link\">编辑</button>&nbsp;&nbsp;";
    // if (row.isStart == "启动") {
    //     btnText += "<button type=\"button\" id=\"btn_stop" + row.id + "\" onclick=\"changeStatus(" + "'" + row.id + "'" + ")\" class=\"btn btn-danger-g ajax-link\">停止</button>&nbsp;&nbsp;";
    // } else if (row.isStart == "停止") {
    //     btnText += "<button type=\"button\" id=\"btn_stop" + row.id + "\" onclick=\"changeStatus(" + "'" + row.id + "'" + ")\" class=\"btn btn-default-g ajax-link\">开启</button>&nbsp;&nbsp;"
    // }
    // btnText += "<button type=\"button\" id=\"btn_stop" + row.id + "\" onclick=\"deleteProjectQuestionnaires(" + "'" + row.id + "'" + ")\" class=\"btn btn-danger-g ajax-link\">删除</button>&nbsp;&nbsp;";
    var btntext1 ='';
    btntext1 += "  <button type=\"button\" class=\"btn btn-default-g ajax-link\"  onclick=\"editProjectQuestionnaires(" + "'" + row.id + "')\" >编辑</button>\n" ;
    if (row.isStart == "启动") {
        btntext1 += "  <button type=\"button\" class=\"btn btn-danger-g ajax-link\" onclick=\"changeStatus(" + "'" + row.id + "'" + ")\">停止</button>\n";
    } else if (row.isStart == "停止") {
        btntext1 += "  <button type=\"button\" class=\"btn btn-success-g ajax-link\" onclick=\"changeStatus(" + "'" + row.id + "'" + ")\">开启</button>\n";
    }
    btntext1 +=   "  <button type=\"button\" class=\"btn btn-danger-g ajax-link\" onclick=\"deleteProjectQuestionnaires(" + "'" + row.id + "'" + ")\">删除</button>\n"

    return btntext1;
}

// 分享链接
function shareUrl(value, row, index) {
    // var btnText = '';
    // btnText += "<button type=\"button\" id=\"btn_look" + row.id + "\" onclick=\"copyUrl(" + "'" + row.id + "'" + ")\" class=\"btn btn-default-g ajax-link\">微信</button>&nbsp;&nbsp;";
    // btnText += "<button type=\"button\" id=\"btn_look" + row.id + "\" onclick=\"copyUrl(" + "'" + row.id + "'" + ")\" class=\"btn btn-default-g ajax-link\">QQ</button>&nbsp;&nbsp;";
    // btnText += "<button type=\"button\" id=\"btn_look" + row.id + "\" onclick=\"email(" + "'" + row.id + "'" + ")\" class=\"btn btn-default-g ajax-link\">邮件</button>&nbsp;&nbsp;";

    var btnText1 = " ";
    btnText1 += "  <image src='../images/wc.png' type=\"button\" class=\"btn btn-default-g  ajax-link\" onclick=\"copyUrl(" + "'" +row.id + "'" + ")\"></image>\n"
    btnText1 += "  <image src='../images/qqq.png' type=\"button\" class=\"btn btn-default-g ajax-link\" onclick=\"copyUrl(" + "'" + row.id+ "'" + ")\"></image>\n"
    btnText1 +=  "  <image src='../images/yx.png' type=\"button\" class=\"btn btn-default-g ajax-link\" onclick=\"email(" + "'" + row.id + "'" + ")\"></image>\n"

    return btnText1;
}

// 表格中按钮
function answerOperateEvents(value, row, index) {
    // var btnText = '';
    // btnText += "<button type=\"button\" id=\"btn_look" + row.id + "\" onclick=\"previewQuestionnaire(" + "'" + row.id + "'" + ")\" class=\"btn btn-default-g ajax-link\">问题</button>&nbsp;&nbsp;";
    // btnText += "<button type=\"button\" id=\"btn_look" + row.id + "\" onclick=\"answerQuestionnaire(" + "'" + row.id + "'" + ")\" class=\"btn btn-default-g ajax-link\">答卷</button>&nbsp;&nbsp;";
    // btnText += "<button type=\"button\" id=\"btn_look" + row.id + "\" onclick=\"statistics(" + "'" + row.id + "'" + ")\" class=\"btn btn-default-g ajax-link\">分析</button>&nbsp;&nbsp;";
    // return btnText;
    var btnText1 = " ";
    btnText1 += "  <button type=\"button\" class=\"btn btn-default-g ajax-link\" onclick=\"previewQuestionnaire(" + "'" + row.id + "'" + ")\">预览</button>\n"
    btnText1 +=  "  <button type=\"button\" class=\"btn btn-default-g ajax-link\" onclick=\"answerQuestionnaire(" + "'" + row.id + "'" + ")\">答卷</button>\n"
    btnText1 += "  <button type=\"button\" class=\"btn btn-success-g ajax-link\" onclick=\"statistics(" + "'" + row.id + "'" + ")\">分析</button>\n"

    return btnText1;
}

//查询统计图
function statistics(id) {
    //查询单条问卷信息
    commonAjaxGet(false, '/questionnaireAnswer/queryQuestionnaire/' + id, function (result) {
        if (result.code == "666") {
            console.log(result)
            if(result.data==null){
                layer.msg("该问卷没有提交回答，无法查看统计分析");
            }else{
                window.location.href = 'statistics.html?proId=' + proId + '&questionnaireId=' + id;
            }
        } else if (result.code == "333") {
            layer.msg("无法查看统计分析");
        } else {
            layer.msg("无法查看统计分析")
        }
    });

}

function copyUrl(id) {

//TODO
    var bootstrapTableElement = $("#userTable").bootstrapTable('getData')[id];

    const text = "http://127.0.0.1:8085/pages/answerQuestionnaire.html?questionnaireIssueId="+id;
    /*select() 方法用于选择该元素中的文本，
      元素的disabled属性会让select方法失效，
      所以重新创建了一个input
    */
    const oInput = document.createElement('input');
    oInput.value = text
    document.body.appendChild(oInput)
    //选中新的input里面的值
    oInput.select();
    //执行复制命令
    document.execCommand("copy");
    document.body.removeChild(oInput);
    layer.msg("复制链接成功")
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.split("=");
    //console =
    if (r != null) {
        return r[1];
    }
    return null;
}

//创建项目问卷
function createProjectQuestionnaires(id, value) {
    window.location.href = 'createQuestionnaire.html?proId=' + proId + 'questionnaireId=' + null;
}
function email(id) {
    window.location.href = 'email.html?proId=' + proId + '&questionnaireId=' + id;
}

//导入项目问卷
function importProjectQuestionnaires(id, value) {
    var url = '/questionnaire/queryAll';
    commonAjaxGet(false, url, function (result) {
        if (result.code == "666") {
            if (result.data.length > 0) {
                window.location.href = 'importQuestionnaires.html?proId=' + proId + 'questionnaireId=' + null;
            } else {
                layer.msg("未查询到可导入项目")
            }
        } else if (result.code == "333") {
            layer.msg(result.message);
        } else {
            layer.msg("请求项目列表异常")
        }
    });
}

function editProjectQuestionnaires(id) {
    var url = '/questionnaire/info/' + id;
    commonAjaxGet(false, url, function (result) {
        if (result.code == "666") {
            window.location.href = 'createQuestionnaire.html?proId=' + proId + '&questionnaireId=' + id;
        } else if (result.code == "333") {
            layer.msg(result.message);
        } else {
            layer.msg(result.message)
        }
    });

}

//预览问卷
function previewQuestionnaire(id) {
    window.location.href = 'previewQuestionnaire.html?issueId=' + id + '&answerId=' + null;
}

function answerQuestionnaire(id) {
    window.location.href = 'answerQuestionnaireList.html?questionnaireId=' + id + '&proId=' + proId;
}

// 修改用户状态（禁用、开启）
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

//删除项目问卷
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

