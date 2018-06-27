/**
 * Created by Administrator on 2016/10/25.
 */
$(function () {
    /*页面回到顶部*/
    $("#return_top").click(function(){
        $('body,html').animate({scrollTop:0},1000);
        return false;
    });
})
