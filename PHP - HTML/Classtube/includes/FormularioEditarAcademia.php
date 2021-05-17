<?php
namespace es\ucm\fdi\aw;

class FormularioEditarAcademia extends Form
{
    public function __construct() {
        parent::__construct('formRegistro');
    }
    
    protected function generaCamposFormulario($datos, $errores = array())
    {
        $nombre = $datos['nombre'] ?? '';

        // Se generan los mensajes de error si existen.
        $htmlErroresGlobales = self::generaListaErroresGlobales($errores);
        $errorNombre = self::createMensajeError($errores, 'nombre', 'span', array('class' => 'error'));

        $html = <<<EOF
            <fieldset>
                $htmlErroresGlobales
               
                <div class="grupo-control">
                    <input class="control" type="text" name="nombre" value="$nombre" placeholder="Nuevo nombre de academia"/>$errorNombre
                </div>
                <div class="grupo-control">
                    <input class="control" type="text" name="direccion" placeholder="Nuevo dirección"/>
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
        $academia = $_GET['nombre'];
        
        $direccion = $datos['direccion'] ?? null;
    
        
        $nombre = $datos['nombre'] ?? null;
        if ( empty($nombre) || mb_strlen($nombre) < 3 ) {
            $result['nombre'] = "El nombre tiene que tener una longitud de al menos 3 caracteres.";
        }


        if (count($result) === 0) {
            Academia::actualiza($academia, $nombre, $direccion);
            $result = "editarAcademia.php?nombre=$academia";
        	
        }
        return $result;
    }
}