<?php
namespace es\ucm\fdi\aw;

class FormularioRegistro extends Form
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
                <legend>Registro</legend>
                <div class="grupo-control">
                    <input class="control" type="text" name="nombre" value="$nombre" placeholder="Nombre"/>$errorNombre
                </div>
                <div class="grupo-control">
                    <input class="control" type="text" name="apellido1" value="$apellido1" placeholder="Primer apellido"/>
                </div>
                <div class="grupo-control">
                    <input class="control" type="text" name="apellido2" value="$apellido2" placeholder="Segundo apellido"/>
                </div>
                 <div class="grupo-control">
                    <input class="control" type="text" name="nombreUsuario" value="$nombreUsuario"  placeholder="Nombre de usuario"/>$errorNombreUsuario
                </div>
                <div class="grupo-control">
                    <input class="control" type="password" name="password" placeholder="Contraseña"/>$errorPassword
                </div>
                <div class="grupo-control">
                    <input class="control" type="password" name="password2" placeholder="Repita la contraseña"/>$errorPassword2
                </div>
                <div class="grupo-control">
                    <select name="rol">
                        <option value="1">Estudiante</option>
                        <option value="2">Profesor</option>
                    </select>
                </div>

                <div class="grupo-control"><button type="submit" name="registro">Registrar</button></div>
            </fieldset>
        EOF;
        return $html;
    }
    

    protected function procesaFormulario($datos)
    {
        $result = array();
        
        $nombreUsuario = $datos['nombreUsuario'] ?? null;
    
        
        if ( empty($nombreUsuario) || mb_strlen($nombreUsuario) < 3 ) {
            $result['nombreUsuario'] = "El nombre de usuario tiene que tener una longitud de al menos 3 caracteres.";
        }
        
        $nombre = $datos['nombre'] ?? null;
        if ( empty($nombre) || mb_strlen($nombre) < 3 ) {
            $result['nombre'] = "El nombre tiene que tener una longitud de al menos 3 caracteres.";
        }
        
        $password = $datos['password'] ?? null;
        if ( empty($password) || mb_strlen($password) < 6 || !preg_match('`[A-z]`',$password) || !preg_match('`[0-9]`',$password)) {
            $result['password'] = "La contraseña debe tener 6 o más caracteres, tener al menos una letra y al menos un número.";
        }
        $password2 = $datos['password2'] ?? null;
        if ( empty($password2) || strcmp($password, $password2) !== 0 ) {
            $result['password2'] = "Los passwords deben coincidir";
        }

        $apellido1 = $datos['apellido1'] ?? null;
        $apellido2 = $datos['apellido2'] ?? null;
        $num = $datos['rol'];
        if($num == 1) $rol = "Alumno";
        else $rol = "Profesor";

        if (count($result) === 0) {
            $user = Usuario::crea($nombreUsuario, $nombre, $apellido1, $apellido2, $password, $rol);
            if ( ! $user ) {
                $result[] = "El usuario ya existe";
            } else {
                $_SESSION['login'] = true;
                $_SESSION['nombre'] = $nombreUsuario;
                $_SESSION['rol'] = $rol;
                $result = 'index.php';
            }
        }
        return $result;
    }
}