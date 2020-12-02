<?php

if ($_SERVER['REQUEST_METHOD'] =='POST'){

	$name = $_POST['name'];
    $description = $_POST['description'];
	$eventDateTime = $_POST['eventDateTime'];

    require_once 'connect.php';

    $sql = "INSERT INTO Event (name, description, eventDateTime) VALUES ('$name', '$description', '$eventDateTime');";

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