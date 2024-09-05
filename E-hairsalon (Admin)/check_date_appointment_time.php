<?php


require 'dbcon.php';

if (!isset($_POST['book_date']) == "") {
    $book_date = $_POST['book_date'];
//    echo '$district_id '.$district_id;
    $result = array();

    $sql = 'select * from tbl_appointment where flag="1" and appointment_book_date="'.$book_date.'" ORDER by id DESC';
//echo 'query ' . $sql;
    try {
        if ($con) {
            mysqli_set_charset($con, 'utf8');
            $response = mysqli_query($con, $sql);
//        print_r($response);
            if (mysqli_num_rows($response) > 0) {

                while ($row = mysqli_fetch_assoc($response)) {
                    $index["id"] = $row["id"];
                    $index["appointment_book_date"] = $row["appointment_book_date"];
                    $index["appointment_time"] = $row["appointment_time"];
                    $index["appointment_status"] = $row["appointment_status"];

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