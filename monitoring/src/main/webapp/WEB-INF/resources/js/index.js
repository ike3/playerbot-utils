angular.module('monitoring', []).controller('MonitoringController',
		[ '$scope', '$http', function($scope, $http) {
			$scope.botName = "";
			$scope.faction = "";
			$scope.bots = [];
			$scope.search = function() {
				$http.post("bot-list.json", {
					'name' : $scope.botName,
					'faction' : $scope.faction,
				}).success(function(data) {
					$scope.bots = data;
				});
			};
		} ]);