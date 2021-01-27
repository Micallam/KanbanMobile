<?php

if ($_SERVER['REQUEST_METHOD'] =='POST'){

	$id = $_POST['id'];
    $title = $_POST['title'];
    $description = $_POST['description'];
    $assignedUser = $_POST['assignedUser'];

    require_once 'connect.php';

    $sql = "UPDATE Task
        SET title = '$title',
            description = '$description',
            assignedUser = '$assignedUser'
        WHERE id = '$id'";

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