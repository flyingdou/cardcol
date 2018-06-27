Ext.ns('Ext.calendar');
 (function() {
    Ext.apply(Ext.calendar, {
        Date: {
            diffDays: function(start, end) {
                day = 1000 * 60 * 60 * 24;
                diff = end.clearTime(true).getTime() - start.clearTime(true).getTime();
                return Math.ceil(diff / day);
            },

            copyTime: function(fromDt, toDt) {
                var dt = toDt.clone();
                dt.setHours(
                fromDt.getHours(),
                fromDt.getMinutes(),
                fromDt.getSeconds(),
                fromDt.getMilliseconds());

                return dt;
            },

            compare: function(dt1, dt2, precise) {
                if (precise !== true) {
                    dt1 = dt1.clone();
                    dt1.setMilliseconds(0);
                    dt2 = dt2.clone();
                    dt2.setMilliseconds(0);
                }
                return dt2.getTime() - dt1.getTime();
            },

            // private helper fn
            maxOrMin: function(max) {
                var dt = (max ? 0: Number.MAX_VALUE),
                i = 0,
                args = arguments[1],
                ln = args.length;
                for (; i < ln; i++) {
                    dt = Math[max ? 'max': 'min'](dt, args[i].getTime());
                }
                return new Date(dt);
            },

            max: function() {
                return this.maxOrMin.apply(this, [true, arguments]);
            },

            min: function() {
                return this.maxOrMin.apply(this, [false, arguments]);
            }
        }
    });
})();

 Ext.calendar.DayHeaderTemplate = function(config){

    Ext.apply(this, config);

    this.allDayTpl = new Ext.calendar.BoxLayoutTemplate(config);
    this.allDayTpl.compile();

    Ext.calendar.DayHeaderTemplate.superclass.constructor.call(this,
        '<div class="ext-cal-hd-ct">',
            '<table class="ext-cal-hd-days-tbl" cellspacing="0" cellpadding="0">',
                '<tbody>',
                    '<tr>',
                        '<td class="ext-cal-gutter"></td>',
                        '<td class="ext-cal-hd-days-td"><div class="ext-cal-hd-ad-inner">{allDayTpl}</div></td>',
                        '<td class="ext-cal-gutter-rt"></td>',
                    '</tr>',
                '</tobdy>',
            '</table>',
        '</div>'
    );
};

Ext.extend(Ext.calendar.DayHeaderTemplate, Ext.XTemplate, {
    applyTemplate : function(o){
        return Ext.calendar.DayHeaderTemplate.superclass.applyTemplate.call(this, {
            allDayTpl: this.allDayTpl.apply(o)
        });
    }
});
Ext.calendar.DayHeaderTemplate.prototype.apply = Ext.calendar.DayHeaderTemplate.prototype.applyTemplate;

Ext.calendar.DayBodyTemplate = function(config){
    Ext.apply(this, config);
    Ext.calendar.DayBodyTemplate.superclass.constructor.call(this,
        '<table class="ext-cal-bg-tbl" cellspacing="0" cellpadding="0">',
            '<tbody>',
                '<tr height="1">',
                    '<td class="ext-cal-gutter"></td>',
                    '<td colspan="{dayCount}">',
                        '<div class="ext-cal-bg-rows">',
                            '<div class="ext-cal-bg-rows-inner">',
                                '<tpl for="times">',
                                	'<tpl if="isWorker == true">',
                                    '<div class="ext-cal-bg-row">',
                                        '<div class="ext-cal-bg-row-div ext-row-{[xindex]}"></div>',
                                    '</div>',
                                    '</tpl>',
                                    '<tpl if="isWorker == false">',
                                    '<div class="ext-cal-bg-row" style="background: #b3b3b3;">',
                                    	'<div class="" style="background: #b3b3b3;"></div>',
                                    '</div>',
                                    '</tpl>',
                                '</tpl>',
                            '</div>',
                        '</div>',
                    '</td>',
                '</tr>',
                '<tr>',
                    '<td class="ext-cal-day-times">',
                        '<tpl for="times">',
                            '<div class="ext-cal-bg-row">',
                                '<div class="ext-cal-day-time-inner">{dt}</div>',
                            '</div>',
                        '</tpl>',
                    '</td>',
                    '<tpl for="days">',
                        '<td class="ext-cal-day-col">',
                        	'<tpl if="worker == true">',
                            '<div class="ext-cal-day-col-inner">',
                            '</tpl>',
                            '<tpl if="worker == false">',
                            '<div class="ext-cal-day-col-inner" style="background: #b3b3b3;">',
                            '</tpl>',
                                '<div id="{[this.id]}-day-col-{dt:date("Ymd")}" class="ext-cal-day-col-gutter"></div>',
                            '</div>',
                        '</td>',
                    '</tpl>',
                '</tr>',
            '</tbody>',
        '</table>'
    );
};

