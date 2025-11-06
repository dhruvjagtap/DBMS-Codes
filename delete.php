<?php
include 'dbconnect.php';

$id = $_GET['id'];
$sql = "DELETE FROM students WHERE id=$id";

if (mysqli_query($conn, $sql)) {
    echo "Record deleted successfully.";
} else {
    echo "Error deleting record: " . mysqli_error($conn);
}

mysqli_close($conn);
?>
<br>
<a href="index.php">Go Back</a>
