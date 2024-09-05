<?php
     if(isset($_POST['email']) && isset($_POST['password'])){
        require_once "Conn.php";
        require_once "validate.php";
        $email = validate($_POST['email']);
        $password = validate($_POST['password']);
        $sql = "select * from tblregistration where email='$email' and password = '" . md5($password) ."'";
        $result = $conn->query($sql);
        if($result->num_rows > 0){
            echo "success";
        }else{
            echo "failure";
        }

     }



?>