Ext.extend(Ext.calendar.DayBodyTemplate, Ext.XTemplate, {
    // private
    applyTemplate : function(o){
        this.today = new Date().clearTime();
        this.dayCount = this.dayCount || 1;
        var i = 0, days = [], date, isWorker,
            dt = o.viewStart.clone(),
            times;
        for(; i<this.dayCount; i++){
        	date = dt.add(Date.DAY, i);
        	isWorker = this.workDays.indexOf(parseInt(date.format('w')) + 1) >= 0;
            days[i] = {dt: dt.add(Date.DAY, i), worker: isWorker};
        }
        times = [];
        dt = new Date().clearTime();
        for(i=0; i<24; i++){
        	var sTime = dt.format('H:i');
        	isWorker = this.workTimes.indexOf(sTime) >= 0;
            times.push({dt: dt.format('H:i'), isWorker: isWorker});
            dt = dt.add(Date.HOUR, 1);
        }
        return Ext.calendar.DayBodyTemplate.superclass.applyTemplate.call(this, {
            days: days,
            dayCount: days.length,
            times: times,
            workDays: this.workDays,
            workTimes: this.workTimes
        });
    }
});
Ext.calendar.DayBodyTemplate.prototype.apply = Ext.calendar.DayBodyTemplate.prototype.applyTemplate;

Ext.calendar.DayViewTemplate = function(config){

    Ext.apply(this, config);

    this.headerTpl = new Ext.calendar.DayHeaderTemplate(config);
    this.headerTpl.compile();

    this.bodyTpl = new Ext.calendar.DayBodyTemplate(config);
    this.bodyTpl.compile();

    Ext.calendar.DayViewTemplate.superclass.constructor.call(this,
        '<div class="ext-cal-inner-ct">',
            '{headerTpl}',
            '{bodyTpl}',
        '</div>'
    );
};

Ext.extend(Ext.calendar.DayViewTemplate, Ext.XTemplate, {
    applyTemplate : function(o){
        return Ext.calendar.DayViewTemplate.superclass.applyTemplate.call(this, {
            headerTpl: this.headerTpl.apply(o),
            bodyTpl: this.bodyTpl.apply(o)
        });
    }
});
Ext.calendar.DayViewTemplate.prototype.apply = Ext.calendar.DayViewTemplate.prototype.applyTemplate;

Ext.calendar.MonthViewTemplate = function(config){

    Ext.apply(this, config);

    this.weekTpl = new Ext.calendar.BoxLayoutTemplate(config);
    this.weekTpl.compile();

    var weekLinkTpl = this.showWeekLinks ? '<div class="ext-cal-week-link-hd">&nbsp;</div>' : '';

    Ext.calendar.MonthViewTemplate.superclass.constructor.call(this,
        '<div class="ext-cal-inner-ct {extraClasses}">',
            '<div class="ext-cal-hd-ct ext-cal-month-hd">',
                weekLinkTpl,
                '<table class="ext-cal-hd-days-tbl" cellpadding="0" cellspacing="0">',
                    '<tbody>',
                        '<tr>',
                            '<tpl for="days">',
                                '<th class="ext-cal-hd-day{[xindex==1 ? " ext-cal-day-first" : ""]}" title="{.:date("l, F j, Y")}">{.:date("D")}</th>',
                            '</tpl>',
                        '</tr>',
                    '</tbody>',
                '</table>',
            '</div>',
            '<div class="ext-cal-body-ct">{weeks}</div>',
        '</div>'
    );
};

Ext.extend(Ext.calendar.MonthViewTemplate, Ext.XTemplate, {
    // private
    applyTemplate : function(o){
        var days = [],
            weeks = this.weekTpl.apply(o),
            dt = o.viewStart;

        for(var i = 0; i < 7; i++){
            days.push(dt.add(Date.DAY, i));
        }

        var extraClasses = this.showHeader === true ? '' : 'ext-cal-noheader';
        if(this.showWeekLinks){
            extraClasses += ' ext-cal-week-links';
        }

        return Ext.calendar.MonthViewTemplate.superclass.applyTemplate.call(this, {
            days: days,
            weeks: weeks,
            extraClasses: extraClasses
        });
    }
});
Ext.calendar.MonthViewTemplate.prototype.apply = Ext.calendar.MonthViewTemplate.prototype.applyTemplate;

