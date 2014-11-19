class LegendTagLib {
	
	def legendService = {attrs, body -> // definition of tag for legend display
		
		def data = attrs.data
		def month = attrs.month
		def year = attrs.year
		def activityCode = attrs.activityCode
		def payment = attrs.payment
		def childId = attrs.childId
		def intermediateMapNoGroup = [:]
		def intermediateMap = [:]
		
		
		data.each {
			if(it.value.groupCode != null){
				if(!intermediateMap."${it.value.groupCode}") intermediateMap."${it.value.groupCode}" = [:]
				
				intermediateMap."${it.value.groupCode}".labelGroup = it.value.groupLabel 
				if(!intermediateMap."${it.value.groupCode}".code)
					intermediateMap."${it.value.groupCode}".code = [:]
				if(!intermediateMap."${it.value.groupCode}".code."${it.value.code}") 
					intermediateMap."${it.value.groupCode}".code."${it.value.code}" = [:]
				intermediateMap."${it.value.groupCode}".code."${it.value.code}".label = it.value.label
				intermediateMap."${it.value.groupCode}".code."${it.value.code}".color = it.value.color
			} else {
				if(!intermediateMapNoGroup."${it.value.code}") intermediateMapNoGroup."${it.value.code}" = [:]
				intermediateMapNoGroup."${it.value.code}".label = it.value.label
				intermediateMapNoGroup."${it.value.code}".color = it.value.color
			}
		}
		intermediateMapNoGroup.each{
			out << """
				<li>
				<input type="button" id="month-${it.key}-${month}-${year}-${childId}-${activityCode}-${it.value.label}-${payment}" value="&nbsp;&nbsp;" 
                     class="month" style="background-color: #${it.value.color}" />
              <span id="legend-${it.key}">
                ${it.value.label}
              </span>
			</li>
			"""			
		}
		
		intermediateMap.each{
			out << "<li>"
			out << """<h4>${it.value.labelGroup}</h4>"""
			out << "<ul class='under'>"
			it.value.code.each{ thisService ->
				out << """
				<li>
				<input type="button" id="month-${thisService.key}-${month}-${year}-${childId}-${activityCode}-${thisService.value.label}-${payment}" value="&nbsp;&nbsp;" 
                     class="month" style="background-color: #${thisService.value.color}" />
              <span id="legend-${thisService.key}">
                ${thisService.value.label}
              </span>
				</li>
				"""				
			}
			out << "</ul>"
			out << "</li>"
		}
		
	}
}
