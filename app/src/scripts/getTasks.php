<?php

	require_once 'connect.php';

	if (mysqli_connect_errno()) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
		die();
	}

	$stmt = $conn->prepare("select status, title, description, assignedUser, createdBy, createdDateTime from Task;");

	$stmt->execute();

	$stmt->bind_result($status, $title, $description, $assignedUser, $createdBy, $createdDateTime);

	$tasks = array();

	while($stmt->fetch()){
		$temp = array();
		$temp['status'] = $status;
		$temp['title'] = $title;
		$temp['description'] = $description;
		$temp['assignedUser'] = $assignedUser;
		$temp['createdBy'] = $createdBy;
		$temp['createdDateTime'] = $createdDateTime;

		array_push($tasks, $temp);
	}

	echo json_encode($tasks);

?>