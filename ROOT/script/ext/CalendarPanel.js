Ext.calendar.CalendarPanel = Ext.extend(Ext.Panel, {
    showDayView: true,
    showWeekView: true,
    showMonthView: true,
    showNavBar: true,
    todayText: '今天',
    showTodayText: true,
    showTime: true,
    dayText: '日视图',
    weekText: '周视图',
    monthText: '月视图',
    canDrag: false,
    parameter: {},
    workDays: '',
    workTimes: '',
    layoutConfig: {layoutOnCardChange: true, deferredRender: true},
    startDate: new Date(),
    initComponent: function() {
        this.tbar = {
            cls: 'ext-cal-toolbar',
            border: true,
            buttonAlign: 'center',
            items: [{
                id: this.id + '-tb-prev',
                handler: this.onPrevClick,
                scope: this,
                iconCls: 'x-tbar-page-prev'
            }]
        };
        this.viewCount = 0;

        if (this.showDayView) {
            this.tbar.items.push({
                id: this.id + '-tb-day',
                text: this.dayText,
                handler: this.onDayClick,
                scope: this,
                toggleGroup: 'tb-views'
            });
            this.viewCount++;
        }
        if (this.showWeekView) {
            this.tbar.items.push({
                id: this.id + '-tb-week',
                text: this.weekText,
                handler: this.onWeekClick,
                scope: this,
                toggleGroup: 'tb-views'
            });
            this.viewCount++;
        }
        if (this.showMonthView || this.viewCount == 0) {
            this.tbar.items.push({
                id: this.id + '-tb-month',
                text: this.monthText,
                handler: this.onMonthClick,
                scope: this,
                toggleGroup: 'tb-views'
            });
            this.viewCount++;
            this.showMonthView = true;
        }
        this.tbar.items.push({
            id: this.id + '-tb-next',
            handler: this.onNextClick,
            scope: this,
            iconCls: 'x-tbar-page-next'
        });
        this.tbar.items.push('->');
        var dateBtn = new Ext.ButtonDatePicker({id: 'myCalendarBtn'});
        dateBtn.on('select' ,this.selectDate, this);
        this.tbar.items.push(dateBtn);
        
        var idx = this.viewCount - 1;
        this.activeItem = this.activeItem === undefined ? idx: (this.activeItem > idx ? idx: this.activeItem);

        if (this.showNavBar === false) {
            delete this.tbar;
            this.addClass('x-calendar-nonav');
        }

        Ext.calendar.CalendarPanel.superclass.initComponent.call(this);

        this.addEvents({eventadd: true, eventupdate: true, eventdelete: true, eventcancel: true, viewchange: true});
        this.layout = 'card';
        if (this.showDayView) {
            var day = Ext.apply({
                xtype: 'dayview',
                title: this.dayText,
                showToday: this.showToday,
                parameter: this.parameter,
                enableDD: this.canDrag === true,
                showTodayText: this.showTodayText,
                showTime: this.showTime,
                workDays: this.workDays,
                workTimes: this.workTimes
            },
            this.dayViewCfg);
            day.id = this.id + '-day';
            day.store = day.store || this.eventStore;
            this.initEventRelay(day);
            this.add(day);
        }
        if (this.showWeekView) {
            var wk = Ext.applyIf({
                xtype: 'weekview',
                title: this.weekText,
                type: this.type,
                enableDD: this.canDrag === true,
                showToday: this.showToday,
                showTodayText: this.showTodayText,
                parameter: this.parameter,
                showTime: this.showTime,
                workDays: this.workDays,
                workTimes: this.workTimes
            },
            this.weekViewCfg);

            wk.id = this.id + '-week';
            wk.store = wk.store || this.eventStore;
            this.initEventRelay(wk);
            this.add(wk);
        }
        if (this.showMonthView) {
            var month = Ext.applyIf({
                xtype: 'monthview',
                title: this.monthText,
                showToday: this.showToday,
                showTodayText: this.showTodayText,
                enableDD: false,
                parameter: this.parameter,
                showTime: this.showTime,
                listeners: {
                    'weekclick': {
                        fn: function(vw, dt) {
                            this.showWeek(dt);
                        },
                        scope: this
                    }
                }
            },
            this.monthViewCfg);

            month.id = this.id + '-month';
            month.store = month.store || this.eventStore;
            this.initEventRelay(month);
            this.add(month);
        }
    },
    initEventRelay: function(cfg) {
        cfg.listeners = cfg.listeners || {};
        cfg.listeners.afterrender = {
            fn: function(c) {
                this.relayEvents(c, ['eventsrendered', 'eventclick', 'eventover', 'eventout', 'dayclick',
                'eventmove', 'datechange', 'rangeselect', 'eventdelete', 'eventresize', 'initdrag']);
            },
            scope: this,
            single: true
        };
    },
    afterRender: function() {
        Ext.calendar.CalendarPanel.superclass.afterRender.call(this);
        this.fireViewChange();
    },
    onLayout: function() {
        Ext.calendar.CalendarPanel.superclass.onLayout.call(this);
        if (!this.navInitComplete) {
            this.updateNavState();
            this.navInitComplete = true;
        }
    },
    setCurrentDate: function(dt) {
    	Ext.getCmp('myCalendarBtn').setText(dt.format('Y-m-d'));
    },
    setActiveView: function(id) {
        var l = this.layout;
        l.setActiveItem(id);
        if (id == this.id + '-edit') {
            this.getTopToolbar().hide();
            this.doLayout();
        } else {
            l.activeItem.setParameter(this.parameter);
            this.setStartDate(l.activeItem.getStartDate());
            l.activeItem.refresh();
            this.getTopToolbar().show();
            this.updateNavState();
        }
        this.activeView = l.activeItem;
        this.fireViewChange();
    },
    fireViewChange: function() {
        var info = null,
            view = this.layout.activeItem;

        if (view.getViewBounds) {
            vb = view.getViewBounds();
            info = {
                activeDate: view.getStartDate(),
                viewStart: vb.start,
                viewEnd: vb.end
            };
        };
        this.fireEvent('viewchange', this, view, info);
    },
    updateNavState: function() {
        if (this.showNavBar !== false) {
            var item = this.layout.activeItem,
            suffix = item.id.split(this.id + '-')[1];

            var btn = Ext.getCmp(this.id + '-tb-' + suffix);
            btn.toggle(true);
        }
    },
    setParameter: function(param){
    	for (var o in param) {
        	this.parameter[o] = param[o];
    	}
    	this.setStartDate(this.layout.activeItem.getStartDate());
    },
    selectDate: function(picker ,dt){
    	this.startDate = dt;
    	this.setStartDate(dt);
    },
    setStartDate: function(dt) {
    	this.layout.activeItem.setParameter(this.parameter);
        this.layout.activeItem.setStartDate(dt, true);
        this.updateNavState();
        this.fireViewChange();
    },
    showWeek: function(dt) {
        this.setActiveView(this.id + '-week');
        this.setStartDate(dt);
    },
    onPrevClick: function() {
        this.startDate = this.layout.activeItem.movePrev();
        this.layout.activeItem.setParameter(this.parameter);
        this.layout.activeItem.setStartDate(this.startDate, true);
        this.updateNavState();
        this.fireViewChange();
    },
    onNextClick: function() {
        this.startDate = this.layout.activeItem.moveNext();
        this.layout.activeItem.setParameter(this.parameter);
        this.layout.activeItem.setStartDate(this.startDate, true);
        this.updateNavState();
        this.fireViewChange();
    },
    onDayClick: function() {
        this.setActiveView(this.id + '-day');
    },
    onWeekClick: function() {
        this.setActiveView(this.id + '-week');
    },
    onMonthClick: function() {
        this.setActiveView(this.id + '-month');
    },
    getActiveView: function() {
        return this.layout.activeItem;
    }
});
Ext.reg('calendarpanel', Ext.calendar.CalendarPanel);
