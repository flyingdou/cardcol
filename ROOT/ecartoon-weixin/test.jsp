<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>测试</title>
</head>
<body>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript">
		var json = [
			{"club":"东霆健身万丰路店","account":"15810347776","password":"qwerty","mail":"512833325@qq.com"," pro":"100%"},
			{"club":"东霆健身宣武门店","account":"13601018049","password":"asdzxc","mail":"472911753@qq.com"," pro":"100%"},
			{"club":"东霆健身国贸店","account":"15110250090","password":"qwerty","mail":"1192711878@qq.com"," pro":"100%"},
			{"club":"东霆健身六里桥店","account":"15801386234","password":"iopjkl","mail":"1277240498@qq.com"," pro":"100%"},
			{"club":"东霆健身陶然亭店","account":"18801196847","password":"jklbnm","mail":"1309460658@qq.com"," pro":"100%"},
			{"club":"东霆健身亦庄店","account":"18518160035","password":"zxcvbn","mail":"915309335@qq.com"," pro":"100%"},
			{"club":"东霆健身回龙观店","account":"18510701818","password":"asdfgh","mail":"931005436@qq.com"," pro":"100%"},
			{"club":"东霆健身木樨园店","account":"13701104767","password":"poiuyt","mail":"1316756686@qq.com"," pro":"100%"},
			{"club":"东霆健身东四店","account":"13681165270","password":"lkjhgf","mail":"1425989347@qq.com"," pro":"100%"},
			{"club":"加州铁馆德胜门店","account":"13521715810","password":"qazwsx","mail":"1057881792@qq.com"," pro":"100%"},
			{"club":"加州铁馆甘家口店","account":"13511088846","password":"wsxedc","mail":"303965704@qq.com"," pro":"100%"},
			{"club":"加州铁馆公主坟店","account":"18500348608","password":"edcrfv","mail":"1002733842@qq.com"," pro":"100%"},
			{"club":"加州健身石榴中心店","account":"17610793953","password":"rfvtgb","mail":"577572356@qq.com"," pro":"100%"},
			{"club":"加州铁馆四季青店","account":"17718515815","password":"tgbyhn","mail":"1094852065@qq.com"," pro":"100%"},
			{"club":"加州铁馆中关村店","account":"15811428670","password":"yhnujm","mail":"415140361@qq.com"," pro":"100%"},
			{"club":"锐健身北沙滩店","account":"13552361124","password":"plmokn","mail":"1184378825@qq.com"," pro":"100%"},
			{"club":"锐健身甘露园店","account":"15011393721","password":"okmijn","mail":"552041528@qq.com"," pro":"100%"},
			{"club":"锐健身广安门店","account":"13146842828","password":"ijnuhb","mail":"981700832@qq.com"," pro":"100%"},
			{"club":"锐健身豪园店","account":"17610057572","password":"qweasd","mail":"1181144246@qq.com"," pro":"100%"},
			{"club":"锐健身大兴领海店","account":"18600085752","password":"ygvtfc","mail":"519680391@qq.com"," pro":"100%"},
			{"club":"锐健身帕提欧店","account":"13910516268","password":"tfcrdx","mail":"8929234306@qq.com"," pro":"100%"},
			{"club":"锐健身千鹤店","account":"14794473777","password":"rdxesz","mail":"2292633598@qq.com"," pro":"100%"},
			{"club":"锐健身上林店","account":"15810341348","password":"eszwaq","mail":"479644475@qq.com"," pro":"100%"},
			{"club":"锐健身通州梨园分店","account":"13011008622","password":"ujikop","mail":"963685969@qq.com"," pro":"100%"},
			{"club":"东方浩康健身俱乐部","account":"13381112386","password":"1qa2ws","mail":"850616156@qq.com"," pro":"100%"},
			{"club":"北京观奥园健身中心","account":"13381112386","password":"2ws3ed","mail":"531536213@qq.com"," pro":"100%"},
			{"club":"天奥健身百子湾店","account":"18611015905","password":"3ed4rf","mail":"975921494@qq.com"," pro":"100%"},
			{"club":"天奥健身北苑店","account":"13911197038","password":"4rf5tg","mail":"527097942@qq.com"," pro":"100%"},
			{"club":"天奥健身六里桥店","account":"15010833732","password":"5tg6yh","mail":"1114514032@qq.com"," pro":"100%"},
			{"club":"天奥健身望京店","account":"13701121019","password":"6yh7uj","mail":"743472805@qq.com"," pro":"100%"},
			{"club":"蓝色蜂鸟（非凡）","account":"13888888888","password":"FFYYJS","mail":"785815780@qq.com"," pro":"100%"},
			{"club":"汉水灜","account":"13910704483","password":"HSYJSF","mail":"286032867@qq.com","pro":"100%"},
			{"club":"卡尔森健身上奥世纪店","account":"13264599614","password":"KESJSF"," pro":"100%"},
			{"club":"卡尔森健身三旗百汇","account":"13552219600","password":"KESJSF"," pro":"100%"},
			{"club":" 乐涛轻体运动会所（东环店）","account":"13611116883","password":"LTQTHS"," pro":"100%"},
			{"club":"乐涛养生运动瘦身会所（北环店）","account":"18701117568","password":"LTYSHS"," pro":"100%"},
			{"club":"岚瑜伽（西三旗店）","account":"13552579431","password":"LTYSHS"," pro":"100%"},
			{"club":"力健身俱乐部(三里河1店)","account":"13641318700","password":"LJSJSF"," pro":"100%"},
			{"club":"力健身私教工作室(月坛2店)","account":"15754223443","password":"LJSJSF"," pro":"0%"},
			{"club":"力健身私教工作室(建国门3店)","account":"17319258221","password":"LJSJSF"," pro":"0%"},
			{"club":"千禧国际健身","account":"13701306207","password":"QXGJJS"," pro":"100%"},
			{"club":"胜腾健身俱乐部","account":"13911418222","password":"STJSJLB"," pro":"100%"},
			{"club":"彩虹游泳健身会所","account":"18701333532","password":"CHYYJS"," pro":"100%"},
			{"club":"菲灵健身","account":"18500546880","password":"FLYYJS"," pro":"100%"},
			{"club":"菲灵健身","account":"18911592627","password":"FLJSJLB"," pro":"100%"},
			{"club":"悦健身","account":"18910852817","password":"YJSJSF"," pro":"100%"},
			{"club":"安康健身","account":"13911697687","password":"AKJSJSF"," pro":"100%"},
			{"club":"易加健身（上地店）","account":"13722310121","password":"YJJSSDD",},
			{"club":"易加健身（清河店）","account":"15652977227","password":"YJJSQHD",},
			{"club":"易加健身（辉煌国际店）","account":"18810997650","password":"YJJSGJD",},
			{"club":"易加健身（西二旗店）","account":"18001369158","password":"YJJSXEQD",},
			{"club":"易加健身（航天城店）","account":"18513139741","password":"YJJSHTCD",},
			{"club":"中体力源","account":"13520779555","password":"abc123",},
			{"club":"即客健身","account":"13911317133","password":"JKTYJS",},
			{"club":"千禧园国际健身","account":"13681547777","password":"QXYGJJS",},
			{"club":"英睿健康会","account":"13121801560","password":"YRJKHS",},
			{"club":"乔士尼24小时健身俱乐部","account":"18510096122","password":"QSNJSJLB",},
			{"club":"太阳月亮24小时健身俱乐部","account":"18910021220","password":"TYYLJS",},
			{"club":"爱加健身双井店","account":"18513010325","password":"AJJSSJD",},
			{"club":"爱加健身乐活馆","account":"18501160859","password":"AJJSLHG",},
			{"club":"佰伦斯健身（国贸店）","account":"13469932102","password":"BLSJSF",},
			{"club":"佰伦斯健身（旧宫店）","account":"13469932102","password":"BLSYYJS",},
			{"club":"佰伦斯健身（东路店）","account":"13469932104","password":"BLSDLD",},
			{"club":"佰伦斯健身（皇宛店）","account":"13469932105","password":"BLSHYD",},
			{"club":"佰伦斯健身（燕莎店）","account":"13469932106","password":"BLSYSD",},
			{"club":"佰伦斯健身（卡布店）","account":"13469932107","password":"BLSKBD",},
			{"club":"佰伦斯健身（远洋店）","account":"134699321","password":"BLSYYD",},
			{"club":"佰伦斯健身（中康店）","account":"134699325","password":"BLSZKD",},
			{"club":"佰伦斯健身（锦上店）","account":"1893251276","password":"BLSJSD",},
		];
		
		$.ajax({
			url:"ecoursewx!test.asp",
			type:"post",
			data:{
				json : encodeURI(JSON.stringify(json))
			},
			dataType:"json",
			success:function(res){
				alert("ok");
			}
		});
	</script>
</body>
</html>