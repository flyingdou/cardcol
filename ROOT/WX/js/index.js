/**
 * Created by Administrator on 2016/11/8.
 */


$(function () {
    /*奖惩页面里的完成*/
    $(".assign>.click").on("click", function () {
        $(this).hide();
        $(".finish_bg").show();
        $(".bottom").show();
    });
    $(".close").on("click", function () {
        $(".finish_bg").hide();
    });


    /*挑战目标里的点击自定义按钮展开*/
    $(".custom_click").on("click", function () {
        $(".label_one").css("background","none");
        $(".label_one").css("border","0.1rem #9b9b9b solid");
        $(this).css("background","url('images/xt_checked_icon_06.png') no-repeat");
        $(this).css("background-size","100% 100%");
        $(this).css("border","none");
        $(this).next().css("color","#fe9e3a");
        $(".right").css("transform","rotate(90deg)");
        $(".custom").show();
    });

    /*条件判定左右移动*/
    $(".range").on("click", function () {
       var offset=$(this).find("span").offset().left-$(this).offset().left;
        if($(this).parent().find("input").is(":checked")){
            if(offset==1){
                $(this).find("span").css("left","2rem");
            }else {
                $(this).find("span").css("left","0");
            }
        }else {
            $(".last>p").fadeIn();
            time=setInterval(function () {
                $(".last>p").fadeOut();
            },2000);

        }
    });

    /*系统详情和引擎详情*/
        var num=1;
    $(".assign>.go").on("click", function () {
        $(".expert_title").find("span").css("border-color","#fff");
        $(".expert_title").find("span").css("background-color","#b0b0b0");
        $(".expert_title").find("span").eq(num).css("border-color","#fb6f54");
        $(".expert_title").find("span").eq(num).css("background-color","#fe9e3a");
        $(".expert_list").hide();
        $(".expert_list").eq(num).show();
        num++;
        if(num==$(".expert_list").length){
            $(this).text("完成");
            $(".assign>.go").off();
        }
    });

    /*弹出设置性别框*/
    $("#sex").on("click", function () {
        $(".share_bg").show();
    });
    $(".share_bg").on("click", function (e) {
            $(this).hide();
    });

    /*点击性别设置值*/
    $(".sex>p").on("click", function () {
        var B=$(this).text();
        $("#sex>input").val(B);
        $(".share_bg").hide();
    });

    /*智能引擎里的健身历史*/
    $(".horst>label>input[type=checkbox]").on("click", function () {
        if($(this).is(":checked")){
            $(this).next().css("backgroundColor","#fe9e3a");
            $(this).next().css("color","#fff")
        }else {
            $(this).next().css("backgroundColor","#e3e3e3");
            $(this).next().css("color","#999999")
        }
    });
    $(".liLiang>a").on("click", function () {
        $(".liLiang>a").css("backgroundColor","#e3e3e3");
        $(".liLiang>a").css("color","#999999");
        $(this).css("backgroundColor","#fe9e3a");
        $(this).css("color","#fff")
    });
    $(".youYang>a").on("click", function () {
        $(".youYang>a").css("backgroundColor","#e3e3e3");
        $(".youYang>a").css("color","#999999");
        $(this).css("backgroundColor","#fe9e3a");
        $(this).css("color","#fff")
    });


    /*聘请教练左边边框线*/
    $(".engage_teach").find(".list").each(function (i) {
        if(i%2==1){
            $(".engage_teach").find(".list").eq(i).css("background","#fff url('images/teach_bg_02.png') no-repeat");
            $(".engage_teach").find(".list").eq(i).css("background-size","0.3rem 100%")
        }else {
            $(".engage_teach").find(".list").eq(i).css("background","#fff url('images/teach_bg_09.png') no-repeat");
            $(".engage_teach").find(".list").eq(i).css("background-size","0.3rem 100%")
        }
    });

    /*教练详情切换*/
    $(".teach_title").find("li").on("click", function (a) {
        var i=$(this).index();
        $(".teach_title").find("a").css("color","#333");
        $(".teach_title").find("a").css("border-color","#fff");
        $(this).find("a").css("color","#fe9e3a");
        $(this).find("a").css("border-color","#fe9e3a");
        $(".teach_list").hide();
        $(".teach_list").eq(i).show();
    });

        /*计划筛选功能*/
    $(".planChoice>ul>li").each(function (i) {
        $(".planChoice>ul>li").eq(i).find("a").on("click", function () {
            if($(this).hasClass("all")){
                $(".planChoice>ul>li").eq(i).find("a").css("color","");
                $(".planChoice>ul>li").eq(i).find("a").css("border-color","");
            }else {
                $(".planChoice>ul>li").eq(i).find("a").css("color","");
                $(".planChoice>ul>li").eq(i).find("a").css("border-color","");
                $(this).css("color","#fe9e3a");
                $(this).css("border-color","#fe9e3a");
            }
        })
    });

    /*计划详情里星星评分*/
    var score=100;
    var ImgSrc="images/wujiaoxing1_03.png";
    $(".wjx>img").each(function (i) {
        var num=parseFloat(score/20);
        if(i<num){
            $(".wjx>img").eq(i).attr("src",ImgSrc)
        }
    });

    /*体态分析*/
    $(".table_nav>li").on("click", function () {
        var i=$(this).index();
        $(".table_nav>li").css("backgroundColor","");
        $(this).css("backgroundColor","red");
        $(".table_list").hide();
        $(".table_list").eq(i).show();
    });
});






