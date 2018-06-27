<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link href="css/club-checkin.css" rel="stylesheet" type="text/css" />

<link href="css/condition/dailog.css" rel="stylesheet" type="text/css" />
<link href="css/condition/calendar.css" rel="stylesheet" type="text/css" />
<link href="css/condition/dp.css" rel="stylesheet" type="text/css" />
<link href="css/condition/alert.css" rel="stylesheet" type="text/css" />
<link href="css/condition/main.css" rel="stylesheet" type="text/css" />
<link href="css/pulicstyle.css" rel="stylesheet" type="text/css" />
<%-- <script src="script/condition/jquery.js" type="text/javascript"></script>   --%>
<script src="script/condition/Plugins/Common.js?v=1100000"
	type="text/javascript"></script>
<script src="script/condition/Plugins/datepicker_lang_CN.js"
	type="text/javascript"></script>
<script src="script/condition/Plugins/jquery.datepicker.js"
	type="text/javascript"></script>
<script src="script/condition/Plugins/jquery.alert.js"
	type="text/javascript"></script>
<script src="script/condition/Plugins/jquery.ifrmdailog.js"
	defer="defer" type="text/javascript"></script>
<script src="script/condition/Plugins/wdCalendar_lang_CN.js"
	type="text/javascript"></script>
<script src="script/condition/Plugins/jquery.calendar.js"
	type="text/javascript"></script>

<script type="text/javascript">
	$(document).ready(function() {
		var sportTypes = '<s:property value="#request.sportsTypes"/>';
		if (sportTypes != null && sportTypes != "" && sportTypes != undefined) {
			$("#div_sporttype").show();
			$("#tabs").show();
			var jsonfactorys = $('#jsonfactorys').val();
			viewDivPlace($('#jsonfactorys').val());
			var place = '<s:property value="place"/>';
			calendarInit(place);
		} else {
			$("#div_tosetting").show();
		}
		$('.sportsa').click(function() {
			$('.sportsa').css('color', '#000000');
			$(this).css('color', "rgb(138,188,48)");
			var type = $(this).attr('id');
			$.ajax({
				type : 'post',
				url : 'factoryorder!queryForType.asp',
				data : 'playtype=' + type + '&member.id=' + $('#memberid').val(),
				success : function(msg) {
					var jsonobj = eval('(' + msg + ')');
					var place = jsonobj.place;
					if (place != null && place != "" && place != undefined) {
						viewDivPlace(jsonobj.jsonfactorys)
						$("#div_tosetting").hide();
						$("#tabs").show();
						showData(place);
					} else {
						$('#factoryid').val("");
						$("#div_tosetting").show();
						$("#tabs").hide();
					}
				}
			});

		});
	});

	function calendarInit(id) {
		var view = "week";
		var DATA_FEED_URL = "factoryorder!query.asp";
		var op = {
			view : view,
			theme : 3,
			showday : new Date(),
			EditCmdhandler : Edit,
			DeleteCmdhandler : Delete,
			ViewCmdhandler : View,
			DragCmdhandler : onDrag,
			onWeekOrMonthToDay : wtd,
			autoload : true,
			url : DATA_FEED_URL,
			quickAddUrl : DATA_FEED_URL + "?method=add",
			quickUpdateUrl : DATA_FEED_URL + "?method=update",
			quickDeleteUrl : DATA_FEED_URL + "?method=remove",
			readonly : false
		};
		var $dv = $("#calhead");
		var _MH = document.documentElement.clientHeight;
		var dvH = $dv.height() + 2;
		op.height = _MH - dvH;
		op.eventItems = [];

		// 场地
		if (id != "" && id != undefined) {
			op.extParam = [ {
				name : "place",
				value : id
			} ];
		}

		var p = $("#gridcontainer").bcalendar(op).BcalGetOp();
		if (p && p.datestrshow) {
			$("#txtdatetimeshow").text(p.datestrshow);
		}
		$("#caltoolbar").noSelect();

		$("#hdtxtshow").datepicker({
			picker : "#txtdatetimeshow",
			showtarget : $("#txtdatetimeshow"),
			onReturn : function(r) {
				var p = $("#gridcontainer").gotoDate(r).BcalGetOp();
				if (p && p.datestrshow) {
					$("#txtdatetimeshow").text(p.datestrshow);
				}
			}
		});
		function onDrag(data) {
			if (data && data.length == 3) {
				var parm = 'id=' + data[0] + '&startDate=' + data[1].format()
						+ '&endDate=' + data[2].format();
				$.ajax({
					type : 'post',
					url : 'factoryorder!update.asp?' + parm,
					type : 'json',
					success : function(msg) {
					}
				});
			}
		}
		function Edit(data) {
			if (data) {
				if (data[2] + ':00' > new Date().format()) {
					var eurl = "factoryorder!details.asp?factoryCosts.id={0}&start={2}&end={3}&factory.id="
							+ $("#factoryid").val();
					var url = StrFormat(eurl, data);
					OpenModelWindow(url, {
						width : 500,
						height : 400,
						caption : "预约条件",
						onclose : function() {
							$("#gridcontainer").reload();
						}
					});
				}
			}
		}

		function View(data) {
			$.each(data, function(i, item) {
				str += "[" + i + "]: " + item + "\n";
			});
		}
		function Delete(data, callback) {

			$.alerts.okButton = "Ok";
			$.alerts.cancelButton = "Cancel";
			hiConfirm("Are You Sure to Delete this Event", 'Confirm', function(
					r) {
				r && callback(0);
			});
		}
		function wtd(p) {
			if (p && p.datestrshow) {
				$("#txtdatetimeshow").text(p.datestrshow);
			}
			$("#caltoolbar div.fcurrent").each(function() {
				$(this).removeClass("fcurrent");
			})
			//$("#showdaybtn").addClass("fcurrent");
		}

		//to show week view
		$("#showweekbtn").click(function(e) {
			//document.location.href="#week";
			$("#caltoolbar div.fcurrent").each(function() {
				$(this).removeClass("fcurrent");
			})
			$(this).addClass("fcurrent");
			var p = $("#gridcontainer").swtichView("week").BcalGetOp();
			if (p && p.datestrshow) {
				$("#txtdatetimeshow").text(p.datestrshow);
			}

		});
		//to show month view
		$("#showmonthbtn").click(function(e) {
			//document.location.href="#month";
			$("#caltoolbar div.fcurrent").each(function() {
				$(this).removeClass("fcurrent");
			})
			$(this).addClass("fcurrent");
			var p = $("#gridcontainer").swtichView("month").BcalGetOp();
			if (p && p.datestrshow) {
				$("#txtdatetimeshow").text(p.datestrshow);
			}
		});

		$("#showreflashbtn").click(function(e) {
			$("#gridcontainer").reload();
		});
		//previous date range
		$("#sfprevbtn").click(function(e) {
			var p = $("#gridcontainer").previousRange().BcalGetOp();
			if (p && p.datestrshow) {
				$("#txtdatetimeshow").text(p.datestrshow);
			}

		});
		//next date range
		$("#sfnextbtn").click(function(e) {
			var p = $("#gridcontainer").nextRange().BcalGetOp();
			if (p && p.datestrshow) {
				$("#txtdatetimeshow").text(p.datestrshow);
			}
		});
	}

	function viewDivPlace(jsonfactorys) {
		var divplace = "";
		var myobj = eval(jsonfactorys);
		for (var i = 0; i < myobj.length; i++) {
			divplace += "<a class='css_factory' id=\"" + myobj[i].id
					+ "\" href=\"#\" onclick=\"showData(this)\">" + myobj[i].name + "</a>";
		}
		document.getElementById("div_place").innerHTML = divplace;
	}

	function showData(the) {
		var id = $(the).attr('id');
		$('.css_factory').css('color', '#000000');
		$(the).css('color', 'rgb(138,188,48)');
		$('#factoryid').val(id);
		calendarInit(id);
	}
