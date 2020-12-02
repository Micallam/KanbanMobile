<?php 
 	
	require_once 'connect.php';
	
	if (mysqli_connect_errno()) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
		die();
	}
		
	$stmt = $conn->prepare("select name, description, eventDateTime from Event;");
	
	$stmt->execute();
	
	$stmt->bind_result($name, $description, $eventDateTime);
	
	$events = array();
	
	while($stmt->fetch()){
		$temp = array();
		$temp['name'] = $name;
		$temp['description'] = $description;
		$temp['eventDateTime'] = $eventDateTime;

		array_push($events, $temp);
	}
	
	echo json_encode($events);
	
?>