Ext.calendar.BoxLayoutTemplate = function(config){
    Ext.apply(this, config);
    var weekLinkTpl = this.showWeekLinks ? '<div id="{weekLinkId}" class="ext-cal-week-link">{weekNum}</div>' : '';
    Ext.calendar.BoxLayoutTemplate.superclass.constructor.call(this,
        '<tpl for="weeks">',
            '<div id="{[this.id]}-wk-{[xindex-1]}" class="ext-cal-wk-ct" style="top:{[this.getRowTop(xindex, xcount)]}%; height:{[this.getRowHeight(xcount)]}%;">',
                weekLinkTpl,
                '<table class="ext-cal-bg-tbl" cellpadding="0" cellspacing="0">',
                    '<tbody>',
                        '<tr>',
                            '<tpl for=".">',
                                 '<td id="{[this.id]}-day-{date:date("Ymd")}" class="{cellCls}">&nbsp;</td>',
                            '</tpl>',
                        '</tr>',
                    '</tbody>',
                '</table>',
                '<table class="ext-cal-evt-tbl" cellpadding="0" cellspacing="0">',
                    '<tbody>',
                        '<tr>',
                            '<tpl for=".">',
                                '<td id="{[this.id]}-ev-day-{date:date("Ymd")}" class="{titleCls}"><div>{title}</div></td>',
                            '</tpl>',
                        '</tr>',
                    '</tbody>',
                '</table>',
            '</div>',
        '</tpl>', {
            getRowTop: function(i, ln){
                return ((i-1)*(100/ln));
            },
            getRowHeight: function(ln){
                return 100/ln;
            }
        }
    );
};

Ext.extend(Ext.calendar.BoxLayoutTemplate, Ext.XTemplate, {
    // private
    applyTemplate : function(o){

        Ext.apply(this, o);

        var w = 0, title = '', first = true, isToday = false, showMonth = false, prevMonth = false, nextMonth = false,
            weeks = [[]],
            today = new Date().clearTime(),
            dt = this.viewStart.clone(),
            thisMonth = this.startDate.getMonth();

        for(; w < this.weekCount || this.weekCount == -1; w++){
            if(dt > this.viewEnd){
                break;
            }
            weeks[w] = [];

            for(var d = 0; d < this.dayCount; d++){
                isToday = dt.getTime() === today.getTime();
                showMonth = first || (dt.getDate() == 1);
                prevMonth = (dt.getMonth() < thisMonth) && this.weekCount == -1;
                nextMonth = (dt.getMonth() > thisMonth) && this.weekCount == -1;

                if(dt.getDay() == 1){
                    // The ISO week format 'W' is relative to a Monday week start. If we
                    // make this check on Sunday the week number will be off.
                    weeks[w].weekNum = this.showWeekNumbers ? dt.format('W') : '&nbsp;';
                    weeks[w].weekLinkId = 'ext-cal-week-'+dt.format('Ymd');
                }

                if(showMonth){
                    if(isToday){
                        title = this.getTodayText();
                    }
                    else{
                        title = dt.format(this.dayCount == 1 ? 'l, F j, Y' : (first ? 'M j, Y' : 'M j'));
                    }
                }
                else{
                    var dayFmt = (w == 0 && this.showHeader !== true) ? 'D j' : 'j';
                    title = isToday ? this.getTodayText() : dt.format(dayFmt);
                }

                weeks[w].push({
                    title: title,
                    date: dt.clone(),
                    titleCls: 'ext-cal-dtitle ' + (isToday ? ' ext-cal-dtitle-today' : '') +
                        (w==0 ? ' ext-cal-dtitle-first' : '') +
                        (prevMonth ? ' ext-cal-dtitle-prev' : '') +
                        (nextMonth ? ' ext-cal-dtitle-next' : ''),
                    cellCls: 'ext-cal-day ' + (isToday ? ' ext-cal-day-today' : '') +
                        (d==0 ? ' ext-cal-day-first' : '') +
                        (prevMonth ? ' ext-cal-day-prev' : '') +
                        (nextMonth ? ' ext-cal-day-next' : '')
                });
                dt = dt.add(Date.DAY, 1);
                first = false;
            }
        }

        return Ext.calendar.BoxLayoutTemplate.superclass.applyTemplate.call(this, {
            weeks: weeks
        });
    },

    // private
    getTodayText : function(){
        var dt = new Date().format('l, F j, Y'),
            todayText = this.showTodayText !== false ? this.todayText : '',
            timeText = this.showTime !== false ? ' <span id="'+this.id+'-clock" class="ext-cal-dtitle-time">' +
                    new Date().format('g:i a') + '</span>' : '',
            separator = todayText.length > 0 || timeText.length > 0 ? ' &mdash; ' : '';

        if(this.dayCount == 1){
            return dt + separator + todayText + timeText;
        }
        fmt = this.weekCount == 1 ? 'D j' : 'j';
        return todayText.length > 0 ? todayText + timeText : new Date().format(fmt) + timeText;
    }
});
Ext.calendar.BoxLayoutTemplate.prototype.apply = Ext.calendar.BoxLayoutTemplate.prototype.applyTemplate;