</script>
<input type="hidden" id="factoryid" name="factory.id"
	value="${factory.id}" />
<input type="hidden" id="memberid" name="member.id" value="${member.id}" />
<input type="hidden" id="jsonfactorys" name="jsonfactorys"
	value="<s:property value="#request.jsonfactorys"/>" />
<div id="div_sporttype" style="display: none;">
	<div class="sporttype">1,请选择运动类型：</div>
	<s:iterator value="#request.sportsTypes">

		<a class="sportsa" id="<s:property value="id"/>" href="#"><s:property
				value="name" /></a>
	</s:iterator>
	<div class="sporttype">2,请选择场地</div>

</div>
<div id="div_tosetting" style="display: none;">
	请先到<a href="setting.asp">我的设定</a>中维护您的场地！
</div>
<div id="tabs" style="display: none;">

	<div id="div_calendar">

		<div id="div_place" class="place"></div>
		<div
			style="font-size: 14px; font-weight: bold; overflow: hidden; padding-left: 6px; margin-top: 5px; margin-bottom: 5px;">3,请在下面日历的可预订时间上拖曳鼠标,进行预约</div>
		<div id="calhead" style="padding-left: 1px; padding-right: 1px;">
			<div id="caltoolbar" class="ctoolbar">
				<div id="showweekbtn" class="fbutton fcurrent">
					<div>
						<span title='Week' class="showweekview">周示图</span>
					</div>
				</div>
				<div id="showmonthbtn" class="fbutton">
					<div>
						<span title='Month' class="showmonthview">月示图</span>
					</div>
				</div>
				<div class="btnseparator"></div>
				<div id="showreflashbtn" class="fbutton">
					<div>
						<span title='Refresh view' class="showdayflash">刷新</span>
					</div>
				</div>
				<div class="btnseparator"></div>
				<div id="sfprevbtn" title="Prev" class="fbutton">
					<span class="fprev"></span>
				</div>
				<div id="sfnextbtn" title="Next" class="fbutton">
					<span class="fnext"></span>
				</div>
				<div class="fshowdatep fbutton">
					<div>
						<input type="hidden" name="txtshow" id="hdtxtshow" /><span
							id="txtdatetimeshow">请选择日期</span>
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<div style="padding: 1px;">
				<div class="t1 chromeColor">&nbsp;</div>
				<div class="t2 chromeColor">&nbsp;</div>
				<div id="dvCalMain" class="calmain printborder">
					<div id="gridcontainer" style="overflow-y: visible;"></div>
				</div>
				<div class="t2 chromeColor">&nbsp;</div>
				<div class="t1 chromeColor">&nbsp;</div>
			</div>
		</div>
	</div>
</div>