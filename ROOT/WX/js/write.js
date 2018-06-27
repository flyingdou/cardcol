/**
 * Created by Administrator on 2016/11/25.
 */

$(function () {
    var toddy=new Date();
    var str=toddy.getFullYear()+"-"+(toddy.getMonth()+1)+"-"+toddy.getDate();
    $(".this_date").html(str);

/*体重与身高滚动输入*/
    $(".picker").picker({
        toolbarTemplate: '<div id="top">\
           <h4 class="title bar">请选择体重</h4>\
           <span id="span" class="fr button button-link pull-right close-picker">确定</span>\
  </div>',
        cols: [
            {
                textAlign: 'center',
                values: ['', '1', '2' ]
            },
            {
                textAlign: 'center',
                values: ['6', '7', '8', '9', '0', '1', '2','3','4','5']
            },
            {
                textAlign: 'center',
                values: ['0', '1', '2', '3', '4', '5', '6', '7','8','9']
            }
        ]
    });
    /*判断输入的是身高还是体重，改变弹出框的标题，并有遮罩效果*/
    $(".picker").on("click", function () {
        $("body>.bg").addClass("pickerBg");
        if($(this).hasClass("CM")){
            $("#top>.title").text("请输入您的身高")
        }else {
            $("#top>.title").text("请输入您的体重")
        }
    });
    $("body").on("click","#span", function () {
        $("body>.bg").removeClass("pickerBg")
    })
    $("body>.bg").on("click",function () {
        $(this).removeClass("pickerBg")
    })
});

/*地址选择*/
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

/*日期选择*/
var area2 = new LArea();
area2.init({
    'trigger': '#address',
    'valueTo': '#value2',
    'keys': {
        id: 'value',
        name: 'text'
    },
    'type': 2,
    'data': [provs_data, citys_data, dists_data]



});
