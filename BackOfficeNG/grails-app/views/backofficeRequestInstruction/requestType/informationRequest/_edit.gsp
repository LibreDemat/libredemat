


<div id="requestData" class="yellow-yui-tabview">
  <ul class="yui-nav">
  
    <li class="selected ">
      <a href="#page0"><em><g:message code="ir.step.reason.label" /></em></a>
    </li>
  
  </ul>
   
  <div class="yui-content">
    
      
      <!-- step start -->
      <div id="page0">
        <h2><g:message code="property.form" />
          <span><g:message code="ir.step.reason.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <dl>
                <dt class="required">${message(code:'ir.property.motive.label')} &nbsp;*&nbsp;:</dt><dd id="motive" class="action-editField validate-localReferentialData required-true i18n-ir.property.motive data-localReferentialData" >
           <g:render template="/backofficeRequestInstruction/widget/localReferentialDataStatic" 
                     model="['javaName':'motive', 'lrEntries': lrTypes.motive?.entries, 
                             'rqt':rqt, 'isMultiple':lrTypes.motive?.isMultiple(), 'depth':0]" />
 
          </dd>
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'ir.property.message.label')} &nbsp;*&nbsp;:</dt><dd id="message" class="action-editField validate-regex required-true i18n-ir.property.message rows-8 maxLength-255" regex="^.{0,255}$"><span >${rqt?.message}</span></dd>
              </dl>
              
            
          </div>
          <!-- column end -->
          
          <!-- column start -->
          <div class="yui-u">
            
          </div>
          <!-- column end -->
          
        </div>
        <!-- data step  end -->
      </div>
      <!-- step end -->
      
    
    
  </div>
  
</div>
