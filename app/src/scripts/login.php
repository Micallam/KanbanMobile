<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST')
{

    $login = $_POST['login'];
    $password = $_POST['password'];

    require_once 'connect.php';

    $sql = "SELECT * FROM User WHERE login='$login'";

    $response = mysqli_query($conn, $sql);

    $result = array();
    $result['login'] = array();

    if (mysqli_num_rows($response) == 1)
    {

        $row = mysqli_fetch_assoc($response);
        $hash = $row['password'];

        if ($password == $hash)
        {

            $index['id'] = $row['id'];
            $index['login'] = $row['login'];
			$index['type'] = $row['type'];

            array_push($result['login'], $index);

            $result['success'] = "1";
            $result['message'] = "success";

            echo json_encode($result);
            mysqli_close($conn);

        }
        else
        {

            $result['success'] = "0";
            $result['message'] = "error";
            echo json_encode($result);

            mysqli_close($conn);

        }
    }
}
?>
