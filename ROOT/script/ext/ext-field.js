String.prototype.lpadding = function(l) {
	var fillStr = "0", test = '';
	for (var i = 0 ; i < l; i++) test += fillStr;
	return test.substr(0, l - this.length) + this;
}
Number.prototype.lpadding = function(l){
	var str = this + "", fillStr = "0", test = '';
	for (var i = 0 ; i < l; i++) test += fillStr;
	return test.substr(0, l - str.length) + str;
	
}
Ext.apply(Ext.form.VTypes ,{
	daterange : function(val, field) {
        var date = field.parseDate(val);
        if(!date){
            return;
        }
        if (field.startDateField && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax.getTime()))) {
            var start = Ext.getCmp(field.startDateField);
            start.setMaxValue(date);
            start.validate();
            this.dateRangeMax = date;
        }  else if (field.endDateField && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin.getTime()))) {
            var end = Ext.getCmp(field.endDateField);
            end.setMinValue(date);
            end.validate();
            this.dateRangeMin = date;
        }
        return true;
    },
    timerange: function(val, field){
    	var time = val;
        if (field.startTimeField && (!this.timeRangeMax || (time != this.timeRangeMax))) {
            var start = Ext.getCmp(field.startTimeField);
            start.setMaxValue(time);
            start.validate();
            this.timeRangeMax = time;
        }  else if (field.endTimeField && (!this.timeRangeMin || (time != this.timeRangeMin))) {
            var end = Ext.getCmp(field.endTimeField);
            end.setMinValue(time);
            end.validate();
            this.timeRangeMin = time;
        }
        return true;
    }
});
Ext.ux.ComboField = Ext.extend(Ext.form.ComboBox ,{
	url: '',
	keyId: 'id',
	keyName: 'name',
	bean: null,
	root: null,
	idProperty: null,
	totalProperty: null,
	initComponent: function(){
		var me = this;
		me.mode = 'local';
		me.editable = false;
		if (!me.store) {
			if (!me.bean) me.bean = [me.keyId ,me.keyName];
			me.reader = new Ext.data.JsonReader({root:me.root,idProperty:me.idProperty,totalProperty:me.totalProperty} ,me.bean);
			me.store = new Ext.data.Store({autoLoad: true,proxy: new Ext.data.HttpProxy({url:me.url}),reader: me.reader});
		}
		me.valueField = me.keyId;
		me.displayField = me.keyName;
		me.triggerAction = 'all';
		me.typeAhead = true;
		me.selectOnFocus = true;
		me.forceSelection = true;

		Ext.ux.ComboField.superclass.initComponent.call(me);
	}
});
Ext.reg('combox' ,Ext.ux.ComboField);
Ext.ux.CourseField = Ext.extend(Ext.form.ComboBox ,{
	bean: null,
	root: null,
	idProperty: null,
	totalProperty: null,
	url: '',
	coachId: null,
	initComponent: function(){
//		if (this.type == 1)
//			this.bean = Ext.data.Record.create([{name:'id',type:'int'}, {name:'name', type: 'string'}, {name:'intensity',type:'string'},{name:'costNames',type:'string'}]);
//		else
		var me = this;
		if (!me.store) {
			var _dispField = me.displayField || 'name';
			me.bean = Ext.data.Record.create([{name:'id',type:'int'}, {name: _dispField, type: 'string'}, {name:'charge',type:'string'}]);
			me.reader = new Ext.data.JsonReader({root:me.root,idProperty:me.idProperty,totalProperty:me.totalProperty} ,me.bean);
			me.store = new Ext.data.Store({autoLoad: true,proxy: new Ext.data.HttpProxy({url: me.url}),reader: me.reader});
			me.store.on('beforeload' ,me.handleParam, me);
		}
		me.mode = 'local';
		me.editable = false;
		me.valueField = 'id';
		me.displayField = 'name';
		me.triggerAction = 'all';
		me.typeAhead = true;
		me.selectOnFocus = true;
		me.forceSelection = true;
		me.on('select' ,me.selectData ,me);
		Ext.ux.CourseField.superclass.initComponent.call(me);
	},
	handleParam : function(store){
		Ext.apply(store.baseParams, {coachId: this.coachId});
	},
	setCoachId: function(coachId){
		this.coachId = coachId;
		this.store.reload();
	},
	selectData: function(the ,r ,index){
		if (this.type == 1 || this.type == 3) {
			Ext.getCmp('Intensity').setValue(r.get('intensity'));
		}
	}
});
Ext.reg('course' ,Ext.ux.CourseField);

Ext.ux.WeekNumField = Ext.extend(Ext.form.ComboBox ,{
	initComponent: function(){
		this.mode = 'local';
		this.editable = false;
		this.store = new Ext.data.JsonStore({root:'nums',idProperty:'id',autoLoad:true,proxy: new Ext.data.MemoryProxy(),fields:['id','title'],data:weekOfNoList});
		this.valueField = 'id';
		this.displayField = 'title';
		this.triggerAction = 'all';
		this.typeAhead = true;
		this.selectOnFocus = true;
		this.forceSelection = true;
		Ext.ux.WeekNumField.superclass.initComponent.call(this);
	}
});
Ext.reg('weeknum' ,Ext.ux.WeekNumField);
Ext.ux.WeeksField = Ext.extend(Ext.form.ComboBox ,{
	initComponent: function(){
		this.mode = 'local';
		this.editable = false;
		this.store = new Ext.data.JsonStore({root:'weeks',idProperty:'id',autoLoad:true,proxy: new Ext.data.MemoryProxy(),fields:['id','title'],data:weekOfList});
		this.valueField = 'id';
		this.displayField = 'title';
		this.triggerAction = 'all';
		this.typeAhead = true;
		this.selectOnFocus = true;
		this.forceSelection = true;
		Ext.ux.WeeksField.superclass.initComponent.call(this);
	}
});
Ext.reg('weeks' ,Ext.ux.WeeksField);

