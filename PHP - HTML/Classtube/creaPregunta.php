<?php
namespace es\ucm\fdi\aw;

require_once __DIR__.'/includes/config.php';
require_once __DIR__.'/includes/FormularioCreaPregunta.php';

$form = new FormularioCreaPregunta();
$htmlFormRegistro = $form->gestiona();

$tituloPagina = 'Pregunta - CLASSTUBE';
$idQuiz = $_GET['nombre'];
$numPregunta = Pregunta::preguntaActual($idQuiz);

$contenidoPrincipal = <<<EOS
<h1>Pregunta $numPregunta</h1>
$htmlFormRegistro
EOS;

require __DIR__.'/includes/plantillas/plantilla.php';