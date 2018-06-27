<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:include value="/share/meta.jsp"/>
<meta name="Generator" content="EditPlus">
<meta name="Author" content="">
<meta name="Keywords" content="注册成为健身E卡通会员">
<meta name="Description" content="保持优良的体质体形，从健身开始，赶快注册参加健身计划吧">
<title>网站注册</title>
 <link type="text/css" href="css/bootstrap.css" rel="stylesheet">
 <link type="text/css" href="css/base.css" rel="stylesheet">
 <link rel="stylesheet" type="text/css" href="css/register-2.css" />
<script type="text/javascript" src="script/FormValidator/formValidator-4.1.1.js"></script>
<script type="text/javascript" src="script/FormValidator/formValidatorRegex.js"></script>
<script type="text/javascript" src="script/FormValidator/DateTimeMask.js" language="javascript" ></script>
<script type="text/javascript" src="script/area.js"></script>
<script type="text/javascript" src="script/jquery.choosearea.js"></script>
<script type="text/css">
	.onError_top{
		width:0px;
	}
</script>
<script language="javascript">
$(document).ready(function(){
	$('#birthday').datepicker({autoSize: true,showMonthAfterYear:true,changeMonth:true,yearRange:'c-50:c+5',changeYear:true});
	$.formValidator.initConfig({formID:"regForm",theme:"ArrowSolidBox",submitOnce:true,type:'POST', mode:"AutoTip",onError:function(msg){},onSuccess:function(){
		var checked = $('.pact-1').attr('checked');
		if (!checked || checked != 'checked') {
			alert('如果您要注册成为本站用户，必须同意本站服务协议！');
			return false;
		};
		var role = $('input[name="member.role"]:checked').val();
		if (role != 'E') {
			var birthday = $('#birthday').val();
			if (birthday == '') {
				alert('您的出生日期必须输入！');
				return false;
			}
			var test = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})/;
			if (!test.test(birthday)){ 
				alert("您输入的出生日期格式不正确，请重新输入!");
				return false;
			}
		}
		if ($('#province').val() == '' || $('#city').val() == '') {
			alert('您所在的地区必须输入！');
			return false;
		}
		$('#password_cry').val(md5($('#password').val()));
		return true;
	}});
	$("#nick").formValidator({onShowText:"请输入用户昵称",onFocus:"用户昵称至少4个字符,最多20个字符"}).inputValidator({min:4,max:20,onError:"用户昵称不能为空,并且最少4个，最大20个长度。请确认"})
		.ajaxValidator({type: 'POST', dataType: 'html', async: false, url: 'register!checkName.asp', onWait: '正在对用户昵称进行合法性校验，请稍候...',
			onError: '该用户昵称已经被注册过，请重新输入！',
			success: function(data) {
				if (data == 'OK') return true;
				return false;
			}
		}).defaultPassed();
	$("#password").formValidator({onShowText:"请输入您登录本站的密码！"}).inputValidator({min:6,max:20,onError:"密码不能为空且您的密码最少6个字符，最大20个字符,请确认"});
	$("#password1").formValidator({onShowText:"请输入您登录本站的密码！"}).inputValidator({min:6,max:20,onError:"重复密码不能为空且您的密码最少6个字符，最大20个字符,请确认"}).compareValidator({desID:"password",operateor:"=",onError:"2次密码不一致,请确认"});
	$("#email").formValidator({defaultValue:"@"}).inputValidator({min:1,max:100,onError:"你输入的邮箱长度非法,请确认"}).regexValidator({regExp:"^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$",onError:"你输入的邮箱格式不正确"})
		.ajaxValidator({dataType: 'html', async: false, url: 'register!checkMail.asp', onWait : "正在对电子邮箱进行合法性校验，请稍候...",
			onError : "该电子邮箱已经被注册过，请重新输入",
			success: function(data) {
				if (data == 'OK') return true;
				return false;
			}
		}).defaultPassed();
	$( "#seragree" ).dialog({autoOpen: false, show: "blind", hide: "blind", width:650});
	$( ".left2spana" ).click(function() {
		$( "#seragree" ).dialog( "open" );
		return false;
	});
	var chooseAreaApp1 = new $.choosearea({
		selectDomId : {
			province : "province",
			city : "city",
			county : "county"
		},
		initAreaNames : {
			province : '<s:property value="member.province" escape="false"/>',
			city : '<s:property value="member.city" escape="false"/>',
			county : '<s:property value="member.county" escape="false"/>'	
		},
		data : data
	});
});
</script>
<script type=text/javascript>
	function setTab03Syn(i) {
		selectTab03Syn(i);
	}
	function selectTab03Syn(i) {
		switch (i) {
		case 1:
			document.getElementById("TabTab03Con1").style.display = "block";
			document.getElementById("TabTab03Con2").style.display = "none";
			document.getElementById("font1").style.color = "#000000";
			document.getElementById("font2").style.color = "#ffffff";
			break;
		case 2:
			document.getElementById("TabTab03Con1").style.display = "none";
			document.getElementById("TabTab03Con2").style.display = "block";
			document.getElementById("font1").style.color = "#ffffff";
			document.getElementById("font2").style.color = "#000000";
		}
	}
