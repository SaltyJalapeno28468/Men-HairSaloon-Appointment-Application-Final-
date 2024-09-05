<?php

require 'dbcon.php';

if (!isset($_POST['user_id']) == "" && !isset($_POST['check_id']) == "") {
    $user_id = $_POST['user_id'];
    $check_id = $_POST['check_id'];
    $appointment_date = $_POST['appointment_date'];
    $appointment_time = $_POST['appointment_time'];
    $appointment_time_id = $_POST['appointment_time_id'];
    $total_amount = $_POST['total_amount'];

//    $path_img1 = "complaint_img/" . $u_name . time() . ".jpeg";

    $result = array();
    date_default_timezone_set('Asia/Kolkata');
    $dt = date('Y-m-d h:i:s');

    $query1 = " insert into tbl_appointment (user_id, appointment_book_date, appointment_time, total_amount, appointment_date) "
            . " values ('$user_id','$appointment_date','$appointment_time','$total_amount','$dt')";

    try {

        if ($con->query($query1) === TRUE) {
            $last_id = $con->insert_id;
//            echo "New record created successfully. Last inserted ID is: " . $last_id;

            $service_id_arr = (explode(",", $check_id));
            $check_insert="";
            foreach ($service_id_arr as $value) {
                if ($value != "") {

                    $query1 = " insert into tbl_appointment_detail (appointment_id, service_id) "
                            . " values ('$last_id','$value')";

                    if ($con) {
                        if (mysqli_query($con, $query1)) {
                            $check_insert=1;
//                            echo 'Appointment Book Successfully';
                        } else {
//                            echo 'In Appointment Book  Error Occure.';
                        }
                    } else {
                        echo 'Connection Problem';
                    }
                }
            }
            
            if($check_insert==1){
                echo 'Appointment Book Successfully';
            }else{
                echo 'In Appointment Book  Error Occure.';
            }
        } else {
            echo "Error: " . $sql . "<br>" . $con->error;
        }
    } catch (Exception $ex) {
        echo $ex->getMessage();
    }
} else {
    echo 'full name Not Set';
}
?>