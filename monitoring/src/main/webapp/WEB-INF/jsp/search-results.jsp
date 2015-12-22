<%@include file="header.jsp" %>

<div class="row" id="bot-search-results" >
	<div ng-show="botsLoading" class="loading-spiner"><img src="static/img/ajax-loader.gif" /></div>
	<table class="table" ng-hide="botsLoading">
		<thead>
			<tr>
				<th width="3%">Guid</th>
				<th width="10%">Name</th>
				<th width="3%">Class</th>
				<th width="3%">Race</th>
				<th width="10%">State</th>
				<th width="15%">Target</th>
				<th width="10%">HP</th>
				<th width="20%">Action</th>
				<th>Position</th>
			</tr>
		</thead>
		<tr ng-repeat="bot in bots">
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
