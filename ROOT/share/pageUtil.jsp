<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PageUtil</title>
<script type="text/javascript">
(function($){
	var ms = {
		init:function(obj,args){
			return (function(){
				ms.fillHtml(obj,args);
				ms.bindEvent(obj,args);
			})();
		},
		//填充html
		fillHtml:function(obj,args){
			return (function(){
				obj.empty();
				//上一页
				if(args.current > 1){
					obj.append('<a href="javascript:;" class="prevPage">上一页</a>');
				}else{
					obj.remove('.prevPage');
					obj.append('<span class="disabled">上一页</span>');
				}
				//中间页码
				if(args.current != 1 && args.current >= 9 && args.pageCount != 9){
					obj.append('<a href="javascript:;" class="tcdNumber">'+1+'</a>');
				}
				if(args.current-2 > 2 && args.current <= args.pageCount && args.pageCount > 10){
					obj.append('<span>...</span>');
				}
				var start = args.current -2,end =start+9;
				if((start > 1 && args.current < 9)||args.current == 1){
					end++;
				}
				if(args.current > args.pageCount-10 && args.current >= args.pageCount){
					start--;
				}
				for (;start <= end; start++) {
					if(start <= args.pageCount && start >= 1){
						if(start != args.current){
							obj.append('<a href="javascript:;" class="tcdNumber">'+ start +'</a>');
						}else{
							obj.append('<span class="current">'+ start +'</span>');
						}
					}
				}
				if(args.current + 2 < args.pageCount - 1 && args.current >= 1 && args.pageCount > 10){
					obj.append('<span>...</span>');
				}
				if(args.current != args.pageCount && args.current < args.pageCount -2  && args.pageCount != 9){
					obj.append('<a href="javascript:;" class="tcdNumber">'+args.pageCount+'</a>');
				}
				//下一页
				if(args.current < args.pageCount){
					obj.append('<a href="javascript:;" class="nextPage">下一页</a>');
				}else{
					obj.remove('.nextPage');
					obj.append('<span class="disabled">下一页</span>');
				}
			})();
		},
		//绑定事件
		bindEvent:function(obj,args){
			return (function(){
				obj.on("click","a.tcdNumber",function(){
					var current = parseInt($(this).text());
					ms.fillHtml(obj,{"current":current,"pageCount":args.pageCount});
					if(typeof(args.backFn)=="function"){
						args.backFn(current);
					}
				});
				//上一页
				obj.on("click","a.prevPage",function(){
					var current = parseInt(obj.children("span.current").text());
					ms.fillHtml(obj,{"current":current-1,"pageCount":args.pageCount});
					if(typeof(args.backFn)=="function"){
						args.backFn(current-1);
					}
				});
				//下一页
				obj.on("click","a.nextPage",function(){
					var current = parseInt(obj.children("span.current").text());
					ms.fillHtml(obj,{"current":current+1,"pageCount":args.pageCount});
					if(typeof(args.backFn)=="function"){
						args.backFn(current+1);
					}
				});
			})();
		}
	};
	$.fn.createPage = function(options){
		var args = $.extend({
			pageCount :$("#totalPage").val(),
			current :$("#currentPage").val(),
			backFn : function(){}
		},options);
		ms.init(this,args);
	}
})(jQuery);

$(function () {
    $(".tcdPageCode").createPage({
        pageCount:$("#totalPage").val(),
        current:$("#currentPage").val(),
        backFn:function(p){
            console.log(p);
            queryByPage(p);
        }
    });
})
 
function queryByPage(page, div){
	$('#currentPage').val(page);
	var divId = div || 'right-2';
	if(page){
		loadMask();
		$.ajax({type:"post",url: $('#queryForm').attr("action"),data: $('#queryForm').serialize(),
			success:function(msg){
				removeMask();
				$("#" + divId).html(msg);
			}
		});
	}
}
</script>
</head>
<body>
		    <s:hidden name="pageInfo.pageSize" id="pageSize"/>
			<s:hidden name="pageInfo.currentPage" id="currentPage"/>
			<s:hidden name="pageInfo.totalPage" id="totalPage"/>
			<s:hidden name="pageInfo.totalCount" id="totalCount"/>
			<s:hidden name="pageInfo.splitFlag" id="splitFlag"/>
			<s:hidden name="pageInfo.order" id="order"/>
			<s:hidden name="pageInfo.orderFlag" id="orderFlag"/>
			<div class="tcdPageCode"></div>  <a href="#top" id="return_top"><img src="images/zd_128.png"width="26"height="20">
</body>
</html>