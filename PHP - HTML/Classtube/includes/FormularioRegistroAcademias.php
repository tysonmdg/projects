<?php
namespace es\ucm\fdi\aw;

class FormularioRegistroAcademias extends Form
{
    public function __construct() {
        parent::__construct('formRegistro');
    }
    
    protected function generaCamposFormulario($datos, $errores = array())
    {
        $nombre = $datos['nombre'] ?? '';
        $direccion = $datos['direccion'] ?? '';

        // Se generan los mensajes de error si existen.
        $htmlErroresGlobales = self::generaListaErroresGlobales($errores);
        $errorNombre = self::createMensajeError($errores, 'nombre', 'span', array('class' => 'error'));
        $errorDireccion = self::createMensajeError($errores, 'direccion', 'span', array('class' => 'error'));

        $html = <<<EOF
            <fieldset>
                $htmlErroresGlobales
               
                <div class="grupo-control">
                    <input class="control" type="text" name="nombre" value="$nombre" placeholder="Nombre de la academia"/>$errorNombre
                </div>
                <div class="grupo-control">
                    <input class="control" type="text" name="direccion" value="$direccion" placeholder="Direccion"/>$errorDireccion
                </div>

                <div class="grupo-control"><button type="submit" name="registro">Registrar academia</button></div>
            </fieldset>
        EOF;
        return $html;
    }
    

    protected function procesaFormulario($datos)
    {
        $result = array();
        
    
        $nombre = $datos['nombre'] ?? null;
        if ( empty($nombre) || mb_strlen($nombre) < 4 ) {
            $result['nombre'] = "El nombre tiene que tener una longitud de al menos 4 caracteres.";
        }
        
        $direccion = $datos['direccion'] ?? null;
        if (empty($direccion)) {
            $result['direccion'] = "Debes introducir una direccion";
        }
     


        if (count($result) === 0) {
            $academia = Academia::crea($nombre, $direccion);
            if ( ! $academia ) {
                $result[] = "La academia ya existe";
            } else {
                $result = 'lista.php';
            }
        }
        return $result;
    }
}