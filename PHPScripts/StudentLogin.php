<?php

require('init.php');
$Acc=$_POST["Acc"];
$Pass=$_POST["Password"];
$sql="SELECT Password FROM `student` WHERE (ID ='$Acc' or Email='$Acc');";
$result=mysqli_query($conn,$sql);
$response= array();

if(mysqli_num_rows($result)==1){
	$row = mysqli_fetch_assoc($result);
	$PasswordHashed= $row["Password"];
	if(password_verify($Pass,$PasswordHashed)){
		array_push($response, array('code'=>"Success",'message'=>"Done"));
	}
	else{
		array_push($response, array('code'=>"Fail",'message'=>"Wrong Password!"));
		echo json_encode($response);
	}
}
else{
	array_push($response, array('code'=>"Fail",'message'=>"Wrong ID or Email !"));
	echo json_encode($response);

}

$sql="SELECT ID FROM `student` WHERE (Email='$Acc' OR ID='$Acc')";

$result=mysqli_query($conn,$sql);

if(mysqli_num_rows($result)==1)
{/*
	$row=mysqli_fetch_assoc($result);
	$ID=$row["ID"];
	*/
	$ID=mysqli_fetch_assoc($result)["ID"];
	$sql2="SELECT Course_Code,IS_Evaluate1,IS_Evaluate2 FROM `registeration` WHERE Student_ID='$ID'";
	$subjectQuery=mysqli_query($conn,$sql2);
	if(mysqli_num_rows($subjectQuery)>0){
		while($row=mysqli_fetch_assoc($subjectQuery)){
			$courseCode=$row["Course_Code"];
			$isEval1=$row["IS_Evaluate1"];
			$isEval2=$row["IS_Evaluate2"];
			$sql3="SELECT is_active1 , is_active2 FROM `course` WHERE Code='$courseCode'";

			$activeQuery=mysqli_query($conn,$sql3);
			$activeRow=mysqli_fetch_assoc($activeQuery);

			array_push($response, array("subjectCode"=>$courseCode ,"DoneEval1"=>$isEval1,"DoneEval2"=>$isEval2, "ActiveEval1"=>$activeRow["is_active1"],"ActiveEval2"=>$activeRow["is_active2"]));
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