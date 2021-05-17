<?php

namespace es\ucm\fdi\aw;
require_once __DIR__.'/includes/config.php';

$tituloPagina = 'Perfil - CLASSTUBE';

$contenidoPrincipal = '';
$foto= <<<EOS
<link rel="stylesheet" href="css/Formularios.css"/>
<img id="avatar" src="img/avatar.png">
EOS;
$usuario = $_GET['nombre'];
$contenidoPrincipal .= $foto;
$contenidoPrincipal .= Usuario::mostrarUsuario($usuario);

if(isset($_SESSION["login"]) && ($_SESSION['nombre'] == $usuario))
{
    $contenidoPrincipal .= "<a href='editarPerfil.php?nombre=$usuario'>Editar Perfil</a>";
}

if(isset($_SESSION["login"]) && $_SESSION['rol'] == 'Admin')
{
    $boton = "<form action=borrado.php?nombre=$usuario method='post'>
                <input type='submit' name='deletePerfil' value='Borrar perfil' /></form>";
    $contenidoPrincipal .= "<p>$boton</p>";

}

require __DIR__.'/includes/plantillas/plantilla.php';