var projectName = '';
var dataId = '';
var inquiryName = '';
var inquiryExplain = '';
var belongType = '';
var proId = '';
var questionnaireId = '';

var str = '';

var questionnaire = {
    answerNumber: '',
    createDate: '',
    createName: '',
    endTime: null,
    id: 0,
    inquiryExplain: '',
    inquiryName: '',
    inquiryType: '',
    isDelete: 0,
    isStart: 0,
    projectId: 0,
    projectName: '',
    startTime: null,
    updateDate: null,
    updateUserName: null,
    userId: "1",
    importQuestionnaireId: 0,
    questionContent: '',
    questionList: '',
    questionName: '',
}


$(function () {
    isLoginFun();
    proId = getQueryString("proId");
    var str = decodeURI(getQueryString("questionnaire"), "utf-8");
    if (str != null) {
        questionnaire = JSON.parse(str);
    }
    header();
    $('#questionnaire').css('cursor', 'not-allowed');
    var belongType = document.getElementById('belongType');
    belongType.options.length = 0;
    var collOpt = document.createElement('option');
    collOpt.innerText = "老师";
    collOpt.value = "1";
    belongType.appendChild(collOpt);
    $('#belongType').append(`<option value="2">学生 </option>`);
    queryQuestionnaireName();
    queryProjectName(proId);
});

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}

//铺调查类型
function queryAllDataTypeSuccess(res) {
    //console.log(res);
    if (res.code == "666") {
        var belongType = document.getElementById('belongType');
        belongType.options.length = 0;
        for (var i = 0; i < res.data.length; i++) {
            var collOpt = document.createElement('option');
            collOpt.innerText = res.data[i].name;
            collOpt.value = res.data[i].id;
            belongType.appendChild(collOpt);
        }
        var options = "";

        // setCookie('dataId', options.val());
        //console.log(getCookie('dataId'));
        if (getCookie('dataId') != undefined) {
            var data1 = getCookie('dataId');
            $("#belongType").val(data1);
            $("#belongType").find("option[value = " + data1 + "]").attr("selected", "selected");
            options = $("#belongType option:selected");
            $("#questBelong").val(options.text());
        } else {

            $("#belongType").find("option[value = 2]").attr("selected", "selected");
            options = $("#belongType option:selected");
            $("#questBelong").val(options.text());
        }
    } else if (res.code == "333") {
        layer.msg(res.message, {icon: 2});
        setTimeout(function () {
            window.location.href = 'login.html';
        }, 1000)
    } else {
        layer.msg(res.message, {icon: 2})
    }
}

//查询问卷并渲染
function queryQuestionnaireName(qId) {
    var url = '/questionnaire/queryAll';
    commonAjaxGet(false, url, function (result) {
        if (result.code == "666") {
            for (var i = 0; i < result.data.length; i++) {
                $('#questionnaire').append('<option value="' + result.data[i].id + '" >' + result.data[i].inquiryName + '</option>');
            }
        } else if (result.code == "333") {
            layer.msg(result.message);
        } else {
            layer.msg("请求项目列表异常")
        }
    });
}

//查询项目并渲染
function queryProjectName(id) {
    var url = '/project/queryAll';
    commonAjaxGet(false, url, function (result) {
        if (result.code == "666") {
            for (var i = 0; i < result.data.length; i++) {
                if (result.data[i].id == proId) {
                    $('#belongProject').append('<option value="' + result.data[i].id + '" selected="selected">' + result.data[i].projectName + '</option>');
                } else {
                    $('#belongProject').append('<option value="' + result.data[i].id + '" >' + result.data[i].projectName + '</option>');
                }
            }
        } else if (result.code == "333") {
            layer.msg(result.message);
        } else {
            layer.msg("请求项目列表异常")
        }
    });
}


