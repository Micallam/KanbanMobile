<?php

if ($_SERVER['REQUEST_METHOD'] =='POST'){

	$login = $_POST['login'];
    $password = $_POST['password'];
	$type = $_POST['type'];

    require_once 'connect.php';

    $sql = "INSERT INTO User (type, login, password) VALUES ('$type', '$login', '$password')";

    if ( mysqli_query($conn, $sql) ) {
        $result["success"] = "1";
        $result["message"] = "success";

        echo json_encode($result);
        mysqli_close($conn);

    } else {

        $result["success"] = "0";
        $result["message"] = "error";

        echo json_encode($result);
        mysqli_close($conn);

    }
}

?>