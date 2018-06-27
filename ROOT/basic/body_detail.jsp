<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style><!--#stextarea{overflow: visible;}--></style>

<script type="text/javascript">
<!--
$(function(){
	setStatus(<s:property value="#request.status" escape="false"/>);
	/* new uploadPreview({
		UpBtn : "txtImage1",
		ImgShow : "preview1"
	});
	new uploadPreview({
		UpBtn : "txtImage2",
		ImgShow : "preview2"
	});
	new uploadPreview({
		UpBtn : "txtImage3",
		ImgShow : "preview3"
	});
	CKEDITOR.replace('txtDetails', {
		extraPlugins : 'uicolor'
	}); */
});
/* function deleteImage(o) {
		$('#preview' + o).attr('src', 'images/img-defaul.jpg');
		$('#txtImage' + o).val('');
	} */
 function uploadImage(_type) {
	$('#divimage').dialog({
		title: '图片上传',
		width: 400,
		height: 200,
		modal: true,
		buttons: [{text: '确定', click: onUpload}, {text: '取消', click: onCancel}]
	});
	$('#imageType').val(_type);
}
function onUpload(){
	if ($('#txtImage').val() == '') {
		alert('请先选择一个图片文件再上传！');
		return;
	}
	$('#member_image').val($('#memberId').val());
	$('#analyDate_image').val($('#analyDate').val());
	$('#frmimage').submit(); 
	
/* 	var data=new FormData();
	$.ajax({type:'post',url:'body!upload.asp',data: data, 
		cache: false,
		contentType: false,processData: false,    //不可缺
		success: function(msg){
			var _json = $.parseJSON(msg);
			var result = _json.msg;
			
		}
	}); */
}
function onCancel(){
	$(this).dialog('close');
}
function onSubmit(_type){
	
	 $('#planform').form('submit', {
		url: _type == 1 ? 'body!onSave.asp' : 'body!saveRecord.asp',
		onSubmit: function(param) {
			return true;
		},
		success:function(msg){
			$('#divdetail').html(msg);
			if(_type==1){
				
			}else{
				$(document).mask('请稍候，正在保存数据......');
				$(document).unmask();
				
				$('#divdetail').html(msg);
				alert('体态数据已经成功保存！');
				
			}
		}
	}); 
}
function onSubmit2(){
	 alert($("#doneDate").val())
	/* 
	 $('#planform1').form('submit', {
			url: 'member!saveRecord.asp',
			onSubmit: function(param) {
				return true;
			},
			success:function(msg){
					
					$('#divdetail').html(msg);
					alert('体态数据已经成功保存！');
			}
		}); */
	//$(document).mask('请稍候，正在保存数据......');
	 if(!$("#doneDate").val() ){
			alert("完成日期必须填写后才能保存！");
			return;
		}
		//var parms = $('#recordForm2').serialize() + '&syncWeibo=' + ($('#syncWeibo').attr('checked') === 'checked' ? 1 : 0);
		
		var parms = $('#recordForm2').serialize() + '&syncWeibo=' + ($('#syncWeibo').attr('checked') === 'checked' ? 1 : 0);
		$.ajax({type:'post',url:'body!saveRecord.asp',data: parms, 
			success: function(msg){
				//$(document).unmask();
				var _json = $.parseJSON(msg);
				var result = _json.msg;
				if(result == "ok"){
					alert("当前最新身体数据已成功保存,并已同步微博！");
				}else if(result == "okRecord"){
					alert("当前最新身体数据已成功保存！");
				}else if(result == "noSinaId"){
					alert("当前最新身体数据已成功保存,同步微博失败：您还未关联微博账户！");
				}else{
					alert(msg);
				}
			}
		});
	
}
//-->
function changeNote(){
	var modeValue = $("#modeValue1").val();
	var msgArr = ["您的运动强度较小，可以加大。","您的运动强度合适，请继续坚持。","您的运动强度太大，应减慢速度或减小强度。"];
	var msgIndex;
		if(!modeValue){
			alert("请先输入你运动后的心率，再进行运动强度评估！");
			$("#modeValue1").focus();
			$("#modeValue1").select();
			return;
		}
		if(isNaN(modeValue)){
			alert("请先输入正确的心率数据，谢谢！");
			$("#modeValue1").focus();
			$("#modeValue1").select();
			return;
		}
		modeValue = parseInt(modeValue);
		var birthday = "<s:property value="#session.loginMember.birthday"/>"
		if(!birthday){
			alert("请先在我的账户中维护生日数据，再进行运动强度评估！");
			return;
		}
		var age = new Date(Date.parse(birthday.replace(/-/g,"/"))); 
		age = parseInt((new Date()-age)/(1000*60*60*24*365));

		var bmiHigh = "<s:property value="setting.bmiHigh"/>";
		if(!bmiHigh){
			alert("请先在我的设定【四.设定靶心率】中维护【靶心率阈值上限】数据，再进行运动强度评估！");
			return;
		}
		var bmiLow = "<s:property value="setting.bmiLow"/>";
		if(!bmiLow){
			alert("请先在我的设定【四.设定靶心率】中维护【靶心率阈值下限】数据，再进行运动强度评估！");
			return;
		}
		var heartH = 220-age;
		var heartHigh = heartH*bmiHigh/100;
		var heartLow = heartH*bmiLow/100;

		if(modeValue < heartLow){
			msgIndex = 0;
		}else if(modeValue >=heartLow && modeValue <= heartHigh){
			msgIndex = 1;
		}else{
			msgIndex = 2;
		}
	
	$("#jcz").html(msgArr[msgIndex]);
}
</script>

	<div id="righttop">
		<div id="righttop" style="background: url(images/dhbj.png);  font-size: 14px; font-weight: normal; border-bottom: 1px solid #CCCCCC;padding-left: 10px;">
				<!-- <p><b>训练日志</b></p> -->
				<p><span>训练日志</span></p>
		</div> 
		<div class="rightop_update">
			<s:form name="recordForm2" id="recordForm2" action="body!saveRecord.asp" theme="simple">
	<s:hidden name="body.id"/>
	<s:hidden name="body.member" id="memberId" />
	<s:hidden name="body.analyDate" id="analyDate" />
	<s:hidden name="trainRecord.doneDate" id="doneDate"/> 
	<s:hidden name="body.imageFront"/>
	<s:hidden name="body.imageSide"/>
	<s:hidden name="body.imageBack"/>
			<p>&nbsp;</p>
			<s:if test="#session.loginMember.role == \"S\"">
			<div style="height: 30px;margin-left: 20px"> 
				<span><b>会员名称:</b> </span>&nbsp;
					<s:select list="#request.members" value="member" listKey="id" listValue="nick" cssClass="selectmember"/>
			    <%-- <s:if test="#session.loginMember.role == \"M\"">
					<s:select list="#session.loginMember" value="member" listKey="id" listValue="nick" cssClass="selectmember"/>
			    </s:if> --%>
			</div>
			</s:if>
			<div style="height: 30px;margin-left: 20px">
				<span><b>运动时长：</b> </span>
				<span><s:textfield name="trainRecord.times" maxlength="10" cssClass="txtls" id="recordTimes" /> 分钟 </span>
			</div>
			
			<div style="margin-left: 20px">
				<div class=""><span><b>运动内容:</b> </span></div>
				<div class="" style="height: 30px;margin-left: 20px">
					&nbsp;&nbsp;	
					<span>运动内容:<s:textfield name="trainRecord.action" cssClass="txtls" id="recordAction"/></span>&nbsp;&nbsp;
					<span>本次成绩:<s:textfield name="trainRecord.actionQuan" id="recordActionQuan" cssClass="txtls"/></span>&nbsp;&nbsp;
					<span>单位:<s:textfield name="trainRecord.unit" id="recordUnit" cssClass="txtls"/></span>
				</div>
			</div>
			
			<div style="height: 30px;margin-left: 20px">
				<span id="modeSpan1"><b>最高心率：</b></span>
				 <%-- <span> <input type="text" class="assess-2" name="modeValue1" id="modeValue1"/>次/分钟</span> --%>
				 <span><s:textfield name="trainRecord.heartRate" maxlength="10" cssClass="txtls" id="modeValue1" onblur="changeNote()"/> 次/分钟 </span>
				 <span id="jcz"></span>
			</div>
		<!-- </div>
		<div class="shuju"> -->
				<div class="" style="margin-left: 20px"><span><b>基本数据:</b> </span></div>
				<%-- <s:form name="frmbasic" id="frmbasic" theme="simple"> --%>
				<div class="" style="height: 30px;margin-left: 40px">
					&nbsp;&nbsp;
					<span>体重:<s:textfield name="trainRecord.weight" cssClass="txtls" id="setweight"/><span>kg</span></span>&nbsp;&nbsp;
					<span>腰围:<s:textfield name="trainRecord.waist" id="setwaistline" cssClass="txtls"/><span>cm</span></span>&nbsp;&nbsp;
					<span>臀围:<s:textfield name="trainRecord.hip" id="sethip" cssClass="txtls"/><span>cm</span></span>
				</div>
				<%-- </s:form> --%>
		<!-- </div>-->
		</s:form>
		<s:form id="planform" name="planform" method="post" theme="simple" action="body!saveRecord.asp">
		<s:hidden name="body.id"/>
	<s:hidden name="body.member" id="memberId" />
	<s:hidden name="body.analyDate" id="analyDate" />
	<s:hidden name="body.imageFront"/>
	<s:hidden name="body.imageSide"/>
	<s:hidden name="body.imageBack"/>
		<!--<div class="rightop_update" id="emptyMsg" style="display: block;border:none;"> 
			<h1 id="sprotjh">体态分析</h1>
			<div class="titai">体态照片</div>-->
			<div class="" style="margin-left: 20px"><span><b>体态分析:</b> </span></div>
			<div class="conpick">
				<ul>
