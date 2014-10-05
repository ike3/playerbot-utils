angular.module('monitoring', []).controller('MonitoringController',
		[ '$scope', '$http', function($scope, $http) {
			$scope.botName = "";
			$scope.faction = "Alliance";

			$scope.bots = [];
			$scope.botsLoading = false

			$scope.search = function() {
				$scope.bots = [];
				$scope.botsLoading = true;
				$http.post("bot-list.json", {
					'name' : $scope.botName,
					'faction' : $scope.faction,
				}).success(function(data) {
					$scope.bots = data;
					$scope.botsLoading = false;
				});
			};

			$scope.search();
		} ]);