<!doctype html>
<html>

<head>

<title>Employee Management Portal</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width">
<meta charset="utf-8">


<script src="resources/scripts/extentions/modernizr.js"></script>
<script src="resources/vendor/jquery/dist/jquery.js"></script>
<script src="resources/vendor/angular/angular.min.js"> </script>
<script src="resources/vendor/angular/angular-route.min.js"> </script>
<script src="resources/vendor/angular/angular-cookies.min.js"> </script>
<script src="resources/scripts/angular-messages.js"> </script>
<script src="resources/scripts/pnotify.js"></script>
<script src="resources/scripts/pnotify.confirm.js"></script>
<script src="resources/scripts/pnotify.buttons.js"></script>
<script src="resources/scripts/angular-pnotify.js"> </script>
<script src="resources/scripts/pnotify.animate.js"> </script>
<script src="resources/scripts/logic.js"> </script>
<script src="resources/vendor/bootstrap/dist/js/bootstrap.js"></script>
<script src="resources/vendor/jquery.easing/jquery.easing.js"></script>
<script src="resources/vendor/fastclick/lib/fastclick.js"></script>
<script src="resources/vendor/onScreen/jquery.onscreen.js"></script>
<script src="resources/vendor/jquery-countTo/jquery.countTo.js"></script>
<script src="resources/vendor/perfect-scrollbar/js/perfect-scrollbar.jquery.js"></script>
<script src="resources/scripts/ui/accordion.js"></script>
<script src="resources/scripts/ui/animate.js"></script>
<script src="resources/scripts/ui/link-transition.js"></script>
<script src="resources/scripts/ui/panel-controls.js"></script>
<script src="resources/scripts/ui/preloader.js"></script>
<script src="resources/scripts/ui/toggle.js"></script>
<script src="resources/scripts/urban-constants.js"></script>
<script src="resources/scripts/extentions/lib.js"></script>
<script src="resources/vendor/twitter-bootstrap-wizard/jquery.bootstrap.wizard.min.js"></script>
<script src="resources/vendor/jquery-validation/dist/jquery.validate.min.js"></script>
<script src="resources/vendor/card/lib/js/jquery.card.js"></script>

<!-- page level js-->
<script src="resources/vendor/checkbo/src/0.1.4/js/checkBo.min.js"></script>
<script src="resources/vendor/chosen_v1.4.0/chosen.jquery.min.js"></script>
<script src="resources/scripts/pages/form-wizard.js"></script>
<script src="resources/scripts/checklist-model.js"></script>
<script src="resources/vendor/datatables/media/js/jquery.dataTables.js"></script>
<script src="resources/scripts/extentions/bootstrap-datatables.js"></script>
<script src="resources/scripts/pages/table-edit.js"></script>
<script src="resources/scripts/bootbox.min.js"></script>
<script src="resources/scripts/dirPagination.js"></script>
<!-- /page level js-->

<!-- page level css-->	
<link rel="shortcut icon" href="/favicon.ico">
<link rel="stylesheet" href="resources/vendor/chosen_v1.4.0/chosen.min.css">
<link rel="stylesheet" href="resources/vendor/checkbo/src/0.1.4/css/checkBo.min.css">
<link rel="stylesheet" href="resources/vendor/datatables/media/css/jquery.dataTables.css">
<link rel="stylesheet" href="resources/styles/spinner.css">
<link rel="stylesheet" href="resources/styles/pnotify.css">
<link rel="stylesheet" href="resources/styles/pnotify.buttons.css">
<link rel="stylesheet" href="resources/styles/pnotify.brighttheme.css">
<link rel="stylesheet" href="resources/styles/pnotify.mobile.css">
<!-- /page level css-->

<!-- general level css-->
<link rel="stylesheet"	href="resources/vendor/bootstrap/dist/css/bootstrap.css">
<link rel="stylesheet"	href="resources/vendor/perfect-scrollbar/css/perfect-scrollbar.css">
<link rel="stylesheet" href="resources/styles/roboto.css">
<link rel="stylesheet" href="resources/styles/font-awesome.css">
<link rel="stylesheet" href="resources/styles/panel.css">
<link rel="stylesheet" href="resources/styles/feather.css">
<link rel="stylesheet" href="resources/styles/animate.css">
<link rel="stylesheet" href="resources/styles/urban.css">
<link rel="stylesheet" href="resources/styles/urban.skins.css">
<link rel="stylesheet" href="resources/styles/validation.css">
<!-- /general level css-->

<!-- endbuild -->