//导入模板 历史模板或类型模板
function importTemplate() {
    questionnaireId = $("#questionnaire").val();
    if (questionnaireId === '') {
        layer.msg('请选择所属要导入的问卷')
        return;
    }
    inquiryName = $("#inquiryName").val();
       inquiryExplain = $("#inquiryExplain").val();
       belongType = $("#belongType").val();
      belongProject = $("#belongProject").val();
      startDate = $("#startDate").val().replace("T", " ");
      endDate = $("#endDate").val().replace("T", " ");

    if (inquiryName.trim() === '') {
        layer.msg('请完整填写调查名称')
        return;
    }
    if (belongProject === '') {
        layer.msg('请选择所属项目')
    }
    if (belongType === '') {
        layer.msg('请选择调查类型')
    }
    if (null == startDate || '' === startDate) {
        layer.msg('请选择开始时间')
        return;
    }
    if (null == endDate || '' === endDate) {
        layer.msg('请选择结束时间')
        return;
    }
    if (Date.parse(startDate) >= Date.parse(endDate)) {
        layer.msg('开始时间不能小于结束时间')
        return;
    }
    if (inquiryExplain.trim() === '') {
        layer.msg('请完整填写调查说明')
        return;
    }
    console.log(inquiryExplain);


    var questionnaire = {
        answerNumber: '',
        createDate: '',
        createName: '',
        endTime: endDate,
        id: 0,
        inquiryExplain:inquiryExplain,
        inquiryName: inquiryName,
        inquiryType: belongType,
        isDelete: 0,
        isStart: 0,
        projectId: belongProject,
        projectName: '',
        startTime: startDate,
        updateDate: null,
        updateUserName: null,
        userId: "1",
        importQuestionnaireId: questionnaireId,
        questionContent: '',
        questionList: '',
        questionName: '',
    }
    var urlQ = '';
    if (questionnaire != null && questionnaire.id !== 0) {
        urlQ = '/questionnaire/editImportQuestionnaire';
    } else {
        urlQ = '/questionnaire/saveImportQuestionnaire';
    }
    commonAjaxPost(true, urlQ, questionnaire, addQuestionnaireSuccess)
}


//删除模板
function deleteModal(that, modalId) {
    //console.log(modalId)
    // 询问框
    layer.confirm('您确认要删除此模板吗？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        var url = '/deleteQuestionnaireById';
        var da = {
            'id': modalId
        };
        //commonAjaxPost(true, url, da, deleteQuestionnaireByIdSuccess);
        deleteQuestionnaireByIdSuccess();
        var div = $(that).parent('.figure');
        div.css("display", "none");
        // layer.msg('删除成功', {icon: 1});
    }, function () {
    });
}

//点击div 查看模板具体信息
function viewModal(questionId) {
    window.open("previewQuestionnaire.html?i=" + questionId);
}

//查询历史模板的成功回调
function queryHistoryQuestionnaireSuccess(res) {
    //console.log(res);
    //if (res.code == "666") {
    $("#typeQuestion").css("display", "none");
    //   if (res.data.length == 0) {
    //       layer.msg("暂无历史问卷", {icon: 2});
    //      return
    //   }
    $("#line").css("display", "block");
    $("#historyQuestion").css("display", "block");
    $('#historyQuestion').empty();
    //for (var i = 0; i < res.data.length; i++) {
    // var historyModal_div = '   <div class="figure">' +
    //     '                    <div class="pic-box icon survey-icon pull-left"></div>' +
    //     '                    <div class="details-wrapper pull-left">' +
    //     '                        <div class="details-title">' +
    //     '                            //<span class="pull-left">' + res.data[i].questionName + '</span>' +
    //     '                            <span class="pull-left">页面测试数据</span>' +
    //     '                        </div>' +
    //     // '                        <div class="details-info">丰富题型，强大逻辑</div>' +
    //     // '                        <div class="details-info">问卷密码，红包抽奖</div>' +
    //     '                    </div>' +
    //     '                    <div class="clear dotted-line--solid"></div>\n' +
    //     '                    <a href="#" class="btn btn-blue-frame main__btn--new" onclick=\'importModal(' + '"' + res.data[i].id + '"' + ',' + '"' + res.data[i].questionName + '"' + ',' + '"' + res.data[i].questionContent + '"' +')\'>导入</a>' +
    //     '                </div>';
    // $("#historyQuestion").append(historyModal_div);
    var historyModal_div = '   <div class="figure">' +
        '                    <div class="pic-box icon survey-icon pull-left"></div>' +
        '                    <div class="details-wrapper pull-left">' +
        '                        <div class="details-title">' +
        '                            //<span class="pull-left">测试</span>' +
        '                            <span class="pull-left">页面测试数据</span>' +
        '                        </div>' +
        // '                        <div class="details-info">丰富题型，强大逻辑</div>' +
        // '                        <div class="details-info">问卷密码，红包抽奖</div>' +
        '                    </div>' +
        '                    <div class="clear dotted-line--solid"></div>\n' +
        '                    <a href="#" class="btn btn-blue-frame main__btn--new" onclick=\'importModal(' + '"' + 1 + '"' + ',' + '测试' + ',' + '测试' + ')\'>导入</a>' +
        '                </div>';
    $("#historyQuestion").append(historyModal_div);
    //}
    // } else if (res.code == "333") {
    //    layer.msg(res.message, {icon: 2});
    //    setTimeout(function () {
    //       window.location.href = 'login.html';
    //   }, 1000)
    //} else {
    //    layer.msg(res.message, {icon: 2})
    // }
}

