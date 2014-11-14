<ul>
<g:each in="${items}" var="item">
  <li class="cart"><g:dateFormat day="${item.day}" type="${item.dayType}" color="${item.color}" 
                    childId="${item.childId}" activityCode="${item.activityCode}" 
                    activityServiceCode="${item.activityServiceCode}" 
                    homeFolderId="${item.homeFolderId}" id="${item.id}"/>                    
                    <div>
                    <g:each in="${mapFirstName}">                    
                    	${(it.key.toLong() == item.childId.toLong())?('('+it.value+')'):('')}
                    </g:each>
                    </div>
  </li>
</g:each>
</ul>