Ext.calendar.DragZone = Ext.extend(Ext.dd.DragZone, {
    ddGroup: 'CalendarDD',
    eventSelector: '.ext-cal-evt',

    constructor: function(el, config) {
        if (!Ext.calendar._statusProxyInstance) {
            Ext.calendar._statusProxyInstance = new Ext.calendar.StatusProxy();
        }
        this.proxy = Ext.calendar._statusProxyInstance;
        Ext.calendar.DragZone.superclass.constructor.call(this, el, config);
    },

    getDragData: function(e) {
        // Check whether we are dragging on an event first
        var t = e.getTarget(this.eventSelector, 3);
        if (t) {
            var rec = this.view.getEventRecordFromEl(t);
            return {
                type: 'eventdrag',
                ddel: t,
                eventStart: rec.data[Ext.calendar.EventMappings.StartDate.name],
                eventEnd: rec.data[Ext.calendar.EventMappings.EndDate.name],
                proxy: this.proxy
            };
        }

        // If not dragging an event then we are dragging on
        // the calendar to add a new event
        t = this.view.getDayAt(e.getPageX(), e.getPageY());
        if (t.el) {
            return {
                type: 'caldrag',
                start: t.date,
                proxy: this.proxy
            };
        }
        return null;
    },

    onInitDrag: function(x, y) {
        if (this.dragData.ddel) {
            var ghost = this.dragData.ddel.cloneNode(true),
            child = Ext.fly(ghost).child('dl');

            Ext.fly(ghost).setWidth('auto');

            if (child) {
                // for IE/Opera
                child.setHeight('auto');
            }
            this.proxy.update(ghost);
            this.onStartDrag(x, y);
        }
        else if (this.dragData.start) {
            this.onStartDrag(x, y);
        }
        this.view.onInitDrag();
        return true;
    },

    afterRepair: function() {
        if (Ext.enableFx && this.dragData.ddel) {
            Ext.Element.fly(this.dragData.ddel).highlight(this.hlColor || 'c3daf9');
        }
        this.dragging = false;
    },

    getRepairXY: function(e) {
        if (this.dragData.ddel) {
            return Ext.Element.fly(this.dragData.ddel).getXY();
        }
    },

    afterInvalidDrop: function(e, id) {
        Ext.select('.ext-dd-shim').hide();
    }
});

/*
 * Internal drop zone implementation for the calendar components. This provides base functionality
 * and is primarily for the month view -- DayViewDD adds day/week view-specific functionality.
 */
