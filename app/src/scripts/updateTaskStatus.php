<?php

if ($_SERVER['REQUEST_METHOD'] =='POST'){

	$id = $_POST['id'];
    $status = $_POST['status'];

    require_once 'connect.php';

    $sql = "UPDATE Task SET status=$status WHERE id=$id";
        

    if ( mysqli_query($conn, $sql) ) {

        $result["success"] = "1";
        $result["message"] = "success";

    } else {

        $result["success"] = "0";
        $result["message"] = "error";

    }

    echo json_encode($result);
    mysqli_close($conn);
}

?>