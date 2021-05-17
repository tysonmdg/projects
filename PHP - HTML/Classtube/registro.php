<?php

require_once __DIR__.'/includes/config.php';
require_once __DIR__.'/includes/FormularioRegistro.php';

$form = new es\ucm\fdi\aw\FormularioRegistro();
$htmlFormRegistro = $form->gestiona();

$tituloPagina = 'Registro - CLASSTUBE';

$contenidoPrincipal = <<<EOS
<link rel="stylesheet" href="css/Formularios.css" />
<div class="registro">
    $htmlFormRegistro
</div>
EOS;

require __DIR__.'/includes/plantillas/plantilla.php';