Ext.calendar.DropZone = Ext.extend(Ext.dd.DropZone, {
    ddGroup: 'CalendarDD',
    eventSelector: '.ext-cal-evt',

    // private
    shims: [],

    getTargetFromEvent: function(e) {
        var dragOffset = this.dragOffset || 0,
        y = e.getPageY() - dragOffset,
        d = this.view.getDayAt(e.getPageX(), y);

        return d.el ? d: null;
    },

    onNodeOver: function(n, dd, e, data) {
        var D = Ext.calendar.Date,
        start = data.type == 'eventdrag' ? n.date: D.min(data.start, n.date),
        end = data.type == 'eventdrag' ? n.date.add(Date.DAY, D.diffDays(data.eventStart, data.eventEnd)) :
        D.max(data.start, n.date);

        if (!this.dragStartDate || !this.dragEndDate || (D.diffDays(start, this.dragStartDate) != 0) || (D.diffDays(end, this.dragEndDate) != 0)) {
            this.dragStartDate = start;
            this.dragEndDate = end.clearTime().add(Date.DAY, 1).add(Date.MILLI, -1);
            this.shim(start, end);

            var range = start.format('n/j');
            if (D.diffDays(start, end) > 0) {
                range += '-' + end.format('n/j');
            }
            var msg = String.format(data.type == 'eventdrag' ? this.moveText: this.createText, range);
            data.proxy.updateMsg(msg);
        }
        return this.dropAllowed;
    },

    shim: function(start, end) {
        this.currWeek = -1;
        var dt = start.clone(),
            i = 0,
            shim,
            box,
            cnt = Ext.calendar.Date.diffDays(dt, end) + 1;

        Ext.each(this.shims,
            function(shim) {
                if (shim) {
                    shim.isActive = false;
                }
            }
        );

        while (i++<cnt) {
            var dayEl = this.view.getDayEl(dt);

            // if the date is not in the current view ignore it (this
            // can happen when an event is dragged to the end of the
            // month so that it ends outside the view)
            if (dayEl) {
                var wk = this.view.getWeekIndex(dt);
                shim = this.shims[wk];

                if (!shim) {
                    shim = this.createShim();
                    this.shims[wk] = shim;
                }
                if (wk != this.currWeek) {
                    shim.boxInfo = dayEl.getBox();
                    this.currWeek = wk;
                }
                else {
                    box = dayEl.getBox();
                    shim.boxInfo.right = box.right;
                    shim.boxInfo.width = box.right - shim.boxInfo.x;
                }
                shim.isActive = true;
            }
            dt = dt.add(Date.DAY, 1);
        }

        Ext.each(this.shims,
        function(shim) {
            if (shim) {
                if (shim.isActive) {
                    shim.show();
                    shim.setBox(shim.boxInfo);
                }
                else if (shim.isVisible()) {
                    shim.hide();
                }
            }
        });
    },

    createShim: function() {
        if (!this.shimCt) {
            this.shimCt = Ext.get('ext-dd-shim-ct');
            if (!this.shimCt) {
                this.shimCt = document.createElement('div');
                this.shimCt.id = 'ext-dd-shim-ct';
                Ext.getBody().appendChild(this.shimCt);
            }
        }
        var el = document.createElement('div');
        el.className = 'ext-dd-shim';
        this.shimCt.appendChild(el);

        return new Ext.Layer({
            shadow: false,
            useDisplay: true,
            constrain: false
        },
        el);
    },

    clearShims: function() {
        Ext.each(this.shims,
        function(shim) {
            if (shim) {
                shim.hide();
            }
        });
    },

    onContainerOver: function(dd, e, data) {
        return this.dropAllowed;
    },

    onCalendarDragComplete: function() {
        delete this.dragStartDate;
        delete this.dragEndDate;
        this.clearShims();
    },

    onNodeDrop: function(n, dd, e, data) {
        if (n && data) {
            if (data.type == 'eventdrag') {
                var rec = this.view.getEventRecordFromEl(data.ddel),
                dt = Ext.calendar.Date.copyTime(rec.data[Ext.calendar.EventMappings.StartDate.name], n.date);

                this.view.onEventDrop(rec, dt);
                this.onCalendarDragComplete();
                return true;
            }
            if (data.type == 'caldrag') {
                this.view.onCalendarEndDrag(this.dragStartDate, this.dragEndDate,
                this.onCalendarDragComplete.createDelegate(this));
                //shims are NOT cleared here -- they stay visible until the handling
                //code calls the onCalendarDragComplete callback which hides them.
                return true;
            }
        }
        this.onCalendarDragComplete();
        return false;
    },

    onContainerDrop: function(dd, e, data) {
        this.onCalendarDragComplete();
        return false;
    },

    destroy: function() {
        Ext.calendar.DropZone.superclass.destroy.call(this);
        Ext.destroy(this.shimCt);
    }
});

