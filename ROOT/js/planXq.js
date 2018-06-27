/**
 * Created by Administrator on 2016/10/25.
 */
$(function () {
/*计划详情与评价*/
    $(".plan").on("click", function () {
        var i=$(this).index();
        $(".plan").css("backgroundColor","#353535");
        $(this).css("backgroundColor","#a5c648");
        $(".plan_nav").hide();
        $(".plan_nav").eq(i).show();
    });

    $(".rated >.list").find("a").on("click", function () {
        $(".rated >.list").find("a").css("color","#000");
        $(this).css("color","red");
    })
});