var myApp = angular.module('myApp', ['ngRoute', 'ngCookies', 'checklist-model' , 'angularUtils.directives.dirPagination' , 'ngMessages' , 'jlareau.pnotify' , 'infinite-scroll'] );

function EMPController($scope,$http,$location,$q,$rootScope,$cookieStore,$route,notificationService, $timeout ){

	var REST_SERVICE_URI="http://localhost:9091/EmployeeManagementPortal/";
	
	//jquery library to fetch error messages from properties file
	jQuery.i18n.properties({
	    name:'messages', 
	    path:'resources/bundle/',
	    mode : 'map',
		cache : false
	    
	});
	
	//function to show animated load messages using PNotify library
	function dyn_notice() {
		var percent = 0;
		var notice = new PNotify({
			text: "Please Wait",
			type: 'info',
			icon: 'fa fa-spinner fa-spin',
			hide: false,
			buttons: {
				closer: false,
				sticker: false
			},
			opacity: .75,
			shadow: false,
			width: "170px"
		});

		setTimeout(function() {
			notice.update({
				title: false
			});
			var interval = setInterval(function() {
				percent += 2;
				var options = {
						text: percent + "% complete."
				};
				if (percent == 80) options.title = "Almost There";
				if (percent >= 100) {
					window.clearInterval(interval);
					options.title = "Done!";
					options.type = "success";
					options.hide = true;
					options.buttons = {
							closer: true,
							sticker: true,

					};
					hide: true
					options.icon = 'fa fa-check';
					options.opacity = 1;
					options.shadow = true;
					options.width = PNotify.prototype.options.width;
				}
				notice.update(options);
			}, 80);
		});
	}

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

	/* used to add a new project */
	$scope.addProject = function () {
		if($cookieStore.get("authToken")!=null){
			$http({
				method : 'POST',
				url : REST_SERVICE_URI+'addproject',
				headers : {
					'Content-Type' : 'application/json',
					'authToken' : $cookieStore.get("authToken")
				},
				data : {	

					'projectName' : $scope.newProjectName,
					'budget' : $scope.newBudget,
					'startDate' : $scope.newStartDate,
					'endDate' : $scope.newEndDate

				}
			}).then(function successCallback(response) {  

				$route.reload();
				bootbox.dialog({
					message: 'Project added successfully!',
					onEscape: function() { console.log("Ecsape"); },
					backdrop: true
				});

			}, function errorCallback(response) {

				bootbox.dialog({
					message: 'Project could not be added !',
					onEscape: function() { console.log("Ecsape"); },
					backdrop: true
				});

			});
		}
		else{
			$location.path("/");
		}
	}

	/* used to return login page */
	$scope.returnLogin = function(){
		if($cookieStore.get("authToken")!=null)
			$location.path("/viewProfile");
		else
			$location.path("/");
	}
	
	/* used to sort list of users */
	$scope.sort = function(keyname){
		$scope.sortKey = keyname;   //set the sortKey to the param passed
		$scope.reverse = !$scope.reverse; //if true make it false and vice versa
	}

	/* redirects to home page */
	$scope.homeRedirect = function(){
		$cookieStore.put("numberOfAttempts",0);
		$location.path("/");
	}

	/* redirects to lock page */
	$scope.lockRedirect = function(){
		$rootScope.lockedUserName=$cookieStore.get("usernameLogging");
		$rootScope.lockedMsg= jQuery.i18n.prop('error.login.attempt.exceeded');
		$rootScope.helpMsg= jQuery.i18n.prop('help.contact');
		$location.path("/lockscreen");
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
		$cookieStore.put("numberOfAttempts",0);
	}

	/* login */
	$scope.login= function() {		 

		if($cookieStore.get("numberOfAttempts")<2){
			$cookieStore.put("usernameLogging",$scope.username);
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



				if(response.data.user.lockStatus=="unlock"){
					$rootScope.authToken=response.data.authToken;
					$rootScope.username = response.data.user.userCredential.username;
					$rootScope.name = response.data.user.name;
					$rootScope.usertype = response.data.user.usertype;
					$rootScope.lockStatus=response.data.user.lockStatus;
					$rootScope.isLogged = "true";
		    	  
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
					$rootScope.selfGender=$rootScope.gender;
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

					new PNotify({
						title: 'Success',
						text: 'Welcome, '+$rootScope.name+', '+jQuery.i18n.prop('success.login'),
						type: 'success',
						animate: {
							animate: true,
							in_class: 'rotateInDownLeft',
							out_class: 'rotateOutUpRight'
						},
						delay: 1500,
						opacity: 0.8
					});


					$location.path("/viewProfile");		         
				}

				else{
					$scope.lockRedirect();
				}


			}, function errorCallback(response) {			

				if($cookieStore.get("usernameLogging")==$scope.username){
					$cookieStore.put("numberOfAttempts",($cookieStore.get("numberOfAttempts")+1));
				}

				new PNotify({
					title: 'Uh Oh!',
					text: jQuery.i18n.prop('error.username.notexist')+'.\n'+'You have only '+(3-$cookieStore.get("numberOfAttempts"))+' attempts left !',
					type: 'error',
					animate: {
						animate: true,
						in_class: 'bounceInDown',
						out_class: 'hinge'
					},
					delay:2000
				});
			});

		}
		else{

			$http({
				method : 'POST',
				url : REST_SERVICE_URI+'lockemployee/'+$cookieStore.get("usernameLogging"),
				headers : {
					'Content-Type' : 'application/json'
				},
				data : {	
				}
			}).then(function successCallback(response) {  

				$scope.lockRedirect();

			}, function errorCallback(response) {


				$location.path("/login");
			});

		}
	}

	/* check for login */
	$scope.checkForLogin= function() {
		if($cookieStore.get("authToken")!=null){
			return $cookieStore.get("isLogged");
		}
		else{
			return false;
		}
	}

	/* check for userType */
	$scope.getUserType= function() {
		if($cookieStore.get("authToken")!=null){
			return $cookieStore.get("usertype");
		}
		else{
			return null;
		}
	}

	/* check for name */	 
	$scope.checkForName= function() {
		if($cookieStore.get("authToken")!=null){
			return $cookieStore.get("name");
		}
		else{
			return null;
		}
	}

	/* check for type */	 
	$scope.checkForType= function() {
		if($cookieStore.get("authToken")!=null){
			return $cookieStore.get("usertype");
		}
		else{
			return null;
		}
	}

	/* logout */
	$scope.logout= function() {
		if($cookieStore.get("authToken")!=null){
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
				$cookieStore.remove("lockStatus");
				$cookieStore.put("numberOfAttempts",0);
				$cookieStore.remove("usernameLogging");
				new PNotify({
					title: 'Success',
					text: jQuery.i18n.prop('success.logout'),
					type: 'success',
					animate: {
						animate: true,
						in_class: 'rotateInDownLeft',
						out_class: 'rotateOutUpRight'
					},
					delay: 1500,
					opacity: 0.8
				});
				$location.path("/");

			}, function errorCallback(response) {
				
				new PNotify({
					title: 'Uh Oh!',
					text: jQuery.i18n.prop('error.logout.fail'),
					type: 'error',
					animate: {
						animate: true,
						in_class: 'bounceInDown',
						out_class: 'hinge'
					},
					delay:2000
				});
			});
		}
		else{
			$location.path("/");
		}
	}

	/* get All Departments */
	$scope.getAllDepartments= function() {

		if($cookieStore.get("authToken")!=null){
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
				

			}, function errorCallback(response) {
				notificationService.error(jQuery.i18n.prop('error.department.fetch.fail'));
				
			});
		}
		else{
			$location.path("/");
		}
	}

	/* get All Projects */
	$scope.getAllProjects= function() {
		if($cookieStore.get("authToken")!=null){
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
				notificationService.error(jQuery.i18n.prop('error.project.fetch.fail'));
			});
		}
		else{
			$location.path("/");
		}
	}

	/* get All Skills */
	$scope.getAllSkills= function() {
		if($cookieStore.get("authToken")!=null){
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
				notificationService.error(jQuery.i18n.prop('error.skill.fetch.fail'));
			});
		}
		else{
			$location.path("/");
		}
	}



	/* forgot password */
	$scope.forgotPassword= function() {
		dyn_notice();
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

			new PNotify({
				title: 'Success',
				text: jQuery.i18n.prop('success.mail.sent')+' '+$scope.email,
				type: 'success',
				animate: {
					animate: true,
					in_class: 'rotateInDownLeft',
					out_class: 'rotateOutUpRight'
				},
				delay: 1500,
				opacity: 0.8
			});
			$location.path("/");

		}, function errorCallback(response) {

			new PNotify({
				title: 'Uh Oh!',
				text: jQuery.i18n.prop('error.email.notExist'),
				type: 'error',
				animate: {
					animate: true,
					in_class: 'bounceInDown',
					out_class: 'hinge'
				},
				delay:2000
			});

		});

	}

	/* change password */
	$scope.changePassword= function() {
		dyn_notice();
		if($cookieStore.get("authToken")!=null){
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

				new PNotify({
					title: 'Success',
					text: jQuery.i18n.prop('success.password.changed'),
					type: 'success',
					animate: {
						animate: true,
						in_class: 'rotateInDownLeft',
						out_class: 'rotateOutUpRight'
					},
					delay: 1500,
					opacity: 0.8
				});

				$location.path("/viewProfile");

			}, function errorCallback(response) {

				new PNotify({
					title: 'Uh Oh!',
					text: jQuery.i18n.prop('error.password.unChanged'),
					type: 'error',
					animate: {
						animate: true,
						in_class: 'bounceInDown',
						out_class: 'hinge'
					},
					delay:2000
				});

			});
		}
		else{
			$location.path("/");
		}
	}

	/* Add Employee */
	$scope.addEmployee= function() {

		if($cookieStore.get("authToken")!=null){
			/* loads notifications */
			dyn_notice();
	
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
				
				new PNotify({
					title: 'Success',
					text: jQuery.i18n.prop('success.user.added'),
					type: 'success',
					animate: {
						animate: true,
						in_class: 'rotateInDownLeft',
						out_class: 'rotateOutUpRight'
					},
					delay: 1500,
					opacity: 0.8
				});
				$location.path("/getAllUsers");
	
			}, function errorCallback(response) {
				PNotify.removeAll();
				new PNotify({
					title: 'Uh Oh!',
					text: jQuery.i18n.prop('common.exists'),
					type: 'error',
					animate: {
						animate: true,
						in_class: 'bounceInDown',
						out_class: 'hinge'
					},
					delay:2000
				});
				
			});
		}
		else{
			$location.path("/");
		}
	}

	/* get list of employees */
	$scope.getAllEmployees= function() {
		if($cookieStore.get("authToken")!=null){
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
	
			}, function errorCallback(response) {
				new PNotify({
					title: 'Uh Oh!',
					text: jQuery.i18n.prop('error.user.fetch.fail'),
					type: 'error',
					animate: {
						animate: true,
						in_class: 'bounceInDown',
						out_class: 'hinge'
					},
					delay:2000
				});
				
			});
		}
		else{
			$location.path("/");
		}
	}

	/* to fetch id of employee for delete function */
	$scope.getUserId = function(uid){
		$rootScope.delId = uid;
	}
	
	/* to fetch id of project for delete function */
	$scope.geProjectId = function(uid){
		$rootScope.delProjectId = uid;
	}

	/* delete employee by id */
	$scope.deleteEmployee= function(id) {
		if($cookieStore.get("authToken")!=null){
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
					message: jQuery.i18n.prop('success.user.deleted'),
					onEscape: function() { console.log("Ecsape"); },
					backdrop: true
				});
	
	
	
			}, function errorCallback(response) {
	
				bootbox.dialog({
					message: jQuery.i18n.prop('error.user.delete.fail'),
					onEscape: function() { console.log("Ecsape"); },
					backdrop: true
				});
			});
		}
		else{
			$location.path("/");
		}
	}

	/* delete project by id */
	$scope.deleteProject= function(id) {
		if($cookieStore.get("authToken")!=null){
			$http({
				method : 'DELETE',
				url : REST_SERVICE_URI+'deleteproject/'+id,
				headers : {
					'Content-Type' : 'application/json',
					'authToken' : $cookieStore.get("authToken")
				},
				data : {
	
				}
			}).then(function successCallback(response) {
	
				for(var v=0;v<$rootScope.projectList.length;v++){
					if(id==$rootScope.projectList[v].projectId)
					{
						$rootScope.projectList.splice(v,1);
					}
				}
				bootbox.dialog({
					message: jQuery.i18n.prop('success.project.delete'),
					onEscape: function() { console.log("Ecsape"); },
					backdrop: true
				});
	
	
	
			}, function errorCallback(response) {
	
				bootbox.dialog({
					message: jQuery.i18n.prop('error.project.delete.fail'),
					onEscape: function() { console.log("Ecsape"); },
					backdrop: true
				});
			});
		}
		else{
			$location.path("/");
		}
	}


	/* to fetch id for view function */
	$scope.getIdForUser = function(){
		$scope.viewEmpProfile($cookieStore.get("targetEmpId"));
	}


	/* get own profile*/
	$scope.viewProfile= function() {
		if($cookieStore.get("authToken")!=null){
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
				
				new PNotify({
					title: 'Uh Oh!',
					text: jQuery.i18n.prop('error.user.fetch.fail'),
					type: 'error',
					animate: {
						animate: true,
						in_class: 'bounceInDown',
						out_class: 'hinge'
					},
					delay:2000
				});

			});
		}
		else{
			$location.path("/");
		}
	}


	/* get specific emp profile*/
	$scope.viewProfileTOUpdate= function() {
		if($cookieStore.get("authToken")!=null){
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
				$rootScope.isLogged = "true";
	
				//personal
				$rootScope.designation=response.data.designation;
				$rootScope.empId=response.data.empId;		    	  		    	 
				$rootScope.dob=response.data.dob;
				$rootScope.doj=response.data.doj;		    	  		    	  
				$rootScope.gender=response.data.gender;
				$rootScope.maritalStatus=response.data.maritalStatus;
				$rootScope.username=$cookieStore.get("usernameLogging");
				$rootScope.name=response.data.name;
	
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
	
				$location.path("/editProfile");
	
			}, function errorCallback(response) {
				
				new PNotify({
					title: 'Uh Oh!',
					text: jQuery.i18n.prop('error.user.fetch.fail'),
					type: 'error',
					animate: {
						animate: true,
						in_class: 'bounceInDown',
						out_class: 'hinge'
					},
					delay:2000
				});
	
			});
		}
		else{
			$location.path("/");
		}
	}

	/* get specific emp profile*/
	$scope.viewEmpProfile= function(id) {
		if($cookieStore.get("authToken")!=null){
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
				
				new PNotify({
					title: 'Uh Oh!',
					text: jQuery.i18n.prop('error.user.fetch.fail'),
					type: 'error',
					animate: {
						animate: true,
						in_class: 'bounceInDown',
						out_class: 'hinge'
					},
					delay:2000
				});
	
			});
		}
		else{
			$location.path("/");
		}
	}



	/* update employee profile */
	$scope.updateEmployee= function() {
		if($cookieStore.get("authToken")!=null){
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
					"maritalStatus": $scope.newMaritalStatus,
					"experience": $scope.experience
				}
			}).then(function successCallback(response) {
	
				$cookieStore.put("name", $scope.name );
				bootbox.dialog({
					message: jQuery.i18n.prop('success.profile.update'),
					onEscape: function() { console.log("Ecsape"); },
					backdrop: true
				});
				$location.path("/viewProfile");
	
			}, function errorCallback(response) {
				bootbox.dialog({
					message: jQuery.i18n.prop('error.profile.update'),
					onEscape: function() { console.log("Ecsape"); },
					backdrop: true
				});
	
			});
		}
		else{
			$locaion.path("/");
		}
	}

	/* unlock employee*/
	$scope.unlockEmployee= function(id) {
		if($cookieStore.get("authToken")!=null){
			$http({
				method : 'PUT',
				url : REST_SERVICE_URI+'unlockemployee/'+id,
				headers : {
					'Content-Type' : 'application/json',
					'authToken' : $cookieStore.get("authToken")
				},
				data : {
	
				}
			}).then(function successCallback(response) {
				bootbox.dialog({
					message: jQuery.i18n.prop('success.lock'),
					onEscape: function() { console.log("Ecsape"); },
					backdrop: true
				});
				//$location.path("/getAllUsers");
				$route.reload();
	
			}, function errorCallback(response) {
				bootbox.dialog({
					message: jQuery.i18n.prop('error.lock'),
					onEscape: function() { console.log("Ecsape"); },
					backdrop: true
				});
	
			});
		}
		else{
			$location.path("/");
		}
	}


	/* show all projects*/
	$scope.showAllProjects= function(id) {
		if($cookieStore.get("authToken")!=null){
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
				$rootScope.projectList = response.data;
				$location.path("/viewProjects");
	
			}, function errorCallback(response) {
				new PNotify({
					title: 'Uh Oh!',
					text: jQuery.i18n.prop('error.project.fetch.fail'),
					type: 'error',
					animate: {
						animate: true,
						in_class: 'bounceInDown',
						out_class: 'hinge'
					},
					delay:2000
				});
			});
		}
		else{
			$location.path("/");
		}
	}

	
	/* get history of operations performed by employee */
	$scope.getHistory= function() {
		if($cookieStore.get("authToken")!=null){
			$http({
				method : 'GET',
				url : REST_SERVICE_URI+'viewhistory',
				headers : {
					'Content-Type' : 'application/json',
					'authToken' : $cookieStore.get("authToken")
				},
				data : {
	
				}
			}).then(function successCallback(response) {
	
				if(response!=null)
					$rootScope.historyList = response.data.reverse();
				else
					$rootScope.historyList = response.data.reverse;	
				$location.path("/viewHistory");
	
				//console.log($rootScope.historyList[2]);  
	
			}, function errorCallback(response) {
				new PNotify({
					title: 'Uh Oh!',
					text: jQuery.i18n.prop('error.history.fetch.fail'),
					type: 'error',
					animate: {
						animate: true,
						in_class: 'bounceInDown',
						out_class: 'hinge'
					},
					delay:2000
				});
	
			});
		}
		else{
			$location.path("/");
		}
	}

	/* find if empty */
	$scope.isEmpty = function (obj) {
		for (var i in obj) if (obj.hasOwnProperty(i)) return false;
		return true;
	};

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

myApp.filter('dateToISO', function() {
	return function(input) {
		input = new Date(input).toISOString();
		return input;
	};
});		

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
	.when('/lockscreen', {
		controller: 'EMPController',
		templateUrl: 'resources/views/lockscreen.html'
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
	.when('/viewProjects', {
		controller: 'EMPController',
		templateUrl: 'resources/views/view-projects.html'
	})
	.when('/viewHistory', {
		controller: 'EMPController',
		templateUrl: 'resources/views/extras-timeline.html'
	})
	.when('/error', {
		controller: 'EMPController',
		templateUrl: 'resources/views/extras-500.html'
	})
	.otherwise({redirectTo: '/error'})  


});
