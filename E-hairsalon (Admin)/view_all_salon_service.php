<?php


require 'dbcon.php';

if (!isset($_POST['user_id']) == "") {
    $user_id = $_POST['user_id'];
//    echo '$district_id '.$district_id;
    $result = array();

    $sql = 'select *,DATE_FORMAT(created_date,"%d-%m-%Y %H:%i:%s") AS created_date_1 from tbl_service where flag="1" and package_type="0" ORDER by id ASC';
//echo 'query ' . $sql;
    try {
        if ($con) {
            mysqli_set_charset($con, 'utf8');
            $response = mysqli_query($con, $sql);
//        print_r($response);
            if (mysqli_num_rows($response) > 0) {

                while ($row = mysqli_fetch_assoc($response)) {
                    $index["id"] = $row["id"];
                    $index["service_name"] = $row["service_name"];
                    $index["service_time"] = $row["service_time"];
                    $index["service_price"] = $row["service_price"];
                    $index["service_discount"] = $row["service_discount"];
                    $index["service_final_price"] = $index["service_price"]-$row["service_discount"];
                    $index["img_url"] = $row["img_url"];
                    $index["created_date"] = $row["created_date_1"];

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

