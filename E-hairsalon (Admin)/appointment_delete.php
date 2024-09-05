<?php

require 'dbcon.php';

if (!isset($_POST['appointment_id']) == "") {
    $appointment_id = $_POST['appointment_id'];
//    echo '$district_id '.$district_id;
    $result = array();

    $sql = 'update tbl_appointment set flag="0" where id="'.$appointment_id.'"';
//echo 'query ' . $sql;
    try {
        if ($con) {
            $response = mysqli_query($con, $sql);
                echo 'Record_Delete';
        } else {
            echo 'Connection Problem';
        }
    } catch (Exception $ex) {
        echo $e->getMessage();
    }
} else {
    echo 'Username is Null';
}
?>