<%-- 				<li><a href="javascript:uploadImage(1)" class="sho"><img src="<s:if test="body.imageFront == null">images/body1.png</s:if><s:else>picture/<s:property value="body.imageFront"/></s:else>" width="120" height="160" id="imgView" /></a><span>正面观</span></li>--%> 
					<li><a href="javascript:uploadImage(2)" class="sho"><img src="<s:if test="body.imageSide == null">images/body2.png</s:if><s:else>picture/<s:property value="body.imageSide"/></s:else>" width="120" height="160" /></a><span>侧面观</span></li>
					<li><a href="javascript:uploadImage(3)" class="sho"><img src="<s:if test="body.imageBack == null">images/body3.png</s:if><s:else>picture/<s:property value="body.imageBack"/></s:else>" width="120" height="160" /></a><span>背面观</span></li>
					<%-- <li><img id="preview1" width="60px" height="60px"
								<s:if test="body.imageFront == null">src="images/body1.png"</s:if>
								<s:else> src="picture/<s:property value="body.imageFront"/>"</s:else>
								onclick="javascript: $('#txtImage1').click();" /> 
							<img id="delImg1" width="15px" height="15px" src="images/del_img.png" onClick="deleteImage('1')">
							<s:file id="txtImage1" name="body.imageFront" style="display:none;"/></li> --%>
				</ul>
			</div>
			<!-- <div class="titai">体态分析</div> -->
			<div class="ttfx">
				<table width="698" border="0" cellpadding="0" cellspacing="0">
					<tbody>
						<tr class="bghd">
							<td width="100"><div style="text-align: center; height: 25px; line-height: 25px;"">部位</div></td>
							<td width="267"><div style="text-align: center; height: 25px; line-height: 25px;"">侧面观</div></td>
							<td width="267"><div style="text-align: center; height: 25px; line-height: 25px;"">背面观</div></td>
						</tr>
						<tr>
							<td>
								<div style="text-align: center; height: 25px; line-height: 25px;">头部</div>
							</td>
							<td>
								<div style="margin-left: 40px; height: 25px; line-height: 25px;">
									<s:radio list="#{'A':'中立位','B':'前倾','C':'后仰'}" listKey="key" listValue="value" id="sideHead" name="body.headSide"/>
								</div>
							</td>
							<td>
								<div style="margin-left: 40px; height: 25px; line-height: 25px;">
									<s:radio list="#{'A':'中立位','B':'侧倾','C':'扭转'}" listKey="key" listValue="value" id="headBack" name="body.headBack"/>
								</div>
							</td>
						</tr>
						<tr>
							<td><div style="text-align: center; height: 25px; line-height: 25px;">颈椎</div></td>
							<td>
								<div style="margin-left: 40px; height: 25px; line-height: 25px;">
									<s:radio list="#{'A':'中立位','B':'前曲'}" listKey="key" listValue="value" id="cervicalSide" name="body.cervicalSide"/>
								</div>
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td><div style="text-align: center; height: 25px; line-height: 25px;">肩部</div></td>
							<td><div style="margin-left: 40px; height: 25px; line-height: 25px;">
								<s:radio list="#{'A':'中立位','B':'圆肩'}" listKey="key" listValue="value" id="scapulaSide" name="body.scapulaSide"/>
								</div>
							</td>
							<td>
								<div style="margin-left: 40px; height: 25px; line-height: 25px;">
									<s:radio list="#{'A':'中立位','B':'耸肩','C':'塌肩'}" listKey="key" listValue="value" id="shoulderBack" name="body.shoulderBack"/>								
								</div>
							</td>
						</tr>
						<tr>
							<td><div style="text-align: center; height: 25px; line-height: 25px;">胸椎</div></td>
							<td>
								<div style="margin-left: 40px; height: 25px; line-height: 25px;">
								<s:radio list="#{'A':'中立位','B':'后曲'}" listKey="key" listValue="value" id="thoracicSide" name="body.thoracicSide"/>
								</div>
							</td>
							<td><div style="margin-left: 40px; height: 25px; line-height: 25px;">
								<s:radio list="#{'A':'成一直线','B':'S形','C':'C形'}" listKey="key" listValue="value" id="thoracicBack" name="body.thoracicBack"/>								
							</div></td>
						</tr>
						<tr>
							<td><div style="text-align: center; height: 25px; line-height: 25px;">腰椎</div></td>
							<td>
								<div style="margin-left: 40px; height: 25px; line-height: 25px;">
								<s:radio list="#{'A':'中立位','B':'前曲'}" listKey="key" listValue="value" id="lumbarSide" name="body.lumbarSide"/>
								</div>
							</td>
							<td>
								<div style="margin-left: 40px; height: 25px; line-height: 25px;">
								<s:radio list="#{'A':'成一直线','B':'S形','C':'C形'}" listKey="key" listValue="value" id="lumbarBack" name="body.lumbarBack"/>								
								</div>
							</td>
						</tr>
						<tr>
							<td><div style="text-align: center; height: 25px; line-height: 25px;">骨盆</div></td>
							<td>
								<div style="margin-left: 40px; height: 25px; line-height: 25px;">
								<s:radio list="#{'A':'中立位','B':'前倾','C':'后倾'}" listKey="key" listValue="value" id="pelvisSide" name="body.pelvisSide"/>								
								</div>
							</td>
							<td>
								<div style="margin-left: 40px; height: 25px; line-height: 25px;">
								<s:radio list="#{'A':'中立位','B':'骨盆侧倾'}" listKey="key" listValue="value" id="pelvisBack" name="body.pelvisBack"/>								
								</div>
							</td>
						</tr>

						<tr>
							<td><div style="text-align: center; height: 25px; line-height: 25px;">膝关节</div></td>
							<td><div style="margin-left: 40px; height: 25px; line-height: 25px;">
								<s:radio list="#{'A':'中立位','B':'超伸'}" listKey="key" listValue="value" id="kneeSide" name="body.kneeSide"/>								
							</div></td>
							<td><div style="margin-left: 40px; height: 25px; line-height: 25px;">
								<s:radio list="#{'A':'中立位','B':'膝外翻','C':'膝内翻'}" listKey="key" listValue="value" id="kneeBack" name="body.kneeBack"/>								
							</div></td>
						</tr>

						<tr>
							<td><div style="margin-left: 40px; height: 25px; line-height: 25px;">足部</div></td>
							<td><div style="margin-left: 40px; height: 25px; line-height: 25px;">
								<s:radio list="#{'A':'中立位','B':'扁平足'}" listKey="key" listValue="value" id="footSide" name="body.footSide"/>								
							</div></td>
							<td></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="btii">
				<input type="button" value="自动生成分析结论" class="btnpci" onclick="javascript:onSubmit(1);">
			</div>
			<!-- <div class="titai">分析结论</div> -->
			<div class="textar">
				<s:textarea rows="8" cols="80" name="conclusion" maxLength="250" id="stextarea"></s:textarea>
			</div>
			</s:form>
			<div class="btii">
				<input type="button" value="保存" class="btnpci" onclick="javascript:onSubmit2();">
			</div>
		</div>
	</div>

<div id="divimage" style="display: none;">
	<s:form name="frmimage" theme="simple" id="frmimage" action="body!upload.asp" enctype="multipart/form-data">
	<s:hidden name="type" id="imageType"/>
	<s:hidden name="body.member" id="member_image"/>
	<s:hidden name="body.analyDate" id="analyDate_image"/>
	<table style="width: 100%; border: 0px;">
		<tr>
			<td>选择图片文件：</td><td><input type="file" name="image" id="txtImage"/></td>
		</tr>
	</table>
	</s:form>
</div>