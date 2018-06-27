/**
 * Created by Administrator on 2016/10/25.
 */
$(function () {
    $('#play').flexslider({
        animation: "slide",
        direction: "horizontal",
        easing: "swing",
        slideshowSpeed: 2000
    });


    $(".challenge_cause>span").on("mouseover", function () {
        $(this).css("backgroundColor","#88aa28")
    }).on("mouseout", function () {
        $(this).css("backgroundColor","")
    });
});
