<!doctype html>
<html>

<head>

<title>Employee Management Portal</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width">
<meta charset="utf-8">

<script src="resources/vendor/jquery/dist/jquery.js"></script>
<script src="resources/vendor/jquery.easing/jquery.easing.js"></script>
<script src="resources/vendor/jquery-countTo/jquery.countTo.js"></script>
<script src="resources/vendor/perfect-scrollbar/js/perfect-scrollbar.jquery.js"></script>
<script src="resources/vendor/onScreen/jquery.onscreen.js"></script>
<script src="resources/vendor/angular/angular.min.js"> </script>
<script src="resources/vendor/angular/angular-route.min.js"> </script>
<script src="resources/vendor/angular/angular-cookies.min.js"> </script>
<script src="resources/scripts/logic.js"> </script>
<script src="resources/scripts/extentions/modernizr.js"></script>
<script src="resources/vendor/bootstrap/dist/js/bootstrap.js"></script>
<script src="resources/vendor/fastclick/lib/fastclick.js"></script>
<script src="resources/scripts/ui/accordion.js"></script>
<script src="resources/scripts/ui/animate.js"></script>
<script src="resources/scripts/ui/link-transition.js"></script>
<script src="resources/scripts/ui/panel-controls.js"></script>
<script src="resources/scripts/ui/preloader.js"></script>
<script src="resources/scripts/ui/toggle.js"></script>
<script src="resources/scripts/urban-constants.js"></script>
<script src="resources/scripts/extentions/lib.js"></script>
	
<link rel="shortcut icon" href="/favicon.ico">
<link rel="stylesheet"	href="resources/vendor/bootstrap/dist/css/bootstrap.css">
<link rel="stylesheet"	href="resources/vendor/perfect-scrollbar/css/perfect-scrollbar.css">
<link rel="stylesheet" href="resources/styles/roboto.css">
<link rel="stylesheet" href="resources/styles/font-awesome.css">
<link rel="stylesheet" href="resources/styles/panel.css">
<link rel="stylesheet" href="resources/styles/feather.css">
<link rel="stylesheet" href="resources/styles/animate.css">
<link rel="stylesheet" href="resources/styles/urban.css">
<link rel="stylesheet" href="resources/styles/urban.skins.css">
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
					<li><a href="#/userMgmt"> <i class="fa fa-toggle-on"></i>
							<span>User Management</span>
					</a></li>
					<!-- /User Management  -->

					<!-- Profile -->
					<li><a href="#/landingPage"> <i class="fa fa-tag"></i> <span>Profile</span>
					</a></li>
					<!-- /Profile -->

					<!-- Change Password -->
					<li><a href="javascript:;"> <i class="fa fa-tint"></i> <span>Change
								Password</span>
					</a></li>
					<!-- /Change Password -->

					<!-- About -->
					<li><a href="javascript:;"> <i class="fa fa-pie-chart"></i>
							<span>About</span>
					</a></li>
					<!-- /About -->

					<!-- Logout -->
					<li><a ng-click="logout()"> <i class="fa fa-map-marker"></i> <span>Logout</span>
					</a></li>
				</ul>
			</nav>
			<!-- /main navigation -->

		</div>
		<!-- /sidebar panel -->

		<!-- content panel -->
		<div class="main-panel">

			<!-- top header -->
			<header class="header navbar" style="border-bottom: 1px solid rgba(0, 0, 0, 0.07);" ng-show="checkForLogin()">

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

					<li><a href="javascript:;" data-toggle="dropdown"> <img
							src="resources/images/avatar.jpg"
							class="header-avatar img-circle ml10" alt="user" title="user">
							<span class="pull-left">{{checkForName()}}</span>
					</a>
						<ul class="dropdown-menu">
							<li><a ng-click="logout()">Logout</a></li>
						</ul></li>
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
