<?php
require 'init.php';
$sql = "SELECT description FROM `e_question` WHERE type='before' "; 
$result =mysqli_query($conn,$sql);
$response = array();
if(mysqli_num_rows($result)>0){
	while ($row = mysqli_fetch_assoc($result)){
		$Question=$row["description"];
			array_push($response,array('Question' => $Question));
	}
			echo json_encode($response);
}

?>