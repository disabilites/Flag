var errorInfo = $("#errorInfo");
function signIn() {

    $("[name='name']").blur(function () {
        if ($(this).val() == "") {
            errorInfo.text("你的用户名呢？");
        }else {
            errorInfo.text("");
        }
    });

    $("[name='password']").blur(function () {
        if ($(this).val() == ""){
            errorInfo.text("没有密码可不能登录");
        }else if($(this).val().length < 6){
            errorInfo.text("密码最少都有六位呢");
        }else {
            errorInfo.text("");
        }
    });

    $("#login").click(function () {
        if ($("[name='name']").val() == "" || $("[name='password']").val() == ""){
            errorInfo.text("用户名或密码错误");
            return false;
        }
        $.ajax({
            url: "/login",
            type: "post",
            data: {
                "name": $("[name='name']").val(),
                "password": $("[name='password']").val()
            },
            beforeSend: function () {
                $("#login").attr({ disabled: true});
                $("#loading").show();
            },
            success: function (data) {
                if (!data.state) {
                    errorInfo.text(data.msg);
                }else {
                    window.location.href="/flag";
                }
            },
            complete: function () {
                $("#login").attr({ disabled: false});
                $("#loading").hide();
            }
        })
    });

    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#login").click();
        }
    });
}

function signUp() {
    $("[name='name']").blur(function () {
        if ($(this).val() == "") {
            errorInfo.text("你的用户名呢？");
        }else {
            errorInfo.text("");
        }
    });

    $("[name='email']").blur(function () {
        console.log("邮箱验证");
        var reg = /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/;
        var regExp = new RegExp(reg);
        if(!regExp.test($(this).val())){
            errorInfo.text("邮件格式不正确");
        }else {
            errorInfo.text("");
        }
    });

    $("[name='password']").blur(function () {
        if ($(this).val() == ""){
            errorInfo.text("密码不能为空");
        }else if($(this).val().length < 6){
            errorInfo.text("密码至少六位");
        }else if ($("[name='password2']").val() != "") {
            if ($(this).val() != $("[name='password2']").val()){
                errorInfo.text("密码不一致");
            }
        }else {
            errorInfo.text("");
        }
    });

    $("[name='password2']").blur(function () {
        if ($(this).val() != $("[name='password']").val()){
            errorInfo.text("密码不一致");
        } else {
            errorInfo.text("");
        }
    });

    $("#register").click(function () {
        if ($("[name='name']").val() == "" || $("[name='email']").val() == null || $("[name='password']").val() == null
            || $("[name='password2']").val() == null){
            errorInfo.text("信息不完整");
            return false;
        }
        if ($("[name='password']").val() != $("[name='password2']").val()){
            errorInfo.text("密码不一致");
            return false;
        }
        $.ajax({
            url: "/register",
            type: "post",
            data: {
                "name": $("[name='name']").val(),
                "email": $("[name='email']").val(),
                "password": $("[name='password']").val()
            },
            beforeSend: function () {
                $("#register").attr({ disabled: true});
                $("#loading").show();
            },
            success: function (data) {
                if (data.state) {
                    errorInfo.text(data.msg);
                }else {
                    alert("注册成功，请到邮箱验证");
                    window.location.href="/wait";
                }
            },
            complete: function () {
                $("#register").attr({ disabled: false});
                $("#loading").hide();
            }
        })
    });

    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#register").click();
        }
    });
}

function changePassword() {
    $("[name='password']").blur(function () {
        if ($(this).val() == ""){
            errorInfo.text("密码不能为空");
        }else if($(this).val().length < 6){
            errorInfo.text("密码至少六位");
        }else if ($("[name='password2']").val() != "") {
            if ($(this).val() != $("[name='password2']").val()){
                errorInfo.text("密码不一致");
            }
        }else {
            errorInfo.text("");
        }
    });

    $("[name='password2']").blur(function () {
        if ($(this).val() != $("[name='password']").val()){
            errorInfo.text("密码不一致");
        } else {
            errorInfo.text("");
        }
    });

    $("#changePassword").click(function () {
        if ($("[name='password']").val() == null || $("[name='password2']").val() == null){
            errorInfo.text("信息不完整");
            return false;
        }
        if ($("[name='password']").val() != $("[name='password2']").val()){
            errorInfo.text("密码不一致");
            return false;
        }
        $.ajax({
            url: "/change",
            type: "post",
            data: {
                "code": $("[name='code']").val(),
                "password": $("[name='password']").val()
            },
            beforeSend: function () {
                $("#changePassword").attr({ disabled: true});
                $("#loading").show();
            },
            success: function (data) {
                if (data.state) {
                    alert(data.msg);
                    window.location.href="/logout";
                }else {
                    alert(data.msg);
                    window.location.href="/signIn";
                }
            },
            complete: function () {
                $("#changePassword").attr({ disabled: false});
                $("#loading").hide();
            }
        })
    });

    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#changePassword").click();
        }
    });
}

