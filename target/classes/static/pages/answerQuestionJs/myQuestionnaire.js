/**
 * Created by Amy on 2018/8/9.
 */
$(function () {
    isLoginFun();
    //查pid，如果没有就跳过不然就跳转到问卷页面，这么做是为了解决问卷页面的返回上一页面到修改页面了
    header();
    $("#ctl01_lblUserName").text(getCookie('userName'));
    var oTable = new TableInit();
    oTable.Init();
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

function TableInit() {

    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#userTable').bootstrapTable({
            url: httpRequestUrl + '/project/queryProjectList',         //请求后台的URL（*）
            method: 'POST',                      //请求方式（*）
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortOrder: "asc",                   //排序方式
            queryParamsType: '',
            dataType: 'json',
            paginationShowPageGo: true,
            showJumpto: true,
            pageNumber: 1,                        //初始化加载第一页，默认第一页
            queryParams: queryParams,             //请求服务器时所传的参数
            sidePagination: 'server',             //指定服务器端分页
            pageSize: 10,//单页记录数
            pageList: [10, 20, 30, 40],           //分页步进值
            search: false,                         //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
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
                    field: 'projectName',
                    title: '项目名称',
                    align: 'center',
                    width: '230px'
                },
                {
                    field: 'projectContent',
                    title: '项目描述',
                    align: 'center',
                    width: '230px'
                },
                {
                    field: 'createdBy',
                    title: '创建人',
                    align: 'center'
                }, {
                    field: 'creationDate',
                    title: '创建时间',
                    align: 'center'
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
                if (res.code == "666") {
                    var userInfo = res.data.list;
                    var NewData = [];
                    if (userInfo.length) {
                        for (var i = 0; i < userInfo.length; i++) {
                            var dataNewObj = {
                                'id': '',
                                "projectName": '',
                                'projectContent': '',
                                "createdBy": '',
                                'creationDate': ''
                            };

                            dataNewObj.id = userInfo[i].id;
                            dataNewObj.projectName = userInfo[i].projectName;
                            dataNewObj.projectContent = userInfo[i].projectContent;
                            dataNewObj.createdBy = userInfo[i].createdBy;
                            dataNewObj.creationDate = userInfo[i].creationDate;
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
        var userName = $("#keyWord").val();
        //console.log(userName);
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            page: params.pageNumber,
            limit: params.pageSize,
            projectName: userName
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

    var btnText = '';

    btnText += "<button type=\"button\" id=\"btn_look\" onclick=\"queryQuestionnaires(" + "'" + row.id + "'" + ")\" style='width: 77px;' class=\"btn btn-default-g ajax-link\">查看问卷</button>&nbsp;&nbsp;";

    btnText += "<button type=\"button\" id=\"btn_look\" onclick=\"editProject(" + "'" + row.id + "')\" class=\"btn btn-default-g ajax-link\">编辑</button>&nbsp;&nbsp;";
    console.log("row.status 的值",row.status)
    if (row.status == "1") {
        btnText += "<button type=\"button\" id=\"btn_stop" + row.id + "\" onclick=\"changeStatus(" + "'" + row.id + "'" + ")\" class=\"btn btn-danger-g ajax-link\">关闭</button>&nbsp;&nbsp;";
    } else if (row.status == "0") {
        btnText += "<button type=\"button\" id=\"btn_stop" + row.id + "\" onclick=\"changeStatus(" + "'" + row.id + "'" + ")\" class=\"btn btn-success-g ajax-link\">开启</button>&nbsp;&nbsp;"
    }
    btnText += "<button type=\"button\" id=\"btn_stop" + row.id + "\" onclick=\"deleteProject(" + "'" + row.id + "'" + ")\" class=\"btn btn-danger-g ajax-link\">删除</button>&nbsp;&nbsp;";

    return btnText;
}

//查询问卷
function queryQuestionnaires(id) {
    // alert(id);
    window.location.href = 'questionnairesList.html?id=' + id;
}

// 打开创建项目
function createProject(id, value) {

    window.location.href = 'createProject.html';
}
//编辑项目
function editProject(id) {
    var url = '/project/info/' + id;
    commonAjaxGet(false, url, function (result) {
        if (result.code == "666") {
            window.location.href = 'createProject.html?proId=' + id;
        } else if (result.code == "333") {
            layer.msg(result.message);
        } else {
            layer.msg(result.message)
        }
    });
}

//删除项目问卷
function deleteProject(id) {
    layer.confirm('您确认要删除此项目吗？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        var url = '/project/deleteProjectById';
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


// 修改用户状态（禁用、开启）
function changeStatus(index) {

    alert("修改用户状态")
}

//删除用户
function deleteUser(id) {

    alert("删除用户")
}

