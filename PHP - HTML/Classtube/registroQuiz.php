<?php

require_once __DIR__.'/includes/config.php';
require_once __DIR__.'/includes/FormularioQuiz.php';

$form = new es\ucm\fdi\aw\FormularioQuiz();
$htmlFormRegistro = $form->gestiona();

$tituloPagina = 'Quiz - CLASSTUBE';

$contenidoPrincipal = <<<EOS
<h1>Quiz</h1>
$htmlFormRegistro
EOS;

require __DIR__.'/includes/plantillas/plantilla.php';