</script>
</head>

<body>
		<s:include value="/newpages/head.jsp"/>
		<div class="top_bg"></div>
		<div id="user">
			<div id="user-1">用户注册</div>
			<!--<div id="user-2">
				<p>新浪微博链接成功，填写下面内容完善账号</p>
			</div>-->   <!--暂时隐藏的-->
			<div id="user-left">
				<!--<div id="bg" class="xixi1">
					<div id="font1" class="tab1" onMouseOver="setTab03Syn(1);document.getElementById('bg').className='xixi1'">还没有注册卡库网</div>
					<div id="font2" class="tab2" onMouseOver="setTab03Syn(2);document.getElementById('bg').className='xixi2'">已经注册了卡库网</div>
				</div>-->    <!--暂时隐藏的-->
				<div id=TabTab03Con1>
					<s:form name="regForm" id="regForm" method="post" theme="simple" action="register!save.asp">
					<s:hidden name="member.password" id="password_cry"/>
					<div id="user-left0">
						<!--<p style="color: #666;">将为你创造一个新的账号并且和新浪账号绑定</p>-->   <!--暂时隐藏的-->
						<div class="left1">
							用户类型：<span class="left1-1"><s:radio name="member.role" id="role" list="#{'M':'健身会员','S':'健身教练','E':'健身俱乐部','I':'慈善机构'}" value="'M'" /></span>
						</div>
						<div class="p_div">
							<p>
								<span>用&nbsp户&nbsp名：</span> <input type="text" name="member.nick" id="nick" />
							</p>
						</div>
						<!--
						<div class="left1" id="divsex">
							性&nbsp;&nbsp;&nbsp;&nbsp;别：<span class="left-1"><s:radio name="member.sex" id="sex" list="#{'M':'男','F':'女'}" value="'M'"/></span>
						</div>
						-->
						<div class="p_div">
							<p>
								<span>密&nbsp;&nbsp;&nbsp;&nbsp;码：</span> <input type="password" name="member.password" id="password" />
							</p>
						</div>
						<div class="p_div">
							<p>
								<span>确认密码：</span> <input type="password" name="password1" class="list-2" id="password1" />
							</p>
						</div>
						<div class="p_div">
							<p>
								<span>电子邮箱：</span> <input type="text" name="member.email" class="list-2" id="email" />
							</p>
						</div>
						<div class="p_div">
							<p>
								<span>出生日期：</span><input type="text" name="member.birthday" class="input_public" id="birthday"/>
								<!--<span class="spanp">为了健身计划的科学性，请准确出入你的出生日期。格式：1982-02-11</span>-->
							</p>
						</div>
						<div class="p_div">
							<p class="location">所在地区： 
								<select name="member.province" id="province" ></select>
								<select name="member.city" id="city" ></select>
								<select name="member.county" id="county" ></select>
							</p>
						</div>
							<div class="left2">
								<input name="checkbox" type="checkbox" class="pact-1" value="1" checked="checked" /> 我看过并同意服务协议<span class="left2spana">服务协议</span>
							</div>
						<div>
							<input type="submit" name="Submit" value="" class="left3" />
						</div>
					</div>
					</s:form>
				</div>
				<!--<div id=TabTab03Con2 style="display: none">
					<div id="user-left0">
						<p style="color: #666;">请输入卡库网的账号、密码，绑定账号</p>
						<p>
							<span>用&nbsp;户&nbsp;名：</span> <input type="text" name="user.name"
								id="name" />
						</p>
						<p>
							<span>密&nbsp;&nbsp;&nbsp;&nbsp;码：</span> <input type="password"
								id="password" name="user.password" />
						</p>
						<div>
							<input type="submit" name="Submit" value="" class="left3" />
						</div>
					</div>

				</div>-->
			</div>
			<div id="user-right2">
				<div class="userright">
					<h3>为什么要填写用户名</h3>
					<p>填写用户名可以方便您在E卡通上与教练，俱乐部之间的交流，</p>
				</div>
				<div class="userright">
					<h3>为什么要填写电子邮箱</h3>
					<p>完善邮箱信息后，一些新的资讯可以通过邮箱快速的传递给你，如果你的密码不小心丢失了，还可以通过邮箱找回密码。</p>
				</div>
			</div>
		</div>
		<s:include value="/newpages/footer.jsp" />
