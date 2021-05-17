<?php
namespace es\ucm\fdi\aw;
require_once __DIR__.'/includes/config.php';

$tituloPagina = 'Lista de academias - CLASSTUBE';

$contenidoPrincipal = '';
if(isset($_GET['nombre'])) $nombre=$_GET['nombre'];

if(empty($nombre))
	$htmlA=Academia::listaAcademias("");
else 
	$htmlA=Academia::listaAcademias("$nombre");


if($htmlA == '') $htmlA = 'No se han encontrado academias.';
$html = "<p><h1>Academias:</h1></p> <p>$htmlA</p>";

$contenidoPrincipal .= <<<EOS
	<p> <h2>$html</h2> </p>
	EOS;



require __DIR__.'/includes/plantillas/plantilla.php';