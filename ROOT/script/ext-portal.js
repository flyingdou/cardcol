Ext.define('Ext.ux.Portlet', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.portlet',
    layout: 'fit',
    anchor: '100%',
    frame: true,
    closable: true,
    collapsible: true,
    animCollapse: true,
    draggable: {moveOnDrag: false},
    cls: 'x-portlet',
    doClose: function() {
        if (!this.closing) {
            this.closing = true;
            this.el.animate({
                opacity: 0,
                callback: function(){
                    this.fireEvent('close', this);
                    this[this.closeAction]();
                },
                scope: this
            });
        }
    }
});
Ext.define('Ext.ux.PortalColumn', {
    extend: 'Ext.container.Container',
    alias: 'widget.portalcolumn',
    requires: [
        'Ext.layout.container.Anchor',
        'Ext.ux.Portlet'
    ],
    layout: 'anchor',
    defaultType: 'portlet',
    cls: 'x-portal-column'
});
Ext.define('Ext.ux.PortalDropZone', {
    extend: 'Ext.dd.DropTarget',
    constructor: function(portal, cfg) {
        this.portal = portal;
        Ext.dd.ScrollManager.register(portal.body);
        Ext.ux.PortalDropZone.superclass.constructor.call(this, portal.body, cfg);
        portal.body.ddScrollConfig = this.ddScrollConfig;
    },
    ddScrollConfig: {
        vthresh: 50,
        hthresh: -1,
        animate: true,
        increment: 200
    },
    createEvent: function(dd, e, data, col, c, pos) {
        return {
            portal: this.portal,
            panel: data.panel,
            columnIndex: col,
            column: c,
            position: pos,
            data: data,
            source: dd,
            rawEvent: e,
            status: this.dropAllowed
        };
    },
    notifyOver: function(dd, e, data) {
        var xy = e.getXY(),
            portal = this.portal,
            proxy = dd.proxy;
        if (!this.grid) {
            this.grid = this.getGrid();
        }
        var cw = portal.body.dom.clientWidth;
        if (!this.lastCW) {
            this.lastCW = cw;
        } else if (this.lastCW != cw) {
            this.lastCW = cw;
            this.grid = this.getGrid();
        }
        var colIndex = 0,
            colRight = 0,
            cols = this.grid.columnX,
            len = cols.length,
            cmatch = false;
        for (len; colIndex < len; colIndex++) {
            colRight = cols[colIndex].x + cols[colIndex].w;
            if (xy[0] < colRight) {
                cmatch = true;
                break;
            }
        }
        if (!cmatch) {
            colIndex--;
        }
        var overPortlet, pos = 0,
            h = 0,
            match = false,
            overColumn = portal.items.getAt(colIndex),
            portlets = overColumn.items.items,
            overSelf = false;

        len = portlets.length;
        for (len; pos < len; pos++) {
            overPortlet = portlets[pos];
            h = overPortlet.el.getHeight();
            if (h === 0) {
                overSelf = true;
            } else if ((overPortlet.el.getY() + (h / 2)) > xy[1]) {
                match = true;
                break;
            }
        }
        pos = (match && overPortlet ? pos : overColumn.items.getCount()) + (overSelf ? -1 : 0);
        var overEvent = this.createEvent(dd, e, data, colIndex, overColumn, pos);
        if (portal.fireEvent('validatedrop', overEvent) !== false && portal.fireEvent('beforedragover', overEvent) !== false) {
            proxy.getProxy().setWidth('auto');
            if (overPortlet) {
                dd.panelProxy.moveProxy(overPortlet.el.dom.parentNode, match ? overPortlet.el.dom : null);
            } else {
                dd.panelProxy.moveProxy(overColumn.el.dom, null);
            }

            this.lastPos = {
                c: overColumn,
                col: colIndex,
                p: overSelf || (match && overPortlet) ? pos : false
            };
            this.scrollPos = portal.body.getScroll();

            portal.fireEvent('dragover', overEvent);
            return overEvent.status;
        } else {
            return overEvent.status;
        }
    },
    notifyOut: function() {
        delete this.grid;
    },
    notifyDrop: function(dd, e, data) {
        delete this.grid;
        if (!this.lastPos) {
            return;
        }
        var c = this.lastPos.c,
            col = this.lastPos.col,
            pos = this.lastPos.p,
            panel = dd.panel,
            dropEvent = this.createEvent(dd, e, data, col, c, pos !== false ? pos : c.items.getCount());

        if (this.portal.fireEvent('validatedrop', dropEvent) !== false && 
            this.portal.fireEvent('beforedrop', dropEvent) !== false) {

            Ext.suspendLayouts();
            panel.el.dom.style.display = '';
            dd.panelProxy.hide();
            dd.proxy.hide();

            if (pos !== false) {
                c.insert(pos, panel);
            } else {
                c.add(panel);
            }

            Ext.resumeLayouts(true);

            this.portal.fireEvent('drop', dropEvent);
            var st = this.scrollPos.top;
            if (st) {
                var d = this.portal.body.dom;
                setTimeout(function() {
                    d.scrollTop = st;
                },
                10);
            }
        }
        delete this.lastPos;
        return true;
    },
    getGrid: function() {
        var box = this.portal.body.getBox();
        box.columnX = [];
        this.portal.items.each(function(c) {
            box.columnX.push({
                x: c.el.getX(),
                w: c.el.getWidth()
            });
        });
        return box;
    },
    unreg: function() {
        Ext.dd.ScrollManager.unregister(this.portal.body);
        Ext.ux.PortalDropZone.superclass.unreg.call(this);
    }
});
Ext.define('Ext.ux.PortalPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.portalpanel',
    requires: [
        'Ext.layout.container.Column',
        'Ext.ux.PortalDropZone',
        'Ext.ux.PortalColumn'
    ],
    cls: 'x-portal',
    bodyCls: 'x-portal-body',
    defaultType: 'portalcolumn',
    autoScroll: true,
    manageHeight: false,
    initComponent : function() {
        this.layout = {type : 'column'};
        this.callParent();
        this.addEvents({
            validatedrop: true,
            beforedragover: true,
            dragover: true,
            beforedrop: true,
            drop: true
        });
    },
    beforeLayout: function() {
        var items = this.layout.getLayoutItems(),
            len = items.length,
            firstAndLast = ['x-portal-column-first', 'x-portal-column-last'],
            i, item, last;
        for (i = 0; i < len; i++) {
            item = items[i];
            item.columnWidth = 1 / len;
            last = (i == len-1);
            if (!i) { // if (first)
                if (last) {
                    item.addCls(firstAndLast);
                } else {
                    item.addCls('x-portal-column-first');
                    item.removeCls('x-portal-column-last');
                }
            } else if (last) {
                item.addCls('x-portal-column-last');
                item.removeCls('x-portal-column-first');
            } else {
                item.removeCls(firstAndLast);
            }
        }
        return this.callParent(arguments);
    },
    initEvents : function(){
        this.callParent();
        this.dd = Ext.create('Ext.ux.PortalDropZone', this, this.dropConfig);
    },
    beforeDestroy : function() {
        if (this.dd) {
            this.dd.unreg();
        }
        this.callParent();
    }
});