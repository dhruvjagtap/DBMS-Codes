<?php
include 'dbconnect.php';

$fname = $_POST['firstname'];
$lname = $_POST['lastname'];
$age = $_POST['age'];

$sql = "INSERT INTO students (firstname, lastname, age) VALUES ('$fname', '$lname', '$age')";

if (mysqli_query($conn, $sql)) {
    echo "Record added successfully.<br>";
    echo "<a href='index.php'>Go Back</a>";
} else {
    echo "Error: " . mysqli_error($conn);
}

mysqli_close($conn);
?>
