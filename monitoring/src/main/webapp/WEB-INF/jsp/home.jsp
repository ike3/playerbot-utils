<%@include file="header.jsp" %>

<body ng-app="monitoring">

	<div class="container" role="main" ng-controller="MonitoringController">
		<div class="page-header">
			<h1>
                MaNGOS-Bot Monitoring
                <div class="col-sm-2 pull-right">
                    <input type="button" value="Pause" class="btn btn-warning" ng-click="stopLiveUpdate()" ng-disabled="!liveUpdate">
                    <input type="button" value="Continue" class="btn btn-success" ng-click="startLiveUpdate()" ng-disabled="liveUpdate">
                </div>
            </h1>
		</div>

        <%@include file="search-form.jsp" %>
        <div ng-show="viewMode == 'Table'">
            <%@include file="search-results.jsp" %>
        </div>
        <div ng-show="viewMode == 'Map'">
            <%@include file="search-map.jsp" %>
        </div>
	</div>
</body>

</html>