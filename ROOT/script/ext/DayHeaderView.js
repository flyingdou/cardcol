Ext.calendar.DayHeaderView = Ext.extend(Ext.calendar.MonthView, {
    weekCount: 1,
    dayCount: 1,
    allDayOnly: true,
    monitorResize: false,
    isHead: true,
    afterRender: function() {
        if (!this.tpl) {
            this.tpl = new Ext.calendar.DayHeaderTemplate({
                id: this.id,
                showTodayText: this.showTodayText,
                todayText: this.todayText,
                showTime: this.showTime
            });
        }
        this.tpl.compile();
        this.addClass('ext-cal-day-header');

        Ext.calendar.DayHeaderView.superclass.afterRender.call(this);
    },

    // private
    forceSize: Ext.emptyFn,

    // private
    refresh: function() {
        Ext.calendar.DayHeaderView.superclass.refresh.call(this);
        this.recalcHeaderBox();
    },

    // private
    recalcHeaderBox: function() {
        var tbl = this.el.child('.ext-cal-evt-tbl'),
        h = tbl.getHeight();

        this.el.setHeight(h + 7);

        if (Ext.isIE && Ext.isStrict) {
            this.el.child('.ext-cal-hd-ad-inner').setHeight(h + 4);
        }
        if (Ext.isOpera) {
            //TODO: figure out why Opera refuses to refresh height when
            //the new height is lower than the previous one
            //            var ct = this.el.child('.ext-cal-hd-ct');
            //            ct.repaint();
            }
    },

    // private
    moveNext: function(noRefresh) {
        return this.moveDays(this.dayCount, noRefresh);
    },

    // private
    movePrev: function(noRefresh) {
        return this.moveDays( - this.dayCount, noRefresh);
    },

    // private
    onClick: function(e, t) {
        var el = e.getTarget('td', 3), parts, dt;
        if (el) {
            if (el.id && el.id.indexOf(this.dayElIdDelimiter) > -1) {
                parts = el.id.split(this.dayElIdDelimiter);
                dt = parts[parts.length - 1];
                this.fireEvent('dayclick', this, Date.parseDate(dt, 'Ymd'), true, Ext.get(this.getDayId(dt)));
                return;
            }
        }
        Ext.calendar.DayHeaderView.superclass.onClick.apply(this, arguments);
    }
});

Ext.reg('dayheaderview', Ext.calendar.DayHeaderView);
