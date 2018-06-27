/**
 * Created by Administrator on 2016/10/25.
 */
$(function () {

    $(".left>ul>li>span").on("click", function () {
        $(this).parent().siblings().find(".left_option").hide();
        $(this).next().toggle();
        if($(this).next().is(":visible")==true){
            $(".left>ul>li>span").find("i").css("transform","");
            $(this).children().css("transform","rotate(90deg)");
        }else {
            $(this).children().css("transform","");
        }
    });

    /*轮播*/
    $('#demo').flexslider({
        animation: "slide",
        direction:"horizontal",
        easing:"swing"
    });
    $(".left_option1").on("click", "p",function () {
        var oId=$(this).parent().attr("id") ;   //父级的id名字
        if ($(this).hasClass("select-all")) {
            $('.'+oId).remove();
        } else {
            var $p='<p class="'+oId+'"><a>'+$(this).text()+'<img src="images/cha-icon_03.png"></a></p>';
            if($('.'+oId).length >0){
                $('.'+oId).html('<a>'+$(this).text()+'<img src="images/cha-icon_03.png">');
            }else{
                $(".chose .list_span").append($p);
            }
        }
    });
    $(".list_span").on("click","img", function () {
        $(this).parent().parent().remove();
    });
});