Ext.ux.ColorField = Ext.extend(Ext.form.TriggerField,  {
    triggerConfig: {
        src: Ext.BLANK_IMAGE_URL,
        tag: "img",
        cls: "x-form-trigger x-form-color-trigger"
    },
    invalidText : "Colors must be in a the hex format #FFFFFF.",
    regex: /^\#[0-9A-F]{6}$/i,
    editable: false,
    allowBlank: true,
    initComponent : function(){
        Ext.ux.ColorField.superclass.initComponent.call(this);
        this.addEvents('select');
        this.on('change', function(c, v){
            this.onSelect(c, v);
        }, this);
    },
    onDestroy : function(){
		Ext.destroy(this.menu);
        Ext.ux.ColorField.superclass.onDestroy.call(this);
    },
    afterRender: function(){
        Ext.ux.ColorField.superclass.afterRender.call(this);
        this.el.setStyle('background', this.value);
        this.detectFontColor();        
    },
    onTriggerClick : function(){
        if(this.disabled){
            return;
        }
        if(this.menu == null){
            this.menu = new Ext.ux.ColorMenu({
                hideOnClick: false,
                fallback: this.fallback
            });
        }
        this.onFocus();
        this.menu.picker.setValue(this.getValue() || '#FFFFFF');
        this.menu.show(this.el, "tl-bl?");
        this.menuEvents('on');
    },
    menuEvents: function(method){
        this.menu[method]('select', this.onSelect, this);
        this.menu[method]('hide', this.onMenuHide, this);
        this.menu[method]('show', this.onFocus, this);
    },
    onSelect: function(m, d){
        this.setValue(d);
        this.fireEvent('select', this, d);
        this.el.setStyle('background', d);
        this.detectFontColor();
    },
    detectFontColor : function(){
        if(!this.menu || !this.menu.picker.rawValue){
            if(!this.value) {
                value = 'FFFFFF';
            } else {
                var h2d = function(d){ return parseInt(d, 16); };
                var value = [
                    h2d(this.value.slice(1, 3)),
                    h2d(this.value.slice(3, 5)),
                    h2d(this.value.slice(5))
                ];
            }
        } else {
            var value = this.menu.picker.rawValue;
        }
        var avg = (value[0] + value[1] + value[2]) / 3;
        this.el.setStyle('color', (avg > 128) ? '#000' : '#FFF');
    },    
    onMenuHide: function(){
        this.focus(false, 60);
        this.menuEvents('un');
    }
});    

Ext.ux.ColorMenu = Ext.extend(Ext.menu.Menu, {
   enableScrolling: false,
   initComponent: function(){
       Ext.apply(this, {
           plain: true,
           showSeparator: false,
           items: this.picker = new Ext.ux.ColorPicker(Ext.apply({
               internalRender: this.strict || !Ext.isIE,
               wheelImage: this.wheelImage,
               gradientImage: this.gradientImage,
               fallback: this.fallback
           }, this.initialConfig))
       });
       this.picker.purgeListeners();
       Ext.ux.ColorMenu.superclass.initComponent.call(this);
       this.relayEvents(this.picker, ["select"]);
       this.on('select', this.menuHide, this);
       if(this.handler){
           this.on('select', this.handler, this.scope || this);
       }
   },
   menuHide: function() {
       if(this.hideOnClick){
           this.hide(true);
       }
   },
   doLayout: function(shallow, force){
       Ext.ux.ColorMenu.superclass.doLayout.call(this, shallow, force);
       this.getEl().setZIndex(30000);
   }
});

Ext.ux.ColorMenu.prototype.wheelImage = (function(){
    var wheelImage = new Image();
    wheelImage.onload = Ext.emptyFn;
    wheelImage.src = 'images/wheel.png';
    return wheelImage;
})();

Ext.ux.ColorMenu.prototype.gradientImage = (function(){
    var gradientImage = new Image();
    gradientImage.onload = Ext.emptyFn;
    gradientImage.src = 'images/gradient.png';
    return gradientImage;
})();

Ext.ux.ColorPicker = function(config){
    Ext.ux.ColorPicker.superclass.constructor.call(this, config);
    this.addEvents('select');
    if(!this.value)
        this.value = this.defaultValue;
    if(this.handler){
        this.on("select", this.handler, this.scope, true);
    }
};
Ext.extend(Ext.ux.ColorPicker, Ext.ColorPalette, {
    canvasSupported: true,
    itemCls: 'x-color-picker',
    defaultValue: "#333333",
    width: 200,
    onRender : function(container, position){
        if(!this.value)
            this.value = this.defaultValue;
        var el = document.createElement("div");
        el.className = this.itemCls;
        container.dom.insertBefore(el, position);
        Ext.get(el).setWidth(this.width);
        this.canvasdiv = Ext.get(el).createChild({
            tag: 'div'
        });
        this.wheel = this.canvasdiv.dom.appendChild(document.createElement("canvas"));
        this.wheel.setAttribute('width', '200');
        this.wheel.setAttribute('height', '200');
        this.wheel.setAttribute('class', 'x-color-picker-wheel');
        
        if(this.fallback || !this.wheel.getContext || !this.wheel.getContext('2d').getImageData){
            this.canvasSupported = false;
            this.itemCls = 'x-color-palette';
            while(container.dom.firstChild){ container.dom.removeChild(container.dom.firstChild); }
            Ext.ux.ColorPicker.superclass.onRender.call(this, container, position);
            return;
        }
        this.wheel.getContext('2d').drawImage(this.wheelImage, 0, 0);
        this.drawGradient();
        Ext.get(this.wheel).on('click', this.select, this);
        this.el = Ext.get(el);
    },
    afterRender : function(){
        Ext.ColorPalette.superclass.afterRender.call(this);
        if(!this.canvasSupported) return;
        var t = new Ext.dd.DragDrop(this.wheel);
        var self = this;
        t.onDrag = function(e, t){
            self.select(e, this.DDM.currentTarget);
        };
    },
    select : function(e, t){
        if(!this.canvasSupported){
            this.value = e;
            Ext.ux.ColorPicker.superclass.select.call(this, e);
            this.fireEvent('select', this, '#'+this.value); 
            return;
        }
        var context = this.wheel.getContext('2d');
        var coords = [
            e.xy[0] - Ext.get(t).getLeft(),
            e.xy[1] - Ext.get(t).getTop()
        ];
        try{
            var data = context.getImageData(coords[0], coords[1], 1, 1);
        }catch(e){ return; } // The user selected an area outside the <canvas>
        if(data.data[3] == 0){
            var context = this.gradient.getContext('2d');
            var data = context.getImageData(coords[0], coords[1], 1, 1);
            if(data.data[3] == 0) return;
            
            this.rawValue = data.data;
            this.value = this.hexValue(data.data[0], data.data[1], data.data[2]);
            this.fireEvent('select', this, this.value);
        }else{
            this.rawValue = data.data;
            this.value = this.hexValue(data.data[0], data.data[1], data.data[2]);
            this.drawGradient();
            this.fireEvent('select', this, this.value);
        }
    },
    drawGradient : function(){
        if(!this.gradient){
            this.gradient = this.canvasdiv.dom.appendChild(document.createElement("canvas"));
            this.gradient.setAttribute('width', '200');
            this.gradient.setAttribute('height', '200');
            this.gradient.setAttribute('class', 'x-color-picker-gradient');
            if(typeof G_vmlCanvasManager != 'undefined') 
                this.gradient = G_vmlCanvasManager.initElement(this.gradient);
            Ext.get(this.gradient).on('click', this.select, this);
        }
        var context = this.gradient.getContext('2d');
        var center = [97.5, 98];
        
        context.clearRect(0, 0, this.gradient.width, this.gradient.height);
        
        context.beginPath();
        context.fillStyle = this.value;
        context.strokeStyle = this.value;
        context.arc(center[0], center[0], 65, 0, 2*Math.PI, false);
    	context.closePath();
    	context.fill();
        this.gradient.getContext('2d').drawImage(this.gradientImage, 33, 32);
    },
    hexValue : function(r,g,b){
        var chars = '0123456789ABCDEF';
        return '#'+(
            chars[parseInt(r/16)] + chars[parseInt(r%16)] +
            chars[parseInt(g/16)] + chars[parseInt(g%16)] +
            chars[parseInt(b/16)] + chars[parseInt(b%16)]
        );
    },
    getValue: function(){
        return this.value;
    },
    setValue: function(v){
        this.value = v;
    }
});
Ext.reg('colorfield', Ext.ux.ColorField);

