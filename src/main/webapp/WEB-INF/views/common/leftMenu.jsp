<%--
  Left Menu

  @author kjhoon
  @version 1.0
  @since 2020.08.21
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.container.platform.web.user.common.Constants" %>
<nav style="height: 90%;">
    <div class="scroll_style">
        <ul class="nav_1d cp_nav">
            <li id="left-menu-intro" onclick="procMovePage('<%= Constants.CP_INIT_URI %>');">
                <dl>
                    <dt>
                        <div id="lnbimg19" class="lefticos">
                        </div>
                    </dt>
                    <dd>
                        <p>
                            Intro
                        </p>
                    </dd>
                </dl>
            </li>
            <li id="left-menu-workloads" onclick="procMovePage('<%= Constants.URI_WORKLOAD_OVERVIEW %>');">
                <dl>
                    <dt>
                        <div id="lnbimg17" class="">

                        </div>
                    </dt>
                    <dd>
                        <p>
                            Workloads
                        </p>
                    </dd>
                </dl>
            </li>
            <li id="left-menu-services" onclick="procMovePage('<%= Constants.URI_SERVICES %>');">
                <dl>
                    <dt>
                        <div id="lnbimg18" class="">

                        </div>
                    </dt>
                    <dd>
                        <p>
                            Services
                        </p>
                    </dd>
                </dl>
            </li>
            <li id="left-menu-storages" onclick="procMovePage('<%= Constants.URI_STORAGES %>');">
                <dl>
                    <dt>
                        <div id="lnbimg20" class="">

                        </div>
                    </dt>
                    <dd>
                        <p>
                            Storages
                        </p>
                    </dd>
                </dl>
            </li>
        </ul>
    </div>
</nav>
