function coursePurchase(id) {
		if (loginId === '') {
			openLogin();
			return;
		}
		$.ajax({
			type : "post",
			url : "coursewindow!onJoin.asp",
			data : 'course.id=' + id,
			success : function(msg) {
				var $msg = $.parseJSON(msg);
				if ($msg.success === true) {
					if($msg.message=== 'pay'){
				        window.location.href = 'order!submitProd.asp?prodType=5&id='+id;
					}else{
						alert('您的申请已经成功提交，请等待俱乐部的审核！');
					}
				} else {
					if ($msg.message=== 'full') {
						alert('当前课程的人数已经达到上限，不能进行申请！');
					} else if ($msg.message === 'joining') {
						alert('您已经申请过该课程，请不要重复进行申请！');
					} else if ($msg.message === 'exist') {
						alert('您当前时间已经有安排的课程，请换个时间段再申请！');
					} else if($msg.message=== 'nomemeber'){
						alert('您还不是当前俱乐部会员，请先加入该俱乐部！');
					}else {
						alert('未知错误，请联系系统设计人员。可能的原因为：' + msg.message);
					}
				}
			}
		});
	}