<%@include file="header.jsp" %>

<div class="row" id="bot-search-results" >
	<div ng-show="botsLoading" class="loading-spiner"><img src="static/img/ajax-loader.gif" /></div>
	<table class="table" ng-hide="botsLoading">
		<thead>
			<tr>
				<th width="3%">Guid</th>
				<th class="sortable" width="10%" ng-click="changeSortOrder('name')">Name
                    <span class="sortOrder" ng-show="sortBy === 'name'" ng-class="{sortDesc:sortDesc}"></span>
                </th>
				<th class="sortable" width="8%" ng-click="changeSortOrder('cls')">Class
                    <span class="sortOrder" ng-show="sortBy === 'cls'" ng-class="{sortDesc:sortDesc}"></span>
                </th>
				<th class="sortable" width="8%" ng-click="changeSortOrder('race')">Race
                    <span class="sortOrder" ng-show="sortBy === 'race'" ng-class="{sortDesc:sortDesc}"></span>
                </th>
				<th class="sortable" width="10%" ng-click="changeSortOrder('liveData.state')">State
                    <span class="sortOrder" ng-show="sortBy === 'liveData.state'" ng-class="{sortDesc:sortDesc}"></span>
                </th>
				<th class="sortable" width="15%" ng-click="changeSortOrder('liveData.target')">Target
                    <span class="sortOrder" ng-show="sortBy === 'liveData.target'" ng-class="{sortDesc:sortDesc}"></span>
                </th>
				<th class="sortable" width="10%" ng-click="changeSortOrder('liveData.hp')">HP
                    <span class="sortOrder" ng-show="sortBy === 'liveData.hp'" ng-class="{sortDesc:sortDesc}"></span>
                </th>
				<th width="20%">Action</th>
				<th>Position</th>
			</tr>
		</thead>
		<tr ng-repeat="bot in bots | orderBy:sortBy:sortDesc">
			<td><span ng-bind="bot.guid" /></td>
            <td><a href="bot.html?name={{bot.name}}" target="_blank"><span ng-bind="bot.name" /></a></td>
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
