/**
 * Created by Administrator on 2016/10/25.
 */
$(function () {
    $(".left_list").on("mouseover", function () {
        $(this).css("color","#fff")
        $(this).css("backgroundColor","#92ba23");
        $(".left_list").find(".inner_list").hide();
        $(this).find(".inner_list").show();
    }).on("mouseout", function () {
        $(this).css("color","")
        $(this).css("backgroundColor","")
        $(this).find(".inner_list").hide();
    });

/*
    $(".inner_list").find("span").on("mouseover",function(){
        $(this).css("color","red");
    }).on("mouseout", function () {
        $(this).css("color","");
    });
*/

    /*轮播*/
    $('#play').flexslider({
        animation: "slide",
        direction:"horizontal",
        easing:"swing"
    });

    $(".inner_list1").on("click", "span",function () {
        var oId=$(this).parent().attr("id") ;   //父级的id名字
        if ($(this).hasClass("select-all")) {
            $('.'+oId).remove();
        } else {
            var $p='<span class="'+oId+'">'+$(this).text()+'<img src="images/cha-icon_03.png"></span>';
            if($('.'+oId).length >0){
                $('.'+oId).html($(this).text()+'<img src="images/cha-icon_03.png">');
            }else{
                $(".chose .list_span").append($p);
            }
        }
    });

    $(".list_span").on("click","img", function () {
        $(this).parent().remove();
    });
})
