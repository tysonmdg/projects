<?php
namespace es\ucm\fdi\aw;
require_once __DIR__.'/includes/config.php';

$tituloPagina = 'Ver Quiz - CLASSTUBE';
$quiz = $_GET['nombre'];
$curso = $_GET['curso'];

$contenidoPrincipal = '';
$html=Quiz::realizarQuiz($quiz, $curso);

$contenidoPrincipal .= <<<EOS
	<p> <h1>$html</h1> </p>
	EOS;



require __DIR__.'/includes/plantillas/plantilla.php';