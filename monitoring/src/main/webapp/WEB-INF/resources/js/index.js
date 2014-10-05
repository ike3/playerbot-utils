angular.module('monitoring', []).controller('MonitoringController',
		[ '$scope', '$http', '$interval', function($scope, $http, $interval) {
			$scope.botName = "";
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

			$scope.startLiveUpdate = function() {
				$scope.liveUpdate = $interval(function() {
					$http.post("bot/live-data.json",
							$scope.bots.map(function(item) { return item.guid; })
					).success(function(data) {
						$($scope.bots).each(function(idx, bot) {
							bot.liveData = data[bot.guid];
						});
					});
				}, 500);
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