<?php

include('includes/dbconnection.php');
if (isset($_POST['register'])) {

//        $ip = getIp();

    $sername = $_POST['sername'];
    $service_price = $_POST['service_price'];
    $service_time = $_POST['service_time'];
    $service_discount = $_POST['service_discount'];
    $package_type = $_POST['package'];
    $c_image = $_FILES['c_image']['name'];
    $c_image_tmp = $_FILES['c_image']['tmp_name'];

//        move_uploaded_file($c_image_tmp,"customer/customer_images/$c_image");
    move_uploaded_file($c_image_tmp, "../service_img/$c_image");
    $file_name="service_img/$c_image";

    $query = mysqli_query($con, "insert into  tbl_service(service_name,service_time,service_price,service_discount,package_type,img_url) value('$sername','$service_time','$service_price','$service_discount','$package_type','$file_name')");
    if ($query) {
        echo "<script>alert('Service has been added.');</script>";
        echo "<script>window.location.href = 'add-services.php'</script>";
    } else {
        echo "<script>alert('Something Went Wrong. Please try again.');</script>";
    }

}
?>