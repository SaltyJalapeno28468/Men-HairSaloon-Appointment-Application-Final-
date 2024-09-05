<?php
session_start();
error_reporting(0);
include('includes/dbconnection.php');
if (strlen($_SESSION['bpmsaid'] == 0)) {
    header('location:logout.php');
} else {

    if (isset($_POST['submit'])) {

        $cid = $_GET['viewid'];
        $remark = $_POST['remark'];
        $status = $_POST['status'];

        $query = mysqli_query($con, "update  tbl_appointment set owner_txt='$remark',appointment_status='$status' where id='$cid'");
        if ($query) {

            echo '<script>alert("All remark has been updated")</script>';
        } else {
            echo '<script>alert("Something Went Wrong. Please try again.")</script>';
        }
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
                        <div class="tables">
                            <h3 class="title1">View Appointment</h3>



                            <div class="table-responsive bs-example widget-shadow">
                                <p style="font-size:16px; color:red" align="center"> <?php
                                    if ($msg) {
                                        echo $msg;
                                    }
                                    ?> </p>
                                <h4>View Appointment:</h4>

                                <?php
                                $cid = $_GET['viewid'];
                                $ret1 = mysqli_query($con, "SELECT tad.*,ts.* from tbl_appointment_detail as tad left join tbl_service as ts on ts.id=tad.service_id where tad.appointment_id='$cid'");
                                while ($row1 = mysqli_fetch_array($ret1)) {
                                    $service_details .= $row1['service_name'] . "/ Rs." . $row1['service_price'] . "<br>";
                                }
                                ?>
                                <?php
                                $cid = $_GET['viewid'];
                                $ret = mysqli_query($con, "SELECT ta.*,DATE_FORMAT(ta.appointment_book_date,'%d-%m-%Y') AS appointment_book_date_1,DATE_FORMAT(ta.appointment_date,'%d-%m-%Y') AS appointment_date_1,tu.name,tu.mobile_no,tu.email_id FROM `tbl_appointment` as ta left join tbl_user as tu on tu.id=ta.user_id WHERE ta.flag=1 and ta.id='$cid'");
                                $cnt = 1;
                                while ($row = mysqli_fetch_array($ret)) {
                                    ?>
                                    <table class="table table-bordered">
                                        <tr>
                                            <th>Appointment Number</th>
                                            <td><?php echo $row['id']; ?></td>
                                        </tr>
                                        <tr>
                                            <th>Name</th>
                                            <td><?php echo $row['name']; ?></td>
                                        </tr>

                                        <tr>
                                            <th>Email</th>
                                            <td><?php echo $row['email_id']; ?></td>
                                        </tr>
                                        <tr>
                                            <th>Mobile Number</th>
                                            <td><?php echo $row['mobile_no']; ?></td>
                                        </tr>
                                        <tr>
                                            <th>Appointment Date</th>
                                            <td><?php echo $row['appointment_book_date_1']; ?></td>
                                        </tr>

                                        <tr>
                                            <th>Appointment Time</th>
                                            <td><?php echo $row['appointment_time']; ?></td>
                                        </tr>

                                        <tr>
                                            <th>Services</th>


                                            <td><?php echo $service_details; ?></td>
                                        </tr>
                                        <tr>
                                            <th>Apply Date</th>
                                            <td><?php echo $row['appointment_date_1']; ?></td>
                                        </tr>


                                        <tr>
                                            <th>Status</th>
                                            <td> <?php
                                                if ($row['appointment_status'] == "Selected") {
                                                    echo "Selected";
                                                }

                                                if ($row['appointment_status'] == "Rejected") {
                                                    echo "Rejected";
                                                }
                                                ?></td>
                                        </tr>
                                    </table>
                                    <table class="table table-bordered">
                                        <?php if ($row['owner_txt'] == "") { ?>


                                            <form name="submit" method="post" enctype="multipart/form-data"> 

                                                <tr>
                                                    <th>Remark :</th>
                                                    <td>
                                                        <textarea name="remark" placeholder="" rows="12" cols="14" class="form-control wd-450" required="true"></textarea></td>
                                                </tr>

                                                <tr>
                                                    <th>Status :</th>
                                                    <td>
                                                        <select name="status" class="form-control wd-450" required="true" >
                                                            <option value="Selected" selected="true">Selected</option>
                                                            <option value="Rejected">Rejected</option>
                                                        </select></td>
                                                </tr>

                                                <tr align="center">
                                                    <td colspan="2"><button type="submit" name="submit" class="btn btn-az-primary pd-x-20">Submit</button></td>
                                                </tr>
                                            </form>
                                        <?php } else { ?>
                                        </table>
                                        <table class="table table-bordered">
                                            <tr>
                                                <th>Remark</th>
                                                <td><?php echo $row['owner_txt']; ?></td>
                                            </tr>


                                    <!--                                            <tr>
                                                                                    <th>Remark date</th>
                                                                                    <td><?php // echo $row['RemarkDate'];    ?>  </td></tr>-->

                                        </table>
                                    <?php } ?>
                                <?php } ?>
                            </div>
                        </div>
                    </div>
                </div>
                <!--footer-->

                <!--//footer-->
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