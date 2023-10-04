<%--
  overview page

  @author kjhoon
  @version 1.0
  @since 2020.08.21
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="org.container.platform.web.user.common.Constants" %>
<div class="content">
    <jsp:include page="../common/contentsTab.jsp"/>
    <!-- Overview 시작 (Overview start)-->
    <div class="cluster_content01 row two_line two_view harf_view">
        <ul class="maT30">
            <!-- 그래프 시작 (Graph start)-->
            <li class="cluster_first_box">
                <div class="sortable_wrap">
                    <div class="sortable_top">
                        <p>Overview</p>
                        <div class="sortable_right label">
                            <span class="running2 maR10"><i class="fas fa-circle"></i></span>Running
                            <span class="failed2 maL25 maR10"><i class="fas fa-circle"></i></span>Failed
                            <span class="pendding2 maL25 maR10"><i class="fas fa-circle"></i></span>Pending
                            <span class="succeeded2 maL25 maR10"><i class="fas fa-circle"></i></span>Succeeded
                        </div>
                    </div>
                    <div class="graphArea"><div id="piechart01" style="height: 260px"></div></div>
                    <div class="graphArea"><div id="piechart02" style="height: 260px"></div></div>
                    <div class="graphArea"><div id="piechart03" style="height: 260px"></div></div>
                    <div style="clear:both;"></div>
                </div>
            </li>
            <!-- 그래프 끝 (Graph end)-->
            <!-- Deployments 시작 (Deployments start)-->
            <li class="cluster_third_box">
                <jsp:include page="../deployments/list.jsp"/>
            </li>
            <!-- Deployments 끝 (Deployments end)-->

            <!-- Pods 시작 (Pods start)-->
            <li class="cluster_third_box">
                <jsp:include page="../pods/list.jsp"/>
            </li>
            <!-- Pods 끝 (Pods end)-->

            <!-- Replica Sets 시작 (Replica Sets start)-->
            <li class="cluster_fourth_box maB50">
                <jsp:include page="../replicasets/list.jsp"/>
            </li>
            <!-- Replica Sets 끝 (Replica Sets end)-->
        </ul>
    </div>
    <!-- Overview 끝 (Overview end)-->

</div>

