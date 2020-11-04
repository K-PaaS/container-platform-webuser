/**
 * API call function
 * @param reqUrl
 * @param reqMethod
 * @param param
 * @param preFunc
 * @param callback
 * @description
 *      async: false 동기 처리
 * @author jjy
 * @since 2020.09.03
 */
var procCallAjax = function (reqUrl, reqMethod, param, preFunc, callback) {
    var reqData = "";
    var contentTypeValue = 'application/json';
    if (param != null) {
        reqData = param;
    }

    if (preFunc != null && preFunc === true) {
        contentTypeValue = 'text/plain';
    }

    $.ajax({
        url: reqUrl,
        method: reqMethod,
        data: reqData,
        dataType: 'json',
        async: false,
        contentType: contentTypeValue,
        beforeSend: function (xhr) {
            // preFunc
            //xhr.setRequestHeader(_csrf_header, _csrf_token);
            // CONTAINER-PLATFORM-API Ajax request header setting
            xhr.setRequestHeader("X-Container-Platform-Ajax-call", "true");
        },
        success: function (data) {
            if (data.resultCode == "FAIL") {

                if (data.resultMessage == "TOKEN_FAILED") {
                    location.href = "/logout";
                    return false;
                }

                if(preFunc == 'ENDPOINTS') {
                    return false;
                }

                procViewLoading('hide');
                procAlertMessage(data.detailMessage);
            } else {
                callback(data);
            }

        },
        error: function (jqXHR, exception) {
            console.log("jqXHR.status::::" + jqXHR.status + " exception:::" + exception);
            procViewLoading('hide');

            if (jqXHR.status == 401) {
                procAlertMessage('API unauthorized.', false);
            } else if (jqXHR.status == 500) {
                procAlertMessage();
            }
        },
        complete: function (data) {
            // SKIP
            console.log("COMPLETE :: data :: ", data);
        }
    });
};


// MOVE PAGE
var procMovePage = function (pageUrl) {
    if (pageUrl === null || pageUrl.length < 1) {
        return false;
    }

    if ((!!pageUrl && typeof pageUrl === 'number') && -1 === pageUrl) {
        history.back();
    } else {
        // pageUrl = ("/" === pageUrl) ? "" : pageUrl;
        // location.href = procGetDashboardUrl() + pageUrl;
        location.href = pageUrl;
    }

};