//查询问卷模板的成功回调
function queryQuestionnaireMouldSuccess(res) {
    //console.log(res);
    //if (res.code == "666") {
    $("#historyQuestion").css("display", "none");
    $("#line").css("display", "block");
    $("#typeQuestion").css("display", "block");
    $('#typeQuestion').empty();
    var createQuestionnaireModal = '<div class="figure">\n' +
        '                    <div class="pic-box icon exam-icon pull-left"></div>\n' +
        '                    <div class="details-wrapper pull-left">\n' +
        '                        <div class="details-title">\n' +
        '                            <span class="pull-left">创建模板</span>\n' +
        '                        </div>\n' +
        '                        <div class="details-info">题库抽题，限时作答</div>\n' +
        '                        <div class="details-info">成绩查询，自动阅卷</div>\n' +
        '                    </div>\n' +
        '                    <div class="clear dotted-line--solid"></div>\n' +
        '                    <a href="" class="btn btn-blue-frame main__btn--new" data-toggle="modal" data-target="#createQuestModal">创建</a>\n' +
        '                    <input type="checkbox"\n' +
        '                           style="position:absolute;right:20px;bottom:25px;width:18px;height:18px;display:none;"/>\n' +
        '                </div>';
    $("#typeQuestion").append(createQuestionnaireModal);
    //console.log(res)
    //for (var i = 0; i < res.data.length; i++) {
    // var questionnaireModal_div = '  <div class="figure">' +
    //     '                    <i class="fa fa-close" style="font-size: 1rem; color: #ccc; position: absolute;right: 7px;top:4px"' +
    //     '                       onclick=\'deleteModal(this' + ',' + '"' + res.data[i].id + '"' + ')\'></i>' +
    //     '                    <div class="pic-box icon survey-icon pull-left"></div>' +
    //     '                    <div class="details-wrapper pull-left" onclick=\'viewModal(' + '"' + res.data[i].id + '"' + ')\'>' +
    //     '                        <div class="details-title">' +
    //     '                            <span class="pull-left">' + res.data[i].questionName + '</span>' +
    //     '                        </div>' +
    //     // '                        <div class="details-info">丰富题型，强大逻辑</div>' +
    //     // '                        <div class="details-info">问卷密码，红包抽奖</div>' +
    //     '                    </div>' +
    //     '                    <div class="clear dotted-line--solid"></div>' +
    //     '                    <a href="javascript:void(0)" class="btn btn-blue-frame editModal" onclick=\'editModal(' + '"' + res.data[i].id + '"' + ')\'>编辑</a>' +
    //     '                    <a href="javascript:void(0)" class="btn btn-blue-frame main__btn--new" style="left:71%" onclick=\'importModal(' + '"' + res.data[i].id + '"' + ',' + '"' + res.data[i].questionName + '"' + ',' + '"' + res.data[i].questionContent + '"'+','+ '"'+getCookie('projectIdForCreate') +'"' + ')\'>导入</a>' +
    //     '                </div>';
    // $("#typeQuestion").append(questionnaireModal_div);
    var questionnaireModal_div = '  <div class="figure">' +
        '                    <i class="fa fa-close" style="font-size: 1rem; color: #ccc; position: absolute;right: 7px;top:4px"' +
        '                       onclick=\'deleteModal(this' + ',' + '"' + 1 + '"' + ')\'></i>' +
        '                    <div class="pic-box icon survey-icon pull-left"></div>' +
        '                    <div class="details-wrapper pull-left" onclick=\'viewModal(' + '"' + 1 + '"' + ')\'>' +
        '                        <div class="details-title">' +
        '                            <span class="pull-left">测试</span>' +
        '                        </div>' +
        // '                        <div class="details-info">丰富题型，强大逻辑</div>' +
        // '                        <div class="details-info">问卷密码，红包抽奖</div>' +
        '                    </div>' +
        '                    <div class="clear dotted-line--solid"></div>' +
        '                    <a href="javascript:void(0)" class="btn btn-blue-frame editModal" onclick=\'editModal(' + '"' + 1 + '"' + ')\'>编辑</a>' +
        '                    <a href="javascript:void(0)" class="btn btn-blue-frame main__btn--new" style="left:71%" onclick=\'importModal(' + '"' + 1 + '"' + ',' + '测试' + ',' + '测试' + ',' + '测试' + ')\'>导入</a>' +
        '                </div>';
    $("#typeQuestion").append(questionnaireModal_div);
    //}
    // } else if (res.code == "333") {
    //     layer.msg(res.message, {icon: 2});
    //     setTimeout(function () {
    //         window.location.href = 'login.html';
    //     }, 1000)
    // } else {
    //     layer.msg(res.message, {icon: 2})
    // }
}

