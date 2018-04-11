<?php
require ('init.php');

$Acc=$_POST["Acc"];

$sql="SELECT ID FROM `student` WHERE (Email='$Acc' OR ID='$Acc')";

$result=mysqli_query($conn,$sql);
$response =array();

if(mysqli_num_rows($result)==1)
{
	$row=mysqli_fetch_assoc($result);
	$ID=$row["ID"];
	
	//$ID=mysqli_fetch_assoc($result)["ID"];
	$sql2="SELECT Course_Code FROM `registeration` WHERE Student_ID='$ID'";
	$subjectQuery=mysqli_query($conn,$sql2);
	if(mysqli_num_rows($subjectQuery)>0){
		while($row=mysqli_fetch_assoc($subjectQuery)){
			$courseCode=$row["Course_Code"];
			array_push($response, array("SubjectCode"=>$courseCode));
		}
		echo json_encode($response);
	}
	else{
	array_push($response, array('code'=>"HasNoSubject!",'message'=>"You havn't Registered to any subject yet!"));
	echo json_encode($response);

	}
}
else{
	array_push($response, array('code'=>"NotFound!",'message'=>"Please go to the department to check your data !"));
	echo json_encode($response);
}
mysqli_close($conn);
?>