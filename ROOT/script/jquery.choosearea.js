/**
 * jquery.choosearea.js - 地区联动封装
 * By Jacky.Wei
*/
;(function($){
	var choosearea = function(options){
		this.set = $.extend({
			selectDomId : {
				province : "a",
				city : "b",
				county : "c"
			},
			data : null,
			initAreaNames: {
				province : "",
				city : "",
				county : ""
			},
			eventInterface : {
				renderProvinceList : function(list, selectedName){
					this.jq_province.empty().append($(this.getListOptionsHtml(list, selectedName, "请选择省")));
				},
				renderCityList : function(list, selectedName, isInit){
					var city = this.jq_city;
					isInit = typeof(isInit) == "undefined" ? false : true;
					city.empty().append($(this.getListOptionsHtml(list, selectedName, "请选择市")));	
				},
				renderCountyList : function(list, selectedName, isInit){
					var optionsHtml = this.getListOptionsHtml(list, selectedName, "请选择县");
					var county = this.jq_county;
					isInit = typeof(isInit) == "undefined" ? false : true;
					county.empty().append($(optionsHtml));
				},
				onchanged : function(cityId){
					 
				}
			}
		
		}, options);	
		this.provinceList = [];
		this.cityList = [];
		this.countyList = [];
		this.jq_province = $("#" + this.set.selectDomId.province); 
		this.jq_city = $("#" + this.set.selectDomId.city); 
		this.jq_county = $("#" + this.set.selectDomId.county); 
		this._init();
	};	
	choosearea.prototype = {};
	choosearea.fn = choosearea.prototype;
	choosearea.fn._init = function(){
		if(this.set.data == null){
			return;
		};
		this._setAreaList();
		this._initRender(this.set.initAreaNames.province, this.set.initAreaNames.city, this.set.initAreaNames.county);
		this._initEvents();
	};
	//设置地区列表
	choosearea.fn._setAreaList = function(){
		this.provinceList = this.set.data.provinceList;
		this.cityList = this.set.data.cityList;
		this.countyList = this.set.data.countyList;
	};
	
	//初始化渲染
	choosearea.fn._initRender = function(province, city, county){
		this.set.eventInterface.renderProvinceList.call(this, this.provinceList, province);
		var provinceId = this._findIdByProvince(province);
		var cityList = this.cityList["city_" + provinceId] || [];
		this.set.eventInterface.renderCityList.call(this, cityList, city, true);
		var cityId = this._findIdByCity(provinceId, city);
		var countyList = this.countyList["city_" + cityId] || [];
		this.set.eventInterface.renderCountyList.call(this, countyList, county, true);
	};
	//根据省名获取其ID号
	choosearea.fn._findIdByProvince = function(province) {
		var provinceId = 0;
		for (var i = 0 ; i < this.provinceList.length; i++) {
			if (this.provinceList[i].name == province) {
				provinceId = this.provinceList[i].id;
				break;
			}
		}
		return provinceId;
	};
	//根据省ID号及城市名称获取其城市ID号
	choosearea.fn._findIdByCity = function(provinceId, city) {
		var cityId = 0, _cityList = this.cityList["city_" + provinceId] || [];
		for (var i = 0 ; i < _cityList.length; i++) {
			if (_cityList[i].name == city) {
				cityId = _cityList[i].id;
				break;
			}
		}
		return cityId;
	};
	//根据城市ID及区县名称，获取其区县ID号
	choosearea.fn._findIdByCounty = function(cityId, county) {
		var countyId = 0, _countyList = this.countyList["city_" + cityId] || [];
		for (var i = 0 ; i < _countyList.length; i++) {
			if (_countyList[i].name == county) {
				countyId = _countyList[i].id;
				break;
			}
		}
		return countyId;
	};
	//渲染列表
	choosearea.fn.getListOptionsHtml = function(list, selectedName, firstTips){
		firstTips = firstTips || "";
		var selectedAttr = selectedName == '' ? " selected='selected'" : "";
		var optionsHtml = firstTips != "" ? "<option value='' " + selectedAttr + ">" + firstTips + "</option>" : "";
		var sel = $("#province");
		if(typeof(list) != "undefined"){
			$.each(list, function(i, city){
				var selAttr = selectedName == city.name ? " selected='selected'" : "";
				optionsHtml += "<option value='" + city.name + "' " + selAttr + ">" + city.name + "</option>";
			});
		};
		return optionsHtml;
	};
	//初始化事件
	choosearea.fn._initEvents = function(){
			var province = this.jq_province;
			var city = this.jq_city;
			var county = this.jq_county;
			var _this = this;
			province.change(function(){
				var pid = _this._findIdByProvince($(this).val());
				var cityList = _this.cityList["city_" + pid] || [];
				_this.set.eventInterface.renderCityList.call(_this, cityList, 0);
				_this.set.eventInterface.renderCountyList.call(_this, [], 0, false);
				_this.set.eventInterface.onchanged.call(this, pid);
			});
			
			city.change(function(){
				var pid = _this._findIdByProvince(province.val());
				var id = _this._findIdByCity(pid, $(this).val());
				var countyList = _this.countyList["city_" + id] || [];
				_this.set.eventInterface.renderCountyList.call(_this, countyList, 0, false);
				_this.set.eventInterface.onchanged.call(this, id);
			});
	};
	$.choosearea = choosearea;
})(jQuery);