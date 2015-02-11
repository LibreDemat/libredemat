zenexity.libredemat.tools.namespace('zenexity.libredemat.fong.requesttype');

(function(){

  var zct = zenexity.libredemat.tools;
  var zcc = zenexity.libredemat.common;
  var zcv = zenexity.libredemat.Validation;
  
  var zcf = zenexity.libredemat.fong;
  var zcfr = zcf.requesttype;
  
  var yl = YAHOO.lang;
  var yu = YAHOO.util;
  var yud = yu.Dom;
  var yue = YAHOO.util.Event;
  var yus = YAHOO.util.Selector;
  var ylj = YAHOO.lang.JSON;

  zcfr.ParkingPermitTemporaryRelocationRequest = function() {
	  
	  var getDate = function(dayAdded, date)
		{
			var d = new Date();
			if (date != null) 
			{
				var dates = date.split("/");
				d = new Date(dates[2] + "/" + dates[1]+ "/" + dates[0]);
			}
			var new_date = new Date(d.getTime() + (1000 * 60 * 60 * 24 * dayAdded));
			return new_date;
		};

		var getMinDate = function(target)
		{
			var classes = yud.getAttribute(target, "class");
			classes = classes.split(" ");
			var mindate;
			var added;
			for ( var i = 0; i < classes.length; i++)
			{
				if (classes[i].indexOf('mindayouvre_') > -1)
				{
					if (classes[i].split("_").length > 1)
					{
						added = classes[i].split("_")[1];
						mindate = getDate(added);
					}
				}
				else if (classes[i].indexOf('notBeforeDate_') > -1)
				{
					//var inputLabel = target.id.substring(0, target.id.indexOf("Show"));
					var inputLabel = classes[i].split("_")[1];
					if (inputLabel != undefined && inputLabel != "null" && yud.get(inputLabel) != null)
					{
						var inputValue = yud.get(inputLabel).value;
						if (inputValue != undefined && inputValue != "")
							mindate = getDate(0, inputValue); 
					}
				}
			}
			return mindate;
		};
		
		var isDisabled = function(target)
		{
			var classes = yud.getAttribute(target, "class");
			classes = classes.split(" ");
			for ( var i = 0; i < classes.length; i++)
			{
				if (classes[i].indexOf('disabledWith_') > -1)
				{
					if (classes[i].split("_").length > 1)
					{
						var inputLabel = classes[i].split("_")[1];
						if (inputLabel != undefined && inputLabel != "null" && yud.get(inputLabel) != null)
						{
							var inputValue = yud.get(inputLabel).value;
							if (inputValue != undefined && inputValue != "")
								return false;
							return true;
						}
					}
				}
			}
			return false;
		};
		
		var disabledFields = function()
		{
			var periodeEnd = yud.get("periodeEnd");
			var periodeEndShow = yud.get("periodeEndShow");
			if (isDisabled(periodeEndShow))
			{
				periodeEnd.disabled = true;
				yud.setStyle("periodeEndShow", "cursor", "default");
				periodeEndShow.disabled = true;
			}
			else
			{
				yue.on(periodeEndShow,'click',zcfr.ParkingPermitTemporaryRelocationRequest.processClickEnd,zcfr.ParkingPermitTemporaryRelocationRequest.processClickEnd,true);
				periodeEnd.disabled = false;
				yud.setStyle("periodeEndShow", "cursor", "pointer");
				periodeEndShow.disabled = false;
			}
		};
	  
    return {
      init : function()
      {
        yue.on(yud.get('periodeStartShow'),'click',zcfr.ParkingPermitTemporaryRelocationRequest.processClickStart,zcfr.ParkingPermitTemporaryRelocationRequest.processClickStart,true);
        //zcf.Calendar("periodeStart");
        //zcf.Calendar("periodeEnd");
	    disabledFields();
      },
      	/**
		 * @description The name of the method to call is the first part of the
		 *              clicked item's ID, except for new season creation
		 */
	      processClickStart : function(e)
	      {
	    	  // Pour les options voir le fichier calendar_fo.js
	    	  var target = yue.getTarget(e);
			  var options =
			  {
				  close : true,
				  title : "Date prévue du : ",
				  minDate : getMinDate(target)
			  };
			  zcf.Calendar("periodeStart", options, zcfr.ParkingPermitTemporaryRelocationRequest.callBack);
	      },
	      
      	processClickEnd : function(e)
		{
			// Pour les options voir le fichier calendar_fo.js
			var target = yue.getTarget(e);
			var options =
			{
				close : true,
				title : "Au : ",
				minDate : getMinDate(target)
			};				
			zcf.Calendar("periodeEnd", options, zcfr.ParkingPermitTemporaryRelocationRequest.callBack);
		},
	      
	      callBack : function()
	      {
	    	  disabledFields();
	      }
    }
  }();
  YAHOO.util.Event.onDOMReady(zcfr.ParkingPermitTemporaryRelocationRequest.init);
}());
