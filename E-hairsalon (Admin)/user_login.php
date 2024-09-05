<?php

require 'dbcon.php';
if (!isset($_POST['username']) == "") {

    $username = $_POST['username'];
    $password = $_POST['password'];
//    $userType = $_POST['userType'];
//    $password = md5($password1);

    $result = array();
    $result['data'] = array();
    $result['status'] = array();

//    if ($userType == "personal") {
        $sql = "SELECT * FROM tbl_user WHERE username = '$username' AND  password='$password' and flag = '1'";
        mysqli_set_charset($con, 'utf8');
        $sql_run = mysqli_query($con, $sql);
        if ($sql_run) {
            $row = mysqli_num_rows($sql_run);
            if ($row === 1) {
                $rows = mysqli_fetch_assoc($sql_run);
                if ($password == $rows['password']) {
                    $index['id'] = $rows['id'];
                    $index['username'] = $rows['username'];
                    $index['name'] = $rows['name'];
                    $index['email_id'] = $rows['email_id'];
                    $index['mobile_no'] = $rows['mobile_no'];
                    array_push($result['data'], $index);

                    $index1['status'] = "success";
                    $index1['code'] = "1";
                    array_push($result['status'], $index1);
                } else {
                    $index1['status'] = "wrong password";
                    $index1['code'] = "2";
                    array_push($result['status'], $index1);
                }
            } else {
                $index1['status'] = "not found";
                $index1['code'] = "0";
                array_push($result['status'], $index1);
            }
        } else {
            $index1['status'] = "error";
            $index1['code'] = "-1";
            array_push($result['status'], $index1);
        }
    echo json_encode($result);
} else {
    echo 'Username is empty';
}

?>
