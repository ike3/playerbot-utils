<%@include file="header.jsp" %>

<body ng-app="monitoring">

	<div class="container" role="main" ng-controller="MonitoringController">
		<div class="page-header">
			<h1>
                Bot Problems <span ng-show="coldStart">(please wait to populate - {{iterCount}})</span>
                <div class="col-sm-2 pull-right">
                    <input type="button" value="Pause" class="btn btn-warning" ng-click="stopLiveUpdate()" ng-disabled="!liveUpdate">
                    <input type="button" value="Continue" class="btn btn-success" ng-click="startLiveUpdate()" ng-disabled="liveUpdate">
                </div>
            </h1>
		</div>

        <%@include file="search-form.jsp" %>
        <div class="row" id="bot-search-results" >
            <div ng-show="botsLoading" class="loading-spiner"><img src="static/img/ajax-loader.gif" /></div>
            <table class="table bot-problem-table" ng-hide="botsLoading">
                <thead>
                    <tr>
                        <th>Guid</th>
                        <th class="sortable" ng-click="changeSortOrder('name')">Name
                            <span class="sortOrder" ng-show="sortBy === 'name'" ng-class="{sortDesc:sortDesc}"></span>
                        </th>
                        <th class="sortable" ng-click="changeSortOrder('level')">Lvl
                            <span class="sortOrder" ng-show="sortBy === 'level'" ng-class="{sortDesc:sortDesc}"></span>
                        </th>
                        <th class="sortable" ng-click="changeSortOrder('cls')">C
                            <span class="sortOrder" ng-show="sortBy === 'cls'" ng-class="{sortDesc:sortDesc}"></span>
                        </th>
                        <th class="sortable" ng-click="changeSortOrder('race')">R
                            <span class="sortOrder" ng-show="sortBy === 'race'" ng-class="{sortDesc:sortDesc}"></span>
                        </th>
                        <th class="sortable" ng-click="changeSortOrder('problems')">Problems
                            <span class="sortOrder" ng-show="sortBy === 'problems'" ng-class="{sortDesc:sortDesc}"></span>
                        </th>
                    </tr>
                </thead>
                <tr ng-repeat="bot in bots | orderBy:sortBy:sortDesc">
                    <td><span ng-bind="bot.guid" /></td>
                    <td><a href="bot.html?name={{bot.name}}" target="_blank"><span ng-bind="bot.name" /></a></td>
                    <td><span ng-bind="bot.level" /></td>
                    <td><img ng-src="{{'static/img/class/' + bot.cls + '.gif'}}"/></td>
                    <td><img ng-src="{{'static/img/race/' + bot.race + '-' + bot.gender + '.gif'}}"/></td>
                    <td>{{bot.problems}}</td>
                </tr>
            </table>
        </div>
	</div>
</body>

</html>