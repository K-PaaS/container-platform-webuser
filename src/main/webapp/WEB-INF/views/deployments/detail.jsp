<%--
  Deployment main

  @author hrjin
  @version 1.0
  @since 2020.09.15
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="org.container.platform.web.user.common.Constants" %>

<div class="content">
    <h1 class="view-title"><span class="detail_icon"><i class="fas fa-file-alt"></i></span> <c:out value="${deploymentName}"/></h1>
    <jsp:include page="../common/contentsTab.jsp"/>
    <!-- Details 시작 (Details start)-->
    <div class="cluster_content01 row two_line two_view harf_view">
        <ul class="maT30">
            <li class="cluster_first_box">
                <div class="sortable_wrap">
                    <div class="sortable_top">
                        <p>Details</p>
                    </div>
                    <div class="account_table view">
                        <table>
                            <colgroup>
                                <col style="width:20%">
                                <col style=".">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th><i class="cWrapDot"></i> Name</th>
                                <td id="name"></td>
                            </tr>
                            <tr>
                                <th><i class="cWrapDot"></i> Namespace</th>
                                <td id="namespaceID"></td>
                            </tr>
                            <tr>
                                <th><i class="cWrapDot"></i> Labels</th>
                                <td id="labels" class="labels_wrap"></td>
                            </tr>

                            <tr>
                                <th><i class="cWrapDot"></i> Annotations</th>
                                <td id="annotations" class="labels_wrap">
                                </td>
                            </tr>
                            <tr>
                                <th><i class="cWrapDot"></i> Creation Time</th>
                                <td id="creationTime"></td>
                            </tr>
                            <tr>
                                <th><i class="cWrapDot"></i> Selector</th>
                                <td id="selector"></td>
                            </tr>
                            <tr>
                                <th><i class="cWrapDot"></i> Strategy</th>
                                <td id="strategy"></td>
                            </tr>
                            <tr>
                                <th><i class="cWrapDot"></i> Min ready seconds</th>
                                <td id="minReadySeconds"></td>
                            </tr>
                            <tr>
                                <th><i class="cWrapDot"></i> Revision history limit</th>
                                <td id="revisionHistoryLimit"></td>
                            </tr>
                            <tr>
                                <th><i class="cWrapDot"></i> Rolling update strategy</th>
                                <td id="rollingUpdateStrategy"></td>
                            </tr>
                            <tr>
                                <th><i class="cWrapDot"></i> Status</th>
                                <td id="status"></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </li>
            <!-- Details 끝 (Details end)-->
            <!-- Replica Set 시작 (Replica Set start)-->
            <li class="cluster_third_box">
                <jsp:include page="../replicasets/list.jsp"/>
            </li>
            <!-- Replica Sets 끝 (Replica Sets end)-->
            <!-- Pods 시작 (Pods start)-->
            <li class="cluster_third_box maB50">
                <jsp:include page="../pods/list.jsp"/>
            </li>
            <!-- Pods 끝 (Pods end)-->
            <li class="cluster_fifth_box maB50">
                <jsp:include page="../common/commonDetailsBtn.jsp"/>
            </li>
        </ul>
    </div>
    <!-- Details 끝 (Details end)-->
</div>
<input type="hidden" id="hiddenNamespace" name="hiddenNamespace" value="" />
<input type="hidden" id="hiddenResourceKind" name="hiddenResourceKind" value="deployments" />
<input type="hidden" id="hiddenResourceName" name="hiddenResourceName" value="" />

