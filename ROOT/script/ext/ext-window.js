/**
 * 业务复杂程度
 * uiType: 1展现会员的课程，2展现俱乐部的课程，3展现俱乐部课程，4俱乐部维护课程，5教练维护课程，6会员自己的课程，7教练访问会员的课程
 * 1.能否录入，2能否查看，3能否删除，4能否评分
 * 由后台处理并加载所有数据，前台控制，能否录入，能否查看，能否评分
 */
Ext.calendar.EventEditWindow = Ext.extend(Ext.Window ,{
    titleTextAdd: '新增课程安排',
    titleTextEdit: '课程预约',
    titleTextView: '课程安排查看',
    url: {},		//当前保存及删除URL
    funcs: {},		//当前功能权限
    role: {},		//当前角色与被访问者角色{current: 'S', access: 'aaaa'}
    width: 460,
    autoHeight: true,
    autocreate: true,
    border: true,
    closeAction: 'close',
    modal: false,
    resizable: false,
    buttonAlign: 'left',
    savingMessage: 'Saving changes...',
    deletingMessage: 'Deleting event...',
    modified: false,
    courseStore: null,
    coachStore: null,
    memberStore: null,
    handlerType: 0,
    parent: null,
    activeRecord: null,
    initComponent: function() {
        var me = this;
        me.buttonAlign = 'center';
        var fields = me.getFields();
        me.fbar = me.getButton();
        me.items = [{
            xtype: 'form',
            labelWidth: 70,
            labelAlign: 'right',
            frame: false,
            bodyStyle: 'background:transparent;padding:5px 10px 10px;',
            bodyBorder: false,
            border: false,
            defaultType: 'textfield',
            items: fields
        }]; 
        Ext.calendar.EventEditWindow.superclass.initComponent.call(me);
        me.formPanel = this.items.items[0];
        me.addEvents({eventadd: true,eventupdate: true,eventdelete: true,eventcancel: true,editdetails: true});
    },
    getButton: function() {
        var me = this, M = Ext.calendar.EventMappings, uiType = me.activeRecord.get(M.UIType.name);
        if (uiType === 1 || uiType === 2) {
        	if (me.funcs.canAdd === true) {
	        	return [
					{id: 'see-btn', text: '查看计划', disabled: false, handler: me.onSee, scope: me},
					{id: 'save-btn', text: '保存', disabled: false, handler: me.onSave, scope: me},
					{id: 'delete-btn', text: '删除', disabled: false, handler: me.onDelete, scope: me, hideMode: 'offsets'},
					{text: '关闭', disabled: false, handler: me.onClose, scope: me,hideMode: 'offsets'}
				];
        	} else {
        		return [{text: '关闭', disabled: false, handler: me.onClose, scope: me,hideMode: 'offsets'}];
        	}
        } else if (uiType === 4) {
    		return [{id: 'join-btn', text: '预约', disabled: false, handler: me.onJoin, scope: me},
    		        {text: '确定', disabled: false, handler: me.onClose, scope: me,hideMode: 'offsets'}];
        } else if (uiType === 3) {
        	return [
				{id: 'save-btn', text: '保存', disabled: false, handler: me.onSave, scope: me},
				{id: 'delete-btn', text: '删除', disabled: false, handler: me.onDelete, scope: me, hideMode: 'offsets'},
        	    {text: '关闭', disabled: false, handler: me.onClose, scope: me,hideMode: 'offsets'}
        	];
        } else if (uiType === 5) {
        	return [
				{id: 'see-btn', text: '查看计划', disabled: false, handler: me.onSee, scope: me},
				{id: 'save-btn', text: '保存', disabled: false, handler: me.onSave, scope: me},
				{id: 'delete-btn', text: '删除', disabled: false, handler: me.onDelete, scope: me, hideMode: 'offsets'},
				{text: '关闭', disabled: false, handler: me.onClose, scope: me,hideMode: 'offsets'}
			];
        } else if (uiType === 6) {
        	return [
        	    {text: '取消预约', disabled: false, handler: me.onUndo1, scope: me,hideMode: 'offsets'},
        	    {text: '评分', disabled: false, handler: me.onGrade, scope: me,hideMode: 'offsets'},
        	    {text: '关闭', disabled: false, handler: me.onClose, scope: me,hideMode: 'offsets'}
            ];
        } else if (uiType === 7) {
        	return [
            	{text: '取消预约', disabled: false, handler: me.onUndo2, scope: me,hideMode: 'offsets'},
        	    {text: '评分', disabled: false, handler: me.onGrade, scope: me,hideMode: 'offsets'},
        	    {text: '关闭', disabled: false, handler: me.onClose, scope: me,hideMode: 'offsets'}
            ];
        } else if (uiType === 8) {
        	return [
        		{id: 'join-btn', text: '预约', disabled: false, handler: me.onJoin, scope: me},
        		{text: '关闭', disabled: false, handler: me.onClose, scope: me,hideMode: 'offsets'}
        	];
        } else {
        	return [];
        }
    },
    getFields: function() {
        var M = Ext.calendar.EventMappings, uiType = this.activeRecord.get(M.UIType.name), me = this,
        	_key = me.activeRecord.get(M.EventId.name);
        if (uiType === 1) {			//会员录入，指在教练首页进行录入
        	return [
		        {id: 'EventId' ,name: 'eventID' ,inputType:'hidden'},
		        {id: 'CourseId', fieldLabel: '课程名称',xtype: 'course', store: me.courseStore, anchor: '60%',allowBlank:false},
		        {id: 'CoachName', fieldLabel: '授课教练',xtype: 'textfield',anchor:'50%', readOnly: true},
		        {id: 'Intensity', fieldLabel: '运动强度',xtype: 'textfield',anchor:'40%'},
		        {id: 'Costs', fieldLabel: '收费方式',xtype: 'textfield',anchor:'50%'},
		        {id: 'Memo', fieldLabel:'备注', xtype: 'textfield', anchor: '100%'},
		        {id: 'datetime', fieldLabel:'课程日期',xtype:'datetimefield',anchor:'100%',readOnly:true},
		        {id: 'Reminder', fieldLabel:'是否提醒',xtype:'remindfield',anchor:'100%'},
		        {id: 'Type',fieldLabel: '周期课程', xtype:'checkbox', anchor: '50%', listeners: {'check': me.changeState, scope: me}},
		        {id: 'other',xtype:'fieldset',title:'课程周期', items:[
		            {id: 'repeatWhere', name:'repeat', fieldLabel:'周期条件',xtype:'repeatfield',anchor:'100%'},
		            {id: 'repeatScope', name:'repeatScope', fieldLabel:'周期范围', xtype:'scopefield',anchor:'100%'}
		        ]}
		    ];
        } else if (uiType === 2) {	//教练录入会员课程，一般指教练在本人页面进行录入
        	return [
		        {id: 'EventId', inputType:'hidden'},
		        {id: 'GroupBy', inputType: 'hidden'},
		        {id: 'CoachId', inputType: 'hidden'},
		        {id: 'CourseId', fieldLabel: '选择课程',xtype: 'course', store: me.courseStore, width: 200,allowBlank:false},
		        {id: 'MemberId', fieldLabel: '选择会员',xtype: 'combox', width: 140,store: me.memberStore,keyName:'nick' , allowBlank:false},
		        {id: 'Costs',name:'charge',fieldLabel: '收费方式',xtype: 'textfield',anchor:'100%'},
		        {id: 'Place',name:'house',fieldLabel:'授课地点',xtype:'textfield',anchor:'100%'},
		        {id: 'Memo', name: 'memo',fieldLabel:'备注', xtype: 'textfield', anchor:'100%',allowBlank:true},
		        {id: 'datetime',name:'datetime',fieldLabel:'排课日期',xtype:'datetimefield',anchor:'100%',allowBlank:false,readOnly:true},
		        {id: 'Color',fieldLabel:'课程颜色',xtype:'colorfield',value: '#ffcc00', msgTarget: 'qtip', fallback: true,width: 140},
		        {id: 'Reminder',name: 'reminder',fieldLabel:'是否提醒',xtype:'remindfield',anchor:'100%'},
		        {id: 'Type',fieldLabel: '周期课程', xtype:'checkbox', anchor: '50%', listeners: {'check': me.changeState, scope: me}},
		        {id: 'other',xtype:'fieldset',title:'课程周期', items:[
		            {id: 'repeatWhere', name:'repeat', fieldLabel:'周期条件',xtype:'repeatfield',anchor:'100%'},
		            {id: 'repeatScope', name:'repeatScope', fieldLabel:'周期范围', xtype:'scopefield',anchor:'100%'}
		        ]}
		    ];
        } else if (uiType === 3) {	//俱乐部录入课程，一般指俱乐部自己页面进行录入
        	return [
	            {id: 'EventId' ,inputType: 'hidden'},
	            {id: 'MemberId' ,inputType: 'hidden'},
	            {id: 'Place', inputType: 'hidden'},
	            {id: 'GroupBy', inputType: 'hidden'},
	            {id: 'JoinNum', inputType: 'hidden'},
	            {id: 'CourseId', fieldLabel: '选择课程',xtype: 'course', store: me.courseStore, anchor: '60%',allowBlank:false},
	            {id: 'CoachId', fieldLabel: '授课教练',xtype: 'combox',anchor:'50%',store: me.memberStore,keyName:'nick',allowBlank:false},
	            {id: 'Intensity', fieldLabel: '运动强度',xtype: 'textfield',anchor:'100%',readOnly:true},
	            {id: 'prices', fieldLabel: '会员价(元)', xtype: 'costfield', anchor: '100%'},
	            {id: 'Count', fieldLabel: '人数上限',xtype: 'numberfield',anchor:'40%',allowBlank:false},
	            {id: 'Memo', fieldLabel: '备注', xtype: 'textfield', anchor: '100%'},
	            {id: 'datetime', name:'datetime',fieldLabel:'排课日期',xtype:'datetimefield',anchor:'100%',allowBlank:false,readOnly:true},
	            {id: 'Color', fieldLabel:'课程颜色',xtype:'colorfield',value: '#ffcc00', msgTarget: 'qtip', fallback: true,width: 140},
	            {id: 'Reminder', fieldLabel:'是否提醒',xtype:'remindfield',anchor:'100%'},
	            {id: 'Type', fieldLabel: '周期课程', xtype:'checkbox', anchor: '50%',listeners: {'check': me.changeState, scope: me}},
	            {id: 'other', xtype:'fieldset',title:'课程周期', items:[
  	                {id: 'repeatWhere', name:'repeat', fieldLabel:'周期条件',xtype:'repeatfield',anchor:'100%', disabled: true},
  	                {id: 'repeatScope', name:'repeatScope', fieldLabel:'周期范围', xtype:'scopefield',anchor:'100%', disabled: true}
  	            ]}
	        ];
        } else if (uiType === 4) {	//教练展现俱乐部课程
        	return [
	            {id: 'EventId' ,inputType: 'hidden'},
	            {id: 'CourseName', fieldLabel: '课程名称', anchor: '60%',allowBlank:false},
	            {id: 'CoachName', fieldLabel: '教练昵称',anchor:'50%',allowBlank:false},
	            {id: 'Intensity', fieldLabel: '运动强度',xtype: 'textfield',anchor:'100%',readOnly:true},
	            {id: 'prices', fieldLabel: '会员价（元）',xtype: 'costfield',anchor:'100%'},
	            {id: 'Count', fieldLabel: '人数上限',xtype: 'empfield',anchor:'100%',allowBlank:false},
	            {id: 'Memo', fieldLabel: '备注', xtype: 'textfield', anchor: '100%'},
	            {id: 'datetime', name:'datetime',fieldLabel:'排课日期',xtype:'datetimefield',anchor:'100%',allowBlank:false,readOnly:true},
	            {id: 'Reminder', fieldLabel:'是否提醒',xtype:'remindfield',anchor:'100%'},
		        {id: 'Score', fieldLabel: '分数', xtype: 'panel', html: '<div id="divscore" data-number="0"></div>'}
	        ];
        } else if (uiType === 5) {	//教练展现会员课程
        	return [
	            {id: 'EventId' ,inputType: 'hidden'},
	            {id: 'CourseName', fieldLabel: '课程名称', anchor: '60%',allowBlank:false},
	            {id: 'CoachName', fieldLabel: '授课教练',anchor:'50%',allowBlank:false},
	            {id: 'Intensity', fieldLabel: '运动强度',xtype: 'textfield',anchor:'100%',readOnly:true},
	            {id: 'prices', fieldLabel: '会员价（元）',xtype: 'costfield',anchor:'100%'},
	            {id: 'Count', fieldLabel: '人数上限',xtype: 'numberfield',anchor:'40%',allowBlank:false},
	            {id: 'Memo', fieldLabel: '备注', xtype: 'textfield', anchor: '100%'},
	            {id: 'datetime', name:'datetime',fieldLabel:'排课日期',xtype:'datetimefield',anchor:'100%',allowBlank:false,readOnly:true},
	            {id: 'Reminder', fieldLabel:'是否提醒',xtype:'remindfield',anchor:'100%'},
	            {id: 'Type', fieldLabel: '周期课程', xtype:'checkbox', anchor: '50%',listeners: {'check': me.changeState, scope: me}},
	            {id: 'other', xtype:'fieldset',title:'课程周期', items:[
  	                {id: 'repeatWhere', name:'repeat', fieldLabel:'周期条件',xtype:'repeatfield',anchor:'100%', disabled: true},
  	                {id: 'repeatScope', name:'repeatScope', fieldLabel:'周期范围', xtype:'scopefield',anchor:'100%', disabled: true}
  	            ]}
	        ];
        } else if (uiType === 6) {	//会员展现俱乐部课程
        	return [
	            {id: 'EventId' ,inputType: 'hidden'},{id: 'MemberId', inputType: 'hidden'},
	            {id: 'CourseName', fieldLabel: '课程名称', anchor: '60%',allowBlank:false},
	            {id: 'CoachName', fieldLabel: '教练昵称',anchor: '100%', allowBlank: false},
	            {id: 'Intensity', fieldLabel: '运动强度',xtype: 'textfield',anchor:'100%',readOnly:true},
	            {id: 'prices', fieldLabel: '会员价（元）',xtype: 'costfield',anchor:'100%'},
	            {id: 'Count', fieldLabel: '人数上限',xtype: 'empfield',anchor:'100%',allowBlank:false},
	            {id: 'Memo', fieldLabel: '备注', xtype: 'textfield', anchor: '100%'},
	            {id: 'datetime', name:'datetime',fieldLabel:'排课日期',xtype:'datetimefield',anchor:'100%',allowBlank:false,readOnly:true},
	            {id: 'Reminder', fieldLabel:'是否提醒',xtype:'remindfield',anchor:'100%'},
		        {id: 'Score', fieldLabel: '分数', xtype: 'panel', html: '<div id="divscore" data-number="5"></div>'},
		        {id: 'Appriase', fieldLabel: '评价内容', xtype: 'textarea', anchor: '100%'}
	        ];
        } else if (uiType === 7) {	//会员展现教练课程
        	return [
		        {id: 'EventId', inputType:'hidden'},
		        {id: 'GroupBy', inputType: 'hidden'},
		        {id: 'CoachId', inputType: 'hidden'},
		        {id: 'MemberId', inputType: 'hidden'},
		        {id: 'CourseName', fieldLabel: '选择课程',xtype: 'textfield', width: 200,allowBlank:false},
		        {id: 'CoachName', fieldLabel: '教练', xtype: 'textfield', anchor: '100%'},
		        {id: 'Costs',name:'costs',fieldLabel: '收费方式',xtype: 'textfield',anchor:'100%', readOnly: _key && _key > 0 ? true : false},
		        {id: 'Place',name:'place',fieldLabel:'授课地点',xtype:'textfield',anchor:'100%',allowBlank:false, readOnly: _key && _key > 0 ? true : false},
	            {id: 'Memo', fieldLabel: '备注', xtype: 'textfield', anchor: '100%'},
		        {id: 'datetime',name:'datetime',fieldLabel:'排课日期',xtype:'datetimefield',anchor:'100%',allowBlank:false,readOnly:true},
		        {id: 'Color',fieldLabel:'课程颜色',xtype:'colorfield',value: '#ffcc00', msgTarget: 'qtip', fallback: true,width: 140},
		        {id: 'Reminder',name: 'reminder',fieldLabel:'是否提醒',xtype:'remindfield',anchor:'100%'},
		        {id: 'Score', fieldLabel: '分数', xtype: 'panel', html: '<div id="divscore" data-number="5"></div>'},
		        {id: 'Appriase', fieldLabel: '评价内容', xtype: 'textarea', anchor: '100%'}
		    ];
        } else if (uiType === 8) { //访问俱乐部课程
        	return [
	            {id: 'EventId' ,inputType: 'hidden'},{id: 'MemberId', inputType: 'hidden'},
	            {id: 'CourseName', fieldLabel: '课程名称', anchor: '60%',allowBlank:false},
	            {id: 'CoachName', fieldLabel: '教练昵称',anchor: '100%', allowBlank: false},
	            {id: 'Intensity', fieldLabel: '运动强度',xtype: 'textfield',anchor:'100%',readOnly:true},
	            {id: 'prices', fieldLabel: '会员价(元)',xtype: 'costfield',anchor:'100%'},
	            {id: 'Count', fieldLabel: '人数上限',xtype: 'empfield',anchor:'100%',allowBlank:false},
	            {id: 'Memo', fieldLabel: '备注', xtype: 'textfield', anchor: '100%'},
	            {id: 'datetime', name:'datetime',fieldLabel:'排课日期',xtype:'datetimefield',anchor:'100%',allowBlank:false,readOnly:true},
	            {id: 'Reminder', fieldLabel:'是否提醒',xtype:'remindfield',anchor:'100%'}
	        ];
        }
    },
    afterRender: function() {
        Ext.calendar.EventEditWindow.superclass.afterRender.call(this);
        this.el.addClass('ext-cal-event-win');
    },
    onRepeat: function(){
    	Ext.getCmp('other').show();
    },
    changeState: function(the ,checked){
		Ext.getCmp('repeatWhere').setDisabled(!checked);
		Ext.getCmp('repeatScope').setDisabled(!checked);
		var where = Ext.getCmp('repeatWhere').getValue();
		if (where == null ||!where || !where.mode) {
			where = {mode: 1, value: 1};
			Ext.getCmp('repeatWhere').setValue(where);
		}
		var scope = Ext.getCmp('repeatScope').getValue();
		if (scope == null || !scope || !scope.start) {
			scope = {start: Ext.getCmp('datetime').getValue().start.format('Y-m-d'), num: 7};
			Ext.getCmp('repeatScope').setValue(scope);
		}
    },
    show: function(rec, animateTarget) {
        var anim = (Ext.isIE8 && Ext.isStrict) ? null: animateTarget;
        Ext.calendar.EventEditWindow.superclass.show.call(this, anim, function() {});
        var rec, M = Ext.calendar.EventMappings;
        f = this.formPanel.form;
        f.reset();
        if (rec.data[M.EventId.name]) {
        	this.setTitle(this.titleTextEdit);
        } else {
            this.setTitle(this.titleTextAdd);
        }
        Ext.getCmp('datetime').setValue({start: rec.data[M.StartDate.name] ,end: rec.data[M.EndDate.name]});
        if (Ext.getCmp('prices')) Ext.getCmp('prices').setValue({memberPrice: rec.data[M.MemberPrice.name], hourPrice: rec.data[M.HourPrice.name]});
        if (Ext.getCmp('Reminder')) Ext.getCmp('Reminder').setValue({hasReminder: rec.get(M.HasReminder.name), reminder: rec.get(M.Reminder.name)});
        f.loadRecord(rec);
        if (rec.get(M.Type.name) == 1){
//    		Ext.getCmp('isRepeat').setValue(true);
    		var where = {mode: rec.get(M.RepeatMode.name),value: rec.get(M.RepeatValue.name),weekOf: rec.get(M.RepeatWeekOf.name)};
    		if (Ext.getCmp('repeatWhere')) Ext.getCmp('repeatWhere').setValue(where);
    		var scope = {start: rec.get(M.RepeatStart.name), num: rec.get(M.RepeatNum.name), end: rec.get(M.RepeatEnd.name)};
    		if (Ext.getCmp('repeatScope')) Ext.getCmp('repeatScope').setValue(scope);
        }
        if (Ext.getCmp('Count') && Ext.getCmp('Count').getXType() === 'empfield') {
        	Ext.getCmp('Count').setValue({count: rec.data[M.Count.name], joinNum: rec.data[M.JoinNum.name]});
        }
        if (Ext.getCmp('Score')) {
        	var _number = rec.get(M.Score.name);
        	$("#divscore").jRating({type:'big', length : 10, decimalLength : 1, rateMax:100});
        }
        return this;
    },
    roundTime: function(dt, incr) {
        incr = incr || 15;
        var m = parseInt(dt.getMinutes(), 10);
        return dt.add('mi', incr - (m % incr));
    },
    onClose: function() {
        this.cleanup(true);
        this.fireEvent('eventcancel', this);
    },
    cleanup: function(hide) {
        if (this.activeRecord && this.activeRecord.dirty) this.activeRecord.reject();
        delete this.activeRecord;
        if (hide === true) this.destroy();
    },
    onUndo: function(type) {
    	var me = this, _keyId = Ext.getCmp('EventId').getValue();
		var dates = Ext.getCmp('datetime').getValue();
		Ext.Msg.confirm('提示', '是否确认撤消当前课程的预约？', function(btn){
			if (btn == 'yes') {
				Ext.Ajax.request({method: 'post', url: 'course!undo.asp', params: {id: _keyId, saveType: type}, scope: me,
					success: function(response) {
						var $resp = Ext.util.JSON.decode(response.responseText);
						if ($resp.success === true) {
							Ext.Msg.alert('提示', '当前课程已经成功撤消！');
							me.close();
							me.fireEvent('eventdelete', me, me.activeRecord);
					        me.parent.calendarPanel.setStartDate(dates.start);
						} else {
							Ext.Msg.alert('提示', '当前预约课程撤消失败！可能的原因为：' + $resp.message);
						}
					}
				});
			}
		});
    },
    //俱乐部
    onUndo1: function() {
    	this.onUndo(1);
    },
    //私教
    onUndo2: function() {
    	this.onUndo(2);
    },
    onGrade: function(){
    	var me = this, _number = $('#divscore').attr('rate'),
    		_memo = Ext.getCmp('Appriase').getValue(),
    		_keyId = Ext.getCmp('EventId').getValue();
		Ext.Ajax.request({method: 'post', url: 'course!grade.asp', params: {id: _keyId, grade: _number, memo: _memo}, scope: this,
			success: function(response) {
				var $resp = Ext.util.JSON.decode(response.responseText);
				if ($resp.success === true) {
					Ext.Msg.alert('提示', '当前分数已经提交！');
					me.close();
				} else {
					Ext.Msg.alert('提示', '当前分数提交失败，可能的原因为：' + $resp.message);
				}
			}
		});
    },
    onJoin: function() {
    	var me = this, _id = Ext.getCmp('EventId').getValue();
    	if(!me.role['current']) {
    		me.onClose();
			openLogin();
			return;
		}
    	Ext.getCmp('join-btn').setDisabled(true);
		Ext.Ajax.request({method: 'post', url: 'coursewindow!onJoin.asp', params: 'course.id=' + _id, scope: this,
			success: function(response) {
		    	Ext.getCmp('join-btn').setDisabled(false);
				var $resp = Ext.util.JSON.decode(response.responseText);
				if ($resp.success === true) {
					if($resp.message=== 'pay'){
				        window.location.href = 'order!submitProd.asp?prodType=5&id='+_id;
					}else{
						alert('您的申请已经成功提交，请等待俱乐部的审核！');
					}
					me.close();
				} else {
					if ($resp.message=== 'full') {
						alert('当前课程的人数已经达到上限，不能进行申请！');
					} else if ($resp.message === 'joining') {
						alert('您已经申请过该课程，请不要重复进行申请！');
					} else if ($resp.message === 'exist') {
						alert('您当前时间已经有安排的课程，请换个时间段再申请！');
					} else if($resp.message=== 'nomemeber'){
						alert('您还不是当前俱乐部会员，请先加入该俱乐部！');
					}else {
						alert('未知错误，请联系系统设计人员。可能的原因为：' + $resp.message);
					}
				}
			}
		});
    },
    onSee: function() {
        if (!this.formPanel.form.isValid()) {
        	alert('请先输入需要必填的数据后再查看计划！');
        	return;
        }
    	this.updateRecord();
    	var parms = '', M = Ext.calendar.EventMappings;
    	if (this.activeRecord) {
    		for (var o in this.activeRecord.data) {
    			if (M[o]) {
    				if (o == 'StartDate')
    					parms += 'course.startTime=' + this.activeRecord.get(o).format('H:i') + '&';
    				else if (o == 'EndDate')
    					parms += 'course.endTime=' + this.activeRecord.get(o).format('H:i') + '&';
    				else
    					parms += 'course.' + M[o].mapping + '=' + escape(this.activeRecord.get(o)) + '&';
    			}
    		}
    		parms += 'course.planDate=' + this.activeRecord.get('StartDate').format('Y-m-d');
    	}
    	window.location.href = this.type <= 3 ? 'workout.asp?' + parms : 'workoutwindow.asp?' + parms;
    },
    updateRecord: function() {
        var f = this.formPanel.form, 
        	dates = Ext.getCmp('datetime').getValue(), 
        	M = Ext.calendar.EventMappings,
        	type = Ext.getCmp('Type').getValue(),
        	reminder = Ext.getCmp('Reminder').getValue(),
        	repeatWhere = type === true ? Ext.getCmp('repeatWhere').getValue() : null,
        	repeatScope = type === true ? Ext.getCmp('repeatScope').getValue() : null;
        f.updateRecord(this.activeRecord);
        for (var o in repeatWhere) this.activeRecord.set(o, repeatWhere[o]);
        for (var o in repeatScope) this.activeRecord.set(o, repeatScope[o]);
        this.activeRecord.set(M.Reminder.name, reminder.reminder ? reminder.reminder : null);
        this.activeRecord.set(M.HasReminder.name, reminder.hasReminder);
        this.activeRecord.set(M.Type.name, type === true ? 1 : 0);
        this.activeRecord.set(M.CourseName.name ,Ext.get('CourseId').dom.value);
        if (Ext.getCmp('CoachId') && Ext.getCmp('CoachId').getXType() === 'combox') {
        	this.activeRecord.set(M.CoachName.name ,Ext.get('CoachId').dom.value);
        }
        if (Ext.getCmp('MemberId') && Ext.getCmp('MemberId').getXType() === 'combox') {
        	this.activeRecord.set(M.MemberName.name ,Ext.get('MemberId').dom.value);
        }
        if (Ext.getCmp('prices')) {
        	var v = Ext.getCmp('prices').getValue();
        	this.activeRecord.set(M.MemberPrice.name, v.memberPrice);
        	this.activeRecord.set(M.HourPrice.name, v.hourPrice);
        }
        this.activeRecord.set(M.StartDate.name, dates.start);
        this.activeRecord.set(M.EndDate.name, dates.end);
    },
    onSave: function() {
    	var me = this;
        if (!me.formPanel.form.isValid()) {
        	alert('请输入必须填写的栏位的数据！');
        	return;
        }
        me.updateRecord();
        if (!me.activeRecord.dirty) {
        	me.onClose();
            return;
        }
        if (Ext.getCmp('Type').getValue() == true) {
        	if (!Ext.getCmp('repeatWhere').isValidate()) return;
        	if (!Ext.getCmp('repeatScope').isValidate()) return;
        }
    	Ext.getCmp('save-btn').setDisabled(true);
        var params = me.getParameter();
		var dates = Ext.getCmp('datetime').getValue();
       	params += "&saveType=" + me.handlerType;
		Ext.Ajax.request({method: 'post', url: me.url.saveUrl, params: params,scope: me,
			success: function(response) {
		    	Ext.getCmp('save-btn').setDisabled(false);
				var resp = Ext.util.JSON.decode(response.responseText);
				if (resp.success === true) {
					var key = me.activeRecord.get[Ext.calendar.EventMappings.EventId.name];
					me.fireEvent(key ? 'eventupdate' : 'eventadd', me, me.activeRecord);
					me.parent.calendarPanel.setStartDate(dates.start);
				} else {
					alert(resp.desc);
				}
			}
		});
    },
    getParameter: function() {
    	var parms = '', M = Ext.calendar.EventMappings;
    	if (this.activeRecord) {
    		for (var o in this.activeRecord.data) {
    			if (M[o]) {
    				if (o == 'StartDate')
    					parms += 'course.startTime=' + this.activeRecord.get(o).format('H:i') + '&';
    				else if (o == 'EndDate')
    					parms += 'course.endTime=' + this.activeRecord.get(o).format('H:i') + '&';
    				else
    					parms += 'course.' + M[o].mapping + '=' + this.activeRecord.get(o) + '&';
    			}
    		}
    		parms += 'course.planDate=' + this.activeRecord.get('StartDate').format('Y-m-d');
    	}
    	return parms;
    },
    onDelete: function() {
    	var me = this, 
    		id = Ext.getCmp('EventId').getValue();
    	if (id == null || id == ""){
    		me.fireEvent('eventdelete', me, me.activeRecord);
            return;
    	}
    	if (confirm('是否确认删除当前课程安排？')) {
        	Ext.getCmp('delete-btn').setDisabled(true);
    		var groupBy = me.handlerType == 1 ? me.activeRecord.get(Ext.calendar.EventMappings.GroupBy.name) : 0;
	    	var params = "id=" + id + "&saveType=" + groupBy;
			var dates = Ext.getCmp('datetime').getValue();
			Ext.Ajax.request({method: 'post', url: me.url.delUrl, params: params, scope: me,
				success: function(response){
		        	Ext.getCmp('delete-btn').setDisabled(false);
					var msg = response.responseText;
					if (msg == "OK"){
						alert((groupBy > 0 ? '当前所有循环的' : '当前') + '课程安排已经成功删除！');
						me.fireEvent('eventdelete', me, me.activeRecord);
				        me.parent.calendarPanel.setStartDate(dates.start);
					} else {
						alert('删除错误，错误原因可能为：' + msg);
					}
				}
			});
    	}
    },
    setStatus: function(status) {
    	this.formPanel.items.each(function(item, index, lenght) {
			if (!item.inputType || item.inputType != 'hidden') {
				try {
					item.setDisabled(status);
				} catch (e) {
				}
			}
    	});
    }
});