Ext.calendar.ColorPicker = Ext.extend(Ext.form.ComboBox, {
    fieldLabel: '事件颜色',
    valueField: 'Id',
    displayField: 'Title',
    triggerAction: 'all',
    mode: 'local',
    forceSelection: true,
    width: 200,
    initComponent: function() {
        this.store = new Ext.data.JsonStore({
    		storeId : 'colorStore',
    		root : 'colors',
    		idProperty : 'id',
    		data : colorList,
    		proxy : new Ext.data.MemoryProxy(),
    		autoLoad : true,
    		fields : [
    		    {name : 'Id',mapping : 'id',type : 'int'},
    		    {name : 'Title',mapping : 'title',type : 'string'}
    		],
    		sortInfo : {field : 'Id',direction : 'ASC'}
    	});
        Ext.calendar.ColorPicker.superclass.initComponent.call(this);
        this.tpl = this.tpl ||
        '<tpl for="."><div class="x-combo-list-item ext-color-{' + this.valueField +
        '}"><div class="ext-cal-picker-icon">&nbsp;</div>{' + this.displayField + '}</div></tpl>';
    },
    afterRender: function() {
        Ext.calendar.ColorPicker.superclass.afterRender.call(this);

        this.wrap = this.el.up('.x-form-field-wrap');
        this.wrap.addClass('ext-calendar-picker');

        this.icon = Ext.DomHelper.append(this.wrap, {
            tag: 'div',
            cls: 'ext-cal-picker-icon ext-cal-picker-mainicon'
        });
    },
    setValue: function(value) {
        this.wrap.removeClass('ext-color-' + this.getValue());
        if (!value && this.store !== undefined) {
            value = this.store.getAt(0).data.Id;
        }
        Ext.calendar.ColorPicker.superclass.setValue.call(this, value);
        this.wrap.addClass('ext-color-' + value);
    }
});
Ext.reg('colorpicker', Ext.calendar.ColorPicker);

