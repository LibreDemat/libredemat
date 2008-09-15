<html>
  <head>
    <meta name="layout" content="fong_main" />
  </head>
  
  <body>
    <h2>
<!--      <a href="#" id="requestSubmit">envoyer</a>-->
      Demande d'acte de mariage
      <span>Ici, une br�ve description du t�l�-service</span>
      <span>Dur�e de la d�marche : <strong>5 min</strong></span>
      <span>
        Documents � fournir :
        <strong>Pi�ce d'identit�</strong>, 
        <strong class="mandatory">Livret de famille</strong>
      </span>
    </h2>
    <div id="yui-main"> 
     <div id="main" class="yui-b">
     
       <div id="requestTabView" class="yui-navset">
         <ul class="yui-nav">
           
           <li class="${currentTab == 'tab1' ? 'selected' : ''}"><a href="#tab1"><em>
             <span class="tag-no_right">1</span>
             <span class="tag-validated">c</span>
             Demandeur
           </em></a></li>
           
           <li class="${currentTab == 'tab2' ? 'selected' : ''}"><a href="#tab2"><em>
             <span class="tag-no_right">2</span>
             <span class="tag-rejected">i</span>
             Nature de l'acte
           </em></a></li>
           
           <li class="${currentTab == 'tab3' ? 'selected' : ''}"><a href="#tab3"><em>
             <span class="tag-no_right">3</span>
             <span class="tag-rejected">i</span>
             Type d'acte
           </em></a></li>
           
           <li><a href="#tab4"><em>
             <span class="tag-no_right">4</span>
             <span class="tag-rejected">i</span>
             Documents
           </em></a></li>
           
           <li><a href="#tab5"><em>
             <span class="tag-no_right">5</span>
             Contact
           </em></a></li>
           
           <li class="${currentTab == 'tab6' ? 'selected' : ''}"><a href="#tab6"><em>
             <span class="tag-no_right">6</span>
             Valider
           </em></a></li>
           
         </ul>
          
         <div class="yui-content">
           <div id="tab1">
             <form method="POST" action="<g:createLink action="validRequester" />">
               <h3>
                 <span class="tag-validated">complet</span>
                 Demandeur
                 <span>Informations con�ernant le demandeur de l'acte</span>
               </h3>
               
               <label for="title">
                 <strong>Civilit�</strong>
               </label>
               <select name="title">
                 <option value="Mister" ${mdr.requester?.title.toString() == 'Mister' ? 'selected' : ''}>Monsieur</option>
                 <option value="Madam" ${mdr.requester?.title.toString() == 'Madam' ? 'selected' : ''}>Madame</option>
               </select>
               
               <label for="lastName"><strong>Nom</strong></label>
               <input name="lastName" type="text" value="${mdr.requester?.lastName}" />
               
               <label for="firstName"><strong>Pr�nom</strong></label>
               <input name="firstName" type="text" value="${mdr.requester?.firstName}" />
               
               <label for="birthDate">
                 <strong>Date de naissance</strong>
                 <span>(ex: 10/10/1970)</span>
               </label>
               <input name="birthDate"" type="text" value="${mdr.requester?.birthDate}" />
               
               <fieldset><legend>Adresse</legend>
                 <label for="adress"><strong>Rue</strong></label>
                 <input name="adress" type="text" value="${mdr.requester?.adress?.adress}" />
                 
                 <label for="postalCode"><strong>Code Postal</strong></label>
                 <input name="postalCode" type="text" value="${mdr.requester?.adress?.postalCode}" />
                 
                 <label for="city"><strong>Ville</strong></label>
                 <input name="city" type="text" value="${mdr.requester?.adress?.city}" />
               </fieldset>
               
               <label for="homePhone">
                 <strong>T�l�phone</strong>
                 <span> (ex: 0130204050)</span>
               </label>
               <input name="homePhone" type="text" value="${mdr.requester?.homePhone}" />
               
               <label for="email">
                 Courriel
                 <span>(ex: capdemat@fong.biz)</span>
               </label>
               <input name="email" type="text" value="${mdr.requester?.email}" />
                              

               <!-- Input submit-->
               <input type="submit" name="submitMdrRequester" value="Valider cette page" />
             </form>
             <!-- navigation link -->
             <div class="navTab">
               <a href="#tab2" class="nextTab">Page suivante &gt;&gt;</a>
             </div>
           </div>
           
           <div id="tab2">
             <form method="POST" action="<g:createLink action="validDetailsNature" />">
               <h3>
                 <span class="tag-rejected">imcomplet</span>
                 Nature de l'acte
                 <span>Informations con�ernant les conjoints</span>
               </h3>
               
               <label for="requesterQuality">Qualit� du demandeur</label>
               <select name="requesterQuality">
                 <option value="Requester" ${mdr?.requesterQuality.toString() == 'Requester' ? 'selected' : ''}>Titulaire de l'acte</option>
                 <option value="Spouse" ${mdr?.requesterQuality.toString() == 'Spouse' ? 'selected' : ''}>Son conjoint</option>
                 <option value="Parent" ${mdr?.requesterQuality.toString() == 'Parent' ? 'selected' : ''}>Son p�re / sa m�re</option>
                 <option value="GrandParent">Son grand-p�re / sa grand-m�re</option>
                 <option value="Child">Son fils / sa fille</option>
                 <option value="LegalRepresentant">Son repr�sentant l�gal</option>
                 <option value="Agent">Son mandataire</option>
                 <option value="HeirFamily">Son h�ritier et aussi son fr�re ou sa soeur</option>
                 <option value="Heir">Son h�ritier sans �tre son fr�re ou sa soeur</option>
                 <option value="Authorized">Autoris� par le procureur de la R�publique</option>
                 <option value="LawyerNotary">Avocat, notaire</option>
                 <option value="Other">Autre</option>
               </select>
               
               <fieldset><legend>L'�poux</legend>
                 <label for="marriageHusbandLastName"><strong>Nom</strong></label>
                 <input name="marriageHusbandLastName" type="text" value="${mdr?.marriageHusbandLastName}" />
                 
                 <label for="marriageHusbandFirstNames"><strong>Pr�nom</strong></label>
                 <input name="marriageHusbandFirstNames" type="text" value="${mdr?.marriageHusbandFirstNames}" />
               </fieldset>
               
               <fieldset><legend>L'�pouse</legend>
                 <label for="marriageWifeLastName"><strong>Nom</strong></label>
                 <input name="marriageWifeLastName" type="text" value="${mdr?.marriageWifeLastName}" />
                 
                 <label for="marriageWifeFirstNames"><strong>Pr�nom</strong></label>
                 <input name="marriageWifeFirstNames" type="text" value="${mdr?.marriageWifeFirstNames}" />
               </fieldset>
               
               <fieldset><legend>Le mariage</legend>
                 <label for="marriageDate"><strong>Date</strong></label>
                 <input name="marriageDate" type="text" value="${mdr?.marriageDate}" />
                 
                 <label for="marriageCity"><strong>Ville</strong></label>
                 <input name="marriageCity" type="text" value="${mdr?.marriageCity}"/>
                 
                 <label for="marriagePostalCode"><strong>D�partement</strong></label>
                 <input name="marriagePostalCode" type="text" value="${mdr?.marriagePostalCode}" />
               </fieldset>
              
               <!-- Input submit-->
               <input type="submit" name="submitMdrDetailsNature" value="Valider cette page" />
             </form>
             <!-- navigation link -->
             <div class="navTab">
               <a href="#tab1" class="prevTab">&lt;&lt; Page pr�c�dente</a>
               <a href="#tab3" class="nextTab">Page suivante &gt;&gt;</a>
             </div>
           </div>
           
           <div id="tab3">
             <form method="POST" action="<g:createLink action="validDetailsNature" />">
             <h3>
                 <span class="tag-rejected">imcomplet</span>
                 Nature de l'acte
                 <span>Informations con�ernant l'acte</span>
               </h3>
               
               <label for="format"><strong>Type d'acte</strong></label>
               <select name="format">
                 <option value="FullCopy" ${mdr?.format.toString() == 'FullCopy' ? 'selected' : ''}>Copy int�grale</option>
                 <option value="ExtractWithRelationship" ${mdr?.format.toString() == 'ExtractWithRelationship' ? 'selected' : ''}>Extrait avec filiation</option>
                 <option value="ExtractWithoutRelationship" ${mdr?.format.toString() == 'ExtractWithoutRelationship' ? 'selected' : ''}>Extrait sans filiation</option>
               </select>
               
               <label for="copies"><strong>Nombre d'actes</strong></label>
               <input name="copies" type="text" value="${mdr?.copies}"/>
               
               <label for="usage">Usage</label>
               <input name="usage" type="text" value="${mdr?.usage}" />
                 
               <label><strong>Filiation de</strong></label>
               <select name="requesterQuality">
                 <option value="Husband" ${mdr?.relationship.toString() == 'Husband' ? 'selected' : ''}>L'�poux</option>
                 <option value="Wife" ${mdr?.relationship.toString() == 'Wife' ? 'selected' : ''}>L'�pouse</option>
               </select>
               
               <fieldset><legend>Le p�re</legend>
                 <label for="fatherLastName"><strong>Nom</strong></label>
                 <input name="fatherLastName" type="text" value="${mdr?.fatherLastName}" />
                 
                 <label for="fatherFirstNames"><strong>Pr�nom</strong></label>
                 <input name="fatherFirstNames" type="text" value="${mdr?.fatherFirstNames}" />
               </fieldset>
               
               <fieldset><legend>La m�re</legend>
                 <label for="motherMaidenName"><strong>Nom</strong></label>
                 <input name="motherMaidenName" type="text" value="${mdr?.motherMaidenName}" />
                 
                 <label for="motherFirstNames"><strong>Pr�nom</strong></label>
                 <input name="motherFirstNames" type="text" value="${mdr?.motherFirstNames}" />
               </fieldset>
               
               <!-- Input submit-->
               <input type="submit" name="submitMdrDetailsFormat" value="Valider cette page" />
             </form>
             <!-- navigation link -->
             <div class="navTab">
               <a href="#tab2" class="prevTab">&lt;&lt; Page pr�c�dente</a>
               <a href="#tab4" class="nextTab">Page suivante &gt;&gt;</a>
             </div>
           </div>
           
           <div id="tab4">
             <form action="#">
               <h3>
                 <span class="tag-rejected">incomplet</span> 
                 Documents
                 <span>Cette �tape vous permet de fournir des justificatifs num�ris�s, elle n'est pas obligatoire</span>
               </h3>
               
               <ul>
                 <li>
                   <span class="tag-rejected">r</span> 
                   <a href="#"><strong>Pi�ce d'identit�</strong></a>
                 </li>
                 <li>
                   <span class="tag-validated">v</span> 
                   <a href="#"><strong>Livret de famille</strong></a>
                </li>
              </ul>
               
               <!-- Input submit-->
               <input type="submit" value="Valider cette page" />
             </form>
             <!-- navigation link -->
             <div class="navTab">
               <a href="#tab3" class="prevTab">&lt;&lt; Page pr�c�dente</a>
               <a href="#tab5" class="nextTab">Page suivante &gt;&gt;</a>
             </div>
           </div>
           
            <div id="tab5">
             <form method="POST" action="<g:createLink action="validDetailsNature" />">
               <h3>
                 Contact
                 <span>Comment souhaitez-vous �tre contact� pour cette demande</span>
               </h3>
               
               <label>Moyen de contact</label>
               <select name="requestMeansOfContactSelect">
                 <option>Courriel</option>
                 <option>T�l�phone</option>
                 <option>Courrier</option>
                 <option>SMS</option>
                 <option>Mobile</option>
               </select>
                
               <!-- Input submit-->
               <input type="submit" value="Valider cette page" />
             </form>
             <!-- navigation link -->
             <div class="navTab">
               <a href="#tab4" class="prevTab">&lt;&lt; Page pr�c�dente</a>
               <a href="#tab6" class="nextTab">Page suivante &gt;&gt;</a>
             </div>
           </div>
           
           <div id="tab6">
             <form method="POST" action="<g:createLink action="sendRequest" />">
               <h3>
                 Validation
                 <span>V�rifiez les donn�es saisies et envoyez votre demande</span>
               </h3>
               
              <P>Ici la visualisation des donn�es saisies ...<P>
              
              <div class="message ${message != null ? '' : 'hidden'}">
                ${message}
              </div>
                
               <!-- Input submit-->
               <input name="submitMdrSend" type="submit" value="Envoyer la demande" />
             </form>
             <!-- navigation link -->
             <div class="navTab">
               <a href="#tab5" class="prevTab">&lt;&lt; Page pr�c�dente</a>
             </div>
           </div>
           
        </div><!-- end yui-content -->
        
      </div><!-- end requestTabView -->
        
       <div class="helpBox">
        <h3>Aide</h3>
        <dl>
          <dt>Sujet</dt>
          <dd>
            Afin d'am�liorer la communication et les �changes et de favoriser la participation 
            et la contribution, le projet CapD�mat se dote de nouveaux moyens de discussion
          </dd>
          <dt>Documents</dt>
          <dd>
            Afin d'am�liorer la communication et les �changes et de favoriser la participation 
          </dd>
          <dt>Formulaire</dt>
          <dd>
             et la contribution, le projet CapD�mat se dote de nouveaux moyens de discussion
            Afin d'am�liorer la communication et les �changes et de favoriser la participation
          </dd>
          <dt>Moyen de contact</dt>
          <dd>
            de favoriser la participation 
            et la contribution, le projet CapD�mat se dote de nouveaux moyens de discussion
          </dd>
        </dl>
      </div>
     
     </div> <!-- end main -->
    </div> <!-- end yui-main -->

    <div id="narrow" class="yui-b">
      
      <div id="requestSubject" class="requestBox">
        <h3>
          <span class="tag-validated">v</span>
          <em>Sujet de la demande</em>
        </h3>
        <div class="body">
          <strong>M. Herv� Martin</strong>
          <a href="#">choisir un autre sujet</a>
        </div>
      </div>
      
      <div id="requestMeansOfContact" class="requestBox">
        <h3>
          <em>Moyen de contact</em>
        </h3>
        <div class="body">
          <form action="#">
            <select name="requestMeansOfContactSelect">
              <option>Courriel</option>
              <option>T�l�phone</option>
              <option>Courrier</option>
              <option>SMS</option>
              <option>Mobile</option>
            </select>
          </form>
        </div>
      </div>
     
      <div id="requestDocument" class="requestBox">
        <h3>
          <span class="tag-rejected">r</span>
          <em>Documents � fournir</em>
        </h3>
        <div class="body">
          <ul>
            <li>
              <span class="tag-rejected">r</span> 
              <a href="#"><strong>Pi�ce d'identit�</strong></a>
            </li>
            <li>
              <span class="tag-validated">v</span> 
              <a href="#"><strong>Livret de famille</strong></a>
            </li>
          </ul>
        </div>
      </div>
    
      <!-- 
      <div class="nobox">
        <h3>Broullons</h3>
        <ul>
         <li><span class="tag-validated">complet</span>Demande d'assistance � distance </li>
         <li><span class="tag-validated">complet</span>Inscription scolair</li>
         <li><span class="tag-pending">partiel</span>Inscription � la cantine scolaire</li>
        </ul>
      </div>
      -->
      
      <!--
      <h3>Deni�res demandes</h3>
      <ul>
       <li><span class="tag-pending">trait�e</span>Demande d'assistance � distance - 12/08/2008</li>
       <li><span class="tag-validated">valid�e</span>Inscription scolair - 10/08/2008</li>
       <li><span class="tag-rejected">annul�e</span>Inscription � la cantine scolaire - 10/08/2008</li>
      </ul>

      <h3>Documents</h3>
      <ul>
       <li>Carte d'identit� - rafik</li>
       <li>Passeport - superman</li>
       <li>Lvret de famille - Zenexity</li>
       <li>Justificatif de domocile - rue Taitbout</li>
      </ul>

      <h3>Compte famille</h3>
      <ul>
       <li>Rafik DJEJDIG - 10/10/1910</li>
       <li>Rafik DJEJDIG - 10/10/1910</li>
       <li>Rafik DJEJDIG - 10/10/1910</li>
       <li>Rafik DJEJDIG - 10/10/1910</li>
      </ul>
      -->     
    </div><!-- end of narrow -->
    <script type="text/javascript">
      // next Links
      var activeNextTabByLink = function(e) {
	      YAHOO.util.Event.preventDefault(e);
        var requestFormTabView = new YAHOO.widget.TabView('requestTabView');
	      var activeTabIndex = requestFormTabView.get('activeIndex');
	      requestFormTabView.set('activeIndex' , activeTabIndex + 1);
      }
      
      YAHOO.util.Event.addListener(
          YAHOO.util.Dom.getElementsByClassName("nextTab", "a" ),
          "click", 
          activeNextTabByLink
      );
      
      // prev Links
      var activePrevTabByLink = function(e) {
	      YAHOO.util.Event.preventDefault(e);
        var requestFormTabView = new YAHOO.widget.TabView('requestTabView');
	      var activeTabIndex = requestFormTabView.get('activeIndex');
	      requestFormTabView.set('activeIndex' , activeTabIndex - 1);
      }
      
      YAHOO.util.Event.addListener(
          YAHOO.util.Dom.getElementsByClassName("prevTab", "a" ),
          "click", 
          activePrevTabByLink
      );
   </script>

  </body>
</html>

