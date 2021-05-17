<?php
namespace es\ucm\fdi\aw;

class FormularioEditarPerfil extends Form
{
    public function __construct() {
        parent::__construct('formRegistro');
    }
    
    protected function generaCamposFormulario($datos, $errores = array())
    {
        $nombreUsuario = $datos['nombreUsuario'] ?? '';
        $nombre = $datos['nombre'] ?? '';
        $apellido1 = $datos['apellido1'] ?? '';
        $apellido2 = $datos['apellido2'] ?? '';

        // Se generan los mensajes de error si existen.
        $htmlErroresGlobales = self::generaListaErroresGlobales($errores);
        $errorNombreUsuario = self::createMensajeError($errores, 'nombreUsuario', 'span', array('class' => 'error'));
        $errorNombre = self::createMensajeError($errores, 'nombre', 'span', array('class' => 'error'));
        $errorPassword = self::createMensajeError($errores, 'password', 'span', array('class' => 'error'));
        $errorPassword2 = self::createMensajeError($errores, 'password2', 'span', array('class' => 'error'));

        $html = <<<EOF
            <fieldset>
                $htmlErroresGlobales
               
               <p><h1> Cambio de información de usuario <h1></p>
                <div class="grupo-control">
                    <input class="control" type="text" name="nombre" value="$nombre" placeholder="Nuevo nombre"/>$errorNombre
                </div>
                <div class="grupo-control">
                    <input class="control" type="text" name="apellido1" value="$apellido1" placeholder="Nuevo primer apellido"/>
                </div>
                <div class="grupo-control">
                    <input class="control" type="text" name="apellido2" value="$apellido2" placeholder="Nuevo segundo apellido"/>
                </div>

                <p><h1> Cambio de nombre de usuario <h1></p>
                 <div class="grupo-control">
                    <input class="control" type="text" name="nombreUsuario" value="$nombreUsuario"  placeholder="Nuevo nombre de usuario"/>$errorNombreUsuario
                </div>
                
                <p><h1> Cambio de contraseña <h1></p>
                <div class="grupo-control">
                    <input class="control" type="password" name="newpassword1" placeholder="Nueva contraseña"/>$errorPassword2
                </div>
                <div class="grupo-control">
                    <input class="control" type="password" name="newpassword2" placeholder="Repite la nueva contraseña"/>$errorPassword2
                </div>

                <p><h1> Verifique su contraseña actual <h1></p>
                <div class="grupo-control">
                    <input class="control" type="password" name="passwordAct" placeholder="Contraseña actual"/>$errorPassword
                </div>

                <div class="grupo-control"><button type="submit" name="registro">Guardar cambios</button>
                    </div>
            </fieldset>
        EOF;
        return $html;
    }
    

    protected function procesaFormulario($datos)
    {
        $result = array();
        $usuario = $_GET['nombre'];
        $user = Usuario::buscaUsuario($usuario);
        
        $nombreUsuario = $datos['nombreUsuario'] ?? null;
        if (!empty($nombreUsuario) ) {
            if(mb_strlen($nombreUsuario) < 3 ) $result['nombreUsuario'] = "El nombre de usuario tiene que tener una longitud de al menos 3 caracteres.";
        }
        else $nombreUsuario = $user->nombreUsuario();

        
        $nombre = $datos['nombre'] ?? null;
        if (!empty($nombre)) {
            if(mb_strlen($nombre) < 3 ) $result['nombre'] = "El nombre tiene que tener una longitud de al menos 3 caracteres.";
        }
        else $nombre = $user->nombre();

        $passwordAct = $datos['passwordAct'] ?? null;
        $comprueba = $user->compruebaPassword($passwordAct);
        if ( empty($passwordAct) || !$comprueba) {
            $result['newpassword1'] = "La contraseña no coincide.";
        }
        
        $newpassword1 = $datos['newpassword1'] ?? null;
        if (!empty($newpassword1)) {
            if(mb_strlen($newpassword1) < 6 || !preg_match('`[A-z]`',$newpassword1) || !preg_match('`[0-9]`',$newpassword1))
                $result['newpassword1'] = "La contraseña debe tener 6 o más caracteres, tener al menos una letra y al menos un número.";
            $user->cambiaPassword($newpassword1);
        }
        else $newpassword1 = $passwordAct;

        $newpassword2 = $datos['newpassword2'] ?? null;
        if (!empty($newpassword2) ) {
            $user->cambiaPassword($newpassword2);
            if(strcmp($newpassword1, $newpassword2) !== 0) $result['newpassword2'] = "Los passwords deben coincidir";
        }   

        $apellido1 = $datos['apellido1'] ?? null;
        if (empty($apellido1) ) {
            $apellido1 = $user->apellido1();
        }

        $apellido2 = $datos['apellido2'] ?? null;
        if (empty($apellido2) ) {
            $apellido2 = $user->apellido2();
        }


        if (count($result) === 0) {

            if($usuario == $_SESSION['nombre']) $_SESSION['nombre'] = $nombreUsuario;
            Usuario::actualiza($usuario, $nombreUsuario, $nombre, $apellido1, $apellido2, $newpassword1);
            $result = "editarPerfil.php?nombre=$nombreUsuario";
        	
        }
        return $result;
    }
}