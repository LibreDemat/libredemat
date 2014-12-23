zenexity.libredemat.tools.namespace("zenexity.libredemat.fong.reservation");
(function() {
	var zcf = zenexity.libredemat.fong;
	var zcfr = zcf.reservation;
	var zcc = zenexity.libredemat.common;
	var zct = zenexity.libredemat.tools;
	var yud = YAHOO.util.Dom;
	var yue = YAHOO.util.Event;
	var yus = YAHOO.util.Selector;
	var yw = YAHOO.widget;
	var ylj = YAHOO.lang.JSON;
	var yl = YAHOO.lang;
	var yu = YAHOO.util;

	zcfr.Reservation = function(){
		var panelDay = undefined;
		var panelMonth = undefined;
		var caddyBody = undefined;
		var panelCancel = undefined;
		var panelDetail = undefined;
		var panelWait = undefined;
		var panelError = undefined;
		var month = {'01':'Janvier', '02':'Février', '03':'Mars', '04':'Avril', '05':'Mai', '06':'Juin','07':'Juillet', 
				'08':'Août','09':'Septembre','10':'Octobre','11':'Novembre','12':'Décembre'};

		var stateChange = { // object to toggle state of day
				'Ouvert':'ReservationEnCours',
				'ReservationEnCours':'Ouvert',
				'ReservationValide':'ReportEnCours',
				'ReportEnCours':'ReservationValide'
		};

		var stateChangeLegend = { // object to générate label when toggle state
				'Ouvert':'legend-label-box-O',
				'ReservationEnCours':'legend-label-box-R',
				'ReservationValide':'legend-label-box',
				'ReportEnCours':'legend-label-box-A'
		};
		var stateChangeLegendDay = { // object to générate label when toggle state for day
				'Ouvert':'legend-label-box',
				'ReservationEnCours':'legend-label-box-R',
				'ReservationValide':'legend-label-box',
				'ReportEnCours':'legend-label-box-A'
		};
		
		var changeAssociation = {
				'unassociateItem':'associateItem',
				'associateItem':'unassociateItem'
		};
		var changeAssociationClass = {
				'associateItem':'unassociate',
				'unassociateItem':'associate'
		};
		var url = undefined;
		var reservationUrl = undefined;

		return {
			clickAssociationEvent : undefined,
			clickAssociationMonthEvent : undefined,
			clickEventDay : undefined,
			clickEventMonth : undefined,
			clickUpdateReservation : undefined,
			clickCancelReservation : undefined,
			clickDeleteItem : undefined,
			clickShowDetail : undefined,
			clickUpdateAndPay : undefined,
			init : function() { // function initialy load with page

				var path = zenexity.libredemat.baseUrl.split('/');
				path.pop();
				url = path.join("/");

				caddyBody = yud.get('planingActivity');

				panelDay = zcfr.Reservation.getPanel("dayPanel","350px",
						[ {text:"Valider", handler:zcfr.Reservation.handleYes, isDefault:true}, 
						  {text:"Annuler",  handler:zcfr.Reservation.handleNo} ]);
				panelDay.render();							

				panelMonth = zcfr.Reservation.getPanel("monthPanel","570px",
						[ {text:"Annuler",  handler:zcfr.Reservation.handleMonthNo} ]);
				panelMonth.render();

				panelMessage = zcfr.Reservation.getPanel("messagePanel","570px",[]);
				panelMessage.render();

				panelMessageAndPay = zcfr.Reservation.getPanel("messagePanelPay","570px",[]);
				panelMessageAndPay.render();

				panelCancel = zcfr.Reservation.getPanel("cancelPanel","350px", 
						[ {text:"Valider", handler:zcfr.Reservation.handleCancelYes, isDefault:true}, 
						  {text:"Annuler", handler:zcfr.Reservation.handleCancelNo} ]);
				panelCancel.render();
				
				panelError = zcfr.Reservation.getPanel("errorPanel","350px", 
						[ {text:"Fermer", handler:zcfr.Reservation.handleErrorClose, isDefault:true}]);
				panelError.render();

				panelDetail = zcfr.Reservation.getPanel("detailPanel", "500px", 
						[ {text:"Fermer", handler: zcfr.Reservation.handleDetailYes, isDefault:true} ])
						panelDetail.render();

				panelWait = zcfr.Reservation.getPanel("waitPanel", "200px",	[])
				panelWait.render();
								

				zcfr.Reservation.getPlaningCart(caddyBody.className, false, null, null); // get the planning cart

				zct.doAjaxCall(url + "/getCancelReservation", null, function(o){} ,true); // kill ciril reservation on refrexh page or open reservation in new window

				zcfr.Reservation.clickEventDay = new zct.Event(zcfr.Reservation, zcfr.Reservation.getHandlerDay);
				zcfr.Reservation.clickEventMonth = new zct.Event(zcfr.Reservation, zcfr.Reservation.getHandlerMonth);
				zcfr.Reservation.clickUpdateReservation = new zct.Event(zcfr.Reservation, zcfr.Reservation.getUpdateReservation);
				zcfr.Reservation.clickCancelReservation = new zct.Event(zcfr.Reservation, zcfr.Reservation.getCancelReservation);
				zcfr.Reservation.clickShowDetail = new zct.Event(zcfr.Reservation, zcfr.Reservation.getShowDetail);
				zcfr.Reservation.clickUpdateAndPay = new zct.Event(zcfr.Reservation, zcfr.Reservation.getUpdateAndPay);

				yue.on(yus.query('.day'),'click', zcfr.Reservation.clickEventDay.dispatch, zcfr.Reservation.clickEventDay,true);
				yue.on(yus.query('.month'),'click', zcfr.Reservation.clickEventMonth.dispatch, zcfr.Reservation.clickEventMonth,true);
				yue.on(yus.query('.update'),'click', zcfr.Reservation.clickUpdateReservation.dispatch, zcfr.Reservation.clickUpdateReservation, true);
				yue.on(yus.query('.cancel'),'click', zcfr.Reservation.clickCancelReservation.dispatch, zcfr.Reservation.clickCancelReservation, true);
				yue.on(yus.query('.detail'),'click', zcfr.Reservation.clickShowDetail.dispatch, zcfr.Reservation.clickShowDetail, true);
				yue.on(yus.query('.pay'),'click', zcfr.Reservation.clickUpdateAndPay.dispatch, zcfr.Reservation.clickUpdateAndPay, true);

			},

			getUpdateAndPay : function(e){
				var targetEl = yue.getTarget(e);
				var tokens = targetEl.id.split('-');
				var queryString = "homeFolderId="+tokens[1];
				panelMessageAndPay.setHeader("Message : ");
				panelMessageAndPay.setBody('<div class="wait"><img src="'+url+'/../../images/spinner.gif" class="spinner"/> patientez...</div>');
				zct.doAjaxCall(url + "/getUpdateReservationToPay?"+ queryString, null, function(o){
					panelMessageAndPay.setBody(o.responseText);
					var amount = yud.get('amountOrigin');
					if(amount.value <= 0){
						panelMessageAndPay.cfg.setProperty("buttons", [{text:"valider", handler:zcfr.Reservation.handleMessagePayYes, isDefault:true}, 
						  {text:"Retour planning", handler:zcfr.Reservation.handleMessagePayNo}])
					} else {
						panelMessageAndPay.cfg.setProperty("buttons", [{text:"Payer", handler:zcfr.Reservation.handleMessagePayYes, isDefault:true}, 
						                     						  {text:"Retour planning", handler:zcfr.Reservation.handleMessagePayNo}])
					}
					
				} ,true);
				panelMessageAndPay.show();
			},

			handleMessagePayYes : function(){
				var form = yud.get('payForm');
				var amount = yud.get('amountOrigin');
				var queryString = "amount="+amount.value;
				zct.doAjaxCall(url + "/getAmountVerification?"+queryString, null,function(o){
					var response = ylj.parse(o.responseText);
					if(response.result.label == "OK"){
						form.submit();
					} else {						
						panelMessageAndPay.hide();						
						panelError.setHeader("Message d'erreur");
						var text = response.result.label
						if(response.result.amountExpected > 0){
							var number = response.result.amountExpected.toString();
							text += "<br/>Montant attendu : " +number.substring(0,number.length -2) + "," + number.substring(number.length -2);+ " eur"
						}
						panelError.setBody(text)
						panelError.show();
						
					}
				} ,true);
				
			},
			
			handleErrorClose : function(){
				this.hide();
			},

			handleMessagePayNo : function(){
				panelMessageAndPay.hide();
				var reservationRefuse = yud.getElementsByClassName("refuse");
				for(var i=0; i < reservationRefuse.length; i++){
					var queryString = "parametters="+reservationRefuse[i].id;
					zct.doAjaxCall(url + "/getDeleteRefuse?"+queryString, null,function(o){
						var response = ylj.parse(o.responseText);
						zcfr.Reservation.getPlaningCart(caddyBody.className, true, response.activityCode, response.childId);

					} ,true);                                    
				}							
				zct.doAjaxCall(url + "/getCancelReservation", null, function(o){} ,true);	
			},

			getShowDetail: function(e){ // display detail of cart
				var targetEl = yue.getTarget(e);
				var tokens = targetEl.id.split('-');
				panelDetail.setHeader("D&eacute;tail du panier");
				panelDetail.setBody('<div class="wait"><img src="'+url+'/../../images/spinner.gif" class="spinner"/> patientez...</div>');
				var queryString = "homeFolderId="+tokens[1];
				zct.doAjaxCall(url + "/getDetailShow?"+ queryString, null, function(o){
					panelDetail.setBody(o.responseText);
				},true);
				panelDetail.show()
			},

			handleDetailYes : function(){ // close detail of cart
				this.hide();
			},

			getPanel : function(id, widthSize, button){
				var pan = new yw.SimpleDialog( // dialog box for month reservation
						id,
						{width: widthSize, zindex : 1000,
							visible: false,
							fixedcenter: true, 
							modal: true,
							constraintoviewport: true, draggable: true,
							underlay: "shadow", 
							close: false,
							buttons: button 
						});
				return pan;
			},

			getDeleteItem : function(e){ // delete one iem from cart
				var targetEl = yue.getTarget(e);
				var tokens = targetEl.id.split('-');				
				var queryString = "itemId="+tokens[1];
				zct.doAjaxCall(url + "/getDeleteItemReservation?"+ queryString, null, function(o){
					var response = ylj.parse(o.responseText);
					zcfr.Reservation.getPlaningCart(caddyBody.className, true, response.activityCode, response.childId);					
				},true);
			},

			getCancelReservation : function(e){ // delete all reservation from cart
				var targetEl = yue.getTarget(e);
				var tokens = targetEl.id.split('-');
				panelCancel.setHeader("Effacer le panier");
				panelCancel.setBody("&Ecirc;tes-vous sûr ?");
				panelCancel.setBody("&Ecirc;tes-vous s&ucirc;r(e) ?");
				panelCancel.show();
			},

			getUpdateReservation: function(e){ // call to validate reservation
				var targetEl = yue.getTarget(e);
				var tokens = targetEl.id.split('-');
				var queryString = "homeFolderId="+tokens[1];
				panelMessage.setHeader("Message : ");
				panelMessage.setBody('<div class="wait"><img src="'+url+'/../../images/spinner.gif" class="spinner"/> patientez...</div>');
				zct.doAjaxCall(url + "/getUpdateReservation?"+ queryString, null, function(o){
					var message = "";
					var response = ylj.parse(o.responseText);
					message = response.result.message;
					zct.doAjaxCall(url + "/getCleanReservation?"+ queryString, null, function(o){
						var rep = o.responseText;
					},true);
					panelMessage.setBody(message);
					panelMessage.cfg.setProperty("buttons", [{text:"Ok", handler:zcfr.Reservation.handleMessageYes, isDefault:true}]);
				} ,true);
				panelMessage.show();
			},

			getHandlerDay : function(e) { // method generate Day panel and ajax call of possible activity service
				var targetEl = yue.getTarget(e);
				var tokens = targetEl.id.split('-');
				var dateSplit = tokens[1].split('/');
				panelDay.setBody('<div class="wait"><img src="'+url+'/../../images/spinner.gif" class="spinner"/> patientez...</div>');
				var queryString = "day="+dateSplit[2]+"&month="+dateSplit[1]+"&year="+dateSplit[0]+"&childId="+tokens[2]+"&activityCode="+tokens[3];
				panelDay.setHeader("le " + dateSplit[2] + " " + month[dateSplit[1]] + " " + dateSplit[0]);		          
				zct.doAjaxCall(url + "/getDayActivityService?"+ queryString, null, function(o){
					panelDay.setBody(o.responseText);
					zcfr.Reservation.clickAssociationEvent = new zct.Event(zcfr.Reservation, zcfr.Reservation.getAssociate);
					yue.on(yus.query('.link'),'click',zcfr.Reservation.clickAssociationEvent.dispatch, zcfr.Reservation.clickAssociationEvent,true);
				} ,true);
				panelDay.show();		          
			},

			getHandlerMonth : function(e){ // method generate Mont panel and ajax call of possible activity service
				var targetEl = yue.getTarget(e);
				var tokens = targetEl.id.split('-');
				var label = yud.get('legend-'+tokens[1]);
				var payment = tokens[7];
				panelMonth.setHeader(label.innerHTML);
				panelMonth.setBody('<div class="wait"><img src="'+url+'/../../images/spinner.gif" class="spinner"/> patientez...</div>');
				var queryString = "month="+tokens[2]+"&year="+tokens[3]+"&childId="+
				tokens[4]+"&activityCode="+tokens[5]+"&activityServiceCode="+encodeURIComponent(tokens[1])+"&payment="+payment
				zct.doAjaxCall(url + "/getMonthlyActivityServices?"+ queryString, null, function(o){
					panelMonth.setBody(o.responseText);
					var inputFromMonth = yud.getElementsByClassName("dataMonth", 'input');
					for(var i= 0; i < inputFromMonth.length; i++){
						zcfr.Reservation.getMonthToggleIncompatible(inputFromMonth[i].id, inputFromMonth[i].value);
					}
					zcfr.Reservation.clickAssociationMonthEvent = new zct.Event(zcfr.Reservation, 
							zcfr.Reservation.getAssociateMonth);
					yue.on(yus.query('.linkMonth'),'click',zcfr.Reservation.clickAssociationMonthEvent.dispatch, 
							zcfr.Reservation.clickAssociationMonthEvent,true);
					yue.on('linkAll','click', zcfr.Reservation.cocheAll, zcfr.Reservation.cocheAll, true);
					yue.on('allReservation','click', zcfr.Reservation.reserveAll, zcfr.Reservation.reserveAll, true);
					yue.on('monthReservation','click', zcfr.Reservation.handleMonthYes, zcfr.Reservation.handleMonthYes, true);
				} ,true);
				panelMonth.show();				
			},
			
			

			getAssociate : function(e){ //method to change state of services for a day
				var targetEl = yue.getTarget(e);
				var tokens = targetEl.id.split('-');
				if(tokens[0] == "unassociateItem"){
					tokens[0] = "associateItem";
					yud.removeClass(targetEl, 'unassociate');
					yud.addClass(targetEl, 'associate');

				} else {
					tokens[0] = "unassociateItem";
					yud.removeClass(targetEl, 'associate');
					yud.addClass(targetEl, 'unassociate');
				}
				if(tokens[3]){
					var incompatible = tokens[3].split('µ');
					for(var t = 0; t < incompatible.length; t++){
						if( tokens[0] == 'associateItem'){
							zcfr.Reservation.getDayToggleIncompatible(tokens[1], tokens[0], incompatible[t]);
						}
					}
				}
				targetEl.id = tokens.join('-');
				var previous = yud.getPreviousSibling(targetEl.id);
				var state = previous.id.split('-');
				state[1] = stateChange[state[1]];
				var inputHidden = yud.get(state[0]);
				var changeInput = inputHidden.value.split('-');
				changeInput[4] = stateChange[changeInput[4]];
				inputHidden.value = changeInput.join('-');
				previous.id = state.join('-');
				var theClass = previous.className;
				yud.removeClass(previous, theClass);
				yud.addClass(previous, stateChangeLegendDay[state[1]]);
			},

			getDayToggleIncompatible: function(nodeToKeep, association, nodeToChange){ // method to control incompatibiliy between services in day
				var liBox = yud.getElementsByClassName('day-box', 'li');
				for(var i= 0; i<liBox.length; i++){                            
					var children = yud.getChildren(liBox[i]);
					for(var h=0; h<children.length; h++){
						if(children[0].id.split('-')[0] == nodeToChange){
							if(children[1].id.split('-')[0] == association){                                        
								children[0].id = children[0].id.split('-')[0] + '-' + stateChange[children[0].id.split('-')[1]];
								children[0].className = stateChangeLegendDay[children[0].id.split('-')[1]];
								var aSplit = children[1].id.split('-');
								var classAfter = changeAssociationClass[aSplit[0]]; 
								aSplit[0] = changeAssociation[aSplit[0]];
								var classBefore = changeAssociationClass[aSplit[0]];
								yud.removeClass(children[1],classBefore);
								yud.addClass(children[1],classAfter);
								children[1].id = aSplit.join('-');
								var childValue = children[2].value.split('-');
								childValue[4] = stateChange[childValue[4]];
								children[2].value = childValue.join('-');                                        
							}                                    
							break;
						}
					}
				}
			},
			
			reserveAll : function(e) {
				
				panelMessage.setHeader("Message : ");
				panelMessage.setBody('<div class="wait"><img src="'+url+'/../../images/spinner.gif" class="spinner"/> patientez...</div>');
				
				zct.doAjaxFormSubmitCall('allReservationForm', null, function(o){
					message = o.responseText;
					panelMessage.setBody(message);
					panelMessage.cfg.setProperty("buttons", [{text:"Ok", handler:zcfr.Reservation.handleMessageYes, isDefault:true}]);
					
					
					panelMessageAndPay.setBody(o.responseText);
					var amount = yud.get('amountOrigin');
					if(amount.value <= 0){
						panelMessageAndPay.cfg.setProperty("buttons", [{text:"valider", handler:zcfr.Reservation.handleMessagePayYes, isDefault:true}, 
						  {text:"Retour planning", handler:zcfr.Reservation.handleMessagePayNo}])
					} else {
						panelMessageAndPay.cfg.setProperty("buttons", [{text:"Payer", handler:zcfr.Reservation.handleMessagePayYes, isDefault:true}, 
						                     						  {text:"Retour planning", handler:zcfr.Reservation.handleMessagePayNo}])
					}
					
				}, false);
				panelMessage.show();
			},
			
			reserveAllAndPay : function(e) {
				
				panelMessage.setHeader("Message : ");
				panelMessage.setBody('<div class="wait"><img src="'+url+'/../../images/spinner.gif" class="spinner"/> patientez...</div>');
				
				zct.doAjaxFormSubmitCall('allReservationForm', null, function(o){
					message = o.responseText;
					panelMessage.setBody(message);
					panelMessage.cfg.setProperty("buttons", [{text:"Ok", handler:zcfr.Reservation.handleMessageYes, isDefault:true}]);
					
					
					panelMessageAndPay.setBody(o.responseText);
					var amount = yud.get('amountOrigin');
					if(amount.value <= 0){
						panelMessageAndPay.cfg.setProperty("buttons", [{text:"valider", handler:zcfr.Reservation.handleMessagePayYes, isDefault:true}, 
						  {text:"Retour planning", handler:zcfr.Reservation.handleMessagePayNo}])
					} else {
						panelMessageAndPay.cfg.setProperty("buttons", [{text:"Payer", handler:zcfr.Reservation.handleMessagePayYes, isDefault:true}, 
						                     						  {text:"Retour planning", handler:zcfr.Reservation.handleMessagePayNo}])
					}
					
				}, false);
				panelMessage.show();
			},

			
			cocheAll : function(e){
				var targetEl = yud.get('linkAll');
				if (yud.hasClass(targetEl, 'unassociateCheckBox'))
				{
					// check all
					yud.removeClass(targetEl, 'unassociateCheckBox');
					yud.addClass(targetEl, 'associateCheckBox');
					
					var elems = document.getElementsByTagName('a');
				    for (var i = 0; i< elems.length;i++) 
				    {
				    	targetEl = elems[i];
				        if (yud.hasClass(targetEl, 'unassociate')) 
				        {
				        	var tokens = targetEl.id.split('-');  
				        	tokens[0] = "associateItem";
				        	yud.removeClass(targetEl, 'unassociate');
							yud.addClass(targetEl, 'associate');
				        	var previousId = tokens[1] + '-' + tokens[2];
							var previous = yud.get(previousId.toString());
							tokens[3] = stateChange[tokens[3]];
							targetEl.id = tokens.join('-');                        
							var inputHidden = yud.get('monthly-'+tokens[2]);                       
							var changeInput = inputHidden.value.split('-');
							changeInput[4] = stateChange[changeInput[4]];                        
							inputHidden.value = changeInput.join('-');
							zcfr.Reservation.getMonthToggleIncompatible(inputHidden.id, inputHidden.value);
							if (previous != undefined)
							{
								var theClass = previous.className;
								yud.removeClass(previous, theClass);
								yud.addClass(previous, stateChangeLegend[tokens[3]]);   
							}
				        }
				    }
				}
				else
				{
					// decheck all
					yud.removeClass(targetEl, 'associateCheckBox');
					yud.addClass(targetEl, 'unassociateCheckBox');
					
					var elems = document.getElementsByTagName('a');
				    for (var i = 0; i< elems.length;i++) 
				    {
				    	targetEl = elems[i];
				        if (yud.hasClass(targetEl, 'associate')) 
				        {
				        	var tokens = targetEl.id.split('-');  
				        	tokens[0] = "unassociateItem";
				        	yud.removeClass(targetEl, 'associate');
							yud.addClass(targetEl, 'unassociate');
				        	var previousId = tokens[1] + '-' + tokens[2];
							var previous = yud.get(previousId.toString());
							tokens[3] = stateChange[tokens[3]];
							targetEl.id = tokens.join('-');                        
							var inputHidden = yud.get('monthly-'+tokens[2]);                       
							var changeInput = inputHidden.value.split('-');
							changeInput[4] = stateChange[changeInput[4]];                        
							inputHidden.value = changeInput.join('-');
							zcfr.Reservation.getMonthToggleIncompatible(inputHidden.id, inputHidden.value);
							if (previous != undefined)
							{
								var theClass = previous.className;
								yud.removeClass(previous, theClass);
								yud.addClass(previous, stateChangeLegend[tokens[3]]);   
							}  
				        }
				    }
				}
			},

			getAssociateMonth : function(e){ //method to change state of services for a month
				var targetEl = yue.getTarget(e);
				var tokens = targetEl.id.split('-');                        
				if(tokens[0] == "unassociateItem"){
					tokens[0] = "associateItem";
					yud.removeClass(targetEl, 'unassociate');
					yud.addClass(targetEl, 'associate');		        			        			        	
				} else {
					tokens[0] = "unassociateItem";
					yud.removeClass(targetEl, 'associate');
					yud.addClass(targetEl, 'unassociate');		        	        	
				}
				var previousId = tokens[1] + '-' + tokens[2];
				var previous = yud.get(previousId.toString());
				tokens[3] = stateChange[tokens[3]];
				targetEl.id = tokens.join('-');                        
				var inputHidden = yud.get('monthly-'+tokens[2]);                       
				var changeInput = inputHidden.value.split('-');
				changeInput[4] = stateChange[changeInput[4]];                        
				inputHidden.value = changeInput.join('-');
				zcfr.Reservation.getMonthToggleIncompatible(inputHidden.id, inputHidden.value);
				var theClass = previous.className;
				yud.removeClass(previous, theClass);
				yud.addClass(previous, stateChangeLegend[tokens[3]]);   	
			},

			getMonthToggleIncompatible: function(id, value){ // method to controle imcompatibility between services in month
				var splitHidden = value.split("-");                        
				var cellFromPlanning = yud.get(splitHidden[3]);
				var cellChildren = yud.getChildren(cellFromPlanning);
				var newArray = [];
				for(var i = 0; i<cellChildren.length;i++){
					var spanColor = cellChildren[i].id;
					if(splitHidden[7].length > 1){
						var incompatible = splitHidden[7].split('µ');					
						for(var u=0; u<incompatible.length;u++){						
							if(incompatible[u].split("|")[1].toLowerCase() == spanColor.toLowerCase()){
								newArray.push(incompatible[u]);
							}
						}
					}
				}
				var newIncompatible = newArray.join('µ');
				splitHidden[7] = newIncompatible;
				var newInput = yud.get(id);
				newInput.value = splitHidden.join("-");
			},

			handleYes : function() { // yes button from day panel
				zct.doAjaxFormSubmitCall('dayForm', null, function(o){
					var response = ylj.parse(o.responseText);
					zcfr.Reservation.getPlaningCart(response.homeFolder, true, response.activityCode, response.childId);
				}, false);

				this.hide();
			},

			handleCancelYes : function(){ // yes button on cancel reservation panel
				var activityCode = yud.get('cActivityCode');
				var childId = yud.get('cChildId');
				var queryString = "homeFolderId="+ caddyBody.className;			
				zct.doAjaxCall(url + "/getCleanReservation?"+ queryString, null, function(o){
					var rep =o.responseText;
					zcfr.Reservation.getPlaningCart(caddyBody.className, true, activityCode.value, childId.value);
				},true);


				this.hide();
			},

			handleCancelNo : function(){ //no button on cancel reservation panel
				this.hide();
			},

			handleMessageYes : function(){ // ok button on message panel
				this.hide();
				var year = yud.get('cYear');
				panelWait.setHeader('Patientez...');
				panelWait.setBody('<div class="wait"><img src="'+url+'/../../images/spinner.gif" class="spinner"/> patientez...</div>');
				panelWait.show();
				var month = yud.get('cMonth');
				var activityCode = yud.get('cActivityCode');
				var childId = yud.get('cChildId');
				window.location = url +"/details?month="+month.value+
				"&year="+year.value+"&activityCode="+activityCode.value+"&childId="+childId.value;
			},

			handleNo : function() { // cancel button on day panel
				this.hide();
			},

			handleMonthYes : function() {
				
				zct.doAjaxFormSubmitCall('monthForm', null, function(o){
					var response = ylj.parse(o.responseText);
					zcfr.Reservation.getPlaningCart(response.homeFolder, true, response.activityCode, response.childId);
				}, false);
				panelMonth.hide();
			},

			handleMonthNo : function() {
				panelMonth.hide();
			}, 

			getPlaningCart : function(val, noAdd, ac, ci) { // method to fill the cart div
				// Ajout du timestamp dans l'appel ajax pour ne pas que le navigateur garde en cache le retour (Bug sur IE 11, le panier n'était pas mis à jour lorsqu'on ajoutait une réservation, il fallait faire F5)
				d = new Date(); 
				zct.doAjaxCall(url + "/getPlanningCart?homeFolderId="+val+"&d="+d.getTime(), null, function(o){
					caddyBody.innerHTML = o.responseText;
					zcfr.Reservation.clickDeleteItem = new zct.Event(zcfr.Reservation, zcfr.Reservation.getDeleteItem);
					yue.on(yus.query('.deleteItem'),'click', zcfr.Reservation.clickDeleteItem.dispatch, zcfr.Reservation.clickDeleteItem,true);
					if(noAdd != false) {
						zct.doAjaxCall(url + "/getPlanningCartToJson?homeFolderId="+val+"&d="+d.getTime(), null, function(o){
							var result = ylj.parse(o.responseText);                                  
							var data = result.result
							for(var i=0; i < data.length; i++){
								if(data[i].activityCode == ac && data[i].childId == ci){
									var day = data[i].day;
									var cell = yud.get(day); // get the container div
									if(cell != null){
										var cellChildren = yud.getChildren(cell); // get childs of container
										var test = false;
										for(var u = 0; u < cellChildren.length; u++){										
											if(cellChildren[u].id.toLowerCase() == data[i].color.toLowerCase()){ // if exist e span with the same color
												if(cellChildren[u].className != stateChangeLegend[data[i].dayType] 
												&& stateChangeLegend[data[i].dayType] != undefined){ // if class is different
													yud.removeClass(cellChildren[u], cellChildren[u].className);
													yud.addClass(cellChildren[u], stateChangeLegend[data[i].dayType]);                                                    
												} 
												test = true;
												break;
											}                                            
										} 
										if(test == false) {											
											var newElement = document.createElement('span');
											newElement.id = data[i].color;
											newElement.style.backgroundColor =  '#' + data[i].color;
											newElement.className = stateChangeLegend[data[i].dayType];
											cell.appendChild(newElement);
										}
									}
								}
							}
							// special case not more in cart
							var allReservationCells = yud.getElementsByClassName('legend-label-box-R', 'span');
							for(var z=0; z < allReservationCells.length; z++){
								var cellContainer = yud.getAncestorByClassName(allReservationCells[z], 'container');
								if(cellContainer != null){
									var isInArray = false;
									for(var i=0; i < data.length; i++){
										if(data[i].day == cellContainer.id){												
											var spanColor = allReservationCells[z];
											if(spanColor.id.toLowerCase() == data[i].color.toLowerCase()){													
												isInArray = true;
												break;
											}
										}                              
									}
									if(isInArray == false){
										cellContainer.removeChild(allReservationCells[z]);                 
									}
								}
							}
							var allReportCells = yud.getElementsByClassName('legend-label-box-A', 'span');
							for(var y=0; y < allReportCells.length; y++){
								var cellContainer = yud.getAncestorByClassName(allReportCells[y], 'container');
								if(cellContainer != null){
									var isInArray = false;
									for(var i=0; i < data.length; i++){
										if(data[i].day == cellContainer.id){
											var spanColor = allReportCells[y];
											if(spanColor.id.toLowerCase() == data[i].color.toLowerCase()){
												isInArray = true;
												break;
											}
										}
									}
									if(isInArray == false){										
										yud.removeClass(allReportCells[y], 'legend-label-box-A');
										yud.addClass(allReportCells[y], 'legend-label-box');
									}
								}
							}
						},true);
					}
				},true);
			}
		};
	}();
	yue.onDOMReady(zcfr.Reservation.init);	
}());
