<?php 
 	
	require_once 'connect.php';
	
	if (mysqli_connect_errno()) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
		die();
	}
		
	$stmt = $conn->prepare("select login, type from User;");
	
	$stmt->execute();
	
	$stmt->bind_result($login, $type);
	
	$users = array(); 
	
	while($stmt->fetch()){
		$temp = array();
		$temp['login'] = $login;
		$temp['type'] = $type;
		
		array_push($users, $temp);
	}
	
	echo json_encode($users);
	
?>