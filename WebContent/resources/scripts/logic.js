var myApp = angular.module('myApp', ['ngRoute', 'ngCookies'] );

function EMPController($scope,$http,$location,$q,$rootScope,$cookieStore) {
	
	var REST_SERVICE_URI="http://localhost:9091/EmployeeManagementPortal/";
	
	
	console.log("Default value of isLogged: "+$scope.isLogged);
	
	$rootScope.initializeApp= function (){
		if($cookieStore.get("authToken")==null){
			  $cookieStore.remove("authToken");
			  $cookieStore.remove("name");
			  $cookieStore.remove("usertype");
			  $cookieStore.remove("isLogged");
		}
	}
	
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
		    	  $rootScope.authToken=response.data.authToken;
		    	  $rootScope.name = response.data.user.name;
		    	  $rootScope.usertype = response.data.user.usertype;
		    	  $rootScope.lockStatus=response.data.user.lockStatus;
		    	  $rootScope.isLogged = "true";
		    	  
		    	  console.log("logged in successfully: "+ $scope.authToken);		    	  
		    	  $cookieStore.put("isLogged", $rootScope.isLogged);
		    	  $cookieStore.put("name", $rootScope.name );
		    	  $cookieStore.put("authToken",$rootScope.authToken);
		    	  $cookieStore.put("usertype", $rootScope.usertype);
		    	  $cookieStore.put("lockStatus", $rootScope.lockStatus);
		    	  console.log("Value of isLogged after login: "+$scope.isLogged);
		    	  
		    	  //personal
		    	  $rootScope.role=response.data.user.designation;
		    	  $rootScope.empId=response.data.user.empId;
		    	  $rootScope.about=response.data.user.about;		    	  
		    	  $rootScope.dob=response.data.user.dob;
		    	  $rootScope.doj=response.data.user.doj;		    	  		    	  
		    	  $rootScope.gender=response.data.user.gender;
		    	  $rootScope.maritalStatus=response.data.user.maritalStatus;
		    	  
		    	  //contact
		    	  $rootScope.email=response.data.user.email;
		    	  $rootScope.address=response.data.user.address;
		    	  $rootScope.contact=response.data.user.contact;
		    	  
		    	  //professional
		    	  $rootScope.salary=response.data.user.salary;		    	 
		    	  $rootScope.experience=response.data.user.experience;		    	  
		    	  $rootScope.manager=response.data.user.manager;
		    	  $rootScope.project=response.data.user.project;
		    	  $rootScope.skills=response.data.user.skills;
		    	  $rootScope.department=response.data.user.department;
		    	  
		    	  
		    	  console.log($rootScope.role);
		          $location.path("/landingPage");
		         
		    }, function errorCallback(response) {
		    	  console.log("logged in not successfully");
		          $location.path("/login");
		    }
	 );}
	 
	 /* check for login */
	 $scope.checkForLogin= function() {
		 
			 return $cookieStore.get("isLogged");
		 
	       
	 }
	 
	 /* check for name */	 
	 $scope.checkForName= function() {
		 
		 return $cookieStore.get("name");
	 
       
	 }
	 
		/* logout */
	 $scope.logout= function() {

	        $http({
		           method : 'DELETE',
		           url : REST_SERVICE_URI+'logout',
		           headers : {
		                 'Content-Type' : 'application/json',
		                 'authToken' : $cookieStore.get("authToken")
		           },
		           data : {
		                
		                 }
		    }).then(function successCallback(response) {
		    	  console.log("logged out successfully ");
		    	  $rootScope.isLogged = "false";  
		  		  $cookieStore.put("isLogged", $rootScope.isLogged);
		  		  $cookieStore.remove("authToken");
				  $cookieStore.remove("name");
				  $cookieStore.remove("usertype");
				  $cookieStore.remove("isLogged");
		    	  console.log("Value of isLogged after logout: "+$scope.isLogged);
		          $location.path("/manageUser");
		         
		    }, function errorCallback(response) {
		    	  console.log("logged out not successfully");
		    	  $cookieStore.remove("authToken");
				  $cookieStore.remove("name");
				  $cookieStore.remove("usertype");
				  $cookieStore.remove("isLogged");
		          $location.path("/login");
		    });
	        }
	 
	 
	 
}

myApp.controller('EMPController',EMPController);

myApp.config(function($routeProvider) {
    $routeProvider  
    .when('/', {
		controller: 'EMPController',
		templateUrl: 'resources/views/login.html'
	})
	.when('/landingPage', {
		controller: 'EMPController',
		templateUrl: 'resources/views/landing-page.html'
	})
    .otherwise({redirectTo: '/'})
   
});
