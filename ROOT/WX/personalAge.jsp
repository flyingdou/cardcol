<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>修改出生日期</title>
    <link type="text/css" href="css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="css/FJL.css" rel="stylesheet">
    <link type="text/css" href="css/FJL.picker.css" rel="stylesheet">
    <link type="text/css" href="css/base.css" rel="stylesheet">	
    <link type="text/css" href="css/form.css" rel="stylesheet">
    <script type="text/javascript" src="js/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="js/FJL.min.js"></script>
    <script type="text/javascript" src="js/FJL.picker.min.js"></script>
</head>
<body>
<div class="container">
    <form id="queryForm" action="personalwx!savebirthday.asp" method="post"> 
        <button id='date' data-options='{"type":"date","beginYear":1900,"endYear":2086}' class="date btn mui-btn mui-btn-block">请输入您的出生年月</button>
        <input type="hidden" name="birthday" value="" id="setdate">
        <input type="text" value="保存" class="send" style="text-align: center;height: 2.5rem">
    </form>
</div>
<script type="text/javascript">
$(".send").click(function(){
	var data = $("#date").text();
	$("#setdate").val(data);
	url = 'personalwx!savebirthday.asp';
	$('#queryForm').attr("action", url);
	$('#queryForm').submit();
});


    $(function () {
        var toddy=new Date();
        var str=toddy.getFullYear()+"-"+(toddy.getMonth()+1)+"-"+toddy.getDate();
        $(".this_date").html(str);
    });

    (function($) {
        $.init();
        var result = $('#date')[0];
        var btns = $('.btn');
        btns.each(function(i, btn) {
            btn.addEventListener('tap', function() {
                var optionsJson = this.getAttribute('data-options') || '{}';
                var options = JSON.parse(optionsJson);
                var id = this.getAttribute('id');
                var picker = new $.DtPicker(options);
                picker.show(function(rs) {
                    result.innerText = rs.text;
                    picker.dispose();
                });
            }, false);
        });
    })(mui);

</script>
</body>
</html>