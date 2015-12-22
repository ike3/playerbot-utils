var MAX_HISTORY_SIZE = 30;
angular.module('monitoring', [])
.config(['$locationProvider', function($locationProvider) {
        $locationProvider.html5Mode(true);
}])
.filter('reverse', function() {
	return function(items) {
		return items.slice().reverse();
	};
})
.filter('unique', function() {
	return function(history) {
		var action;
		var result = [];
		for (var i = 0; i < history.length; i++) {
			if (history[i] != action) {
				action = history[i];
				result.push(history[i]);
			}
		}
		return result;
	};
})
.controller('MonitoringController',
		[ '$scope', '$http', '$interval', '$location', function($scope, $http, $interval, $location) {
			$scope.botName = $location.search().name;
			$scope.faction = "Alliance";

			$scope.bots = [];
			$scope.botsLoading = false
			$scope.liveUpdate = null;

			$scope.stopLiveUpdate = function() {
				if ($scope.liveUpdate) {
					$interval.cancel($scope.liveUpdate);
				}
				$scope.liveUpdate = null;
			};

			function convertAction(str) {
				var p1 = str.indexOf(":");
				var p2 = str.lastIndexOf(" - ");
				var action = { type: "info", text: str };
				if (p1 != -1) {
					action.type = str.substring(0, p1);
				}
				if (p2 != -1) {
					action.result = str.substring(p2 + 3);
				}
				if (p1 != -1 && p2 == -1) {
					action.text = str.substring(p1 + 1);
				}
				if (p1 != -1 && p2 != -1) {
					action.text = str.substring(p1 + 1, p2);
				}
				return action;
			}

			function groupActions(actions) {
				var result = [];
				var type;
				for (var i = 0; i < actions.length; i++) {
					var action = actions[i];
					if (!action.text) continue;
					if (action.type == "info" || action.type == "T") {
						result.push({type: action.type, actions: [action]});
					} else if (action.type != type) {
						type = action.type;
						result.push({type: type, actions: [action]});
					} else {
						result[result.length - 1].actions.push(action);
					}
				}
				return result;
			}

			$scope.startLiveUpdate = function() {
				$scope.liveUpdate = $interval(function() {
					$http.post("bot/live-data.json",
							$scope.bots.map(function(item) { return item.guid; })
					).success(function(data) {
						$($scope.bots).each(function(idx, bot) {
							bot.liveData = data[bot.guid];

							bot.liveData.actions = [];
							angular.forEach(bot.liveData.action.split("|"), function(action) {
								bot.liveData.actions.push(convertAction(action));
							});
							bot.liveData.lastAction = bot.liveData.actions[bot.liveData.actions.length - 1];
							bot.liveData.actionGroups = groupActions(bot.liveData.actions);

							if (!bot.actionHistory) bot.actionHistory = [];
							angular.forEach(bot.liveData.actionGroups, function(group) {
								bot.actionHistory.push(group);
							});
							if (bot.actionHistory.length > MAX_HISTORY_SIZE) bot.actionHistory.splice(0, bot.actionHistory.length - MAX_HISTORY_SIZE);
						});
					});
				}, 100);
			};

			$scope.search = function() {
				$scope.bots = [];
				$scope.botsLoading = true;
				$http.post("bots.json", {
					'name' : $scope.botName,
					'faction' : $scope.faction,
				}).success(function(data) {
					$scope.bots = data;
					$scope.botsLoading = false;
					$scope.startLiveUpdate();
				});
			};



			$scope.search();
		} ]);