</head>
<body ng-app="myApp">
	<!-- main area -->
	<div  ng-controller="EMPController">
	<div class="app layout-fixed-header" ng-init="initializeApp()" data-ng-class="{'layout-small-menu': app.layout.isSmallSidebar, 'layout-chat-open': app.layout.isChatOpen, 'layout-fixed-header': app.layout.isFixedHeader, 'layout-boxed': app.layout.isBoxed, 'layout-static-sidebar': app.layout.isStaticSidebar, 'layout-right-sidebar': app.layout.isRightSidebar, 'layout-fixed-footer': app.layout.isFixedFooter, 'message-open': app.isMessageOpen}">

		<!-- sidebar panel -->
		<div class="sidebar-panel offscreen-left" ng-show="checkForLogin()">

			<div class="brand">

				<!-- logo -->
				<div class="brand-logo">
					<img src="resources/images/logo.png" height="15" alt="">
				</div>
				<!-- /logo -->

				<!-- toggle small sidebar menu -->
				<a href="javascript:;"
					class="toggle-sidebar hidden-xs hamburger-icon v3"
					data-ng-click="app.layout.isSmallSidebar = !app.layout.isSmallSidebar"> <span></span> <span></span> <span></span>
					<span></span>
				</a>
				<!-- /toggle small sidebar menu -->

			</div>

			<!-- main navigation -->
			<nav role="navigation">

				<ul class="nav">

					<!-- User Management -->
					<li ng-if="getUserType()=='Admin'"><a href="#/getAllUsers"> <i class="fa fa-toggle-on"></i>
							<span>User Management</span>
					</a></li>
					<!-- /User Management  -->
					
					<!-- Add Employee -->
					<li ng-if="getUserType()=='Admin'"><a href="#/addEmployee"> <i class="fa fa-toggle-on"></i>
							<span>Add Employee</span>
					</a></li>
					<!-- /Add Employee  -->

					<!-- Profile -->
					<li><a href="#/viewProfile"> <i class="fa fa-tag"></i> <span>Profile</span>
					</a></li>
					<!-- /Profile -->
					
					<!-- Profile -->
					<li ng-if="getUserType()=='Admin'"><a href="#/viewProjects"> <i class="fa fa-tag"></i> <span>View Projects</span>
					</a></li>
					<!-- /Profile -->
					
					<!-- Edit Profile -->
					<li><a href="#/editProfile"> <i class="fa fa-tag"></i> <span>Edit Profile</span>
					</a></li>
					<!-- /Edit Profile -->

					<!-- Change Password -->
					<li><a href="#changePassword"> <i class="fa fa-tint"></i> <span>Change
								Password</span>
					</a></li>
					<!-- /Change Password -->

					<!-- Logout -->
					<li><a ng-click="logout()"> <i class="fa fa-map-marker"></i> <span>Logout</span>
					</a></li>
				</ul>
			</nav>
			<!-- /main navigation -->

		</div>
		<!-- /sidebar panel -->

		<!-- content panel -->
		<div class="main-panel" style="overflow-x: hidden;">

			<!-- top header -->
			<header class="header navbar" style="border-bottom: 1px solid rgba(0, 0, 0, 0.30);" ng-show="checkForLogin()">

				<div class="brand visible-xs">
					<!-- toggle offscreen menu -->
					<div class="toggle-offscreen">
						<a href="#" class="hamburger-icon visible-xs"
							data-toggle="offscreen" data-move="ltr"> <span></span> <span></span>
							<span></span>
						</a>
					</div>
					<!-- /toggle offscreen menu -->

					<!-- logo -->
					<div class="brand-logo">
						<img src="resources/images/logo-dark.png" height="15" alt="">
					</div>
					<!-- /logo -->

					<!-- toggle chat sidebar small screen-->

					<!-- /toggle chat sidebar small screen-->
				</div>

				<ul class="nav navbar-nav hidden-xs">
					<li>
						<p class="navbar-text"></p>
					</li>
				</ul>

				<ul class="nav navbar-nav navbar-right hidden-xs">

					<li>
					<a href="javascript:;" data-toggle="dropdown"> 
					<img ng-if="selfGender== 'Male'" src="resources/images/male-avatar2.png" class="header-avatar img-circle ml10" alt="user" title="user">
					<img ng-if="selfGender== 'Female'" src="resources/images/female-avatar2.png" class="header-avatar img-circle ml10" alt="user" title="user">
					<span class="pull-left">{{checkForName()}}</span>
					</a>
						<ul class="dropdown-menu">
							<li><a ng-click="logout()">Logout</a></li>
						</ul>
					</li>
				</ul>
			</header>
			
			<!-- main area -->
	 			<div ng-view="" class="main-content">
	 				
 				</div>
			<!-- /main area -->
			
			<!-- /top header -->
		</div>
		<!-- /content panel -->

		<!-- bottom footer -->
		<footer class="content-footer">

			<nav class="footer-right">
				<ul class="nav">
					<li><a href="javascript:;">Feedback</a></li>
					<li><a href="javascript:;" class="scroll-up"> <i
							class="fa fa-angle-up"></i>
					</a></li>
				</ul>
			</nav>

			<nav class="footer-left">
				<ul class="nav">
					<li><a href="javascript:;">Copyright <i
							class="fa fa-copyright"></i> <span>Xoriant Solutions</span> 2016.
					</a></li>
					<li><a href="javascript:;">Careers</a></li>
					<li><a href="javascript:;"> Privacy Policy </a></li>
				</ul>
			</nav>

		</footer>
		<!-- /bottom footer -->

	</div>

	</div>


	<!-- build:js({.tmp,app}) scripts/app.min.js -->
	<!-- endbuild -->
</body>

</html>
