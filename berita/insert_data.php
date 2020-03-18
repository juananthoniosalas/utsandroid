<?php

include("config.php");

$judul_berita = $_POST['judul_berita'];
$waktu_berita = $_POST['waktu_berita'];
$penulis_berita = $_POST['penulis_berita'];
$isi_berita = $_POST['isi_berita'];

$sql = "INSERT INTO data_berita VALUES ( '$judul_berita', '$waktu_berita' , '$penulis_berita', '$isi_berita' )";
$query = mysqli_query($db , $sql);

// apakah query update berhasil ?
if ($query) {
  // code...
} else {
  // kalau gagal tampilkan pesan
  die("Gagal menyimpan perubahan");
}