Ext.calendar.DateRepeatField = Ext.extend(Ext.form.Field, {
    toText: 'to',
    onRender: function(ct, position) {
        if (!this.el) {
        	this.whereField = new Ext.form.RadioGroup({
        		id: 'mode',
        		columns: 1,
        		disabled: this.disabled,
        		items:[
	                {boxLabel: '按日', name: 'mode',inputValue:1},
	                {boxLabel: '按周', name: 'mode',inputValue:2},
	                {boxLabel: '按月', name: 'mode',inputValue:3}
        		]
        	});
        	this.whereField.on('change' ,this.changeView ,this);
        	this.dayField = new Ext.form.CompositeField({
        		disabled: this.disabled,
        		items:[
        		    {xtype:'displayfield',value:'每',style:'margin-top:5px;'},
        		    {xtype:'numberfield',id:'dayNum',name:'dayNum',width:50},
        		    {xtype:'displayfield',value:'天',style:'margin-top:5px;'}
        		]
        	});
        	this.weekField = new Ext.form.CheckboxGroup({
        		hidden: true,
        		columns: 3,
        		disabled: this.disabled,
        		items:[
                   {boxLabel: '星期日', name: 'cb-auto-1',inputValue: 1},
                   {boxLabel: '星期一', name: 'cb-auto-2',inputValue: 2},
                   {boxLabel: '星期二', name: 'cb-auto-3',inputValue: 3},
                   {boxLabel: '星期三', name: 'cb-auto-4',inputValue: 4},
                   {boxLabel: '星期四', name: 'cb-auto-5',inputValue: 5},
                   {boxLabel: '星期五', name: 'cb-auto-5',inputValue: 6},
                   {boxLabel: '星期六', name: 'cb-auto-5',inputValue: 7}
        		]
        	});
        	this.dayOfMonth = new Ext.form.CompositeField({
        		hidden: true,
        		disabled: this.disabled,
        		items:[{xtype:'radio',id:'month1',name:'month',boxLabel:'每月的第'},{xtype:'numberfield',id:'dayNum1',width:50},{xtype:'displayfield',value:'天',style:'margin-top:5px;'}]
        	});
        	this.weekOfMonth = new Ext.form.CompositeField({
        		hidden: true,
        		disabled: this.disabled,
        		items:[{xtype:'radio',id:'month2',name:'month',boxLabel:'每月的第'},{xtype:'weeknum',id:'weekNum1',width:60},{xtype:'weeks',id:'weekNum2',width:60}]
        	});
            this.fieldCt = new Ext.Container({
                autoEl: {id: this.id},
                cls: 'ext-dt-range',
                renderTo: ct,
                layout: 'column',
                height: 65,
                defaults: {hideParent: true},
                items: [{
                	columnWidth:.3,
                	baseCls:'x-plain',
                	items:[this.whereField]
                },{
                	columnWidth:.7,
                	baseCls:'x-plain',
                	items:[this.dayField,this.weekField,this.dayOfMonth,this.weekOfMonth]
                }]
            });

            this.fieldCt.ownerCt = this;
            this.el = this.fieldCt.getEl();
            this.items = new Ext.util.MixedCollection();
            this.items.addAll([this.whereField,this.dayField,this.weekField,this.dayOfMonth,this.weekOfMonth]);
        }
        Ext.calendar.DateRepeatField.superclass.onRender.call(this, ct, position);
    },
    changeView: function(the ,checked){
    	if (the.getValue()) {
	    	var val = the.getValue().inputValue;
	    	if (val == 1){
	    		this.weekField.hide();
	    		this.dayOfMonth.hide();
	    		this.weekOfMonth.hide();
	    		this.dayField.show();
	    	} else if (val == 2){
	    		this.dayField.hide();
	    		this.weekField.show();
	    		this.dayOfMonth.hide();
	    		this.weekOfMonth.hide();
	    	} else {
	    		this.dayField.hide();
	    		this.weekField.hide();
	    		this.dayOfMonth.show();
	    		this.weekOfMonth.show();
	    	}
    	}
    },
    getValue: function() {
    	var values = {};
    	if (this.whereField.getValue()) {
	    	var v = this.whereField.getValue().inputValue;
	    	if (v == 1) {
	    		values = {RepeatMode: 1, RepeatValue: Ext.getCmp('dayNum').getValue()};
	    	} else if (v == 2) {
	    		var val = ""; arr = this.weekField.getValue();
	    		for (var i = 0 ; i < arr.length ;i++) {
	    			val = val + (i > 0 ? "," : "") + arr[i].inputValue; 
	    		}
	    		values = {RepeatMode: 2, RepeatValue: val};
	    	} else if (v == 3) {
	    		if (Ext.getCmp('month1').getValue() == true)
	    			values = {RepeatMode: 3, RepeatValue: Ext.getCmp('dayNum1').getValue()};
	    		else if (Ext.getCmp('month2').getValue() == true)
	    			values = {RepeatMode: 4, RepeatValue: Ext.getCmp('weekNum1').getValue(), RepeatWeekOf: Ext.getCmp('weekNum2').getValue()};
	    	}
    	}
    	return values;
    },
    setValue: function(v) {
    	if (v != null && v.mode) {
    		var mode = v.mode;
    		var where = mode == 4 ? 3 : mode;
    		this.whereField.items.each(function(item){
    			if (item.inputValue == where) item.setValue(true);
    		});
   			if (mode == 1){
   				Ext.getCmp('dayNum').setValue(v.value);
   			} else if (mode == 2){
   				var weeks = v.value.split(',') + ",";
   				this.weekField.items.each(function(item){
   					if (weeks.indexOf(item.inputValue + ",") != -1) item.setValue(true);
   				});
   			} else if (mode == 3){
   				Ext.getCmp('month1').setValue(true);
   				Ext.getCmp('dayNum1').setValue(v.value);
   			} else {
   				Ext.getCmp('month2').setValue(true);
   				Ext.getCmp('weekNum1').setValue(v.value);
   				Ext.getCmp('weekNum2').setValue(v.weekOf);
   			}
    	} else {
    		this.whereField.items.each(function(item){
    			item.setValue(false);
    		});
    	}
    },
    isValidate: function(){
    	if (!this.whereField.getValue()) {
    		alert('您的重复条件未选择，请先选择！');
    		return false;
    	}
    	var v = this.whereField.getValue().inputValue;
    	if (v == 1){
    		if (Ext.getCmp('dayNum').getValue() <= 0){
    			alert('您的频率天数未输入！');
    			return false;
    		}
    	} else if (v == 2){
    		var hasCheck = false;
    		this.weekField.items.each(function(item){
    			if (item.getValue() == true){
    				hasCheck = true;
    			}
    		});
    		if (!hasCheck){
    			alert('您的频率周期未选择！');
    			return false;
    		}
    	} else {
    		if (Ext.getCmp('month1').getValue() == false && Ext.getCmp('month2').getValue() == false){
    			alert('您选择的按月的频率未设置！');
    			return false;
    		}
    		if (Ext.getCmp('month1').getValue() == true){
    			if (Ext.getCmp('dayNum1').getValue() <= 0){
    				alert('您的月频率未设置！');
    				return false;
    			}
    		}
    		if (Ext.getCmp('month2').getValue() == true){
        		if (Ext.getCmp('weekNum1').getValue == ''){
    				alert('您的月频率未设置！');
    				return false;
        		}
        		if (Ext.getCmp('weekNum2').getValue == ''){
    				alert('您的月频率未设置！');
    				return false;
        		}
    		}
    	}
    	return true;
    },
    isDirty: function() {
        var dirty = false;
        if (this.rendered && !this.disabled) {
            this.items.each(function(item) {
                if (item.isDirty()) {
                    dirty = true;
                    return false;
                }
            });
        }
        return dirty;
    },
    onDisable: function() {
        this.delegateFn('disable');
    },
    onEnable: function() {
        this.delegateFn('enable');
    },
    reset: function() {
        this.delegateFn('reset');
    },
    delegateFn: function(fn) {
        this.items.each(function(item) {
            if (item[fn]) {
                item[fn]();
            }
        });
    },
    beforeDestroy: function() {
        Ext.destroy(this.fieldCt);
        Ext.calendar.DateRepeatField.superclass.beforeDestroy.call(this);
    },
    getRawValue: Ext.emptyFn,
    setRawValue: Ext.emptyFn
});
Ext.reg('repeatfield', Ext.calendar.DateRepeatField);

var colorList = {
    "colors":[
        {"id":1,"title":"蓝色"},
        {"id":2,"title":"绿色"},
        {"id":3,"title":"其它"}]
};
var weekOfList = {
    "weeks":[
         {"id":1,"title":"星期日"},
         {"id":2,"title":"星期一"},
         {"id":3,"title":"星期二"},
         {"id":4,"title":"星期三"},
         {"id":5,"title":"星期四"},
         {"id":6,"title":"星期五"},
         {"id":7,"title":"星期六"}
    ]
};
var weekOfNoList = {
    "nums":[
         {"id":1,"title":"第一个"},
         {"id":2,"title":"第二个"},
         {"id":3,"title":"第三个"},
         {"id":4,"title":"第四个"}
    ]
};
var remindList = {
	"reminders":[
	     {"id":240,"title":"4个小时"},
	     {"id":480,"title":"8个小时"},
	     {"id":720,"title":"12个小时"},
	     {"id":960,"title":"16个小时"},
	     {"id":1200,"title":"20个小时"},
	     {"id":1440,"title":"24个小时"}
	]
};