Ext.dd.ScrollManager = function() {
    var ddm = Ext.dd.DragDropMgr,
        els = {},
        dragEl = null,
        proc = {},
        onStop = function(e) {
            dragEl = null;
            clearProc();
        },
        triggerRefresh = function() {
            if (ddm.dragCurrent) {
                ddm.refreshCache(ddm.dragCurrent.groups);
            }
        },
        doScroll = function() {
            if (ddm.dragCurrent) {
                var dds = Ext.dd.ScrollManager,
                    inc = proc.el.ddScrollConfig ? proc.el.ddScrollConfig.increment: dds.increment;
                if (!dds.animate) {
                    if (proc.el.scroll(proc.dir, inc)) {
                        triggerRefresh();
                    }
                } else {
                    proc.el.scroll(proc.dir, inc, true, dds.animDuration, triggerRefresh);
                }
            }
        },
        clearProc = function() {
            if (proc.id) {
                clearInterval(proc.id);
            }
            proc.id = 0;
            proc.el = null;
            proc.dir = "";
        },
        startProc = function(el, dir) {
            clearProc();
            proc.el = el;
            proc.dir = dir;
            var freq = (el.ddScrollConfig && el.ddScrollConfig.frequency) ?
                            el.ddScrollConfig.frequency: Ext.dd.ScrollManager.frequency,
                group = el.ddScrollConfig ? el.ddScrollConfig.ddGroup: undefined;

            if (group === undefined || ddm.dragCurrent.ddGroup == group) {
                proc.id = setInterval(doScroll, freq);
            }
        },
        onFire = function(e, isDrop) {
            if (isDrop || !ddm.dragCurrent) {
                return;
            }
            var dds = Ext.dd.ScrollManager;
            if (!dragEl || dragEl != ddm.dragCurrent) {
                dragEl = ddm.dragCurrent;
                // refresh regions on drag start
                dds.refreshCache();
            }

            var xy = Ext.lib.Event.getXY(e),
                pt = new Ext.lib.Point(xy[0], xy[1]),
                id,
                el,
                r,
                c;
            for (id in els) {
                if (els.hasOwnProperty(id)) {
                    el = els[id];
                    r = el._region;
                    c = el.ddScrollConfig ? el.ddScrollConfig: dds;
                    if (r && r.contains(pt) && el.isScrollable()) {
                        if (r.bottom - pt.y <= c.vthresh) {
                            if (proc.el != el) {
                                startProc(el, "down");
                            }
                            return;
                        } else if (r.right - pt.x <= c.hthresh) {
                            if (proc.el != el) {
                                startProc(el, "left");
                            }
                            return;
                        } else if (pt.y - r.top <= c.vthresh) {
                            if (proc.el != el) {
                                startProc(el, "up");
                            }
                            return;
                        } else if (pt.x - r.left <= c.hthresh) {
                            if (proc.el != el) {
                                startProc(el, "right");
                            }
                            return;
                        }
                    }
                }
            }
            clearProc();
        };

    ddm.fireEvents = ddm.fireEvents.createSequence(onFire, ddm);
    ddm.stopDrag = ddm.stopDrag.createSequence(onStop, ddm);

    return {
        /**
         * Registers new overflow element(s) to auto scroll
         * @param {Mixed/Array} el The id of or the element to be scrolled or an array of either
         */
        register: function(el) {
            if (Ext.isArray(el)) {
                var i = 0,
                    len = el.length;
                for (; i < len; i++) {
                    this.register(el[i]);
                }
            } else {
                el = Ext.get(el);
                els[el.id] = el;
            }
        },

        /**
         * Unregisters overflow element(s) so they are no longer scrolled
         * @param {Mixed/Array} el The id of or the element to be removed or an array of either
         */
        unregister: function(el) {
            if (Ext.isArray(el)) {
                var i = 0,
                    len = el.length;
                for (; i < len; i++) {
                    this.unregister(el[i]);
                }
            } else {
                el = Ext.get(el);
                delete els[el.id];
            }
        },

        /**
         * The number of pixels from the top or bottom edge of a container the pointer needs to be to
         * trigger scrolling (defaults to 25)
         * @type Number
         */
        vthresh: 25,
        /**
         * The number of pixels from the right or left edge of a container the pointer needs to be to
         * trigger scrolling (defaults to 25)
         * @type Number
         */
        hthresh: 25,

        /**
         * The number of pixels to scroll in each scroll increment (defaults to 50)
         * @type Number
         */
        increment: 100,

        /**
         * The frequency of scrolls in milliseconds (defaults to 500)
         * @type Number
         */
        frequency: 500,

        /**
         * True to animate the scroll (defaults to true)
         * @type Boolean
         */
        animate: true,

        /**
         * The animation duration in seconds -
         * MUST BE less than Ext.dd.ScrollManager.frequency! (defaults to .4)
         * @type Number
         */
        animDuration: 0.4,

        /**
         * Manually trigger a cache refresh.
         */
        refreshCache: function() {
            var id;
            for (id in els) {
                if (els.hasOwnProperty(id)) {
                    if (typeof els[id] == 'object') {
                        // for people extending the object prototype
                        els[id]._region = els[id].getRegion();
                    }
                }
            }
        }
    };
}();

