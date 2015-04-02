import java.text.SimpleDateFormat
import java.util.Locale

class PlanningTagLib {
	def styles = [:]
	def calendar =  Calendar.instance

	def activityPlanning = {attrs,body ->  // tag lib to render planning
		def year = attrs.year ? attrs.year : Calendar.instance.get(Calendar.YEAR) //get the year of define current year
		def month = attrs.month ? Integer.valueOf(attrs.month) - 1 : (Calendar.instance.get(Calendar.MONTH)) //get the year of define current month
		def data = attrs.data, i = 0
		def deadLine = attrs.deadLine 											// get the deadline
		def childId = attrs.childId												// get the child id
		def activityCode = attrs.activityCode 									// get the activity code

		def dl = new SimpleDateFormat("yyyy-MM-dd").parse(deadLine)				// format deadline
		dl = dl+1																//we need to increase day if dealine to exclude it from the limit
		calendar.setMinimalDaysInFirstWeek(1)	
		calendar.setFirstDayOfWeek(Calendar.MONDAY)
		calendar.set(Calendar.DATE, 1)
		calendar.set(Calendar.ERA, GregorianCalendar.AD)
		calendar.set(Calendar.YEAR, Integer.valueOf(year))
		calendar.set(Calendar.MONTH, Integer.valueOf(month))

		def cal1 = [:], cal2 = [:]
		for(Integer num : (1..6)) cal1[num] = [1,2,3,4,5,6,7]
		/**
		 * we contruct a map cal1 with index on week and each week get seven days exemple : cal1[1]=[1,2,3,4,5,6,7]
		 * we contruct a map cal2 with index on week and each week get the number of the days exmple cal2[1]=[5,6,7,8,9,10,11]
		 */
		for(Integer day : (1 .. calendar.getActualMaximum(Calendar.DAY_OF_MONTH))) {
			calendar.set(Calendar.DATE, day)
			def wom = calendar.get(Calendar.WEEK_OF_MONTH)
			def dom = calendar.get(Calendar.DAY_OF_MONTH)

			if(!cal2[wom]) cal2[wom] = []
			cal2[wom].add(dom)
		}

		calendar.set(Calendar.DATE, 1)

		def table = '' 															// begin to buid the table tag
		for(Integer wom : cal1.keySet()) { 										// parse cal 1
			if(cal2[wom]) {														// find index cal1 in call2 
				def tr = ''														// begin a line				
				for(Integer row : cal1[wom]) {									// get from call1 element
					def index = cal1[wom].indexOf(row)							// use it as index

					if(cal2[wom][index]) tr += this.buildCell(cal2[wom][index], data, dl, activityCode, childId) // build cell from the number of the day get in cell2
					else if(wom == 1) tr = "\t\t<td></td>\n" + tr 				// empty cell for week one
					else tr += "\t\t<td></td>\n"								// empty cell
				}
				table += "\t<tr>\n$tr\n\t</tr>\n"
			}
		}
		table = """
<table class="activity-calendar">
  <thead>
\t<tr>
\t\t<th>${message(code:'header.monday')}</th>
\t\t<th>${message(code:'header.tuesday')}</th>
\t\t<th>${message(code:'header.wednesday')}</th>
\t\t<th>${message(code:'header.thursday')}</th>
\t\t<th>${message(code:'header.friday')}</th>
\t\t<th>${message(code:'header.saturday')}</th>
\t\t<th>${message(code:'header.sunday')}</th>
\t</tr>
  </thead>
  <tbody>
$table
  </tbody>
</table>
"""
		out << table
	}

	protected String buildCell(Integer dom, Map data, Date dl, String activityCode, Long childId) {  // method to buil cell of planning
		def c = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, dom)
		def result = ""
		def listDate = []															// we prepare an array to stock activity date from busines soft 
		data.each { activity ->														// we parse data from business soft		
			activity.value.days.each{
				Date d3 = new SimpleDateFormat("yyyy-MM-dd").parse(it.key)			// format the activity date
				c.setTime(d3)
				if(c.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)){			// if the month is the good one
					def dd = c.get(Calendar.DAY_OF_MONTH)	
					listDate.add(dd)												// adding date in arraye
				}
			}
		}
		def dateRegistered = listDate.contains(calendar.get(Calendar.DAY_OF_MONTH)) // test if the date of cell is in the array
		if(dateRegistered != false){												// if it is => this cell is clickable
			result += '\t\t<td>'
		} else {
			result += '\t\t<td class="notRegistered">'								// else the cell is desactived
		}

		// to do ask if deadline is include or exclude
		if( dl.after(calendar.getTime()) ||
		!listDate.contains(calendar.get(Calendar.DAY_OF_MONTH))   ) { // test if day is after dead line or not in registered day
			result +=  '<span class="valueBefore">' + dom + '</span><div class="container">'
		} else {
			result +=  '<span class="value day" id="day-'+ String.format(Locale.FRANCE, '%tY/%<tm/%<td', calendar) +'-'+ childId +'-' + activityCode.encodeAsURL() + '">' +
					dom + '</span><div class="container" id="'+  String.format(Locale.FRANCE, '%tY/%<tm/%<td', calendar) +'">'
		}

		data.each { activity ->
			def activityLabel = activity.key
			activity.value.days.each{
				if(it.value.toString() != "Ouvert"){
					Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse(it.key)
					c.setTime(d2)
					if(c.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)
					&& c.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
					&& c.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) { // for each activity service exist in business soft data and is not 'Ouvert'
						if(it.value.toString().equals("ReservationValide"))
							result += '<span class="legend-label-box" id="'+ activity.value.color+'" style="background-color: #'+ activity.value.color+';">&nbsp;</span>'
						else  if(it.value.toString().equals("ReservationEnCours"))
							result += '<span class="legend-label-box-R" id="'+ activity.value.color+'" style="background-color: #'+ activity.value.color+';">&nbsp;</span>'
						else if(it.value.toString().equals("ReportEnCours"))
							result += '<span class="legend-label-box-A" id="'+ activity.value.color+'" style="background-color: #'+ activity.value.color+';">&nbsp;</span>'
						return
					}
				}
			}
		}

		return result + "</div></td>\n";
	}
}