</body>
</html>
<div id="seragree" title="服务协议">
	<b>服务条款</b>
	<p>若要访问和使用健身E卡通-www.ecartoon.com.cn/健身电子商务服务网站（以下简称健身E卡通），您必须不加修改地完全接受本协议中所包含的条款、条件和健身E卡通网站即时刊登的通告，并且遵守有关互联网及/或本网站的相关法律、规定与规则。一旦您访问、使用了健身E卡通网站，即表示您同意并接受了所有该等条款、条件及通告。
	</p>
	<b>1. 接受条款</b>
	<div>
		<p>以任何方式进入健身E卡通网站即表示您同意自己已经与健身E卡通订立本协议，且您将受本协议的条款和条件(“条款”) 约束。健身E卡通可随时自行全权决定更改“条款”。如“条款”有任何变更，健身E卡通在其网站上刊载公告，通知予您。如您不同意相关变更，必须停止使用“服务”。经修订的“条款”一经在健身E卡通网站公布后，立即自动生效。您应在第一次登录后仔细阅读修订后的“条款”，并有权选择停止继续使用“服务”；一旦您继续使用“服务”，则表示您已接受经修订的“条款”，当您与健身E卡通发生争议时，应以最新的服务协议为准。除另行明确声明外，任何使“服务”范围扩大或功能增强的新内容均受本协议约束。除非经健身E卡通的授权高层管理人员签订书面协议，本协议不得另行作出修订。
		</p>
	</div>
	<b>2.谁可使用健身E卡通网站？</b>
	<div>
		<p>“服务”仅供能够根据相关法律订立具有法律约束力的合约的个人或公司使用。因此，您的年龄必须在十八周岁或以上，才可使用本公司服务。如不符合本项条件，请勿使用“服务”。健身E卡通可随时自行全权决定拒绝向任何人士提供“服务”。“服务”不会提供给被暂时或永久中止资格的健身E卡通用户。	
		</p>
	</div>
	<b>3. 收费</b>
	<div>
		<p>本公司有权对使用健身E卡通网进行的交易收取服务手续费用。您因进行交易、向本公司获取有偿服务或接触本公司服务器而发生的所有应纳税赋，以及相关硬件、软件、通讯、网络服务及其他方面的费用均由您自行承担。本公司保留在无须发出书面通知，仅在健身E卡通网站公示的情况下，暂时或永久地更改或停止部分或全部“服务”的权利。
		</p>
	</div>
	<b>4. 效果及风险</b>
	<div>
		<h3>4.1 健身E卡通网站仅作为健身交易和健身指导的平台。</h3>
		<p>健身E卡通网站仅作为健身方面的会员（包括健身爱好者、教练、健身俱乐部）进行健身卡和健身服务的交易以及获取健身服务的平台。但是，本公司不能控制健身服务及健身指导的质量、安全或合法性，个人或公司信息的真实性或准确性，以及交易方履行其在交易协议项下的各项义务的能力。本公司不能也不会控制交易各方能否履行协议义务。此外，您应注意到，与外国国民、未成年人或以欺诈手段行事的人进行交易的风险是客观存在的。	
		</p>
		<h3>4.2 风险</h3>
		<p>您明白任何健身计划都存在风险，并自愿接受这些风险。并且您在使用本“服务”和执行用本“服务”为您或为他人制订健身计划，或其他会员使用本“服务”为您制订的健身计划之前将向医生详细咨询，并听取医生的建议。您知道本“服务”和其他会员在使用本“服务”的过程中并没有就任何您本人的身体状况、使用各种健身设施及实施各种健身项目等事宜提供任何医学上的意见。	
		</p>
		<p>如果因为使用本“服务”和采用任何方式用它制订的健身计划而导致您、您的配偶、胎儿或亲属蒙受身体或精神伤害、经济损失或其它伤害，您同意本“服务”平台研发、销售厂商、您的健身俱乐部、教练和其他计划制订方无须为此负责。
		</p>
	</div>
	<b>5.您的资料和供服务的内容</b>
	<p>“您的资料”包括您在注册、服务过程中、在任何公开信息场合或通过任何电子邮件形式，向本公司或其他用户提供的任何资料，包括数据、文本、软件、音乐、声响、照片、图画、影像、词句或其他材料。您应对“您的资料”负全部责任，而本公司仅作为您在网上发布和刊登“您的资料”的被动渠道。但是，倘若本公司认为“您的资料”可能使本公司承担任何法律或道义上的责任，或可能使本公司 (全部或部分地) 失去本公司的互联网服务供应商或其他供应商的服务，或您未在健身E卡通规定的期限内登录或再次登录网站，则本公司可自行全权决定对“您的资料”采取本公司认为必要或适当的任何行动，包括但不限于删除该类资料。您特此保证，您对提交给健身E卡通的“您的资料”拥有全部权利，包括全部版权。您确认，健身E卡通没有责任去认定或决定您提交给本公司的资料哪些是应当受到保护的，对享有“服务”的其他用户使用“您的资料”，本公司也不必负责。
	</p>
	<div>
		<h3>5.1 注册义务</h3>
		<p>如您在健身E卡通网站注册，您同意：
		</p>
		<p>(a) 根据健身E卡通网站刊载的会员资料表格的要求，提供关于您或贵公司的真实、准确、完整和反映当前情况的资料；
		</p>
		<p>(b) 维持并及时更新会员资料，使其保持真实、准确、完整和反映当前情况。倘若您提供任何不真实、不准确、不完整或不能反映当前情况的资料，或健身E卡通有合理理由怀疑该等资料不真实、不准确、不完整或不能反映当前情况，健身E卡通有权暂停或终止您的注册身份及资料，并拒绝您在目前或将来对“服务”(或其任何部份) 以任何形式使用。如您代表一家公司或其他法律主体在本公司登记，则您声明和保证，您有权使该公司或其他法律主体受本协议“条款”约束。 
		</p>
		<h3>5.2 会员注册名、密码和保密</h3>
		<p>在登记过程中，您将选择会员注册名和密码。您须自行负责对您的会员注册名和密码保密，且须对您在会员注册名和密码下发生的所有活动承担责任。您同意：
		</p>
		<p>(a) 如发现任何人未经授权使用您的会员注册名或密码，或发生违反保密规定的任何其他情况，您会立即通知健身E卡通；
		</p>
		<p>(b) 确保您在每个上网时段结束时，以正确步骤离开网站。健身E卡通不能也不会对因您未能遵守本款规定而发生的任何损失或损毁负责。
		</p>
		<h3>5.3 关于您的资料的规则</h3>
		<p>您同意，“您的资料”和您在健身E卡通网站上提供的任何信息内容和服务
		</p>
		<p>a. 不会有欺诈成份，与售卖伪造或盗窃无涉；
		</p>
		<p>b. 不会侵犯任何第三者对该内容或服务享有的物权，或版权、专利、商标、商业秘密或其他知识产权，或隐私权、名誉权；
		</p>
		<p>c. 不会违反任何法律、法规、条例或规章 (包括但不限于关于保护消费者、不正当竞争或虚假广告的法律、法规、条例或规章)；
		</p>
		<p>d. 不会含有诽谤（包括商业诽谤）、非法恐吓或非法骚扰的内容；
		</p>
		<p>e. 不会含有淫秽、或包含任何儿童色情内容； 
		</p>
		<p>f. 不会含有蓄意毁坏、恶意干扰、秘密地截取或侵占任何系统、数据或个人资料的任何病毒、伪装破坏程序、电脑蠕虫、定时程序炸弹或其他电脑程序；
		</p>
		<p>g. 不会直接或间接与下述各项内容或服务连接，或包含对下述各项内容或服务的描述：<br>
			(i) 本协议项下禁止的内容或服务； <br>
			(ii) 您无权连接或包含的内容或服务。 
		</p>