<script type="text/javascript">
    var ownerParamForReplicaSetsByDeployments ='';

    var getDetail = function() {
        procViewLoading('show');
        var reqUrl = "<%= Constants.API_URL %><%= Constants.URI_API_DEPLOYMENTS_DETAIL %>"
            .replace("{cluster:.+}", CLUSTER_NAME)
            .replace("{namespace:.+}", NAME_SPACE)
            .replace("{deploymentName:.+}", "<c:out value='${deploymentName}'/>");
        procCallAjax(reqUrl, "GET", null, null, callbackGetDeployments);
    };

    // GET DETAIL FOR PODS LIST
    var getDetailForPodsList = function(selector, searchName) {
        var param = "?selector=" + selector ;
        var reqUrl = "<%= Constants.API_URL %><%= Constants.URI_API_PODS_LIST_BY_SELECTOR %>" + param;
        reqUrl = reqUrl.replace("{cluster:.+}", CLUSTER_NAME).replace("{namespace:.+}", NAME_SPACE);

        if (searchName != null) {
            reqUrl += "&searchName=" + searchName;
        }

        getPodListUsingRequestURL(reqUrl);
        procViewLoading('hide');
    };

    var callbackGetDeployments = function (data) {

        var f_srch_deployments = 'Deployments 상세 조회에 실패하였습니다.';
        var s_msg_f_srch_deployments = '<spring:message code="M0054" arguments='arg_f_srch_deployments' javaScriptEscape="true" text="Deployments 상세 조회에 실패하였습니다."/>';
        s_msg_f_srch_deployments_lang = s_msg_f_srch_deployments.replace('arg_f_srch_deployments', f_srch_deployments);

        if (!procCheckValidData(data)) {
            procViewLoading('hide');
            procAlertMessage(s_msg_f_srch_deployments_lang, false);
            return false;
        }


        var metadata = data.metadata;
        var spec = data.spec;
        var status = data.status;

        var deployName = metadata.name;
        var namespace = data.metadata.namespace;
        var deploymentsUid = data.metadata.uid;
        var labels = procSetSelector(metadata.labels);
        var annotations = metadata.annotations;
        var creationTimestamp = metadata.creationTimestamp;

        var selector = procSetSelector(spec.selector).replace(/matchLabels=/g, '');
        var strategy = spec.strategy.type;
        var minReadySeconds = spec.minReadySeconds;
        var revisionHistoryLimit = spec.revisionHistoryLimit;
        var rollingUpdateStrategy ="";
        var rollingUpdate = spec.strategy.rollingUpdate;
        if(!rollingUpdate) {
            rollingUpdateStrategy = "-";
        } else {
            rollingUpdateStrategy = "Max surge: " + rollingUpdate.maxSurge + ", "
                + "Max unavailable: " + rollingUpdate.maxUnavailable;
        }


        var updatedReplicas = status.updatedReplicas;
        var totalReplicas = status.replicas;
        var availableReplicas = status.availableReplicas;
        var unavailableReplicas = status.unavailableReplicas;
        var replicaStatus = updatedReplicas + " updated, "
            + totalReplicas + " total, "
            + availableReplicas + " available, "
            + unavailableReplicas + " unavailable";

        $('#name').html(deployName);
        $('#namespaceID').html("<a href='javascript:void(0);' onclick='procMovePage(\"<%= Constants.URI_CLUSTER_NAMESPACES %>/" + namespace + "\");'>" + namespace);
        $('#labels').html(procCreateSpans(labels));
        $('#annotations').html(procSetAnnotations(annotations));
        $('#creationTime').html(creationTimestamp);
        $('#selector').html(procCreateSpans(selector));
        $('#strategy').html(strategy);
        $('#minReadySeconds').html(minReadySeconds);
        $('#revisionHistoryLimit').html(revisionHistoryLimit);
        $('#rollingUpdateStrategy').html(rollingUpdateStrategy);
        $('#status').html(replicaStatus);

        $('#hiddenNamespace').val(namespace);
        $('#hiddenResourceName').val(deployName);

       // get ReplicaSets List By Deployment
        ownerParamForReplicaSetsByDeployments = replaceLabels(selector) + "&type=deployments&ownerReferencesUid="+deploymentsUid+"&ownerReferencesName="+deployName ;
        getReplicaSetsList(ownerParamForReplicaSetsByDeployments ,0, <%= Constants.DEFAULT_LIMIT_COUNT %>, null);

        // get Pods List By Deployment
        getDetailForPodsList(ownerParamForPodsByReplicaSets, null);

    };


    $(document.body).ready(function () {
        getDetail();
    });

</script>
