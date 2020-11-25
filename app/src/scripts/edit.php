<?php

if ($_SERVER['REQUEST_METHOD'] =='POST'){

	$login = $_POST['login'];
    $old_password = $_POST['old_password'];
	$new_password = $_POST['new_password'];

    require_once 'connect.php';

    $sql = "SELECT * FROM User WHERE login='$login'";
    $sql2 = "UPDATE User SET password='$new_password' WHERE login='$login'";

    if( mysqli_num_rows(mysqli_query($conn, $sql)) == 1) {

        $row = mysqli_fetch_assoc(mysqli_query($conn, $sql));

        if($row['password'] == $old_password) {

            mysqli_query($conn, $sql2);

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

    } else {

        $result["success"] = "0";
        $result["message"] = "error";

        echo json_encode($result);
        mysqli_close($conn);

    }
}

?>