/**
 * 图片裁剪显示
 */
function clipImgs(selector) {
	setTimeout(function(){
		$(selector).each(function(){
			var defaultHeight = $(this).parent().height();
			var imgHeight = $(this).height();
			if(imgHeight > defaultHeight){
				var deviation = 0 - ((imgHeight - defaultHeight) * 0.5);
				$(this).css({"margin-top": deviation + "px"});
			}
		});
	}, 10);
	
}