此外，您同意不会：
		<p>(h) 在与任何连锁信件、大量胡乱邮寄的电子邮件、滥发电子邮件或任何复制或多余的信息有关的方面使用“服务”；
		</p>
		<p>(i) 未经其他人士同意，利用“服务”收集其他人士的电子邮件地址及其他资料；
		</p>
		<p>(j) 利用“服务”制作虚假的电子邮件地址，或以其他形式试图在发送人的身份或信息的来源方面误导其他人士。
		</p>
		<h3>5.4 被禁止内容或服务</h3>
		<p>您不得在本公司网站公布或通过本公司网站买卖：
		</p>
		<p>(a) 可能使本公司违反任何相关法律、法规、条例或规章的任何内容或服务；
		</p>
		<p>(b) 健身E卡通认为应禁止或不适合通过本网站买卖的任何内容或服务。
		</p>
	</div>
	<b>6. 您授予本公司的许可使用权</b>
	<div>
		<p>您授予本公司独家的、全球通用的、永久的、免费的许可使用权利 (并有权在多个层面对该权利进行再授权)，使本公司有权(全部或部份地) 使用、复制、修订、改写、发布、翻译、分发、执行和展示"您的资料"或制作其派生作品，和/或以现在已知或日后开发的任何形式、媒体或技术，将"您的资料"纳入其他作品内。
		</p>
	</div>
	<b>7.隐私</b>
	<div>
		<p>尽管有第6条所规定的许可使用权，健身E卡通将仅根据本公司的隐私声明使用“您的资料”。本公司隐私声明的全部条款属于本协议的一部份，因此，您必须仔细阅读。请注意，您一旦自愿地在健身E卡通披露“您的资料”，该等资料即可能被其他人士获取和使用。
		</p>
	</div>
	<b>8.交易程序</b>
	<div>
		<h3>8.1 服务内容</h3>
		<p>服务内容是由您提供的在健身E卡通网站上展示的文字描述、图画和/或照片和或动画、视频，可以是对您拥有而您希望出售的服务的描述；或者对您正寻找的服务的描述。您可在健身E卡通发布相关服务描述，条件是，您必须将该服务描述归入正确的类目内。健身E卡通不对服务描述的准确性或内容负责。
		</p>
		<p>本公司对您发布的服务（产品）有审核权利。本公司可自行全权决定对您发布的服务（产品）采取本公司认为必要或适当的任何行动，包括但不限于删除该类资料。
		</p>
		<h3>8.2 委托及授权</h3>
		<p>作为健身服务企业（个人）您同意委托本公司在健身E卡通网上销售您自行发布的服务（产品），并担任来源于健身E卡通网站获得的预付式消费资金的管理人，于消费者接受您的服务、实现消费前管理该部分资金，同时为保障资金安全，同意本公司选择的银行或其它机构作为资金的托管人。
		</p>
		<p>作为健身消费者，您同意本公司担任基于健身E卡通网站支付的预付式消费资金的管理人，于您接受健身服务企业（个人）提供的服务，实现消费前管理该部分资金，同时为保障资金安全，同意本公司选择的银行或其它机构作为资金的托管人。
		</p>
		<h3>8.3 处理交易争议</h3>
		<p>本公司仅受理并调解健身服务企业（个人）是否正常营业的相关投诉，并协助双方办理合同款项结算。本公司不受理和调解甲乙双方履约过程中产生的服务质量、安全责任事故、人身、财产损害事故或任何其它纠纷的投诉。
		</p>
		<p>通过本平台进行交易的双方如产生争议，应向本公司提供证据，由本公司主持双方调解。调解达成协议的，本公司协助办理结算付/退款事宜；调解无法达成协议，本公司对托管资金进行冻结，双方均有权提起诉讼解决，冻结资金由司法机关处理。
		</p>
		<p>倘若您与一名或一名以上用户，或与您通过本公司网站获取其服务的第三方服务供应商发生争议，您免除健身E卡通 (及本公司代理人和雇员) 在因该等争议而引起的，或在任何方面与该等争议有关的不同种类和性质的任何 (实际和后果性的) 权利主张、要求和损害赔偿等方面的责任。
		</p>
		<h3>8.4 运用常识</h3>
		<p>本公司不能亦不试图对其他用户通过“服务”提供的资料进行控制。就其本质而言，其他用户的资料，可能是令人反感的、有害的或不准确的，且在某些情况下可能带有错误的标识说明或以欺诈方式加上标识说明。本公司希望您在使用本公司网站时，小心谨慎并运用常识。
		</p>
	</div>
	<b>9.交易系统</b>
	<div>
		<h3>9.1 不得操纵交易</h3>
		<p>您同意不利用帮助实现蒙蔽或欺骗意图的同伙(下属的客户或第三方)，操纵与另一交易方所进行的商业谈判的结果。
		</p>
		<h3>9.2 系统完整性</h3>
		<p>您同意，您不得使用任何装置、软件或例行程序干预或试图干预健身E卡通网站的正常运作或正在本公司网站上进行的任何交易。您不得采取对任何将不合理或不合比例的庞大负载加诸本公司网络结构的行动。您不得向任何第三者披露您的密码，或与任何第三者共用您的密码，或为任何未经批准的目的使用您的密码。 
		</p>
		<h3>9.3 反馈</h3>
		<p>您不得采取任何可能损害信息反馈系统的完整性的行动，诸如：利用第二会员身份标识或第三者为您本身留下正面反馈；利用第二会员身份标识或第三者为其他用户留下负面反馈 (反馈数据轰炸)；或在用户未能履行交易范围以外的某些行动时，留下负面的反馈 (反馈恶意强加)。 
		</p>
		<h3>9.4 不作商业性利用</h3>
		<p>您同意，您不得对任何资料作商业性利用，包括但不限于在未经健身E卡通授权高层管理人员事先书面批准的情况下，复制在健身E卡通上展示的任何资料并用于商业用途。
		</p>
	</div>
	<b>10. 终止或访问限制</b>
	<div>
		<p>您同意，在健身E卡通未向您收费的情况下，健身E卡通可自行全权决定以任何理由 (包括但不限于健身E卡通认为您已违反本协议的字面意义和精神，或您以不符合本协议的字面意义和精神的方式行事) 终止您的“服务”密码、帐户 (或其任何部份) 或您对“服务”的使用，并删除和丢弃您在使用“服务”中提交的 “您的资料”。您同意，在健身E卡通向您收费的情况下，健身E卡通应基于合理的怀疑且经电子邮件通知的情况下实施上述终止服务的行为。健身E卡通同时可自行全权决定，在发出通知或不发出通知的情况下，随时停止提供“服务”或其任何部份。您同意，根据本协议的任何规定终止您使用“服务”之措施可在不发出事先通知的情况下实施，并承认和同意，健身E卡通可立即使您的帐户无效，或撤销您的帐户以及在您的帐户内的所有相关资料和档案，和/或禁止您进一步接入该等档案或“服务”。帐号终止后，健身E卡通没有义务为您保留原帐号中或与之相关的任何信息，或转发任何未曾阅读或发送的信息给您或第三方。此外，您同意，健身E卡通不会就终止您接入“服务”而对您或任何第三者承担任何责任。第12、13、14和22各条应在本协议终止后继续有效。
		</p>
	</div>
	<b>11. 违反规则会有什么后果</b>
	<div>
		<p>在不限制其他补救措施的前提下，发生下述任一情况，本公司可立即发出警告，暂时中止、永久中止或终止您的会员资格，删除您的任何现有产品信息，以及您在网站上展示的任何其他资料：(i) 您违反本协议；(ii) 本公司无法核实或鉴定您向本公司提供的任何资料；或 (iii) 本公司相信您的行为可能会使您、本公司用户或通过本公司或本公司网站提供服务的第三者服务供应商发生任何法律责任。在不限制任何其他补救措施的前提下，倘若发现您从事涉及本公司网站的诈骗活动，健身E卡通可暂停或终止您的帐户。
		</p>
	</div>
	<b>12. 服务“按现状”提供</b>
	<div>
		<p>本公司会尽一切努力使您在使用健身E卡通的过程中得到乐趣。遗憾的是，本公司不能随时预见到任何技术上的问题或其他困难。该等困难可能会导致数据损失或其他服务中断。为此，您明确理解和同意，您使用“服务”的风险由您自行承担。“服务”以“按现状”和“按可得到”的基础提供。健身E卡通明确声明不作出任何种类的所有明示或暗示的保证，包括但不限于关于适用性、适用于某一特定用途和无侵权行为等方面的保证。健身E卡通对下述内容不作保证：<br>
		(i)“服务”会符合您的要求；<br>
		(ii)“服务”不会中断，且适时、安全和不带任何错误；<br>
		(iii) 通过使用“服务”而可能获取的结果将是准确或可信赖的；及 <br>
		(iv) 您通过“服务”而购买或获取的任何产品、服务、资料或其他材料的质量将符合您的预期。通过使用“服务”而下载或以其他形式获取任何材料是由您自行全权决定进行的，且与此有关的风险由您自行承担，对于因您下载任何该等材料而发生的您的电脑系统的任何损毁或任何数据损失，您将自行承担责任。您从健身E卡通或通过或从“服务”获取的任何口头或书面意见或资料，均不产生未在本协议内明确载明的任何保证。
		</p>
	</div>
	<b>13. 责任范围</b>
	<div>
		<p>您明确理解和同意，健身E卡通不对因下述任一情况而发生的任何损害赔偿承担责任，包括但不限于利润、商誉、使用、数据、健身效果等方面的损失或其他无形损失的损害赔偿 (无论健身E卡通是否已被告知该等损害赔偿的可能性)：<br>
		(i) 使用或未能使用“服务”；<br>
		(ii) 因通过或从“服务”购买或获取任何货物、样品、数据、资料或服务，或通过或从“服务”接收任何信息或缔结任何交易所产生的获取替代货物和服务的费用；<br>
		(iii) 未经批准接入或更改您的传输资料或数据；<br>
		(iv) 任何第三者对“服务”的声明或关于“服务”的行为；<br>
		(v) 因任何原因而引起的与“服务”有关的任何其他事宜，包括疏忽。
		</p>
	</div>
	<b>14. 赔偿</b>
	<div>
		<p>您同意，因您违反本协议或经在此提及而纳入本协议的其他文件，或因您违反了法律或侵害了第三方的权利，而使第三方对健身E卡通及其子公司、分公司、董事、职员、代理人提出索赔要求（包括司法费用和其他专业人士的费用），您必须赔偿给健身E卡通运营方武汉好韵莱信息技术有限公司及其子公司、分公司、董事、职员、代理人，使其等免遭损失。
		</p>
		<p>如果有任何人因服务条款4.2条之损伤、损失或伤害而向本“服务”研发厂商、销售厂商、用户提出与您有关的索赔，您同意：
		</p>
		<p>a、为本“服务”的研发、销售厂商、相关用户或会员作出辩护，证明本“服务”研发厂商、销售厂商、用户或会员无须作出赔偿，并为本“服务”的研发、销售厂商、用户或会员支付与索赔有关的所有费用；
		</p>
		<p>b、如果本“服务”研发、销售厂商、用户或会员因此项索赔而需要对您本人、您的配偶、胎儿或亲属负法律责任，本人将向“服务”研发、销售厂商、用户或会员作出全额补偿。
		</p>
	</div>
	<b>15. 遵守法律</b>
	<div>
		<p>您应遵守与您使用“服务”，以及与您购买和销售任何服务以及提供健身教学指导信息有关的所有相关的法律、法规、条例和规章。
		</p>
	</div>
	<b>16. 无代理关系</b>
	<div>
		<p>您与健身E卡通仅为独立订约人关系。本协议无意结成或创设任何代理、合伙、合营、雇用与被雇用或特许权授予与被授予关系。
		</p>
	</div>
	<b>17. 广告和金融服务</b>
	<div>
		<p>您与在“服务”上或通过“服务”物色的刊登广告人士通讯或进行业务往来或参与其推广活动，包括就相关服务付款和交付相关服务，以及与该等业务往来相关的任何其他条款、条件、保证或声明，仅限于在您和该刊登广告人士之间发生。您同意，对于因任何该等业务往来或因在“服务”上出现该等刊登广告人士而发生的任何种类的任何损失或损毁，健身E卡通无需负责或承担任何责任。
		</p>
	</div>
	<b>18. 链接</b>
	<div>
		<p>“服务”或第三者均可提供与其他万维网网站或资源的链接。由于健身E卡通并不控制该等网站和资源，您承认并同意，健身E卡通并不对该等外在网站或资源的可用性负责，且不认可该等网站或资源上或可从该等网站或资源获取的任何内容、宣传、产品、服务或其他材料，也不对其等负责或承担任何责任。您进一步承认和同意，对于任何因使用或信赖从此类网站或资源上获取的此类内容、宣传、产品、服务或其他材料而造成（或声称造成）的任何直接或间接损失，健身E卡通均不承担责任。
		</p>
	</div>
	<b>19. 通知</b>
	<div>
		<p>除非另有明确规定，任何通知应以电子邮件形式发送到您在登记过程中向健身E卡通提供的电子邮件地址，或有关方指明的该等其他地址。在电子邮件发出二十四 (24) 小时后，通知应被视为已送达，除非发送人被告知相关电子邮件地址已作废。或者，本公司可通过邮资预付挂号邮件并要求回执的方式，将通知发到您在登记过程中向健身E卡通提供的地址。在该情况下，在付邮当日三 (3) 天后通知被视为已送达。
		</p>
	</div>
	<b>20. 不可抗力</b>
	<div>
		<p>对于因本公司合理控制范围以外的原因，包括但不限于自然灾害、罢工或骚乱、物质短缺或定量配给、暴动、战争行为、政府行为、通讯或其他设施故障或严重伤亡事故等，致使本公司延迟或未能履约的，健身E卡通不对您承担任何责任。
		</p>
	</div>
	<b>21. 转让</b>
	<div>
		<p>健身E卡通转让本协议无需经您同意。
		</p>
	</div>
	<b>22. 其他规定</b>
	<div>
		<p>本协议取代您和健身E卡通先前就相同事项订立的任何书面或口头协议。本协议各方面应受中华人民共和国大陆地区法律的管辖。倘若本协议任何规定被裁定为无效或不可强制执行，该项规定应被撤销，而其余规定应予执行。条款标题仅为方便参阅而设，并不以任何方式界定、限制、解释或描述该条款的范围或限度。本公司未就您或其他人士的某项违约行为采取行动，并不表明本公司撤回就任何继后或类似的违约事件采取行动的权利。
		</p>
	</div>
	<b>23.诉讼</b>
	<div>
		<p>因本协议或本公司服务所引起或与其有关的任何争议应向湖北省武汉市洪山区人民法院提起诉讼，并以中华人民共和国法律为管辖法律。
		</p>
	</div>
</div>