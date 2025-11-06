<?php
include 'dbconnect.php';

if (isset($_GET['id'])) {
    $id = $_GET['id'];
    $result = mysqli_query($conn, "SELECT * FROM students WHERE id=$id");
    $row = mysqli_fetch_assoc($result);
}
?>

<!DOCTYPE html>
<html>
<body>
<h2>Edit Student</h2>
<form action="" method="post">
    Firstname: <input type="text" name="firstname" value="<?php echo $row['firstname']; ?>"><br><br>
    Lastname: <input type="text" name="lastname" value="<?php echo $row['lastname']; ?>"><br><br>
    Age: <input type="number" name="age" value="<?php echo $row['age']; ?>"><br><br>
    <input type="submit" name="update" value="Update">
</form>

<?php
if (isset($_POST['update'])) {
    $fname = $_POST['firstname'];
    $lname = $_POST['lastname'];
    $age = $_POST['age'];
    $update = "UPDATE students SET firstname='$fname', lastname='$lname', age='$age' WHERE id=$id";
    if (mysqli_query($conn, $update)) {
        echo "Record updated successfully.";
        echo "<br><a href='index.php'>Go Back</a>";
    } else {
        echo "Error updating record.";
    }
}
mysqli_close($conn);
?>
</body>
</html>
