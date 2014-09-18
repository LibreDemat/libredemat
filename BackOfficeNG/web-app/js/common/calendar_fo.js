zenexity.libredemat.tools.namespace('zenexity.libredemat.fong');
(function()
{
	var zcb = zenexity.libredemat.fong;
	var zct = zenexity.libredemat.tools;
	var yud = YAHOO.util.Dom;
	var yue = YAHOO.util.Event;
	var options =
	{
		close : true
	};
	zcb.Calendar = function(label, newOptions, callback)
	{
		var field = yud.get(label);
		var container = yud.get(label + "CalContainer");
		var trigger = yud.get(label + "Show");
		if (newOptions != undefined) options = newOptions;
		var callBackUpdate = callback;
		var show = function()
		{
			update();
			yud.setStyle(container, 'display', 'block');
			yud.setStyle(container, 'z-index', '10');
			var pos = yud.getXY(trigger);
			pos[1] += trigger.offsetHeight + 1;
			yud.setXY(container, pos);
		};
		var fill = function(type, args, obj)
		{
			var year = args[0][0][0];
			var month = args[0][0][1];
			if (month < 10) month = '0' + month;
			var day = args[0][0][2];
			if (day < 10) day = '0' + day;
			var textValue = [ day, month, year ].join('/');
			if (Date != null && zct.isFunction(Date.parseExact))
			{
				textValue = Date.parseExact(textValue, "dd/MM/yyyy").toString(
						Date.CultureInfo.formatPatterns.shortDate);
			}
			zct.val(field, textValue);
			cal.hide();
			if (callBackUpdate != undefined) callBackUpdate.call();
		};
		var update = function()
		{
			var textValue = zct.val(field);
			if (Date != null && zct.isFunction(Date.parse))
			{
				var date = Date.parse(textValue);
				if (date)
				{
					textValue = date.toString("dd/MM/yyyy");
					cal.setMonth(parseInt(date.toString("MM"), 10) - 1);
					cal.setYear(parseInt(date.toString("yyyy"), 10));
				}
			}
			if (textValue && textValue != "")
			{
				cal.select(textValue);
				cal.render();
			}
			if (callBackUpdate != undefined) callBackUpdate.call();
		};
		
		var cal = new YAHOO.widget.Calendar(label + "Cal", container, options);
		
		// Correct formats for Germany: dd.mm.yyyy, dd.mm, mm.yyyy
		cal.cfg.setProperty("DATE_FIELD_DELIMITER", "/");
		 
		cal.cfg.setProperty("MDY_DAY_POSITION", 1);
		cal.cfg.setProperty("MDY_MONTH_POSITION", 2);
		cal.cfg.setProperty("MDY_YEAR_POSITION", 3);
		 
		cal.cfg.setProperty("MD_DAY_POSITION", 1);
		cal.cfg.setProperty("MD_MONTH_POSITION", 2);
		 
		// Date labels for German locale
		cal.cfg.setProperty("MONTHS_SHORT",   ['Jan','Fév','Mar','Avr','Mai','Jun','Jul','Aoû','Sep','Oct','Nov','Déc']);
		cal.cfg.setProperty("MONTHS_LONG",    ['Janvier','Février','Mars','Avril','Mai','Juin','Juillet','Août','Septembre','Octobre','Novembre','Décembre']);
		cal.cfg.setProperty("WEEKDAYS_1CHAR", ["D", "L", "M", "M", "J", "V", "S"]);
		cal.cfg.setProperty("WEEKDAYS_SHORT", ['Di','Lu','Ma','Me','Je','Ve','Sa']);
		cal.cfg.setProperty("WEEKDAYS_MEDIUM",['Dim','Lun','Mar','Mer','Jeu','Ven','Sam']);
		cal.cfg.setProperty("WEEKDAYS_LONG",  ['Dimanche','Lundi','Mardi','Mercredi','Jeudi','Vendredi','Samedi']);
		 
		// Start the week on a Monday (Sunday == 0)
		cal.cfg.setProperty("START_WEEKDAY",  1);
		
		// navigator
		cal.cfg.setProperty("navigator",  true);
		
		cal.selectEvent.subscribe(fill, cal, true);
		update();
		cal.render();
		show();
		//yue.on(trigger, "click", show);
	}
}());
