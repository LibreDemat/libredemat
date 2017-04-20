
<div class="mainbox mainbox-yellow">
    <h2>
        ${message(code:'requestType.configuration.parkCard.street.import')}
    </h2>
    <g:uploadForm action="importStreet">
        <label for="csvFile"> ${message(code:'subscriber.label.chooseFile')}:
        </label>
        <input type="hidden" name="id" id="id" value='${id}' />
        <input type="file" name="csvFile" id="csvFile" />
        <input type="submit" name="save" value="${message(code:'action.save')}" />
    </g:uploadForm>
    ${message(code:'requestType.configuration.parkCard.street.import.descriptif')}
</div>
<div id="streetReferential" class="mainbox mainbox-yellow">
    <table>
        <tr>
            <th>
                <b>&nbsp;&nbsp;${message(code:'requestType.configuration.parkCard.street')}</b>
            </th>
            <th>
                <b>&nbsp;&nbsp;${message(code:'requestType.configuration.parkCard.city')}</b>
            </th>
            <th>
                <b>&nbsp;&nbsp;${message(code:'requestType.configuration.parkCard.postalCode')}</b>
            </th>
        </tr>
        <g:each var="street" in="${streetBorderReferentials}">
            <tr>
                <td>
                    &nbsp;&nbsp;${street.streetLabel}
                </td>
                <td>
                    &nbsp;&nbsp;${street.city}
                </td>
                <td>
                    &nbsp;&nbsp;${street.postalCode}
                </td>
            </tr>
        </g:each>
    </table>
</div>