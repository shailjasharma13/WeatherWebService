var app = angular.module('weatherdata', []);

/*
 * weatherdata.config(function($routeProvider) { $routeProvider.when("/weather", {
 * controller : "UserData", templateUrl : "weather.html" }) })
 */

app.controller('GetAllUser', function($scope, $http) {
	

	$scope.goToLogin = function() {
	
		$http.get('http://localhost:8080/nouser').then(function(response) {
			$scope.numberofUser = response.data;
		});
		};
	 

	$http.get('http://localhost:8080/users').then(function(response) {
		$scope.users = response.data;
	});
	
	$http.get('http://localhost:8080/cities').then(function(response) {
		$scope.cities = response.data;
	});

})


app.controller('GetNumberOfUser', function($scope, $http) {

	$http.get('http://localhost:8080/nouser').then(function(response) {
		$scope.numberofUser = response.data;
	});

})

app.controller('AddUser', function($scope, $http) {

	$scope.addUser = function() {
		var config = {
			headers : {
				'Content-Type' : 'application/json'
			}
		}
		var user = $scope.user

		$http.post('http://localhost:8080/users', user, config).success(
				function(data, status, headers, config) {
					$scope.ResponseDetails = "Sign up sucessful.Please Login"
				}).error(
				function(user, status, header, config) {
					$scope.ResponseDetails = "Data: " + user + "<hr />status: "
							+ status + "<hr />headers: " + header
							+ "<hr />config: " + config;
				});
	};
})

app.controller('DeleteUser', function($scope, $http) {

	$scope.deleteUser = function() {
		var config = {
			headers : {
				'Content-Type' : 'application/json'
			}
		}
		var userId = $scope.userId

		$http.post('http://localhost:8080/user/' + userId).then(
				function(response) {
					if (response.data)
						$scope.msg = "Data Deleted Successfully!";
				}, function(response) {
					$scope.msg = "Service not Exists";
					$scope.statusval = response.status;
					$scope.statustext = response.statusText;
					$scope.headers = response.headers();
				});
	};
})

app.controller('UserLogin', function($scope, $http, $window, $location) {

	//
	$scope.userLogin = function() {
		var config = {
			headers : {
				'Content-Type' : 'application/json'
			}
		}

		var data = $scope.user
		$http.post('http://localhost:8080/user/login', data, config).success(
				function(data, status, headers, config) {
					url = 'http://localhost:8080/UserHome.html?name='
							+ encodeURIComponent(data.userId);
					window.location.href = url

					// $location.path ="/weather"
				}).error(
				function(user, status, header, config) {
					$scope.ResponseDetails = "Data: " + user + "<hr />status: "
							+ status + "<hr />headers: " + header
							+ "<hr />config: " + config;
				});
	};
})

app.controller('UserData',
		function($scope, $http, $window) {

			var url = $window.location.href, params = url.split('?')[1]
					.split('&'), data = {}, tmp;
			for (var i = 0, l = params.length; i < l; i++) {
				tmp = params[i].split('=');
				data[tmp[0]] = tmp[1];
			}
			$scope.userId = data.name;

			$http.get('http://localhost:8080/login').then(function(response) {
				$scope.weatherdata = "jhbhjb";
			});
		})
