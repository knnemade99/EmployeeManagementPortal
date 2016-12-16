var myApp = angular.module('myApp', ['ngRoute', 'ngCookies', 'checklist-model'] );

function EMPController($scope,$http,$location,$q,$rootScope,$cookieStore) {
	
	var REST_SERVICE_URI="http://localhost:9091/EmployeeManagementPortal/";
	
	$scope.art = {
		    skill: []
		  };
	
	$scope.activeTab = 1;
	
	$scope.setActiveTab= function (tabToSet){
		$scope.activeTab = tabToSet;
	}
	
	$scope.nextTab= function (){
		if($scope.activeTab<5){
			$scope.activeTab=$scope.activeTab+1;
		}
	}
	
	$scope.previousTab= function (){
		if($scope.activeTab>1){
			$scope.activeTab=$scope.activeTab-1;
		}
	}
	
	
	
	$rootScope.initializeApp= function (){
		if($cookieStore.get("authToken")==null){
			  $cookieStore.remove("authToken");
			  $cookieStore.remove("name");
			  $cookieStore.remove("usertype");
			  $cookieStore.remove("isLogged");
			  $cookieStore.remove("targetEmpId");
			  $cookieStore.remove("loggedUserId");
			  
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
		    	  $rootScope.username = response.data.user.userCredential.username;
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
		    	  
		    	  //personal
		    	  $rootScope.designation=response.data.user.designation;
		    	  $rootScope.empId=response.data.user.empId;		    	  		    	 
		    	  $rootScope.dob=response.data.user.dob;
		    	  $rootScope.doj=response.data.user.doj;		    	  		    	  
		    	  $rootScope.gender=response.data.user.gender;
		    	  $rootScope.maritalStatus=response.data.user.maritalStatus;
		    	  
		    	  //contact
		    	  $rootScope.email=response.data.user.email;
		    	  $rootScope.address=response.data.user.address;
		    	  $rootScope.contact=response.data.user.contact;
		    	  $rootScope.about=response.data.user.about;
		    	  
		    	  //professional		    	  
		    	  $rootScope.salary=response.data.user.salary;		    	 
		    	  $rootScope.experience=response.data.user.experience;		    	  
		    	  $rootScope.manager=response.data.user.manager;
		    	  $rootScope.project=response.data.user.project;
		    	  $rootScope.skills=response.data.user.skills;
		    	  $rootScope.department=response.data.user.department;
		    	  
		    	  
		    	  console.log($rootScope.role);
		          $location.path("/viewProfile");
		         
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
	 
	 /* check for type */	 
	 $scope.checkForType= function() {
		 return $cookieStore.get("usertype");  
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
				  $cookieStore.remove("targetEmpId");
		          $location.path("/");
		         
		    }, function errorCallback(response) {
		    	  console.log("logged out not successfully");
		    	  $cookieStore.remove("authToken");
				  $cookieStore.remove("name");
				  $cookieStore.remove("usertype");
				  $cookieStore.remove("isLogged");
		          $location.path("/login");
		    });
	 }
	 
		/* get All Departments */
	 $scope.getAllDepartments= function() {
console.log("in getalldepartments");
	        $http({
		           method : 'GET',
		           url : REST_SERVICE_URI+'viewalldepartments',
		           headers : {
		                 'Content-Type' : 'application/json',
		                 'authToken' : $cookieStore.get("authToken")
		           },
		           data : {
		                
		                 }
		    }).then(function successCallback(response) {
		    	  $scope.departments=response.data;
		    	  console.log($scope.departments);
		         
		    }, function errorCallback(response) {
		    });
	 }
	 
	 /* get All Projects */
	 $scope.getAllProjects= function() {
		 console.log("in getallprojects");
	        $http({
		           method : 'GET',
		           url : REST_SERVICE_URI+'viewallprojects',
		           headers : {
		                 'Content-Type' : 'application/json',
		                 'authToken' : $cookieStore.get("authToken")
		           },
		           data : {
		                
		                 }
		    }).then(function successCallback(response) {
		    	  $scope.projects=response.data;
		         
		    }, function errorCallback(response) {
		    });
	 }
	 
	 /* get All Skills */
	 $scope.getAllSkills= function() {
		 console.log("in getallskills");
	        $http({
		           method : 'GET',
		           url : REST_SERVICE_URI+'viewallskills',
		           headers : {
		                 'Content-Type' : 'application/json',
		                 'authToken' : $cookieStore.get("authToken")
		           },
		           data : {
		                
		                 }
		    }).then(function successCallback(response) {
		    	  $scope.skills=response.data;
		         
		    }, function errorCallback(response) {
		    });
	 }
	 
	 
		/* forgot password */
	 $scope.forgotPassword= function() {

	        $http({
		           method : 'PUT',
		           url : REST_SERVICE_URI+'forgetpassword',
		           headers : {
		                 'Content-Type' : 'application/json',
		           },
		           data : {
		                	"email": $scope.email
		                 }
		    }).then(function successCallback(response) {
		    	  
		          $location.path("/");
		         
		    }, function errorCallback(response) {
		    	 
		          
		    });
	 }
	 
		/* change password */
	 $scope.changePassword= function() {

	        $http({
		           method : 'PUT',
		           url : REST_SERVICE_URI+'changepassword',
		           headers : {
		                 'Content-Type' : 'application/json',
		                 'authToken' : $cookieStore.get("authToken")
		           },
		           data : {
		        	   		"oldPassword": $scope.oldPassword,
		        	   		"newPassword": $scope.newPassword
		                 }
		    }).then(function successCallback(response) {
		    	  
		          $location.path("/landingPage");
		         
		    }, function errorCallback(response) {
		    	 
		          
		    });
	 }
	 
	 /* Add Employee */
	 $scope.addEmployee= function() {
		 
	console.log($scope.newEmail);
	console.log($scope.newUserType);
	console.log($scope.newUsername);
	console.log($scope.newAccountPassword);
	console.log($scope.newName);
	console.log($scope.newGender);
	console.log($scope.newDob);
	console.log($scope.newAbout);
	console.log($scope.newCountry);
	console.log($scope.newState);
	console.log($scope.newCity);
	console.log($scope.newMobile);
	console.log($scope.newPinCode);
	console.log($scope.newDesignation);
	console.log($scope.newDepartment);
	console.log($scope.newBasicSalary);
	console.log($scope.newProject);
	console.log($scope.newHra);
	console.log($scope.art);
	console.log($scope.newLta);
	console.log($scope.newExperience);
	console.log($scope.newDoj);
	console.log($scope.newMaritalStatus);
	
	$scope.newArt = {
		    newSkill: []
		  };
	
var data=$scope.art.skill;
console.log("dat:" +data);
	angular.forEach(data, function(v, k) {
	    $scope.newArt.newSkill.push({
	      'skillName': v
	    });
	  });
	console.log($scope.newArt.newSkill);

	        $http({
		           method : 'POST',
		           url : REST_SERVICE_URI+'addemployee',
		           headers : {
		                 'Content-Type' : 'application/json',
		                 'authToken' : $cookieStore.get("authToken")
		           },
		           data : {
			        	   "skills": $scope.newArt.newSkill,
		        		    "userCredential": {
		        		      "username": $scope.newUsername,
		        		      "password": $scope.newAccountPassword
		        		    },
		        		    "address": {
		        		      "city": $scope.newCity,
		        		      "state": $scope.newState,
		        		      "country": $scope.newCountry,
		        		      "zip": $scope.newPinCode
		        		    },
		        		    "salary": {
		        		      "basic": $scope.newBasicSalary,
		        		      "hra": $scope.newHra,
		        		      "lta": $scope.newLta
		        		    },
		        		    "project":{
		        		    	"projectName":$scope.newProject
		        		    },
		        		    "name": $scope.newName,
		        		    "email": $scope.newEmail,
		        		    "about": $scope.newAbout,
		        		    "contact": $scope.newMobile,
		        		    "department": {
		        		      "departmentName": $scope.newDepartment
		        		    },
		        		    "designation": $scope.newDesignation,
		        		    "dob": $scope.newDob,
		        		    "doj": $scope.newDoj,
		        		    "lockStatus": "unlock",
		        		    "gender": $scope.newGender,
		        		    "maritalStatus": $scope.newMaritalStatus,
		        		    "experience": $scope.newExperience,
		        		    "usertype": $scope.newUserType
		        		  }
		    }).then(function successCallback(response) {
		    	  
		          $location.path("/landingPage");
		         
		    }, function errorCallback(response) {
		    	 
		          
		    });
	 }
	 
	 /* get list of employees */
	 $scope.getAllEmployees= function() {

	        $http({
		           method : 'GET',
		           url : REST_SERVICE_URI+'viewallemployees',
		           headers : {
		                 'Content-Type' : 'application/json',
		                 'authToken' : $cookieStore.get("authToken")
		           },
		           data : {
		        	   		
		                 }
		    }).then(function successCallback(response) {
		    	  
		    	var data = response.data;
				$rootScope.UserList=[];
				angular.forEach(data, function(value, key) {
					$rootScope.UserList = data;					
			    });
			
				console.log($rootScope.UserList + "Done!");
		    	  
		          
		         
		    }, function errorCallback(response) {
		    	 
		          
		    });
	 }
	 
	 /* to fetch id for delete function */
	 $scope.getUserId = function(uid){
			$rootScope.delId = uid;
		}
	 
	 /* delete employee by id */
	 $scope.deleteEmployee= function(id) {

	        $http({
		           method : 'DELETE',
		           url : REST_SERVICE_URI+'deleteemployee/'+id,
		           headers : {
		                 'Content-Type' : 'application/json',
		                 'authToken' : $cookieStore.get("authToken")
		           },
		           data : {
		        	   		
		                 }
		    }).then(function successCallback(response) {
		    	
		    	for(var v=0;v<$rootScope.UserList.length;v++){
					if(id==$rootScope.UserList[v].empId)
					{
						$rootScope.UserList.splice(v,1);
					}
				}
				bootbox.dialog({
					  message: 'User deleted successfully!',
					  onEscape: function() { console.log("Ecsape"); },
					  backdrop: true
					});
				
		    	  
		    			         
		    }, function errorCallback(response) {
		    	 
		    	bootbox.dialog({
					  message: 'Deleting the user failed!',
					  onEscape: function() { console.log("Ecsape"); },
					  backdrop: true
					});
		    });
	 }
	 

	 /* to fetch id for view function */
	 $scope.getIdForUser = function(){
		 $scope.viewEmpProfile($cookieStore.get("targetEmpId"));
		}
	 
	 
	 /* get own profile*/
	 $scope.viewProfile= function() {

		 $http({
	           method : 'GET',
	           url : REST_SERVICE_URI+'viewprofile',
	           headers : {
	                 'Content-Type' : 'application/json',
	                 'authToken' : $cookieStore.get("authToken")
	           },
	           data : {
	        	   		
	                 }
	    }).then(function successCallback(response) {
	    	  
	    	  $rootScope.profileName = response.data.name;
	    	  $rootScope.usertype = response.data.usertype;
	    	  $rootScope.lockStatus=response.data.lockStatus;

	    	  
	    	  //personal
	    	  $rootScope.designation=response.data.designation;
	    	  $rootScope.empId=response.data.empId;		    	  		    	 
	    	  $rootScope.dob=response.data.dob;
	    	  $rootScope.doj=response.data.doj;		    	  		    	  
	    	  $rootScope.gender=response.data.gender;
	    	  $rootScope.maritalStatus=response.data.maritalStatus;
	    	 
	    	  //contact
	    	  $rootScope.email=response.data.email;
	    	  $rootScope.address=response.data.address;
	    	  $rootScope.contact=response.data.contact;
	    	  $rootScope.about=response.data.about;
	    	  
	    	  //professional		    	  
	    	  $rootScope.salary=response.data.salary;		    	 
	    	  $rootScope.experience=response.data.experience;		    	  
	    	  $rootScope.manager=response.data.manager;
	    	  $rootScope.project=response.data.project;
	    	  $rootScope.skills=response.data.skills;
	    	  $rootScope.department=response.data.department;
	    	  
			   $location.path("/viewProfile");
	         
	    }, function errorCallback(response) {

	    });
	 }
	 
	 
	 /* get specific emp profile*/
	 $scope.viewEmpProfile= function(id) {
	
		 $http({
	           method : 'GET',
	           url : REST_SERVICE_URI+'viewemployee/'+id,
	           headers : {
	                 'Content-Type' : 'application/json',
	                 'authToken' : $cookieStore.get("authToken")
	           },
	           data : {
	        	   		
	                 }
	    }).then(function successCallback(response) {
	    	  
	    	  $rootScope.profileName = response.data.name;
	    	  $rootScope.usertype = response.data.usertype;
	    	  $rootScope.lockStatus=response.data.lockStatus;
	    	  $rootScope.isLogged = "true";
	    	  
	    	  //personal
	    	  $rootScope.designation=response.data.designation;
	    	  $rootScope.empId=response.data.empId;		    	  		    	 
	    	  $rootScope.dob=response.data.dob;
	    	  $rootScope.doj=response.data.doj;		    	  		    	  
	    	  $rootScope.gender=response.data.gender;
	    	  $rootScope.maritalStatus=response.data.maritalStatus;
	    	 
	    	  //contact
	    	  $rootScope.email=response.data.email;
	    	  $rootScope.address=response.data.address;
	    	  $rootScope.contact=response.data.contact;
	    	  $rootScope.about=response.data.about;
	    	  
	    	  //professional		    	  
	    	  $rootScope.salary=response.data.salary;		    	 
	    	  $rootScope.experience=response.data.experience;		    	  
	    	  $rootScope.manager=response.data.manager;
	    	  $rootScope.project=response.data.project;
	    	  $rootScope.skills=response.data.skills;
	    	  $rootScope.department=response.data.department;
	    	  
	    	  $cookieStore.put("targetEmpId",id);
	    	  
			  $location.path("/viewEmployeeProfile");
	         
	    }, function errorCallback(response) {

	    });
	 }
	 

	 
	 /* update employee profile */
	 $scope.updateEmployee= function() {
		 
		 console.log($scope.address);
		 console.log($scope.name);
		 console.log($scope.maritalStatus);
		 console.log($scope.about);
		 console.log($scope.contact);
		 console.log($scope.experience);

	        $http({
		           method : 'PUT',
		           url : REST_SERVICE_URI+'updateprofile/',
		           headers : {
		                 'Content-Type' : 'application/json',
		                 'authToken' : $cookieStore.get("authToken")
		           },
		           data : {
		        	   "address": {
		     		      "city": $scope.address.city,
		     		      "state": $scope.address.state,
		     		      "country": $scope.address.country,
		     		      "zip": $scope.address.zip
		     		    },
		     		    "name": $scope.name,
		     		    "about": $scope.about,
		     		    "contact": $scope.contact,
		     		    "maritalStatus": $scope.maritalStatus,
		     		    "experience": $scope.experience
		                 }
		    }).then(function successCallback(response) {
		    	bootbox.dialog({
					  message: 'Profile updated successfully!',
					  onEscape: function() { console.log("Ecsape"); },
					  backdrop: true
					});
		    	$location.path("/viewProfile");
		    	
		    }, function errorCallback(response) {
		    	bootbox.dialog({
					  message: 'Profile could not be updated!',
					  onEscape: function() { console.log("Ecsape"); },
					  backdrop: true
					});
		          
		    });
	 }
	 
	 
}