Ext.calendar.DateTimeField = Ext.extend(Ext.form.Field, {
	fromText: '开始时间',
    toText: '结束时间',
    timeSplit: ':',
    readOnly: false,
    onRender: function(ct, position) {
        if (!this.el) {
            this.startDate = new Ext.form.DateField({id: this.id + '-start-date',format: 'Y-m-d',minValue: new Date(),width: 100, readOnly: this.readOnly});
            this.startHour = new Ext.form.NumberField({
                id: this.id + '-start-time-hour',
                labelWidth: 0,
                hideLabel: true,
                width: 25
            });
            this.startMinute = new Ext.form.NumberField({
                id: this.id + '-start-time-minute',
                fieldLabel: this.timeSplit,
                labelWidth: 10,
                width: 25
            });
            this.endHour = new Ext.form.NumberField({
                id: this.id + '-end-time-hour',
                labelWidth: 0,
                hideLabel: true,
                width: 25
            });
            this.endMinute = new Ext.form.NumberField({
                id: this.id + '-end-time-minute',
                fieldLabel: this.timeSplit,
                labelWidth: 10,
                width: 25
            });
            this.fromLabel = new Ext.form.Label({style:'margin-top:5px;',id:this.id + '-from-label',text: this.fromText});
            this.fromSplit = new Ext.form.Label({style:'margin-top:5px;',id:this.id + '-from-split-label',text: this.timeSplit});
            this.toLabel = new Ext.form.Label({style:'margin-top:5px;',id: this.id + '-to-label',text: this.toText});
            this.toSplit = new Ext.form.Label({style:'margin-top:5px;',id: this.id + '-to-split-label',text: this.timeSplit});
            Ext.applyIf(this.formLabel, this.defaults);
            Ext.applyIf(this.toLabel, this.defaults);
            Ext.applyIf(this.fromSplit, this.defaults);
            Ext.applyIf(this.toSplit, this.defaults);
            this.fieldCt = new Ext.Container({
                autoEl: {id: this.id},
                cls: 'ext-dt-range',
                renderTo: ct,
                layout: 'hbox',
                items: [this.startDate, this.fromLabel, this.startHour, this.fromSplit, this.startMinute, this.toLabel, this.endHour, this.toSplit, this.endMinute]
            });

            this.fieldCt.ownerCt = this;
            this.el = this.fieldCt.getEl();
            this.items = new Ext.util.MixedCollection();
            this.items.addAll([this.startDate, this.fromLabel, this.startHour, this.fromSplit, this.startMinute, this.toLabel, this.endHour, this.toSplit, this.endMinute]);
        }
        Ext.calendar.DateTimeField.superclass.onRender.call(this, ct, position);
    },
    getValue: function() {
        return {start: this.getDT('start') ,end: this.getDT('end')};
    },
    getDT: function(startend) {
        var dt = this.startDate.getValue();

        if (Ext.isDate(dt)) {
            dt = dt.format(this['startDate'].format);
        } else {
            return null;
        }
    	var hour = this[startend + 'Hour'].getValue(), hour = hour.lpadding(2);
    	var minute = this[startend + 'Minute'].getValue(), minute = minute.lpadding(2);
        var time = hour + this.timeSplit + minute;
        var strDate = Date.parseDate(dt + ' ' + time + ':00' ,'Y-m-d H:i:s');
        return strDate;
    },
    setValue: function(v) {
        if (v && v.start){
        	this.setDT(v.start ,'start');
        	this.setDT(v.end, 'end');
        }
    },
    setReadOnly: function(readOnly){
        this.readOnly = readOnly;
    	this.startDate.setReadOnly(readOnly);
    },
    setDT: function(dt, startend) {
        if (dt && Ext.isDate(dt)) {
		    this.startDate.setValue(dt);
		    var times = dt.format('H:i').split(':');
		    this[startend + 'Hour'].setValue(times[0].lpadding(2));
		    this[startend + 'Minute'].setValue(times[1].lpadding(2));
		    return true;
    	}
    },

    isDirty: function() {
        var dirty = false;
        if (this.rendered && !this.disabled) {
            this.items.each(function(item) {
                if (item.isDirty()) {
                    dirty = true;
                    return false;
                }
            });
        }
        return dirty;
    },
    onDisable: function() {
        this.delegateFn('disable');
    },
    onEnable: function() {
        this.delegateFn('enable');
    },
    reset: function() {
        this.delegateFn('reset');
    },
    delegateFn: function(fn) {
        this.items.each(function(item) {
            if (item[fn]) {
                item[fn]();
            }
        });
    },
    beforeDestroy: function() {
        Ext.destroy(this.fieldCt);
        Ext.calendar.DateTimeField.superclass.beforeDestroy.call(this);
    },
    getRawValue: Ext.emptyFn,
    setRawValue: Ext.emptyFn
});
Ext.reg('datetimefield', Ext.calendar.DateTimeField);