function forgetPassword() {
    $("#reset").click(function () {
        console.log($("[name='name']").val());
        if ($("[name='name']").val() == ""){
            alert("你还没填写用户名");
            return false;
        }
        $.ajax({
            url: "/checkUser",
            type: "post",
            data: {
                "name": $("[name='name']").val()
            },
            beforeSend: function () {
                $("#reset").attr({ disabled: true});
                $("#loading").show();
            },
            success: function (data) {
                if (data.state){
                    alert("邮箱正在路上…");
                }else {
                    alert("查无此人");
                }
            },
            complete: function () {
                $("#reset").attr({ disabled: false});
                $("#loading").hide();
            }
        })
    });

    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#reset").click();
        }
    });
}

function save(id) {
    var E = window.wangEditor;
    var editor = new E('#editor');
    editor.customConfig.menus = [
        'head',  // 标题
        'bold',  // 粗体
        'fontSize',  // 字号
        'underline',  // 下划线
        'strikeThrough',  // 删除线
        'foreColor',  // 文字颜色
        'backColor',  // 背景颜色
        'link',  // 插入链接
        'list',  // 列表
        'justify',  // 对齐方式
        'quote',  // 引用
        'code'// 插入代码
    ];
    editor.create();

    if($('#remind').prop('checked') === true){
        $('#showTime').prop('style','display:inline-block');
    }

    $('#remind, #notRemind').on('click', function(){
        if($('#remind, #notRemind').prop('checked') === true){
            $('#showTime').prop('style','display:inline-block');
        }else{
            $('#showTime').attr('style','display: none');
        }
    });

    $("#save").click(function () {
        var title = $("[name='title']").val();
        var content = editor.txt.html();
        var saveTime = new Date;
        var remind = 0;
        var mytop = 0;
        var remindTime = $("[name='remindTime']").val();


        if (title == "") {
            alert("你忘了填写标题");
            return false;
        }

        if (editor.txt.text() == ""){
            alert("你还没立Flag");
            return false;
        }

        if ($("[name='remind']").prop('checked') === true){
            remind = 1;
        }
        if ($("[name='top']").prop('checked') === true){
            mytop = 1;
        }


        if (remind == 1){
            var reg = /((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) ([01]?[0-9]|2[0-3]):[0-5][0-9]/;
            var regExp = new RegExp(reg);
            if(!regExp.test(remindTime)){
                alert("时间格式不正确,正确格式为: 2019-01-01 12:00");
                return;
            }else {
                remindTime = new Date(remindTime);
            }
        }else {
            remindTime = new Date("2000-01-01 00:00");
        }

        var flag = {
            'title': title,
            'content': content,
            'saveTime': saveTime,
            'remind': remind,
            'top': mytop,
            'remindTime': remindTime
        };

        $.ajax({
            url: "/flag/save/" + id ,
            type: "post",
            data: flag,
            success:function () {
                alert("保存成功");
                window.location.href=document.referrer;
            }
        })
    });

    $("#cancel").click(function () {
        window.history.back();
    });
}

function flag() {
    $("[name='delete']").click(function () {
        var msg = "确定要拔掉这个Flag？";
        if (confirm(msg)==true){
            return true;
        }else{
            return false;
        }
    });

    $("#changePassword").click(function () {
        $.ajax({
            url: "/checkUser",
            type: "post",
            data:{
                "name": $("#username").text()
            },
            beforeSend: function () {
                $("#username").attr({ disabled: true});
                $("#loading").show();
            },
            success:function (data) {
                if (data.state){
                    alert("邮箱正在路上…");
                }else {
                    alert("发生未知错误");
                }
            },
            complete: function () {
                $("#username").attr({ disabled: false});
                $("#loading").hide();
            }
        })
    });
}