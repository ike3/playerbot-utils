<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="static/css/bootstrap.min.css">
<link rel="stylesheet" href="static/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="static/css/darkstrap.css">

<link rel="stylesheet" href="static/css/index.css">

<script src="static/js/jquery.min.js"></script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/angular.min.js"></script>

<script src="static/js/index.js"></script>

<title>MaNGOS-Bot Monitoring</title>
</head>

<body ng-app="monitoring">
	<div class="container" role="main" ng-controller="MonitoringController">
		<div class="page-header">
			<h1>MaNGOS-Bot Monitoring</h1>
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
					<div class="col-sm-offset-2 col-sm-10">
						<input type="submit" value="Find" class="btn btn-primary">
					</div>
				</div>
			</form>
		</div>

		<div class="row" id="bot-search-results" >
			<div ng-show="botsLoading" class="loading-spiner"><img src="static/img/ajax-loader.gif" /></div>
			<table class="table" ng-hide="botsLoading">
				<thead>
					<tr>
						<th>Guid</th>
						<th>Name</th>
						<th>Class</th>
						<th>Race</th>
					</tr>
				</thead>
				<tr ng-repeat="bot in bots">
					<td><span ng-bind="bot.guid" /></td>
					<td><span ng-bind="bot.name" /></td>
					<td><img ng-src="{{'static/img/class/' + bot.cls + '.gif'}}"/></td>
					<td><img ng-src="{{'static/img/race/' + bot.race + '-' + bot.gender + '.gif'}}"/></td>
				</tr>
			</table>
		</div>
	</div>
</body>

</html>