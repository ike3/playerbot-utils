<%@include file="header.jsp" %>

<div class="row" id="bot-search-results" >
	<div ng-show="botsLoading" class="loading-spiner"><img src="static/img/ajax-loader.gif" /></div>
	<table class="table bot-table" ng-hide="botsLoading">
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
				<th class="sortable" ng-click="changeSortOrder('liveData.state')">State
                    <span class="sortOrder" ng-show="sortBy === 'liveData.state'" ng-class="{sortDesc:sortDesc}"></span>
                </th>
				<th class="sortable" ng-click="changeSortOrder('liveData.target')">Target
                    <span class="sortOrder" ng-show="sortBy === 'liveData.target'" ng-class="{sortDesc:sortDesc}"></span>
                </th>
				<th class="sortable" ng-click="changeSortOrder('liveData.hp')">HP
                    <span class="sortOrder" ng-show="sortBy === 'liveData.hp'" ng-class="{sortDesc:sortDesc}"></span>
                </th>
				<th>Last Action</th>
				<th>Position (.go xyz)</th>
			</tr>
		</thead>
		<tr ng-repeat="bot in bots | orderBy:sortBy:sortDesc">
			<td><span ng-bind="bot.guid" /></td>
            <td><a href="bot.html?name={{bot.name}}" target="_blank"><span ng-bind="bot.name" /></a></td>
			<td><span ng-bind="bot.level" /></td>
			<td><img ng-src="{{'static/img/class/' + bot.cls + '.gif'}}"/></td>
			<td><img ng-src="{{'static/img/race/' + bot.race + '-' + bot.gender + '.gif'}}"/></td>
			<td>{{bot.liveData.state}}</td>
			<td>{{bot.liveData.target}}</td>
			<td>{{bot.liveData.hp}}</td>
			<td>{{bot.liveData.lastAction.text}}</td>
			<td>{{bot.liveData.position}}</td>
		</tr>
	</table>
</div>
