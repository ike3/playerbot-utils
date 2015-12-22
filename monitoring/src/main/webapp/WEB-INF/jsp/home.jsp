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

		<div class="row">
			<form id="bot-search-form" class="form-horizontal" role="form" ng-submit="search()">
				<div class="form-group">
					<label for="botName" class="col-sm-2 control-label">Name</label>
					<div class="col-sm-10">
						<input class="form-control" type="text" name="botName" ng-model="botName" placeholder="Enter first symbols of a character name"/>
					</div>
				</div>
				<div class="form-group">
					<label for="botName" class="col-sm-2 control-label">Faction</label>
					<div class="col-sm-10">
						<div class="radio">
						  <label>
						    <input type="radio" name="faction" id="factionAlliance" value="Alliance" ng-model="faction">
						    Alliance
						  </label>
						</div>
						<div class="radio">
						  <label>
						    <input type="radio" name="faction" id="factionHorde" value="Horde" ng-model="faction" >
						    Horde
						  </label>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-2">
						<input type="submit" value="Find" class="btn btn-primary">
					</div>
				</div>
			</form>
		</div>

        <%@include file="search-results.jsp" %>
	</div>
</body>

</html>