<%--
  Common yaml

  @author kjhoon
  @version 1.0
  @since 2020.08.20
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="content">
    <h1 class="view-title"><span class="detail_icon"><i class="fas fa-file-alt"></i></span> <span id="commonYamlViewTitle"> - </span></h1>
    <jsp:include page="../common/contentsTab.jsp" flush="true"/>
    <!-- YAML 시작 (YAML start)-->
    <div class="cluster_content03 row two_line two_view harf_view custom_display_block">
        <ul class="maT30">
            <li>
                <div class="sortable_wrap">
                    <div class="sortable_top">
                        <p>YAML</p>
                    </div>
                    <div class="paA30">
                        <div class="yaml">
                            <pre class="brush: yaml" id="resultCommonYamlArea"> -
                            </pre>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
    </div>
    <!-- YAML 끝 (YAML end)-->
</div>

<%--SyntexHighlighter--%>
<jsp:include page="../common/syntaxHighlighter.jsp" flush="true"/>


<script type="text/javascript">

    // GET DETAIL
    var procGetCommonDetailYaml = function (reqUrl, titleString) {
        procViewLoading('show');

        $('#commonYamlViewTitle').html(titleString);
        procCallAjax(reqUrl, "GET", null, null, callbackProcGetCommonDetailYaml);
    };


    // CALLBACK
    var callbackProcGetCommonDetailYaml = function (data) {
        if (!procCheckValidData(data)) {
            procViewLoading('hide');
            procAlertMessage('Yaml 조회에 실패하였습니다.', false);
            return false;
        }

        $('#resultCommonYamlArea').html('---\n' + data.sourceTypeYaml);
        procViewLoading('hide');
    };

</script>
