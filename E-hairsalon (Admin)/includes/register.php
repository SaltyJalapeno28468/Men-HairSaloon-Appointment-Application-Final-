<?php
if(isset($_POST['name']) && isset($_POST['email']) && isset($_POST['password'])){

    require_once "Conn.php";
    require_once "validate.php";
    $Fname = validate($_POST['Fname']);
    $Mname = validate($_POST['Mname']);
    $Lname = validate($_POST['Lname']);
    $email = validate($_POST['email']);
    $password =validate($_POST['password']);
    $sql ="insert into tblregistration values('', '$Fname','$Mname','$Lname','$email','". md5($password) . "')";
    if(!$conn->query($sql)){
        echo "failure";
    }else{

        echo"success";
    }
}
?>