/**
 * Created by Administrator on 2016/11/11.
 */
$(function () {


    /*列表切换*/
    $(".table_title>ul>li").on("click", function () {
        var i=$(this).index();
        $(".table_title>ul>li").css("background-image","none");
        $(".table_title>ul>li").css("border-color","#fff");
        $(this).css("background-image","url(images/T_xsj_06.png)");
        $(this).css("background-repeat","no-repeat");
        $(this).css("background-position","bottom center");
        $(this).css("border-color","#fe9e3a");
        $(".table_list").hide();
        $(".table_list").eq(i).show();
    });

    /*分享*/
    $(".share").on("click", function () {
        $(".share_bg").fadeIn();
    });
    $(".quXiao").on("click", function () {
        $(".share_bg").fadeOut();
    });
    $(".share_bg").on("click", function () {
        $(this).fadeOut();
    });
    $(".inner_top").on("click", function (e) {
        e.stopPropagation();//阻止事件
    });

    /*单选框方式1*/

    $(".check_one").on("click", function () {
        if($(this).is(":checked")){
            $(".label_one").css("background","none");
            $(".label_one").css("border","0.1rem #9b9b9b solid");
            $(".custom").hide();
            $(".custom_click").css("background","none");
            $(".custom_click").css("border","0.1rem #9b9b9b solid");
            $(".right").css("transform","rotate(360deg)");
            $(".custom_txt").css("color","#333");
            $(this).parent().css("background","url('images/xt_checked_icon_06.png') no-repeat");
            $(this).parent().css("background-size","100% 100%");
            $(this).parent().css("border","none");
        }else {
            $(this).parent().css("background","url('images/xt_checked_icon_06.png') no-repeat");
            $(this).parent().css("background-size","100% 100%");
            $(this).parent().css("border","none");
        }
    });


    $(".check_two").on("click", function () {
        if($(this).is(":checked")){
            $(".label_two").css("background","none");
            $(".label_two").css("border","0.1rem #9b9b9b solid");
            $(this).parent().css("background","url('images/xt_checked_icon_06.png') no-repeat");
            $(this).parent().css("background-size","100% 100%");
            $(this).parent().css("border","none");
        }else {
            $(this).parent().css("background","url('images/xt_checked_icon_06.png') no-repeat");
            $(this).parent().css("background-size","100% 100%");
            $(this).parent().css("border","none");
        }
    });

    /*单选框选择2*/
    $(".check_three").on("click", function () {
        if($(this).is(":checked")){
            $(".label_three>span").css("background","none");
            $(this).prev().css("background","url('images/check_radio_10.png') no-repeat center");
            $(this).prev().css("background-size","70% 70%")
        }else {
            $(".label_three>span").css("background","none");
            $(this).prev().css("background","url('images/check_radio_10.png') no-no-repeat center");
        }
    });

        /*表单获取焦点改变外边框*/
        $("input[type=text]").focus(function () {
            $(this).css("border-color","#f0ad4e");
        }).blur(function () {
            $(this).css("border-color","")
        });

});


