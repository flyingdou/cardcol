/**
 * Created by Administrator on 2017/5/10.
 */
$(function() {

    if($(document.body).width()<500){
        $('.zc').click(function(){
            $(".dl").addClass("zc1");
            $(".zc").addClass("dl1");
        });

        $('.dl').click(function(){
            $(".dl").removeClass("zc1");
            $(".zc").removeClass("dl1");
        });

        }



    $('.zc').click(function(){
        $("#dl").css("display","none");
        $("#zc").css("display","block");
        $(".error").html("");
    });
    $('.dl').click(function(){
        $("#zc").css("display","none");
        $("#dl").css("display","block");
        $(".error").html("");

    });
    $("#wlmm").click(function(){
        $("#dl").css("display",'none');
        $("#wjmm").css("display",'block');
        $(".error").html("");
    });
    $(".arrowR").click(function(){
        $("#wjmm").css("display",'none');
        $("#dl").css("display",'block');
        $(".error").html("");

    });
//---------------------------点击切换结束----------------------------------------


    $('.rAd label').click(function(){
        var radioId = $(this).attr('name');
        $('.rAd label').removeClass("checked")&&$(this).addClass("checked")
    });

    //---------------------------单选样式切换结束----------------------------
    $("#phone").blur(function(){
        var nc=/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
        if ($(this).val() == ""
        ) {
            $(".error").html("账号不能为空").css("color", "red");
            return false;
        } else if (!nc.test($(this).val())) {
            /*$(".error").html("账号有误！").css("color", "red");*/
            return false;
        } else {
            $('.error').html("").css("color", "blue");
            return true;
        }
    });
    $("#dlpsw").blur(function(){
        var nc=/^\w{6,13}$/;
//            6-13位数字，字母下划线
        if ($(this).val() == ""
        ) {
            $(".error").html("密码不能为空").css("color", "red");
            return false;
        } else if (!nc.test($(this).val())) {
            $(".error").html("密码有误！").css("color", "red");
            return false;
        } else {
            $('.error').html("").css("color", "blue");
            return true;
        }
    });



    //----------------------------------登录验证结束-----------------------------------
    $("#phone1").blur(function(){
        var nc=/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
        if ($(this).val() == ""
        ) {
            $(".error").html("号码不能为空").css("color", "red");
            return false;
        } else if (!nc.test($(this).val())) {
            $(".error").html("号码有误！").css("color", "red");
            return false;
        } else {
            $('.error').html("").css("color", "blue");
            return true;
        }
    });
    $("#zcyzm").blur(function(){
        if ($(this).val() == ""
        ) {
            $(".error").html("验证不能为空").css("color", "red");
            return false;
        }else{
            return ture;
        }
    });

    $("#dlpsw1").blur(function(){
        var nc=/^\w{6,13}$/;
//            6-13位数字，字母
        if ($(this).val() == ""
        ) {
            $(".error").html("密码不能为空").css("color", "red");
            return false;
        } else if (!nc.test($(this).val())) {
            $(".error").html("密码有误！").css("color", "red");
            return false;
        } else {
            $('.error').html("").css("color", "blue");
            return true;
        }
    });

    //-------------------------注册验证结束-----------------------------------
    $("#phone2").blur(function(){
        var nc=/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
        if ($(this).val() == ""
        ) {
            $(".error").html("号码不能为空").css("color", "red");
            return false;
        } else if (!nc.test($(this).val())) {
            $(".error").html("号码有误！").css("color", "red");
            return false;
        } else {
            $('.error').html("").css("color", "blue");
            return true;
        }
    });

    $(".wlmmyz").blur(function(){
        if ($(this).val() == ""
        ) {
            $(".error").html("验证不能为空").css("color", "red");
            return false;
        }else{
            return ture;
        }
    });
    $("#wlpsw").blur(function(){
        var nc=/^\w{6,13}$/;
//            5-12位数字，字母
        if ($(this).val() == ""
        ) {
            $(".error").html("密码不能为空").css("color", "red");
            return false;
        } else if (!nc.test($(this).val())) {
            $(".error").html("密码为6-13位").css("color", "red");
            return false;
        } else {
            $('.error').html("").css("color", "blue");
            return true;
        }
    });
    $("#wlpsw1").blur(function(){
        if ($(this).val() == ""
        ) {
            $(".error").html("密码不能为空").css("color", "red");
            return false;
        }else if ($(this).val()!=$("#wlpsw").val()){
            $(".error").html("两次输入不一致").css("color", "red");
            return false;
        }else{
            return true;
        }
    })
    //-----------------------------忘了密码结束------------------------------


});







function dlyz(){
    var nc = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
    var nc1 =/^\w{6,13}$/;
    if(!nc.test($("#phone").val())){
        alert(1)
        return false
    }else if(!nc1.test($("#dlpsw").val())){
        alert(2);
        return false
    } else{
        alert(ok)
        return true
    }
}

function zcyz(){
    var nc = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
    var nc1 =/^\w{6,13}$/;
    if(!nc.test($("#phone1").val())){
        return false
    }else if(!nc1.test($("#dlpsw1").val())){
        return false
    }else if($("#zcyz").val()==""){
        return false
    } else{
        return true
    }
}

function wlmm(){
    var nc=/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
    var nc1=/^\w{6,13}$/;
    if(!nc.test($("#phone2").val())){
        alert(1)
        return false;
    }else if(
        $("#zcyz").val()==""
    ){
        alert(2)
        return false;
    }
    else if(!nc1.test($("#wlpsw").val())){
        return false
    }
    else if($("#wlpsw1").val()!=$("#wlpsw").val()){
        alert(4)
        return false;
    }else{
        return true
    }
}