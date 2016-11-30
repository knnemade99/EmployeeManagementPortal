var myApp = angular.module('myApp', ['ngRoute', 'ngCookies'] );

function EMPController($scope,$http,$location,$q) {
	
	var REST_SERVICE_URI="http://localhost:9091/EmployeeManagementPortal/";
	/* login */
	 $scope.login= function() {

	        $http({
		           method : 'POST',
		           url : REST_SERVICE_URI+'login',
		           headers : {
		                 'Content-Type' : 'application/json'
		           },
		           data : {
		                username:$scope.username,
		           		password:$scope.password
		                 }
		    }).then(function successCallback(response) {
		    	  console.log("logged in successfully");
		          $location.path("/manageUser");
		         
		    }, function errorCallback(response) {
		    	  console.log("logged in not successfully");
		          $location.path("/login");
		    }
	 );
	 }
}

myApp.controller('EMPController',EMPController);

myApp.config(function($routeProvider) {
    $routeProvider  
    .when('/', {
		controller: 'EMPController',
		templateUrl: 'resources/views/login.html'
	})
    .otherwise({redirectTo: '/'})
   
});
