new_element=document.createElement("script");
new_element.setAttribute("type","text/javascript");
new_element.setAttribute("src","../../js/jquery.cookie.js");
document.body.appendChild(new_element);
layui.use(['form','layer','jquery'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer
        $ = layui.jquery;

    /*$(".loginBody .seraph").click(function(){
        layer.msg("这只是做个样式，至于功能，你见过哪个后台能这样登录的？还是老老实实的找管理员去注册吧",{
            time:5000
        });
    })*/

    //登录按钮
    /*form.on("submit(login)",function(data){
        //var verificationCode = $.sessionStorage.getItem("verificationCode");
        var verificationCode = $.cookie("verificationCode");
        //console.log("asdfasdfasdgasd" + window.sessionStorage.getItem("verificationCode"));

        //var verificationCode = $.session.get("verificationCode");
        //var verificationCode = window.sessionStorage.getItem("verificationCode");
        console.log(verificationCode);
        if(verificationCode !== $.trim($("#verificationCode").val())){
            alert("验证码输入不正确");
            return;
        }
        $.ajax({
            url:"/login",
            type: "POST",
            dataType:"json",
            contentType: "application/json",
            data: JSON.stringify({"userName":$.trim($("#userName").val()),"password":$.trim($("#password").val()),"verificationCode":$.trim($("#verificationCode").val())}),
            success:function (data) {
                if(data.code === 200){
                    window.location.href="/index.html";
                }else{
                    alert("账号密码错误");
                }
            }
        });

        /!*$(this).text("登录中...").attr("disabled","disabled").addClass("layui-disabled");
        setTimeout(function(){
            window.location.href = "/layuicms2.0";
        },1000);
        return false;*!/
    })*/

    //表单输入效果
    $(".loginBody .input-item").click(function(e){
        e.stopPropagation();
        $(this).addClass("layui-input-focus").find(".layui-input").focus();
    })
    $(".loginBody .layui-form-item .layui-input").focus(function(){
        $(this).parent().addClass("layui-input-focus");
    })
    $(".loginBody .layui-form-item .layui-input").blur(function(){
        $(this).parent().removeClass("layui-input-focus");
        if($(this).val() != ''){
            $(this).parent().addClass("layui-input-active");
        }else{
            $(this).parent().removeClass("layui-input-active");
        }
    })
})
