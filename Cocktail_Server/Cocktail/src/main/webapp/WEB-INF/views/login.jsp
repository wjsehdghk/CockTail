<!DOCTYPE html>
<html class="no-js">
    
    <head>
        <title>Admin Home Page</title>
        <!-- Bootstrap -->
        <link href="resources/bootstrap/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
        <link href="resources/bootstrap/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
        <link href="resources/bootstrap/vendors/easypiechart/jquery.easy-pie-chart.css" rel="stylesheet" media="screen">
        <link href="resources/bootstrap/assets/styles.css" rel="stylesheet" media="screen">
        <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
        <script src="resources/bootstrap/vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>
    </head>
    
    <body>
        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container-fluid">
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> <span class="icon-bar"></span>
                     <span class="icon-bar"></span>
                     <span class="icon-bar"></span>
                    </a>
                    <a class="brand" href="#">Cocktail Admin</a>
                    
                    </div>
                    <!--/.nav-collapse -->
                </div>
        </div>
        <div class="container-fluid">
            <div class="row-fluid">
                <div class="span3" id="sidebar">
                    
                </div>
                
                <!--/span-->
                <div class="span6" id="content">
                    
                    <div class="row-fluid">
                        <!-- block -->
                        <div class="block">
                            <div class="navbar navbar-inner block-header">
                                <center><div class="muted pull"><h1>Log-In</h1></div></center>
                            </div>
                            <br></br>
                             
                            <div class="block-content collapse in">
                          
                                <form class="form-horizontal"  method="post" action="loginProcess">
                                      <fieldset>
                                      
                                        <div class="control-group">
                                    <label class="control-label span5" for="focusedInput">ID  </label>
                                          <div class="controls">
                                             &nbsp&nbsp<input class="input-xlarge focused" type="text" name="adminId" >
                                          </div>
                                          
                                     
                                        </div>
                                         <div class="control-group">
                                    <label class="control-label span5" for="focusedInput">PASSWORD  </label>
                                          <div class="controls">
                                            &nbsp&nbsp<input class="input-xlarge focused" type="password" name="password" >
                                          </div>
                
                                        </div>
                                                                  <div class=span12>
                    <center><button class="btn btn-info" type="submit">LogIn</button>
                                       
                    <button class="btn" type="reset">Reset</button></center>
                    </div>
                                        </fieldset>
                                        </form>
                                        
                            </div>
                        </div>
                        <!-- /block -->
                    </div>            
                </div>
            </div>
            <hr>
            
        </div>
        <!--/.fluid-container-->
        <script src="resources/bootstrap/vendors/jquery-1.9.1.min.js"></script>
        <script src="resources/bootstrap/bootstrap/js/bootstrap.min.js"></script>
        <script src="resources/bootstrap/vendors/easypiechart/jquery.easy-pie-chart.js"></script>
        <script src="resources/bootstrap/assets/scripts.js"></script>
        <script>
        $(function() {
            // Easy pie charts
            $('.chart').easyPieChart({animate: 1000});
        });
        </script>
    </body>

</html>