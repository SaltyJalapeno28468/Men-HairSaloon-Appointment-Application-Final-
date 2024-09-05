<?php
session_start();
error_reporting(0);
include('includes/dbconnection.php');
if (strlen($_SESSION['bpmsaid'] == 0)) {
    header('location:logout.php');
} else {

    if (isset($_POST['submit'])) {
//        $sername = $_POST['sername'];
//        $service_price = $_POST['service_price'];
//        $service_time = $_POST['service_time'];
//        $service_discount = $_POST['service_discount'];
//        $package_type = $_POST['package'];
//        $eid = $_GET['editid'];
//        $c_image = $_FILES['c_image']['name'];
//        $c_image_tmp = $_FILES['c_image']['tmp_name'];
//        
//        echo '$c_image '.$c_image;
//        echo '<br>$c_image_tmp '.$c_image_tmp;
//        $query = mysqli_query($con, "update  tbl_service set service_name='$sername', service_time='$service_time',service_price='$service_price',service_discount='$service_discount',package_type='$package_type' where id='$eid' ");
//        if ($query) {
//
//            echo '<script>alert("Service has been Updated")</script>';
//        } else {
//            echo '<script>alert("Something Went Wrong. Please try again.")</script>';
//        }
    }
    ?>
    <!DOCTYPE HTML>
    <html>
        <head>
            <title>Men Salon</title>

            <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
            <!-- Bootstrap Core CSS -->
            <link href="css/bootstrap.css" rel='stylesheet' type='text/css' />
            <!-- Custom CSS -->
            <link href="css/style.css" rel='stylesheet' type='text/css' />
            <!-- font CSS -->
            <!-- font-awesome icons -->
            <link href="css/font-awesome.css" rel="stylesheet"> 
            <!-- //font-awesome icons -->
            <!-- js-->
            <script src="js/jquery-1.11.1.min.js"></script>
            <script src="js/modernizr.custom.js"></script>
            <!--webfonts-->
            <link href='//fonts.googleapis.com/css?family=Roboto+Condensed:400,300,300italic,400italic,700,700italic' rel='stylesheet' type='text/css'>
            <!--//webfonts--> 
            <!--animate-->
            <link href="css/animate.css" rel="stylesheet" type="text/css" media="all">
            <script src="js/wow.min.js"></script>
            <script>
                new WOW().init();
            </script>
            <!--//end-animate-->
            <!-- Metis Menu -->
            <script src="js/metisMenu.min.js"></script>
            <script src="js/custom.js"></script>
            <link href="css/custom.css" rel="stylesheet">
            <!--//Metis Menu -->
        </head> 
        <body class="cbp-spmenu-push">
            <div class="main-content">
                <!--left-fixed -navigation-->
                <?php include_once('includes/sidebar.php'); ?>
                <!--left-fixed -navigation-->
                <!-- header-starts -->
                <?php include_once('includes/header.php'); ?>
                <!-- //header-ends -->
                <!-- main content start-->
                <div id="page-wrapper">
                    <div class="main-page">
                        <div class="forms">
                            <h3 class="title1">Update Services</h3>
                            <div class="form-grids row widget-shadow" data-example-id="basic-forms"> 
                                <div class="form-title">
                                    <h4>Update Services:</h4>
                                </div>
                                <div class="form-body">
                                    <form action="edit-services-php.php" method="post" enctype="multipart/form-data">

                                        <?php
                                        $cid = $_GET['editid'];
                                        $ret = mysqli_query($con, "select * from  tbl_service where id='$cid'");
                                        $cnt = 1;
                                        while ($row = mysqli_fetch_array($ret)) {
                                            ?> 


                                            <div class="form-group"> 
                                                <label for="exampleInputEmail1">Service Name</label> 
                                                <input type="text" class="form-control" id="sername" name="sername" placeholder="Service Name" value="<?php echo $row['service_name']; ?>" required="true"> 
                                            </div> 
                                            <div class="form-group"> 
                                                <label>Service Price</label> 
                                                <input class="form-control" name="service_price" id="service_price" rows="5" placeholder="Price" required="true" value="<?php echo $row['service_price']; ?>"> 
                                            </div>
                                            <div class="form-group"> 
                                                <label for="exampleInputPassword1">Service Time</label> 
                                                <input type="text" id="service_time" name="service_time" class="form-control" placeholder="Time" value="<?php echo $row['service_time']; ?>" required="true"> 
                                            </div>
                                            <div class="form-group"> 
                                                <label for="exampleInputPassword1">Service Discount</label> 
                                                <input type="text" id="service_discount" name="service_discount" class="form-control" placeholder="Discount" value="<?php echo $row['service_discount']; ?>" required="true"> 
                                            </div>
                                            <div class="form-group"> 
                                                <label for="exampleInputPassword1">Package Type</label> 
                                                <select name="package" class="form-control wd-450" required="true" >
                                                    <?php
                                                    if ($row['package_type'] == "0") {
                                                        ?>
                                                        <option value="0" selected="true">Single</option>
                                                        <option value="1">Package</option>
                                                        <?php
                                                    } else {
                                                        ?>
                                                        <option value="0">Single</option>
                                                        <option value="1" selected="true">Package</option>
                                                        <?php
                                                    }
                                                    ?>
                                                </select>
                                                <!--<input type="text" id="service_time" name="cost" class="form-control" placeholder="Cost" value="<?php echo $row['service_time']; ?>" required="true">--> 
                                            </div>
                                            <div class="form-group"> 
                                                <label for="exampleInputPassword1">Image</label>
                                                <img height="70px" id="image_show" src="<?php echo "../" . $row['img_url']; ?>">
                                                <input type="file" id="image_upload" name="c_image"/>
                                            </div>
                                        <input type="text" id="service_id" name="service_id" style="display: none"  value="<?php echo $row['id']; ?>"> 
                                        <?php } ?>
                                        <button type="submit" name="submit" class="btn btn-default">Update</button>
                                    </form> 
                                </div>

                            </div>


                        </div>
                    </div>
                    <?php include_once('includes/footer.php'); ?>
                </div>
                <!-- Classie -->
                <script src="js/classie.js"></script>
                <script>
                var menuLeft = document.getElementById('cbp-spmenu-s1'),
                        showLeftPush = document.getElementById('showLeftPush'),
                        body = document.body;

                showLeftPush.onclick = function () {
                    classie.toggle(this, 'active');
                    classie.toggle(body, 'cbp-spmenu-push-toright');
                    classie.toggle(menuLeft, 'cbp-spmenu-open');
                    disableOther('showLeftPush');
                };

                function disableOther(button) {
                    if (button !== 'showLeftPush') {
                        classie.toggle(showLeftPush, 'disabled');
                    }
                }
                </script>
                <!--scrolling js-->
                <script src="js/jquery.nicescroll.js"></script>
                <script src="js/scripts.js"></script>
                <!--//scrolling js-->
                <!-- Bootstrap Core JavaScript -->
                <script src="js/bootstrap.js"></script>
        </body>
    </html>
<?php } ?>