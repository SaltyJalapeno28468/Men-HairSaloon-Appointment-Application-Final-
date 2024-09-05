<?php

require 'dbcon.php';
if (!isset($_POST['mobileno']) == "") {
    $mobileno = $_POST['mobileno'];
    $sql = "SELECT * FROM tbl_user WHERE mobile_no = '$mobileno'";
    mysqli_set_charset($con, 'utf8');
    $sql_run = mysqli_query($con, $sql);
    if ($sql_run) {
        $row = mysqli_num_rows($sql_run);
        if ($row === 1) {
            echo 'EXIST';
        } else {
            echo 'NOT_EXIST';
        }
    }
} else {
    echo 'Mobile No is empty';
}
?>
