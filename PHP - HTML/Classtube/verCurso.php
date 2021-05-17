<?php
namespace es\ucm\fdi\aw;
require_once __DIR__.'/includes/config.php';

$tituloPagina = 'Ver Curso - CLASSTUBE';
$curso = $_GET['nombre'];
$academia = $_GET['academia'];

$contenidoPrincipal = '';
$html=Curso::mostrarCurso($curso, $academia);

$contenidoPrincipal .= <<<EOS
	<p> <h1>$html</h1> </p>
	EOS;



require __DIR__.'/includes/plantillas/plantilla.php';