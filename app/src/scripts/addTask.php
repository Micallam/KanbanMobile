<?php

if ($_SERVER['REQUEST_METHOD'] =='POST'){

	$title = $_POST['title'];
    $description = $_POST['description'];
	$createdBy = $_POST['createdBy'];
	$assignedUser = $_POST['assignedUser'];

    require_once 'connect.php';

    $sql = "INSERT INTO Task (status, title, description, assignedUser, createdBy, createdDateTime) VALUES (0, '$title', '$description', (SELECT id FROM User WHERE login='$assignedUser'),
        (SELECT id FROM User WHERE login='$createdBy'), NOW())";

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