//创建模板
function createModal() {
    var questionName = $('#questNameModal').val();
    var questionContent = $('#questDescribeModal').val();
    if (questionName == '') {
        layer.msg("模板名称不能为空!", {icon: 2});
        return
    } else if (questionContent == '') {
        layer.msg("模板说明不能为空!", {icon: 2});
        return
    }
    var da = {
        'questionName': questionName,
        'questionContent': questionContent,
        'startTime': "",
        'endTime': "",
        'dataId': $('#belongType').val(),
        'questionStop': '0'
    };
    var url = '/addQuestionnaire';
    //commonAjaxPost(true, url, da, addQuestionnaireSuccess);
    addQuestionnaireSuccess();
}

function deleteQuestionnaireByIdSuccess(res) {
    //console.log(res);
    if (res.code == '666') {
        layer.msg(res.message);
    } else if (res.code == "333") {
        layer.msg(res.message, {icon: 2});
        setTimeout(function () {
            window.location.href = 'login.html';
        }, 1000)
    } else {
        layer.msg(res.message, {icon: 2})
    }
}

//编辑模板
function editModal(questionId) {
    var qId = $.base64.encode(questionId);
    var url = "designQuestionnaire.html?qId=" + qId;//此处拼接内容
    // window.location.href = url;
    window.open(url)
}

//导入模板
function importModal(questionId, questionName, questionContent, projectId) {
    debugger;
    deleteCookie('QuestionId');
    deleteCookie('isEdit');
    deleteCookie('TQuestionName');
    deleteCookie('TQuestionContent');
    //2为导入
    setCookie('isEdit', '2');
    setCookie('QuestionId', questionId);
    setCookie('TQuestionName', questionName);
    setCookie('TQuestionContent', questionContent);
    setCookie('projectIdForCreate', projectId);
    setCookie('dataId', dataId);
    window.location.href = 'namedQuestionnaire.html';
}

//切换所属的调查类型
function changeType() {
    deleteCookie('dataId');
    $("#line").css("display", "none");
    $("#typeQuestion").css("display", "none");
    $("#historyQuestion").css("display", "none");
    var options = $("#belongType option:selected");
    $("#questBelong").val(options.text());
    setCookie('dataId', options.val());
    //console.log(getCookie('dataId'))
}

//创建成功回调
function addQuestionnaireSuccess(res) {
    if (res.code == '666') {
        layer.msg(res.message);
        //打开问卷页面，标识创建成功
        setTimeout(function () {
            // window.history.go(-2);
            window.location.href = 'questionnairesList.html?id=' + proId;
        }, 400)
    } else if (res.code == "333") {
        layer.msg(res.message, {icon: 2});
        setTimeout(function () {
            window.location.href = 'login.html';
        }, 1000)
    } else {
        layer.msg(res.message);
    }

}

//取消按钮
function cancel() {
    //清空输入框
    $('#questNameModal').val('');
    $('#questDescribeModal').val('');

}