Ext.calendar.RemindField = Ext.extend(Ext.form.Field, {
	startText: '在开始前',
    endText: '提醒',
    onRender: function(ct, position) {
        if (!this.el) {
            this.checkBox = new Ext.form.Checkbox();
            this.checkBox.on('check', this.setStatus, this);
            this.reminder = new Ext.form.ComboBox({
            	id: this.id + '-reminder-checkbox',
        		mode: 'local',
        		width: 100,
        		disabled: true,
        		editable: false,
        		valueField: 'id',
        		displayField: 'title',
        		store: new Ext.data.JsonStore({root:'reminders',idProperty:'id',autoLoad:true,proxy: new Ext.data.MemoryProxy(),fields:['id','title'],data:remindList}),
        		triggerAction: 'all',
        		typeAhead: true,
        		selectOnFocus: true,
        		forceSelection: true
            });
            this.startLabel = new Ext.form.Label({style:'margin-top:5px;',id:this.id + '-start-label',text: this.startText});
            this.endLabel = new Ext.form.Label({style:'margin-top:5px;',id: this.id + '-end-label',text: this.endText});
            Ext.applyIf(this.formLabel, this.defaults);
            Ext.applyIf(this.toLabel, this.defaults);
            this.fieldCt = new Ext.Container({
                autoEl: {id: this.id},
                cls: 'ext-dt-range',
                renderTo: ct,
                layout: 'hbox',
                items: [this.checkBox, this.startLabel, this.reminder, this.endLabel]
            });

            this.fieldCt.ownerCt = this;
            this.el = this.fieldCt.getEl();
            this.items = new Ext.util.MixedCollection();
            this.items.addAll([this.checkBox, this.startLabel, this.reminder, this.endLabel]);
        }
        Ext.calendar.RemindField.superclass.onRender.call(this, ct, position);
    },
    setStatus: function(o, checked) {
    	this.reminder.setDisabled(!checked);
    },
    getValue: function() {
    	if (this.checkBox.getValue() == true) {
    		return {hasReminder: this.checkBox.getValue() == true ? "1" : "0" ,reminder: this.reminder.getValue()};
    	} else {
    		return {hasReminder: 0};
    	} 
    },
    setValue: function(v) {
        if (v && v.hasReminder){
        	this.checkBox.setValue(v.hasReminder == '1' ? true : false);
        	this.reminder.setValue(v.reminder);
        }
    },
    setReadOnly: function(readOnly) {
    	this.checkBox.setReadOnly(readOnly);
    	this.reminder.setReadOnly(readOnly);
    },
    isDirty: function() {
        var dirty = false;
        if (this.rendered && !this.disabled) {
            this.items.each(function(item) {
                if (item.isDirty()) {
                    dirty = true;
                    return false;
                }
            });
        }
        return dirty;
    },
    onDisable: function() {
        this.delegateFn('disable');
    },
    onEnable: function() {
        this.delegateFn('enable');
    },
    reset: function() {
        this.delegateFn('reset');
    },
    delegateFn: function(fn) {
        this.items.each(function(item) {
            if (item[fn]) {
                item[fn]();
            }
        });
    },
    beforeDestroy: function() {
        Ext.destroy(this.fieldCt);
        Ext.calendar.RemindField.superclass.beforeDestroy.call(this);
    },
    getRawValue: Ext.emptyFn,
    setRawValue: Ext.emptyFn
});
Ext.reg('remindfield', Ext.calendar.RemindField);

Ext.calendar.RepeatScopeField = Ext.extend(Ext.form.Field, {
    toText: 'to',
    onRender: function(ct, position) {
        if (!this.el) {
        	this.dayField = new Ext.form.CompositeField({
        		disabled: this.disabled,
        		items:[
        		    {xtype:'radio',id:'sd1',name:'startDates', boxLabel:'开始日期'},
        		    {xtype:'datefield',id:'startDate1',width:90,format:'Y-m-d',minValue: new Date()},
        		    {xtype:'displayfield',value:'重复次数',style:'margin-top:2px;',width:50},
        		    {xtype:'numberfield',width: 90, id:'endDate1'}
        		]
        	});
        	this.dateField = new Ext.form.CompositeField({
        		disabled: this.disabled,
        		items:[
        		    {xtype:'radio',id:'sd2',name:'startDates', boxLabel:'开始日期 '},
        		    {xtype:'datefield',id:'startDate2',minValue: new Date(), vtype:'daterange',endDateField:'endDate2',width:90,format:'Y-m-d'},
        		    {xtype:'displayfield',value:'结束日期',style:'margin-top:2px;',width:50},
        		    {xtype:'datefield',width: 90, id:'endDate2',vtype:'daterange',startDateField:'startDate2',format:'Y-m-d'}
        		]
        	});
            this.fieldCt = new Ext.Container({
                autoEl: {id: this.id},
                cls: 'ext-dt-range',
                renderTo: ct,
                layout: 'column',
                height: 45,
                defaults: {hideParent: true},
                items: [{columnWidth:1,baseCls: 'x-plain', items:[this.dayField, this.dateField]}]
            });

            this.fieldCt.ownerCt = this;
            this.el = this.fieldCt.getEl();
            this.items = new Ext.util.MixedCollection();
            this.items.addAll([this.dayField,this.dateField]);
        }
        Ext.calendar.RepeatScopeField.superclass.onRender.call(this, ct, position);
    },
    getValue: function() {
    	return (Ext.getCmp('sd1').getValue() == true ? {RepeatStart: Ext.getCmp('startDate1').getValue().format('Y-m-d') ,RepeatNum: Ext.getCmp('endDate1').getValue(), RepeatEnd: null} :
    		(Ext.getCmp('sd2').getValue() == true ? {RepeatStart: Ext.getCmp('startDate2').getValue().format('Y-m-d') ,RepeatNum: '', RepeatEnd: Ext.getCmp('endDate2').getValue().format('Y-m-d')} : null));
    },
    setValue: function(v) {
    	if (v && v.start){
    		if (v.num && v.num > 0){
    			Ext.getCmp('sd1').setValue(true);
    			Ext.getCmp('startDate1').setValue(v.start.substring(0,10));
    			Ext.getCmp('endDate1').setValue(v.num);
    		} else {
    			Ext.getCmp('sd2').setValue(true);
    			Ext.getCmp('startDate2').setValue(v.start.substring(0,10));
    			Ext.getCmp('endDate2').setValue(v.end.substring(0,10));
    		}
    	} else {
    		this.items.each(function(item){
    			item.setValue('');
    		});
    	}
    },
    isDirty: function() {
        var dirty = false;
        if (this.rendered && !this.disabled) {
            this.items.each(function(item) {
                if (item.isDirty()) {
                    dirty = true;
                    return false;
                }
            });
        }
        return dirty;
    },
    onDisable: function() {
        this.delegateFn('disable');
    },
    onEnable: function() {
        this.delegateFn('enable');
    },
    reset: function() {
        this.delegateFn('reset');
    },
    delegateFn: function(fn) {
        this.items.each(function(item) {
            if (item[fn]) {
                item[fn]();
            }
        });
    },
    isValidate: function(){
    	if (Ext.getCmp('sd1').getValue() == false && Ext.getCmp('sd2').getValue() == false){
    		alert('重复范围必须选择一个！');
    		return false;
    	}
    	if (Ext.getCmp('sd1').getValue() == true){
    		if (Ext.getCmp('startDate1').getValue() == ''){
    			alert('开始日期不能为空！');
    			Ext.getCmp('startDate1').focus();
    			return false;
    		}
    		if (Ext.getCmp('endDate1').getValue() == '' || Ext.getCmp('endDate1').getValue() <= 0){
    			alert('重复次数必须为一个大于零的数值！');
    			Ext.getCmp('endDate1').focus();
    			return false;
    		}
    	}
    	if (Ext.getCmp('sd2').getValue() == true){
    		if (Ext.getCmp('startDate2').getValue() == ''){
    			alert('开始日期不能为空！');
    			Ext.getCmp('startDate2').focus();
    			return false;
    		}
    		if (Ext.getCmp('endDate2').getValue() == ''){
    			alert('结束日期不能为空！');
    			Ext.getCmp('endDate2').focus();
    			return false;
    		}
    	}
    	return true;
    },
    beforeDestroy: function() {
        Ext.destroy(this.fieldCt);
        Ext.calendar.RepeatScopeField.superclass.beforeDestroy.call(this);
    },
    getRawValue: Ext.emptyFn,
    setRawValue: Ext.emptyFn
});
Ext.reg('scopefield', Ext.calendar.RepeatScopeField);

