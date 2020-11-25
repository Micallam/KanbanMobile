<?php

if ($_SERVER['REQUEST_METHOD'] =='POST'){

	$login = $_POST['login'];
    $password = $_POST['password'];
	$type = $_POST['type'];

    require_once 'connect.php';

    $sql = "SELECT login FROM User WHERE login='$login'";
    $sql2 = "INSERT INTO User (type, login, password) VALUES ('$type', '$login', '$password')";

    if ( mysqli_num_rows(mysqli_query($conn, $sql)) == 1 ) {

        $result["success"] = "0";
        $result["message"] = "error";

    } else {
        mysqli_query($conn, $sql2);

        $result["success"] = "1";
        $result["message"] = "success";
    }

    echo json_encode($result);
    mysqli_close($conn);
}

?>