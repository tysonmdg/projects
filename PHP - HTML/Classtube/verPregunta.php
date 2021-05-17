<?php

require_once __DIR__.'/includes/config.php';
require_once __DIR__.'/includes/FormularioResponderPregunta.php';


$tituloPagina = 'Ver Pregunta - CLASSTUBE';
$numPregunta = $_GET['nombre'];

$form = new es\ucm\fdi\aw\FormularioResponderPregunta();
$htmlFormRegistro = $form->gestiona();

$contenidoPrincipal = '';   

$contenidoPrincipal .= <<<EOS
	<p> <h1>$htmlFormRegistro</h1> </p>
	EOS;



require __DIR__.'/includes/plantillas/plantilla.php';