/**
 * Created by Administrator on 2016/11/1.
 */
$(function () {
	
	
    $(".tcdPageCode").createPage({
        pageCount:10,
        current:1,
        backFn:function(p){
            console.log(p);
        }
    });
})