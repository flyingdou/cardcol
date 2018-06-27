Ext.BLANK_IMAGE_URL = 'images/s.gif';
Ext.calendar.MainPanel = Ext.extend(Ext.Panel ,{
	renderTo : 'calendar-ct',
	layout : 'border',
	height : 500,
	activeItem: 1,
	role: {current: 'M'},				//当前角色及被访问角色
	funcs: {},							//页面可操作权限，主要指新增、编辑、拖动、查看等功能
	parameter: {},						//参数列表，主要指其场地
	workDays: '2,3,4,5,6',
	workTimes: '07:00,08:00,09:00,10:00,11:00,12:00,14:00,15:00,16:00,17:00,18:00,19:00,20:00',
	initComponent: function() {
		var me = this;
		if (!me.role.access) {
			me.listUrl = 'course!findCalendar.asp';
			me.delUrl = 'course!delete.asp';
			me.saveUrl = 'course!save.asp';
			me.courseStore = new Ext.data.Store({
				autoLoad: true,
				proxy: new Ext.data.HttpProxy({url: 'mycourse!findCourse.asp'}),
				reader: new Ext.data.JsonReader({root:null,idProperty:null,totalProperty:null} ,[{name:'id',type:'int'}, {name: 'name', type: 'string'}, {name:'charge',type:'string'}])
			});
			me.memberStore = new Ext.data.Store({
				autoLoad: true,
				proxy: new Ext.data.HttpProxy({url: 'member!findMember.asp'}),
				reader: new Ext.data.JsonReader({root:null,idProperty:null,totalProperty:null} ,[{name:'id',type:'int'}, {name: 'nick', type: 'string', mapping: 'name'}])
			});
		} else {
			me.listUrl = 'coursewindow!findCalendar.asp';
			me.delUrl = 'coursewindow!delete.asp';
			me.saveUrl = 'coursewindow!save.asp';
			me.courseStore = new Ext.data.Store({
				autoLoad: true,
				proxy: new Ext.data.HttpProxy({url: 'mycourse!findCourseBy.asp'}),
				reader: new Ext.data.JsonReader({root:null,idProperty:null,totalProperty:null} ,[{name:'id',type:'int'}, {name: 'name', type: 'string'}, {name:'charge',type:'string'}])
			});
			me.memberStore = new Ext.data.Store({
				autoLoad: true,
				proxy: new Ext.data.HttpProxy({url: 'member!findCoach.asp'}),
				reader: new Ext.data.JsonReader({root:null,idProperty:null,totalProperty:null} ,[{name:'id',type:'int'}, {name: 'nick', type: 'string', mapping: 'name'}])
			});
		}
		me.eventStore = new Ext.data.JsonStore({
			id : 'eventStore',
			root : 'evts',
			idProperty: 'id',
			url: this.listUrl,
			fields : Ext.calendar.EventRecord.prototype.fields.getRange()
		});
		this.calendarPanel = new Ext.calendar.CalendarPanel({
			eventStore : this.eventStore,
			border : false,
			id : 'app-calendar',
			region : 'center',
			canDrag : this.funcs.canDrag,
			activeItem : this.activeItem,
			showTime: true,
			parameter: this.parameter,
			workDays: this.workDays,
			workTimes: this.workTimes,
			monthViewCfg : {showHeader : true, showWeekLinks : true, showWeekNumbers : true}
		});
		this.calendarPanel.on('eventclick', this.onEventClick, this);
		this.calendarPanel.on('viewchange', this.onViewChange, this);
		this.calendarPanel.on('dayclick', this.onDayClick, this);
		this.calendarPanel.on('rangeselect', this.onRangeSelect, this);
		if (this.funcs.canEdit === true) {
			this.calendarPanel.on('eventmove', this.onEventMove, this);
			this.calendarPanel.on('eventresize', this.onEventResize, this);
			this.calendarPanel.on('initdrag', this.onInitDrag, this);
		}
		this.calendarPanel.on('eventdelete', this.onEventDelete, this);
		this.items = [this.calendarPanel];
		Ext.calendar.MainPanel.superclass.initComponent.call(this);
	},
	onEventClick: function(vw, rec, el){
		if (this.editWin) {
			this.editWin.destroy();
			this.editWin = null;
		}
		this.showEditWindow(rec, el);
	},
	onViewChange: function(p, vw, dateInfo){
		if (this.editWin) {
			this.editWin.close();
		}
	},
	initialize: function(){
		if (this.editWin) {
			this.editWin.destroy();
			this.editWin = null;
		}
		this.calendarPanel.setStartDate(new Date());
	},
	onDayClick: function(vw, dt, ad, el){
		if (vw.isHead && vw.isHead == true) {
			this.calendarPanel.setCurrentDate(dt);
		} else {
			if (this.workTimes.indexOf(dt.format('H:i')) >= 0 && this.workDays.indexOf(dt.format('w') + 1) >= 0) {
				if (this.editWin) {
					this.editWin.destroy();
					this.editWin = null;
				}
				this.showEditWindow({StartDate : dt,IsAllDay : ad}, el);
			}
		}
	},
	onRangeSelect: function(win, dates, onComplete){
		var dt = dates.StartDate;
		var sj = dt.format('Hi');
		var week = dt.format('w') + 1;
		if (this.hasPermission() && this.isValids(sj) && this.workDays.indexOf(week) >= 0) {
			if (this.editWin) {
				this.editWin.destroy();
				this.editWin = null;
			}
			this.showEditWindow(dates);
			this.editWin.on('close', onComplete, this, {single : true});
		} else {
			onComplete();
			return;
		}
	},
	isValids: function(sj) {
		var times = this.workTimes.split(","), t, t1;
		for (var i = 0 ; i < times.length ; i++) {
			t = times[i].replace(":", "");
			if (i + 1 < times.length) {
				t1 = times[i+1].replace(":", "");
				if ((t1 - t) <= 100 && sj >= t && sj <= t1) {
					return true;
				}
			}
		}
		return false;
	},
	hasPermission: function(){
		if (this.funcs.canEdit === true){
			return true;
		}
		return false;
	},
	onEventMove: function(vw, rec){
		if (this.funcs.canEdit === true) this.saveData(rec);
	},
	onEventResize: function(vw, rec){
		if (this.funcs.canEdit === true) this.saveData(rec);
	},
	onEventDelete: function(win, rec){
		this.eventStore.remove(rec);
		if (win.isVisible()) win.destroy();
	},
	onInitDrag: function(vw){
		if (this.editWin && this.editWin.isVisible()) {
			this.editWin.destroy();
		}
	},
	saveData: function(rec){
        var parms = this.getParams(rec);
		Ext.Ajax.request({method: 'post',url: this.saveUrl, params: parms,scope: this,
			success: function(response){
				rec.commit();
			}
		});
	},
	setParameter: function(param){
		for (var o in param) {
			this.parameter[o] = param[o];
		}
		this.calendarPanel.setParameter(this.parameter);
	},
	getParams: function(rec){
		var M = Ext.calendar.EventMappings, parms = '';
    	if (rec) {
    		for (var o in rec.data) {
    			if (M[o]) {
    				if (o == 'StartDate')
    					parms += 'course.startTime=' + rec.get(o).format('H:i') + '&';
    				else if (o == 'EndDate')
    					parms += 'course.endTime=' + rec.get(o).format('H:i') + '&';
    				else
    					parms += 'course.' + M[o].mapping + '=' + rec.get(o) + '&';
    			}
    		}
    		parms += 'course.planDate=' + rec.get('StartDate').format('Y-m-d');
    	}
    	return parms;
	},
	showEditWindow : function(rec, animateTarget) {
        var M = Ext.calendar.EventMappings, me = this;
		if (!rec || !rec.data) {
			if (me.funcs.canEdit !== true && me.funcs.canAdd != true) return;
        	var start = rec[M.StartDate.name], end = rec[M.EndDate.name] || start.add('h', 1);
        	rec = new Ext.calendar.EventRecord();
            rec.data[M.StartDate.name] = start;
            rec.data[M.EndDate.name] = end;
            rec.data[M.GroupBy.name] = 0;
            rec.data[M.Type.name] = 0;
            rec.data[M.CoachId.name] = me.parameter.toMember || me.parameter.coachId;
            rec.data[M.Place.name] = me.parameter.place;
            rec.data[M.MemberId.name] = me.parameter.memberId;
            rec.data[M.CanEdit.name] = 1;
            rec.data[M.CanJoin.name] = 0;
            rec.data[M.CanDel.name] = 1;
            if (me.role.access) {
            	if (me.role.current.role === 'M') {
            		if (me.role.access.role === 'S') {
            			rec.data[M.UIType.name] = 1;
            			rec.data[M.CoachName.name] = me.role.access.nick;
            		}
            	}
            } else {
            	if (me.role.current.role === 'S') {
            		rec.data[M.UIType.name] = 2;
            	} else if (me.role.current.role === 'E') {
            		rec.data[M.UIType.name] = 3;
            	}
            }
		}
        var _handlerType = 0;
        if (rec.get(M.GroupBy.name) > 0 && (me.funcs.canEdit === true)) {
        	if (confirm('该课程是批次课程，您现在是需要编辑本次课程还是整批次课程？\n如果您需要编辑整批次课程，请点击【确定】按钮！\n否则请点击【取消】按钮！')){
        		_handlerType = 1;
        	}
        }
        me.editWin = new Ext.calendar.EventEditWindow({
			role: me.role,
			funcs: me.funcs,
			courseStore: me.courseStore,
			coachStore: me.coachStore,
			memberStore: me.memberStore,
			url: {saveUrl: me.saveUrl, delUrl: me.delUrl}, 
			handlerType: _handlerType, 
			activeRecord: rec,
			parent: me
		});
        me.editWin.on('eventadd', me.onEventAdd, me);
        me.editWin.on('eventupdate', me.onEventUpdate, me);
        me.editWin.on('eventdelete', me.onEventDelete, me);
        me.editWin.show(rec, animateTarget);
	},
	onEventAdd: function(win, rec){
		win.destroy();
		this.eventStore.add(rec);
	},
	onEventUpdate: function(win, rec){
		win.destroy();
		rec.commit();
	},
	destroy: function() {
		Ext.calendar.MainPanel.superclass.destroy.call(this);
		if (this.editWin) this.editWin.destroy();
	}
});