Ext.calendar.DayViewDragZone = Ext.extend(Ext.calendar.DragZone, {
    ddGroup: 'DayViewDD',
    resizeSelector: '.ext-evt-rsz',

    getDragData: function(e) {
        var t = e.getTarget(this.resizeSelector, 2, true),
            p,
            rec;
        if (t) {
            p = t.parent(this.eventSelector);
            rec = this.view.getEventRecordFromEl(p);

            return {
                type: 'eventresize',
                ddel: p.dom,
                eventStart: rec.data[Ext.calendar.EventMappings.StartDate.name],
                eventEnd: rec.data[Ext.calendar.EventMappings.EndDate.name],
                proxy: this.proxy
            };
        }
        t = e.getTarget(this.eventSelector, 3);
        if (t) {
            rec = this.view.getEventRecordFromEl(t);
            return {
                type: 'eventdrag',
                ddel: t,
                eventStart: rec.data[Ext.calendar.EventMappings.StartDate.name],
                eventEnd: rec.data[Ext.calendar.EventMappings.EndDate.name],
                proxy: this.proxy
            };
        }

        // If not dragging/resizing an event then we are dragging on
        // the calendar to add a new event
        t = this.view.getDayAt(e.getPageX(), e.getPageY());
        if (t.el) {
            return {
                type: 'caldrag',
                dayInfo: t,
                proxy: this.proxy
            };
        }
        return null;
    }
});

/*
 * Internal drop zone implementation for the calendar day and week views.
 */
Ext.calendar.DayViewDropZone = Ext.extend(Ext.calendar.DropZone, {
    ddGroup: 'DayViewDD',

    onNodeOver: function(n, dd, e, data) {
        var dt,
            box,
            endDt,
            text = this.createText,
            curr,
            start,
            end,
            evtEl,
            dayCol;
        if (data.type == 'caldrag') {
            if (!this.dragStartMarker) {
                // Since the container can scroll, this gets a little tricky.
                // There is no el in the DOM that we can measure by default since
                // the box is simply calculated from the original drag start (as opposed
                // to dragging or resizing the event where the orig event box is present).
                // To work around this we add a placeholder el into the DOM and give it
                // the original starting time's box so that we can grab its updated
                // box measurements as the underlying container scrolls up or down.
                // This placeholder is removed in onNodeDrop.
                this.dragStartMarker = n.el.parent().createChild({
                    style: 'position:absolute;'
                });
                this.dragStartMarker.setBox(n.timeBox);
                this.dragCreateDt = n.date;
            }
            box = this.dragStartMarker.getBox();
            box.height = Math.ceil(Math.abs(e.xy[1] - box.y) / n.timeBox.height) * n.timeBox.height;

            if (e.xy[1] < box.y) {
                box.height += n.timeBox.height;
                box.y = box.y - box.height + n.timeBox.height;
                endDt = this.dragCreateDt.add(Date.MINUTE, 30);
            }
            else {
                n.date = n.date.add(Date.MINUTE, 30);
            }
            this.shim(this.dragCreateDt, box);

            curr = Ext.calendar.Date.copyTime(n.date, this.dragCreateDt);
            this.dragStartDate = Ext.calendar.Date.min(this.dragCreateDt, curr);
            this.dragEndDate = endDt || Ext.calendar.Date.max(this.dragCreateDt, curr);

            dt = this.dragStartDate.format('g:ia-') + this.dragEndDate.format('g:ia');
        }
        else {
            evtEl = Ext.get(data.ddel);
            dayCol = evtEl.parent().parent();
            box = evtEl.getBox();

            box.width = dayCol.getWidth();

            if (data.type == 'eventdrag') {
                if (this.dragOffset === undefined) {
                    this.dragOffset = n.timeBox.y - box.y;
                    box.y = n.timeBox.y - this.dragOffset;
                }
                else {
                    box.y = n.timeBox.y;
                }
                dt = n.date.format('n/j g:ia');
                box.x = n.el.getLeft();

                this.shim(n.date, box);
                text = this.moveText;
            }
            if (data.type == 'eventresize') {
                if (!this.resizeDt) {
                    this.resizeDt = n.date;
                }
                box.x = dayCol.getLeft();
                box.height = Math.ceil(Math.abs(e.xy[1] - box.y) / n.timeBox.height) * n.timeBox.height;
                if (e.xy[1] < box.y) {
                    box.y -= box.height;
                }
                else {
                    n.date = n.date.add(Date.MINUTE, 30);
                }
                this.shim(this.resizeDt, box);

                curr = Ext.calendar.Date.copyTime(n.date, this.resizeDt);
                start = Ext.calendar.Date.min(data.eventStart, curr);
                end = Ext.calendar.Date.max(data.eventStart, curr);

                data.resizeDates = {
                    StartDate: start,
                    EndDate: end
                };
                dt = start.format('g:ia-') + end.format('g:ia');
                text = this.resizeText;
            }
        }

        data.proxy.updateMsg(String.format(text, dt));
        return this.dropAllowed;
    },

    shim: function(dt, box) {
        Ext.each(this.shims,
        function(shim) {
            if (shim) {
                shim.isActive = false;
                shim.hide();
            }
        });

        var shim = this.shims[0];
        if (!shim) {
            shim = this.createShim();
            this.shims[0] = shim;
        }

        shim.isActive = true;
        shim.show();
        shim.setBox(box);
    },

    onNodeDrop: function(n, dd, e, data) {
        var rec;
        if (n && data) {
            if (data.type == 'eventdrag') {
                rec = this.view.getEventRecordFromEl(data.ddel);
                this.view.onEventDrop(rec, n.date);
                this.onCalendarDragComplete();
                delete this.dragOffset;
                return true;
            }
            if (data.type == 'eventresize') {
                rec = this.view.getEventRecordFromEl(data.ddel);
                this.view.onEventResize(rec, data.resizeDates);
                this.onCalendarDragComplete();
                delete this.resizeDt;
                return true;
            }
            if (data.type == 'caldrag') {
                Ext.destroy(this.dragStartMarker);
                delete this.dragStartMarker;
                delete this.dragCreateDt;
                this.view.onCalendarEndDrag(this.dragStartDate, this.dragEndDate,
                this.onCalendarDragComplete.createDelegate(this));
                //shims are NOT cleared here -- they stay visible until the handling
                //code calls the onCalendarDragComplete callback which hides them.
                return true;
            }
        }
        this.onCalendarDragComplete();
        return false;
    }
});

