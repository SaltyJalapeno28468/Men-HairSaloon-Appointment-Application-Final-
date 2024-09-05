<?php
    $server = "localhost";
    $username = "root";
    $password = "";
    $database = "e-hair-salon_final";
    $conn = new mysqli($serwer, $username, $password, $database);
    if($conn->connect_error){
         die("Connection failed:". $conn->connect_error);

    }
?>