Ext.ButtonDatePicker = Ext.extend(Ext.Button,  {
	format: 'Y-m-d',
    initComponent : function(){
    	this.text = "　" + new Date().format(this.format) + "　";
        this.menu = new Ext.menu.DateMenu({
            hideOnClick: false,
            focusOnSelect: false
        });
        this.menuEvents('on');
        Ext.ButtonDatePicker.superclass.initComponent.call(this);
        this.addEvents('select');

    },
    menuEvents: function(method){
        this.menu[method]('select', this.onSelect, this);
    },
    onSelect: function(m, d){
    	this.setText("　" + d.format('Y-m-d') + "　");
        this.fireEvent('select', this, d);
        this.menu.hide();
    }
});
Ext.reg('buttondatepicker', Ext.ButtonDatePicker);

Ext.calendar.EventRepeatGrid = Ext.extend(Ext.grid.GridPanel, {
	width: 200,
	height: 120,
	columnLines: true,
	params: {},
	stripeRows: true,
	initComponent: function(){
		var shift = new Ext.data.Record.create([
      		{name:'id',type: 'int'},
      		{name:'name',type:'string',mapping:'course.name'},
      		{name:'start',type:'string',mapping:'startTime'},
      		{name:'end',type:'string',mapping:'endTime'}
      	]);
		var reader = new Ext.data.JsonReader({idProperty:'id'} ,shift);
		this.store = new Ext.data.Store({autoLoad: true,proxy: new Ext.data.HttpProxy({url:'clubShift!findGroup.asp'}),reader: reader});
		this.store.on('beforeload' ,this.handlerParam, this);
		this.sm = new Ext.grid.CheckboxSelectionModel();
		this.cm = new Ext.grid.ColumnModel({
	        defaults: {width: 120, sortable: true},
	        columns: [
	  	        new Ext.grid.RowNumberer({width: 30}),
	            this.sm,
	            {id: 'name', header: '课程名称', dataIndex: 'name', width: 200},
	            {id: 'start', header: '开始时间', dataIndex: 'start', width: 200},
	            {id: 'end', header: '结束时间', dataIndex: 'end', width: 200}
	        ]
	    });
		Ext.calendar.EventRepeatGrid.superclass.initComponent.call(this);
	},
	handlerParam: function(store, opt){
		Ext.apply(store.baseParams, this.params);
	},
	setParams: function(params){
		this.params = params;
		this.store.reload();
	},
	onClean: function(){
		this.store.removeAll();
	},
	getRecords: function(){
		return this.getSelectionModel().getSelections();
	},
	getJsons: function(){
		var records = this.getSelectionModel().getSelections();
		if (records.length > 0) {
			var jsons = '';
			for (var i = 0 ; i < records.length ; i++) {
				obj = records[i].data;
				if (i > 0) jsons += "&";
				jsons += "ids=" + obj.id;
			}
			return jsons;
		}
		return '';
		
	}
});
Ext.reg('eventrepeatgrid', Ext.calendar.EventRepeatGrid);

Ext.calendar.EventMappings = {
	EventId : {name : 'EventId', mapping : 'id', type : 'int'},
	Place : {name : 'Place', mapping : 'place', type : 'string'},
	MemberId : {name : 'MemberId', mapping : 'member.id', type : 'int'},
	MemberName: {name: 'MemberName', mapping: 'member.name', type: 'string'},
	Color : {name : 'Color', mapping: 'color', type:'string'},
	CourseName : {name : 'CourseName', mapping : 'courseInfo.name', type : 'string'},
	CourseId : {name : 'CourseId', mapping : 'courseInfo.id', type : 'int'},
	CoachId : {name : 'CoachId', mapping : 'coach.id', type : 'int'},
	CoachName : {name : 'CoachName', mapping : 'coach.name', type : 'string'},
	Intensity : {name : 'Intensity', mapping : 'courseInfo.intensity', type : 'string'},
	Costs : {name : 'Costs', mapping: 'costs', type : 'string'},
	MemberPrice: {name: 'MemberPrice', mapping: 'memberPrice', type: 'float'},
	HourPrice: {name: 'HourPrice', mapping: 'hourPrice', type: 'float'},
	JoinNum : {name : 'JoinNum', mapping: 'joinNum', type : 'int'},
	Count : {name : 'Count', mapping: 'count', type: 'int'},
	Score : {name : 'Score', mapping: 'grade', type: 'int'},
	Memo: {name: 'Memo', mapping: 'memo', type: 'string'},
	Type : {name : 'Type', mapping: 'type', type : 'string'},
	StartDate : {name : 'StartDate', mapping : 'startDate', type : 'date', dateFormat : 'c'},
	EndDate : {name : 'EndDate', mapping : 'endDate', type : 'date', dateFormat : 'c'},
	RepeatMode: {name: 'RepeatMode', mapping: 'mode', type: 'string'},
	RepeatValue: {name: 'RepeatValue', mapping: 'value', type: 'string'},
	RepeatWeekOf: {name: 'RepeatWeekOf', mapping: 'weekOf', type: 'string'},
	RepeatWhere : {name : 'RepeatWhere', mapping: 'repeatWhere', type : 'string'},
	RepeatStart : {name : 'RepeatStart', mapping: 'repeatStart', type : 'string'},
	RepeatNum : {name : 'RepeatNum', mapping: 'repeatNum', type : 'int'},
	RepeatEnd : {name : 'RepeatEnd', mapping: 'repeatEnd', type : 'string'},
	HasReminder: {name: 'HasReminder', mapping: 'hasReminder', type: 'string'},
	Reminder : {name : 'Reminder', mapping: 'reminder', type : 'string'},
	GroupBy : {name : 'GroupBy', mapping: 'groupBy', type : 'int'},
    IsAllDay: {name: 'IsAllDay', mapping: 'ad', type: 'boolean'},
    CanEdit: {name: 'CanEdit', mapping: 'canEdit', type: 'int'},
    CanJoin: {name: 'CanJoin', mapping: 'canJoin', type: 'int'},
    CanDel: {name: 'CanDel', mapping: 'canDel', type: 'int'},
    UIType: {name: 'UIType', mapping: 'uiType', type: 'int'}
};
(function() {
	var M = Ext.calendar.EventMappings;
	Ext.calendar.EventRecord = Ext.data.Record.create([ 
	    M.EventId, M.Place, M.MemberId, M.MemberName, M.Color, M.CourseName, M.CourseId, M.CoachName, M.CoachId, M.Intensity, 
	    M.Costs, M.MemberPrice, M.HourPrice, M.JoinNum, M.Count, M.Score, M.Memo, M.Type, M.StartDate, M.EndDate, M.RepeatMode, M.RepeatValue, M.RepeatWeekOf, M.RepeatStart, 
		M.RepeatNum, M.RepeatEnd, M.HasReminder, M.Reminder, M.IsAllDay, M.GroupBy, M.CanEdit, M.CanJoin, M.CanDel,
		M.UIType
	]);

	Ext.calendar.EventRecord.reconfigure = function() {
		Ext.calendar.EventRecord = Ext.data.Record.create([
			M.EventId, M.Place, M.MemberId, M.MemberName, M.Color, M.CourseName, M.CourseId, M.CoachName, M.CoachId, M.Intensity, M.Costs, M.MemberPrice, M.HourPrice,
			M.JoinNum, M.Count, M.Score, M.Memo, M.StartDate, M.EndDate, M.Type, M.RepeatMode, M.RepeatValue, M.RepeatWeekOf, M.RepeatStart, M.RepeatNum, 
			M.RepeatEnd, M.HasReminder, M.Reminder, M.IsAllDay, M.GroupBy, M.CanEdit, M.CanJoin, M.CanDel, M.UIType
		]);
	};
})();