// SET SELECTOR
var procSetSelector = function (requestMapString) {
    if (requestMapString === null || requestMapString.length < 1) {
        return false;
    }

    return JSON.stringify(requestMapString).replace(/["{}]/g, '').replace(/:/g, '=');
};

/**
 * 문자열이 빈 문자열인지 체크하여 빈값("") 또는 기본 문자열을 반환한다.
 * @param str           : 체크할 문자열
 */
function nvl(str, defaultStr) {
    if (str == "undefined" || str === undefined || str == "null" || str === null || str == "") {
        if (defaultStr === undefined) {
            str = "";
        } else {
            str = defaultStr;
        }
    }
    return str;
}

// SET MENU CURSOR
var procSetMenuCursor = function () {
    var leftMenuList = ["intro", "workloads", "services", "storages", "info", "users", "roles"];
    var headerMenuList = ["info", "users", "roles"];
    var leftMenuListLength = leftMenuList.length;
    var calledMenu = leftMenuList[0];

    var currentUrl = location.pathname;
    var splitString = "/";
    var splits = currentUrl.split(splitString);
    var splitsLength = splits.length;

    for (var i = 0; i < leftMenuListLength; i++) {
        for (var j = 0; j < splitsLength; j++) {
            if (leftMenuList[i] === splits[j]) {
                calledMenu = leftMenuList[i];

                if (headerMenuList.includes(splits[j])) {
                    $("#header-menu-" + calledMenu).addClass('cur');
                }
            }
        }
    }

    $("#left-menu-" + calledMenu).addClass('cur');
};


// SET SORT LIST
var procSetSortList = function (resultTableString, buttonObject, key) {
    $(buttonObject).toggleClass('sort');

    var resultTable = $('#' + resultTableString);
    var orderDigit = ($(buttonObject).hasClass('sort')) ? 0 : 1; // 0 = ASC, 1 = DESC
    var sorting = [[key, orderDigit]];

    resultTable.trigger("update");
    resultTable.trigger("sorton", [sorting]);
    $('.headerSortFalse > td').unbind();
};


var procCheckValidData = function (data) {
    var ensureData;
    if ('' === nvl(data)) {
        ensureData = {};
        ensureData.resultCode = RESULT_STATUS_FAIL;
    } else {
        ensureData = data;
        if ('' === nvl(data['resultCode'])) {
            ensureData.resultCode = RESULT_STATUS_FAIL;
        }
    }

    if (RESULT_STATUS_FAIL === ensureData.resultCode) {
        return false;
    } else {
        return null != data.resultCode;
    }
};

var procViewLoading = function (type) {
    var dashboardWrap = $("#dashboardWrap");
    var loader = $("#loadingSpinner");

    if (type === 'show') {
        loader.show().gSpinner();
    } else {
        dashboardWrap.show();
        setTimeout(function () {
            loader.gSpinner("hide").hide();
        }, 1000);
    }
};


var procAlertMessage = function (value, result) {
    var messageValue = '시스템 에러가 발생했습니다.';
    var alertLayer = $(".alertLayer");

    if (nvl(value) !== '') {
        messageValue = value;
    }

    $(".alertLayer .in").html(messageValue);
    if (result) {
        alertLayer.css('border-left', '4px solid #3d10ef');
    } else {
        alertLayer.css('border-left', '4px solid #cb3d4a');
    }

    alertLayer.addClass("moveAlert");

    setTimeout(function () {
        alertLayer.removeClass("moveAlert");
    }, 3000);
};

/**
 * 해당 리소스에 이벤트 데이터를 추가한다.
 * @param targetObject   : 해당 리소스의 리스트 JSON Object
 * @param selector       : 연관된 POD를 조회하기 위한 SELECTOR
 * @description
 *    해당 리소스(replicaSet, deployment)에 연관된 POD명을 조회하여,
 *    해당 POD의 이벤트를 조회후,
 *    해당 리소스의 리스트에 이벤트 데이터를 추가 합니다.
 *
 *    targetObject : 이벤트 데이터를 추가할 대상 JSON Object 입니다.(replicaSet, deployment 리스트 데이터)
 *    selector     : replace 처리되지 않은 json Data 입니다.
 *
 *    ex) procAddPodsEvent(itemList, itemList.spec.selector.matchLabels); // event Data added to 'itemList'
 *
 */
var procAddPodsEvent = function (targetObject, selector) {

    selector = procSetSelector(selector);

    // 기존 리스트 데이터에 event.type, event.message 추가
    var eventType = 'normal';
    var eventMessage = [];
    var param = "?selector=" + selector ;

    var reqPodsUrl = URI_API_PODS_RESOURCES.replace("{namespace:.+}", NAME_SPACE) + param;

    procCallAjax(reqPodsUrl, "GET", null, null, function (podsData) {
        $.each(podsData.items, function (index, itemList) {
            var podsName = itemList.metadata.uid;
            var podPhase = nvl(itemList.status.phase).toLowerCase();

            // 해당조건일시 이벤트에서 제외
            if (podPhase == "running" || podPhase.includes("succeeded") || podPhase == "terminated") {
                return true;  // continue;
            }

            var reqEventsUrl = URI_API_EVENTS_LIST
                .replace("{namespace:.+}", NAME_SPACE)
                .replace("{resourceUid:.+}", podsName);
            procCallAjax(reqEventsUrl, "GET", null, null, function (eventData) {
                $.each(eventData.items, function (index, eData) {
                    var eType = eData.type;
                    if (eType == 'Warning' && !(eventMessage.indexOf(eData.message) > -1)) {
                        eventType = eType;
                        eventMessage.push(eData.message);
                    }
                });
            });

        }); // Event API call end
    }); //Pods API call end

    targetObject.type = eventType;
    targetObject.message = eventMessage;

};


// SET TOOL TIP FOR TABLE TD
var procSetToolTipForTableTd = function (tableObjectString) {
    if (nvl(tableObjectString) === '') {
        return false;
    }

    var tableObject = $('#' + tableObjectString + ' tr');

    tableObject.each(function () {
        var tdTags = $(this).find('td');
        var pTags,
            aTags,
            spanTags;

        if (tdTags != null) {
            pTags = $(this).find('p');
            aTags = $(this).find('a');
            spanTags = $(this).find('span');

            pTags.each(function () {
                if (nvl(pTags) !== '') {
                    procSetToolTipAttributes($(this));
                }
            });

            aTags.each(function () {
                if (nvl(aTags) !== '') {
                    procSetToolTipAttributes($(this));
                }
            });

            spanTags.each(function () {
                if (nvl(spanTags) !== '') {
                    procSetToolTipAttributes($(this));
                }
            });
        }
    });

    // TOOL TIP
    $('[data-toggle="tooltip"]').tooltip();
};


// SET TOOL TIP ATTRIBUTES
var procSetToolTipAttributes = function (tagObject) {
    var tagObjectHtml = tagObject.html();
    if (!tagObject.hasClass('tableTdToolTipFalse') && tagObjectHtml !== '' && tagObjectHtml !== '-') {
        tagObject.addClass('custom-tag-content-overflow');
        tagObject.attr('data-toggle', 'tooltip').attr('title', tagObjectHtml);
    }
};

// CREATE SPANS FOR LABELS, ANNOTATIONS
var procCreateSpans = function (data, type) {
    if (!data || data == "null") {
        return "-";
    }
    var datas = data.replace(/=/g, ':').split(',');
    var spanTemplate = "";
    var brTemplate = "";

    if (type === 'LB') {
        brTemplate = '<br>';
    }

    $.each(datas, function (index, data) {
        if (index != 0) {
            spanTemplate += brTemplate;
        }

        spanTemplate += '<span class="bg_gray">' + data + '</span> ';
    });

    return spanTemplate;
};

// SET LAYER POPUP
var procSetLayerPopup = function (reqTitle, reqContents, reqSuccess, reqCancel, reqClose, reqSuccessCallback, reqCancelCallback, reqCloseCallback) {
    $('.modal-backdrop').remove();

    var commonLayerPopupSuccessButton = $('#commonLayerPopupSuccessButton');
    var commonLayerPopupCancelButton = $('#commonLayerPopupCancelButton');
    var commonLayerPopupCloseButton = $('#commonLayerPopupCloseButton');
    var commonLayerPopupFooterWrap = $('#commonLayerPopupFooterWrap');

    commonLayerPopupCancelButton.hide();
    commonLayerPopupCloseButton.hide();
    commonLayerPopupFooterWrap.hide();

    if (nvl(reqSuccess) !== '' || nvl(reqCancel) !== '') {
        commonLayerPopupSuccessButton.html(nvl(reqSuccess, '확인'));
        commonLayerPopupFooterWrap.show();
    }

    if (nvl(reqCancel) !== '') {
        commonLayerPopupCancelButton.html(reqCancel);
        commonLayerPopupCancelButton.show();
    }

    if (nvl(reqClose) !== '') {
        commonLayerPopupCloseButton.html(reqClose);
        commonLayerPopupCloseButton.show();
    }

    if (nvl(reqSuccessCallback) !== '') {
        commonLayerPopupSuccessButton.attr('onclick', reqSuccessCallback);
    }

    if (nvl(reqCancelCallback) !== '') {
        commonLayerPopupCancelButton.attr('onclick', reqCancelCallback);
    }

    if (nvl(reqCloseCallback) !== '') {
        commonLayerPopupCloseButton.attr('onclick', reqCloseCallback);
    }

    $('#commonLayerPopupTitle').html(nvl(reqTitle, '알림'));
    $('#commonLayerPopupContents').html(nvl(reqContents, '정상 처리되었습니다.'));

    $("#commonLayerPopup").modal("show");

};

// SET EXECUTE COMMAND COPY
var procSetExecuteCommandCopy = function (requestValue) {
    if (nvl(requestValue) === '') {
        return false;
    }

    var target = $("#out_a");
    target.val(requestValue);
    target.select();

    return document.execCommand('copy');
};

// CREATE ANNOTATION SPANS
var procSetAnnotations = function (extAnnotations) {
    var annotations = nvl(extAnnotations);
    if ('' === annotations || !(annotations instanceof Object)) {
        return '-';
    }

    // DO TRY TO CONVERT HTML SYMBOL TO RAW CHARACTER OF COMMA AND QUOTA
    var objKeys = Object.keys(annotations);
    if (objKeys.length <= 0) {
        return '-';
    }
    for (var i = 0; i < objKeys.length; i++) {
        // convert raw character of comma and quota to html symbol
        var beforeValue = annotations[objKeys[i]] + '';
        annotations[objKeys[i]] =
            beforeValue.replace(/,/g, '&comma;').replace(/"/g, '&quot;')
                .replace(/{/g, '&lbrace;').replace(/}/g, '&rbrace;').replace(/:/g, '&colon;');
    }

    var tempStr = "";
    var applyKey = 'kubectl.kubernetes.io/last-applied-configuration'
    var applyValue = nvl(annotations[applyKey]);
    if (applyValue !== '') {
        tempStr = '<span class="bg_blue" onclick="procSetAnnotationLayerpop(this)" '
            + 'data-title="' + applyKey + '" data-content=' + applyValue + '>'
            + '<a>' + applyKey + '</a></span>';
        delete annotations[applyKey];
    }

    var annotationsString = procSetSelector(annotations);
    if ('' !== annotationsString) {
        tempStr += ' ' + procCreateSpans(annotationsString, 'NOT_LB');
    }

    return nvl(tempStr, '-');
};

// CONTENT SETTING FOR POP-UP MODAL
var procSetAnnotationLayerpop = function (eventElement) {
    var select = $(eventElement);
    var title = JSON.stringify(select.data('title')).replace(/^"|"$/g, '');
    var content = JSON.stringify(select.data('content')).replace(/^"|"$/g, '');

    procSetLayerPopup(title, content, null, null, 'x', null, null, null);
};

/**
 * 객체의 값을 비교한다.
 * @param object   : 대상 Object 1
 * @param object   : 대상 Object 2
 * @description
 *    label 비교용으로 사용
 *    ex use) procCompareObj( {"app":"wordpress","tier":"front"},{"tier":"front", "app":"wordpress"} )  => true
 *
 */
var procCompareObj = function (a, b) {
    var type = typeof a, i, j;
    if (type == "object") {
        if (a === null) {
            return a === b;
        } else if (Array.isArray(a)) {
            if (!Array.isArray(b) || a.length != b.length) {
                return false;
            }
            for (i = 0, j = a.length; i < j; i++) {
                if (!procCompareObj(a[i], b[i])) {
                    return false;
                }
            }
            return true;
        } else { //일반 오브젝트인 경우

            // b의 키 갯수를 카운트 한다.
            j = 0;
            for (i in b) {
                if (b.hasOwnProperty(i)) j++;
            }

            //a의 각 키와 비교하면서 카운트를 제거한다.
            for (i in a) {
                if (a.hasOwnProperty(i)) {
                    if (!procCompareObj(a[i], b[i])) return false;
                    j--;
                }
            }
            //남은 카운트가 0이라면 같은 객체고 남아있다면 다른 객체.
            return !j;
        }
    }
    return a === b;
};

var procCreateMovePageAnchorTag = function (movePageUrl, content) {
    return '<a href="javascript:void(0);" onclick="procMovePage(\'' + movePageUrl + '\');">' + content + '</a>';
};

var commonUtils = {
    isEmpty: function (object) {
        if (object == undefined || object == null) {
            return true;
        }
        return false;
    },
    isBlank: function (value) {
        if (value == undefined || value == null || value == "") {
            return true;
        }
        return false;
    },
    contains: function (contents, findString) {
        if (this.isBlank(contents) || this.isBlank(findString)) {
            return false;
        }
        return contents.includes(findString);
    },
    regexId: function (value) {
        var re = /^[a-z0-9]([-a-z0-9]*[a-z0-9])?(\.[a-z0-9]([-a-z0-9]*[a-z0-9])?)*/g; // 아이디가 적합한지 검사할 정규식
        if (!re.test(value)) {
            return true;
        }
    },
    regexPwd: function (value) {
        var re = /^[a-zA-Z]+(?=.*\d)(?=.*[-$@$!%*#?&])[a-zA-Z\d-$@$!%*#?&]{3,39}$/g; // 패스워드가 적합한지 검사할 정규식
        if (!re.test(value)) {
            return true;
        }
    },
    regexEmail: function (value) {
        var re = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i; // 이메일이 적합한지 검사할 정규식
        if (!re.test(value)) {
            return true;
        }
    }
};


var makeResourceListParamQuery = function (offset, limit, searchName) {

    var param = "?offset=" + offset + "&limit=" + limit;
    if (searchName != null) {
        param += "&searchName=" + searchName;
    }
    return param;
};


var resourceListMoreBtnDisplay = function (remainItemCountKey, data, buttonID) {

    if (data.hasOwnProperty("itemMetaData")) {
        var itemMetaData = data.itemMetaData;

        if (itemMetaData != null) {
            if (data.itemMetaData.hasOwnProperty(remainItemCountKey)) {

                var remainingItemCount = data.itemMetaData.remainingItemCount;
                if (remainingItemCount <= 0) {
                    $('#' + buttonID).css("display", "none");
                }
            }
        }
    }

};

var setResourceListLimitCount = function () {

    var limitCount = 0;
    if (typeof IS_OVERVIEW_VIEW !== 'undefined') {
        //OVERVIEW
        limitCount = OVERVIEW_LIMIT_COUNT;
    } else {
        limitCount = DEFAULT_LIMIT_COUNT;
    }
    return limitCount;
}

var getNamespaceListByMetaData = function(metadata) {
    var namespaceLIst = [];
    for(i=0; i<metadata.length; i++) {
        namespaceLIst.push(metadata[i].namespace);
    }
    return namespaceLIst;
}

var getUserTypeByMetaData = function(metadata, namespace){

    var userType = '';
    for(i=0; i<metadata.length; i++) {
        if(metadata[i].namespace == namespace ) {
            userType = metadata[i].userType;
        }
    }
    return userType;

}