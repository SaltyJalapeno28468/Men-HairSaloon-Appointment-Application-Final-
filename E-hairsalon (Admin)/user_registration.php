<?php

require 'dbcon.php';

if (!isset($_POST['full_name']) == "" && !isset($_POST['email_id']) == "") {
    $full_name = $_POST['full_name'];
    $email_id = $_POST['email_id'];
    $mobile_no = $_POST['mobile_no'];
    $username = $_POST['username'];
    $password = $_POST['password'];

    $result = array();
    date_default_timezone_set('Asia/Kolkata');
    $dt = date('Y-m-d h:i:s');

    $query1 = " insert into tbl_user (name, mobile_no, email_id, username, password, created_date) "
            . " values ('$full_name','$mobile_no','$email_id','$username','$password','$dt')";

    try {
        if ($con) {
            if (mysqli_query($con, $query1)) {
                echo 'Data insert successfully';
            } else {
                echo 'In Registration Error Occure.';
            }
        } else {
            echo 'Connection Problem';
        }
    } catch (Exception $ex) {
        echo $ex->getMessage();
    }
} else {
    echo 'full name Not Set';
}
?>