Ext.calendar.CostField = Ext.extend(Ext.form.Field, {
	startText: '非会员价：',
    onRender: function(ct, position) {
        if (!this.el) {
            this.priceField = new Ext.form.NumberField({width: 100}), this.priceField1 = new Ext.form.NumberField({width: 100});
            this.label = new Ext.form.Label({text: this.startText,style:'margin-top:2px;'})
            this.fieldCt = new Ext.Container({
                autoEl: {id: this.id},
                cls: 'ext-dt-range',
                renderTo: ct,
                layout: 'hbox',
                items: [this.priceField, this.label, this.priceField1]
            });

            this.fieldCt.ownerCt = this;
            this.el = this.fieldCt.getEl();
            this.items = new Ext.util.MixedCollection();
            this.items.addAll([this.priceField, this.label, this.priceField1]);
        }
        Ext.calendar.CostField.superclass.onRender.call(this, ct, position);
    },
    getValue: function() {
		return {memberPrice: this.priceField.getValue(), hourPrice: this.priceField1.getValue()};
    },
    setValue: function(v) {
    	if (v && Ext.isObject(v)) {
	    	this.priceField.setValue(v.memberPrice);
	    	this.priceField1.setValue(v.hourPrice);
    	}
    },
    setReadOnly: function(readOnly) {
    	this.priceField.setReadOnly(readOnly);
    	this.priceField1.setReadOnly(readOnly);
    },
    isDirty: function() {
        var dirty = false;
        if (this.rendered && !this.disabled) {
            this.items.each(function(item) {
                if (item.isDirty()) {
                    dirty = true;
                    return false;
                }
            });
        }
        return dirty;
    },
    onDisable: function() {
        this.delegateFn('disable');
    },
    onEnable: function() {
        this.delegateFn('enable');
    },
    reset: function() {
        this.delegateFn('reset');
    },
    delegateFn: function(fn) {
        this.items.each(function(item) {
            if (item[fn]) {
                item[fn]();
            }
        });
    },
    beforeDestroy: function() {
        Ext.destroy(this.fieldCt);
        Ext.calendar.CostField.superclass.beforeDestroy.call(this);
    },
    getRawValue: Ext.emptyFn,
    setRawValue: Ext.emptyFn
});
Ext.reg('costfield', Ext.calendar.CostField);

Ext.calendar.EmpNumField = Ext.extend(Ext.form.Field, {
	joinText: '已预约人数：',
    onRender: function(ct, position) {
        if (!this.el) {
            this.countField = new Ext.form.NumberField({width: 100}), this.joinField = new Ext.form.NumberField({width: 100});
            this.label = new Ext.form.Label({text: this.joinText,style:'margin-top:2px;'})
            this.fieldCt = new Ext.Container({
                autoEl: {id: this.id},
                cls: 'ext-dt-range',
                renderTo: ct,
                layout: 'hbox',
                items: [this.countField, this.label, this.joinField]
            });

            this.fieldCt.ownerCt = this;
            this.el = this.fieldCt.getEl();
            this.items = new Ext.util.MixedCollection();
            this.items.addAll([this.countField, this.label, this.joinField]);
        }
        Ext.calendar.EmpNumField.superclass.onRender.call(this, ct, position);
    },
    getValue: function() {
		return {count: this.countField.getValue(), joinNum: this.joinField.getValue()};
    },
    setValue: function(v) {
    	if (v && Ext.isObject(v)) {
	    	this.countField.setValue(v.count);
	    	this.joinField.setValue(v.joinNum);
    	}
    },
    setReadOnly: function(readOnly) {
    	this.countField.setReadOnly(readOnly);
    	this.joinField.setReadOnly(readOnly);
    },
    isDirty: function() {
        var dirty = false;
        if (this.rendered && !this.disabled) {
            this.items.each(function(item) {
                if (item.isDirty()) {
                    dirty = true;
                    return false;
                }
            });
        }
        return dirty;
    },
    onDisable: function() {
        this.delegateFn('disable');
    },
    onEnable: function() {
        this.delegateFn('enable');
    },
    reset: function() {
        this.delegateFn('reset');
    },
    delegateFn: function(fn) {
        this.items.each(function(item) {
            if (item[fn]) {
                item[fn]();
            }
        });
    },
    beforeDestroy: function() {
        Ext.destroy(this.fieldCt);
        Ext.calendar.EmpNumField.superclass.beforeDestroy.call(this);
    },
    getRawValue: Ext.emptyFn,
    setRawValue: Ext.emptyFn
});
Ext.reg('empfield', Ext.calendar.EmpNumField);

