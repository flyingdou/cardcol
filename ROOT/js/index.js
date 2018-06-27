/**
 * Created by Administrator on 2016/10/25.
 */
$(function(){
    $(".find").find("a").on("mousemove", function () {
        $(this).css("backgroundColor","#a4bb2c");
        $(this).parent().css("borderColor","#a4bb2c");
        $(this).parent().prev().css("borderColor","#a4bb2c")
    }).on("mouseout", function () {
        $(this).css("backgroundColor","");
        $(this).parent().css("borderColor","");
        $(this).parent().prev().css("borderColor","")
    });
    
   


    /*轮播*/
    $('#demo').flexslider({
        animation: "slide",
        direction:"horizontal",
        easing:"swing"
    });
    

 
})

