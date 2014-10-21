YAHOO.namespace("document.doc.show");

var ydds = YAHOO.document.doc.show;
var yu = YAHOO.util;
var yue = yu.Event;
var yuel = yu.Element;
var yud = yu.Dom;
var yw = YAHOO.widget;
var yuc = yu.Connect;
var yus = yu.Selector;
var showPanel;

ydds.docs = function()
{
	
	showPanel = new yw.Panel("showDoc",
	{
		width : "800px",
		fixedcenter : true,
		constraintoviewport : true,
		modal : true,
		underlay : "none",
		close : true,
		visible : false,
		draggable : true
	});

	showPanel.render();
	showPanel.hide();

	var resize = new yu.Resize('showDoc',
	{
		handles : [ 'br' ],
		autoRatio : true,
		status : true,
		minWidth : 780,
		minHeight : 600
	});

	resize.on('resize', function(args)
	{
		var panelHeight = args.height;
		this.cfg.setProperty("height", panelHeight + "px");
		var getFrame = yud.get("embedpdf");
		yud.setStyle(getFrame, "height", (panelHeight - 60) + "px");
	}, showPanel, true);

};

function getDocToShow(url, name)
{
	var callback =
	{
		success : function(o)
		{
			var showDocContainer = yud.get("showPanelBody");
			showDocContainer.innerHTML = o.responseText;
			showPanel.show();
		},
		failure : function(o)
		{
			var showDocContainer = yud.get("showPanelBody");
			showDocContainer.innerHTML = 'Une erreur s\'est produite lors de la récupération du document. Ré-essayez plus tard ou veuillez contacter votre mairie. <br /> Erreur : ' + o.status + ' ' + o.statusText;
			showPanel.show();
		}
	};

	var baseUrl = zenexity.libredemat.baseUrl
	var changeUrl = baseUrl.split('/');
	changeUrl.pop();
	var newBaseUrl = changeUrl.join('/');

	var dd = yuc.asyncRequest("POST", newBaseUrl + "/showDoc", callback,
			"fileName=" + name + "&fileUrl=" + url);
}

function showDocPanel(el, title)
{
	var name = el.id.split('_http:')[0];
	var url = el.id.split('_http:')[1];
	url = "http:" + url;
	
	var showDocContainer = yud.get("showPanelBody");
	showDocContainer.innerHTML = "";
	var showPanelBodyHeader = yud.get("showPanelBodyHeader");
	showPanelBodyHeader.innerHTML = title + "-" + name;
	
	getDocToShow(url, name);
}

yue.onDOMReady(ydds.docs);
