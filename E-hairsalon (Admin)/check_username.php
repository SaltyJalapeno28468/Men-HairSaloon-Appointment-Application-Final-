<?php

require 'dbcon.php';
if (!isset($_POST['username']) == "") {
    $username = $_POST['username'];
    $sql = "SELECT * FROM tbl_user WHERE username = '$username'";
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
    echo 'Username is empty';
}
?>