<script type="text/javascript" src='<c:url value="/resources/js/highcharts.js"/>'></script>
<script type="text/javascript">
    var IS_OVERVIEW_VIEW = true;

    var G_PODS_CHART_RUNNING_RATIO = 0;
    var G_PODS_CHART_FAILED_RATIO = 0;
    var G_DEV_CHART_RUNNING_RATIO = 0;
    var G_DEV_CHART_FAILED_RATIO = 0;
    var G_REPLICA_SETS_CHART_RUNNING_RATIO = 0;
    var G_REPLICA_SETS_CHART_FAILED_RATIO = 0;

    var ownerParamForPodsByWorkloadsOverview ='';

    // ON LOAD
    $(window).bind("load", function () {
        getOverview();
        getDeploymentsList(0, <%= Constants.OVERVIEW_LIMIT_COUNT %>, null);
        getPodsList(0, <%= Constants.OVERVIEW_LIMIT_COUNT %>, null);
        getReplicaSetsList(null,0, <%= Constants.OVERVIEW_LIMIT_COUNT %>, null);
        createChart();
    });

    var getOverview = function () {
        procViewLoading('show');
        var reqUrl = "<%= Constants.API_URL %><%= Constants.URI_WORKLOAD_RESOURCE_COUNT %>";
        reqUrl = reqUrl.replace("{cluster:.+}", CLUSTER_NAME).replace('{namespace:.+}', NAME_SPACE);

        procCallAjax(reqUrl, 'GET', null, null, callbackGetOverview);
    };

    // CALLBACK POD LIST
    var callbackGetOverview = function (data) {
        G_PODS_CHART_RUNNING_RATIO = Number(data.podsUsage.running);
        G_PODS_CHART_FAILED_RATIO = Number(data.podsUsage.failed);
        G_DEV_CHART_RUNNING_RATIO = Number(data.deploymentsUsage.running);
        G_DEV_CHART_FAILED_RATIO = Number(data.deploymentsUsage.failed);
        G_REPLICA_SETS_CHART_RUNNING_RATIO = Number(data.replicaSetsUsage.running);
        G_REPLICA_SETS_CHART_FAILED_RATIO = Number(data.replicaSetsUsage.failed);
    };

    var createChart = function() {
        var podsChartRunningPer   = G_PODS_CHART_RUNNING_RATIO;
        var podsChartFailedPer    = G_PODS_CHART_FAILED_RATIO;
        var podsChartPenddingPer  = 0;
        var podsChartSucceededPer = 0;

        var devChartRunningPer    = G_DEV_CHART_RUNNING_RATIO;
        var devChartFailedPer     = G_DEV_CHART_FAILED_RATIO;
        var devChartPenddingPer   = 0;
        var devChartSucceededPer  = 0;

        var repsChartRunningPer   = G_REPLICA_SETS_CHART_RUNNING_RATIO;
        var repsChartFailedPer    = G_REPLICA_SETS_CHART_FAILED_RATIO;
        var repsChartPenddingPer  = 0;
        var repsChartSucceededPer = 0;

        var devPieColors = [];
        var devSeriesData= [];
        var podsPieColors = [];
        var podsSeriesData= [];
        var repsPieColors = [];
        var repsSeriesData= [];

        var pieColors = ['#3076b2', '#85c014', '#f01108' , '#333440'];
        var seriesLabel = ['Succeeded', 'Running', 'Failed', 'Pendding'];

        if (devChartSucceededPer > 0) {
            devPieColors.push(pieColors[0]);
            devSeriesData.push([seriesLabel[0], devChartSucceededPer]);
        }
        if (devChartRunningPer > 0) {
            devPieColors.push(pieColors[1]);
            devSeriesData.push([seriesLabel[1], devChartRunningPer]);
        }
        if (devChartFailedPer > 0) {
            devPieColors.push(pieColors[2]);
            devSeriesData.push([seriesLabel[2], devChartFailedPer]);
        }
        if (devChartPenddingPer > 0) {
            devPieColors.push(pieColors[3]);
            devSeriesData.push([seriesLabel[3], devChartPenddingPer]);
        }

        if (podsChartSucceededPer > 0) {
            podsPieColors.push(pieColors[0]);
            podsSeriesData.push([seriesLabel[0], podsChartSucceededPer]);
        }
        if (podsChartRunningPer > 0) {
            podsPieColors.push(pieColors[1]);
            podsSeriesData.push([seriesLabel[1], podsChartRunningPer]);
        }
        if (podsChartFailedPer > 0) {
            podsPieColors.push(pieColors[2]);
            podsSeriesData.push([seriesLabel[2], podsChartFailedPer]);
        }
        if (podsChartPenddingPer > 0) {
            podsPieColors.push(pieColors[3]);
            podsSeriesData.push([seriesLabel[3], podsChartPenddingPer]);
        }

        if (repsChartSucceededPer > 0) {
            repsPieColors.push(pieColors[0]);
            repsSeriesData.push([seriesLabel[0], repsChartSucceededPer]);
        }
        if (repsChartRunningPer > 0) {
            repsPieColors.push(pieColors[1]);
            repsSeriesData.push([seriesLabel[1], repsChartRunningPer]);
        }
        if (repsChartFailedPer > 0) {
            repsPieColors.push(pieColors[2]);
            repsSeriesData.push([seriesLabel[2], repsChartFailedPer]);
        }
        if (repsChartPenddingPer > 0) {
            repsPieColors.push(pieColors[3]);
            repsSeriesData.push([seriesLabel[3], repsChartPenddingPer]);
        }

        Highcharts.chart('piechart01', {
            chart: {
                type: 'pie',
                marginTop: 0
            },
            title: {
                text: 'Deployments',
                y : 120, // y position
                style: {
                    fontSize: '15px',
                    fontWeight: 'bold'
                }
            },
            plotOptions: {
                pie: {
                    innerSize: 110,
                    colors : devPieColors,
                    dataLabels: {
                        enabled: true,
                        format: '{point.percentage:.0f} %',
                        distance: -25,
                        style: {
                            fontSize: '14px',
                            fontWeight: 'bold'
                        }
                    }
                }
            },
            tooltip: {
                headerFormat: '',
                pointFormat: '{point.name}: <b>{point.y:.2f}%</b><br/>',
                footerFormat:''
            },
            series: [{
                data: devSeriesData
            }],
            credits: { // logo hide
                enabled: false
            }
        });
        Highcharts.chart('piechart02', {
            chart: {
                type: 'pie',
                marginTop: 0
            },
            title: {
                text: 'Pods',
                y : 120, // y position
                style: {
                    fontSize: '15px',
                    fontWeight: 'bold'
                }
            },
            plotOptions: {
                pie: {
                    innerSize: 110,
                    colors : podsPieColors,
                    dataLabels: {
                        enabled: true,
                        format: '{point.percentage:.0f} %',
                        distance: -25,
                        style: {
                            fontSize: '14px',
                            fontWeight: 'bold'
                        }
                    }
                }
            },
            tooltip: {
                headerFormat: '',
                pointFormat: '{point.name}: <b>{point.y:.2f}%</b><br/>',
                footerFormat:''
            },
            series: [{
                data: podsSeriesData
            }],
            credits: { // logo hide
                enabled: false
            }
        });
        Highcharts.chart('piechart03', {
            chart: {
                type: 'pie',
                marginTop: 0
            },
            title: {
                text: 'Replica Sets',
                y : 120, // y position
                style: {
                    fontSize: '15px',
                    fontWeight: 'bold'
                }
            },
            plotOptions: {
                pie: {
                    innerSize: 110,
                    colors : repsPieColors,
                    dataLabels: {
                        enabled: true,
                        format: '{point.percentage:.0f} %',
                        distance: -25,
                        style: {
                            fontSize: '14px',
                            fontWeight: 'bold'
                        }
                    }
                }
            },
            tooltip: {
                headerFormat: '',
                pointFormat: '{point.name}: <b>{point.y:.2f}%</b><br/>',
                footerFormat:''
            },
            series: [{
                data: repsSeriesData
            }],
            credits: { // logo hide
                enabled: false
            }
        });
    };

</script>