Ext.calendar.StatusProxy = function(config) {
    Ext.apply(this, config);
    this.id = this.id || Ext.id();
    this.el = new Ext.Layer({
        dh: {
            id: this.id,
            cls: 'ext-dd-drag-proxy x-dd-drag-proxy ' + this.dropNotAllowed,
            cn: [
            {
                cls: 'x-dd-drop-icon'
            },
            {
                cls: 'ext-dd-ghost-ct',
                cn: [
                {
                    cls: 'x-dd-drag-ghost'
                },
                {
                    cls: 'ext-dd-msg'
                }
                ]
            }
            ]
        },
        shadow: !config || config.shadow !== false
    });
    this.ghost = Ext.get(this.el.dom.childNodes[1].childNodes[0]);
    this.message = Ext.get(this.el.dom.childNodes[1].childNodes[1]);
    this.dropStatus = this.dropNotAllowed;
};

Ext.extend(Ext.calendar.StatusProxy, Ext.dd.StatusProxy, {
    /**
     * @cfg {String} moveEventCls
     * The CSS class to apply to the status element when an event is being dragged (defaults to 'ext-cal-dd-move').
     */
    moveEventCls: 'ext-cal-dd-move',
    /**
     * @cfg {String} addEventCls
     * The CSS class to apply to the status element when drop is not allowed (defaults to 'ext-cal-dd-add').
     */
    addEventCls: 'ext-cal-dd-add',

    // inherit docs
    update: function(html) {
        if (typeof html == 'string') {
            this.ghost.update(html);
        } else {
            this.ghost.update('');
            html.style.margin = '0';
            this.ghost.dom.appendChild(html);
        }
        var el = this.ghost.dom.firstChild;
        if (el) {
            Ext.fly(el).setStyle('float', 'none').setHeight('auto');
            Ext.getDom(el).id += '-ddproxy';
        }
    },

    /**
     * Update the calendar-specific drag status message without altering the ghost element.
     * @param {String} msg The new status message
     */
    updateMsg: function(msg) {
        this.message.update(msg);
    }
});

