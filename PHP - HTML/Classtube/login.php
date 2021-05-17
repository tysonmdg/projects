<?php

require_once __DIR__.'/includes/config.php';

$form = new es\ucm\fdi\aw\FormularioLogin();
$htmlFormLogin = $form->gestiona();

$tituloPagina = 'Login - CLASSTUBE';

$contenidoPrincipal = <<<EOS
<link rel="stylesheet" href="css/Formulario.css" />
<div class="login">
    $htmlFormLogin 
</div>
EOS;

require __DIR__.'/includes/plantillas/plantilla.php';