myApp.directive("formatDate", function(){
	  return {
		   require: 'ngModel',
		    link: function(scope, elem, attr, modelCtrl) {
		      modelCtrl.$formatters.push(function(modelValue){
		        return new Date(modelValue);
		      })
		    }
		  }
		})

myApp.controller('EMPController',EMPController);

myApp.config(function($routeProvider) {
    $routeProvider  
    .when('/', {
		controller: 'EMPController',
		templateUrl: 'resources/views/login.html'
	})
	.when('/viewProfile', {
		controller: 'EMPController',
		templateUrl: 'resources/views/view-profile.html'
	})
	.when('/viewEmployeeProfile', {
		controller: 'EMPController',
		templateUrl: 'resources/views/view-emp-profile.html'
	})
	.when('/forgotPassword', {
		controller: 'EMPController',
		templateUrl: 'resources/views/forgotPassword.html'
	})
	.when('/changePassword', {
		controller: 'EMPController',
		templateUrl: 'resources/views/change-password.html'
	})
	.when('/addEmployee', {
		controller: 'EMPController',
		templateUrl: 'resources/views/add-employee.html'
	})
	.when('/getAllUsers', {
		controller: 'EMPController',
		templateUrl: 'resources/views/user-list.html'
	})	
	.when('/editProfile', {
		controller: 'EMPController',
		templateUrl: 'resources/views/edit-employee.html'
	})
    .otherwise({redirectTo: '/'})  
    
});
