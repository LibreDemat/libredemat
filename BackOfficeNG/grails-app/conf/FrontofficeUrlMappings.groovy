class FrontofficeUrlMappings {
  static mappings = {

    "/frontoffice/requestType/$action?/$id?" (controller : "frontofficeRequestType" )
    "/frontoffice/activity/$action?/$id?" (controller : "frontofficeActivity" )
    "/frontoffice/payment/$action?/$id?" (controller : "frontofficePayment" )
    "/frontoffice/document/$action?/$id?" (controller : "frontofficeDocument" )
    "/frontoffice/request/$action?/$id?" (controller : "frontofficeRequest" )
    "/frontoffice/requestDocument/$action?/$id?" (controller : "frontofficeRequestDocument" )
    "/frontoffice/home/$action?/$id?" (controller : "frontofficeHome" )
    "/frontoffice/homeFolder/$action?/$id?" (controller : "frontofficeHomeFolder" )
    "/frontoffice/homeFolderDocument/$action?/$id?" (controller : "frontofficeHomeFolderDocument" )
    "/frontoffice/ticketBooking/$action?/$id?" (controller : "frontofficeTicketBooking" )
    "/frontoffice/technocarte/$action/$id?" (controller : "frontofficeTechnocarte")
    "/frontoffice/ciril/$action/$id?" (controller : "frontofficeCiril")
    "/frontoffice/planning/$action?" (controller : "frontofficePlanning")
    "/frontoffice/reservation/$action?/$id?" (controller : "frontofficeReservation" )
    "/frontoffice/requestContact/viewAttachment/$id?"(controller : "frontofficeRequestContact" ){action = [GET : "viewAttachment"]}
    "/frontoffice/requestContact/view/$id?"(controller : "frontofficeRequestContact" ){action = [GET : "view"]}
    "/frontoffice/requestContact/$action/$id?"(controller : "frontofficeRequestContact" ){action = [GET : "reply", POST : "reply"]}
  }
}
