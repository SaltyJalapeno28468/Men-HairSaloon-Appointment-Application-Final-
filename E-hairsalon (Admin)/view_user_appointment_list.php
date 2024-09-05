<?php


require 'dbcon.php';

if (!isset($_POST['user_id']) == "") {
    $user_id = $_POST['user_id'];
    $Appointment_id = $_POST['Appointment_id'];
//    $check_id = "12,13";
//    echo '$district_id '.$district_id;
    $result = array();

    $sql = 'select ta.*,DATE_FORMAT(ta.appointment_book_date,"%d-%m-%Y") AS appointment_book_date_1,tad.service_id,ts.service_name,ts.service_time,ts.service_price,ts.service_discount,ts.img_url from tbl_appointment as ta left join tbl_appointment_detail as tad on ta.id=tad.appointment_id LEFT join tbl_service as ts on tad.service_id=ts.id WHERE ta.id="'.$Appointment_id.'" and ta.flag=1';
//echo 'query ' . $sql;
    try {
        if ($con) {
            mysqli_set_charset($con, 'utf8');
            $response = mysqli_query($con, $sql);
//        print_r($response);
            if (mysqli_num_rows($response) > 0) {

                while ($row = mysqli_fetch_assoc($response)) {
                    $index["id"] = $row["id"];
                    
                    $index["user_id"] = $row["user_id"];                    
                    $index["appointment_book_date"] = $row["appointment_book_date_1"];
                    $index["appointment_time"] = $row["appointment_time"];
                    $index["total_amount"] = $row["total_amount"];
                    $index["owner_txt"] = $row["owner_txt"];
                    $index["appointment_status"] = $row["appointment_status"];
                    
                    $index["service_name"] = $row["service_name"];
                    $index["service_time"] = $row["service_time"];
                    $index["service_price"] = $row["service_price"];
                    $index["service_discount"] = $row["service_discount"];
                    $index["service_final_price"] = $index["service_price"]-$row["service_discount"];
                    $index["img_url"] = $row["img_url"];

                    array_push($result, $index);
                }
//            $result["success"] = "1";
                echo json_encode($result);
            } else {
                echo 'NO_Record_Found';
            }
        } else {
            echo 'Connection Problem';
        }
    } catch (Exception $ex) {
        echo $ex->getMessage();
    }
} else {
    echo 'Username is Null';
}
?>

