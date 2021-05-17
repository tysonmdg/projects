<?php
namespace es\ucm\fdi\aw;

class Curso
{

    public static function buscaCurso($nombre, $academia)
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query = sprintf("SELECT * FROM cursos WHERE nombre = '%s' AND academia = '%s'"
            , $conn->real_escape_string($nombre)
            , $conn->real_escape_string($academia));
        $rs = $conn->query($query);
        $result = false;
        if ($rs) {
            if ( $rs->num_rows == 1) {
                $fila = $rs->fetch_assoc();
                $curso = new Curso($fila['Id'],$fila['Nombre'],$fila['Comienzo'],$fila['Final'], $fila['Academia']);
                $curso->id = $fila['Id'];
                $result = $curso;
            }
            $rs->free();
        } else {
            echo "Error al consultar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }
        return $result;
    }

    public static function buscaCursoId($id)
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query = sprintf("SELECT * FROM cursos WHERE Id = '%s'"
            , $conn->real_escape_string($id));
        $rs = $conn->query($query);
        $result = false;
        if ($rs) {
            if ( $rs->num_rows == 1) {
                $fila = $rs->fetch_assoc();
                $curso = new Curso($fila['Id'],$fila['Nombre'],$fila['Comienzo'],$fila['Final'], $fila['Academia']);
                $curso->id = $fila['Id'];
                $result = $curso;
            }
            $rs->free();
        } else {
            echo "Error al consultar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }
        return $result;
    }

    public static function inscribirse($idCurso)
{
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query=sprintf("INSERT INTO rel_usuario_curso(Usuario, Curso) VALUES('%s', '%s')"
            , $conn->real_escape_string($_SESSION['nombre'])
            , $conn->real_escape_string($idCurso));
        if ( $conn->query($query) ) {
            if ( $conn->affected_rows != 1) {
                echo "No se ha podido inscribir el usuario: " . $_SESSION['nombre']. "al curso: " . $idCurso;
                exit();
            }
        } else {
            echo "Error al insertar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }

}

public static function salirse($idCurso)
{
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query=sprintf("DELETE FROM rel_usuario_curso WHERE  Usuario = '%s' && Curso = '%s'"
            , $conn->real_escape_string($_SESSION['nombre'])
            , $conn->real_escape_string($idCurso));
        if ( !$conn->query($query) ) {
            echo "Error al insertar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }

}
    
    public static function listaCursos($academia)
    {
        $html = "";
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query =  "SELECT * FROM cursos WHERE Academia LIKE '%$academia%'";
        $rs = $conn->query($query);

        if ($rs && $rs->num_rows > 0) {
            while($fila = $rs->fetch_assoc()){
                $curso = new Curso($fila['Id'],$fila['Nombre'],$fila['Comienzo'],$fila['Final'], $fila['Academia']); 
                if($_SESSION['rol']=="Admin")
                    $html .= "<p><li> $curso->nombre</li> </p> ";
                else
                    $html .= "<p><li> <a href='verCurso.php?nombre=$curso->nombre&&academia=$curso->academia'> $curso->nombre</a></li> </p> ";
            }
        }else  $html .= '';

        $rs->free();

        return $html;
    }

    public static function pagarCurso($idCurso){
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query = "UPDATE rel_usuario_curso SET Pago = 'Pagado' WHERE Curso = '$idCurso' AND Usuario = '{$_SESSION["nombre"]}'";
        $conn->query($query);
        $html = "Has pagado el curso";

        return $html;
    }

    public static function crea($nombre, $fechaI, $fechaF, $academia)
    {

        $curso = self::buscaCurso($nombre, $academia);
        if ($curso) {
            return false;
        }
        $lastId = self::lastId();
        $curso = new Curso($lastId, $nombre, $fechaI, $fechaF, $academia);
        return self::guarda($curso);
    }
    
    public static function lastId()
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $lastId = "SELECT id FROM cursos ORDER BY id DESC LIMIT 1";
        $res = mysqli_query($conn, $lastId);
        $i = 0;
        while ($registro = mysqli_fetch_array($res)){
        $i = intval($registro['id']);
        }
        $ress = $i + 1;
        return $ress;
    }

    public static function guarda($curso)
    {
        $c = self::buscaCurso($curso->nombre(), $curso->academia());
        if ($c) {
            return self::actualiza($curso);
        }
        
        return self::inserta($curso);
    }
    
    private static function inserta($curso)
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();

        //insertar en la tabla de curso
        $query=sprintf("INSERT INTO cursos(id, nombre, Comienzo, Final, Academia) VALUES('%s', '%s', '%s', '%s', '%s')"
            , $conn->real_escape_string($curso->id)
            , $conn->real_escape_string($curso->nombre)
            , $conn->real_escape_string($curso->fechaI)
            , $conn->real_escape_string($curso->fechaF)
            , $conn->real_escape_string($curso->academia));
        if (!$conn->query($query) ) {
            echo "Error al insertar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }
        //insertamos en la tabla rel_usuario_curso
        $query=sprintf("INSERT INTO rel_usuario_curso(Usuario, Curso, Gestor) VALUES ('%s', '%s', 1)"
            ,$conn->real_escape_string($_SESSION['nombre'])
            ,$conn->real_escape_string($curso->id));
        if ( $conn->query($query) ){
            $curso->id = $conn->insert_id;
        } else {
            echo "Error al insertar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }
        return $curso;
    }
    
    private static function actualiza($usuario)
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query=sprintf("UPDATE Usuarios U SET id = '%s', nombre='%s', password='%s', rol='%s' WHERE U.id=%i"
            , $conn->real_escape_string($usuario->nombre)
            , $conn->real_escape_string($usuario->password)
            , $conn->real_escape_string($usuario->rol)
            , $usuario->id);
        if ( $conn->query($query) ) {
            if ( $conn->affected_rows != 1) {
                echo "No se ha podido actualizar el usuario: " . $usuario->id;
                exit();
            }
        } else {
            echo "Error al insertar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }
        
        return $usuario;
    }


    /////////////////////////////////////////
    public static function mostrarCurso($curso, $academia)
    {
        $course = self::buscaCurso($curso, $academia);
        $contenidoPrincipal = '';
        $html='';
        $idCurso = $course->id();
        $id = $_SESSION["nombre"];
        if(isset($_SESSION['nombre'])){
            $app = Aplicacion::getSingleton();
            $conn = $app->conexionBd();
            $id = $_SESSION['nombre'];
            $query = sprintf("SELECT * FROM rel_usuario_curso WHERE Usuario = '%s' AND Curso = '%s'"
            		, $conn->real_escape_string($id)
            	 	, $conn->real_escape_string($idCurso));
            $rs = $conn->query($query);
            $fila= mysqli_fetch_array($rs);
    
            if(!empty($fila['Usuario']) && $fila['Curso']==$idCurso){
                $html .= "<h1>Ya estás registrado en el curso</h1>";
                if($fila['Gestor']==0){
                    if($fila['Pago'] == "No pagado"){
                        $html .="<p>¿Quieres salirte del curso?
                        <a href='SalirCurso.php?idCurso=$idCurso'> Salirse </a> </p>
                        <p>¿Quieres pagar el curso?
                        <a href='PagarCurso.php?idCurso=$idCurso'> Pagar </a> </p>"; //Si llamas a esto te pone en pendiente
                    }
                    else if($fila['Pago'] == "Pagado"){
                        //Contenido del curso
                        $html .= "CONTENIDO CURSO";
                        $html .= Quiz::listaQuizes( $idCurso);
                    }
                }
                if($fila['Gestor']==1){
                    
                    $html .= Quiz::listaQuizes( $idCurso);
                    $html.= "<p>¿Quieres subir nuevo contenido en este curso?
                    <a href='registroContenido.php?idCurso=$idCurso'> Subir contenido </a></p>";
                    $html.="<p>¿Quieres crear un nuevo quiz en este curso?
                    <a href='registroQuiz.php?nombre=$idCurso'> Crear Quiz </a></p>";
                }
            }else {
                $html .= "
                <p>¿Quieres unirte a este curso?
                <a href='InscribirseCurso.php?idCurso=$idCurso'> Unirse </a></p>";
            }
           /* if($_SESSION['rol']=="Profesor"){
                $query = sprintf("SELECT * FROM rel_usuario_academia WHERE IdUsuario = '%s' AND IdAcademia = '%s'"
                        , $conn->real_escape_string($id)
                        , $conn->real_escape_string($idAcademia));
                if($conn->query($query)->num_rows == 1){
                    $html.= "<p>¿Quieres registrar un nuevo curso en esta academia?
                    <a href='registroCurso.php?idAcademia=$idAcademia'> Crear Curso </a></p>";
                }
            }*/
        }


        $contenidoPrincipal .= <<<EOS
            <p> <h1>$curso</h1> </p>
            <p> <h3>$course->fechaI</h3> </p>
            <p> <h3>$course->fechaF</h3> </p>
            <p> $html </p>
    
        EOS;

    return $contenidoPrincipal;
    }


    private $id;

    private $nombre;

    private $fechaI;

    private $fechaF;

    private $academia;


    private function __construct($id, $nombre, $fechaI, $fechaF, $academia)
    {
        $this->nombre = $nombre;
        $this->id=$id;        
        $this->fechaI=$fechaI;
        $this->fechaF=$fechaF;
        $this->academia=$academia;
    }

     public function nombre()
    {
        return $this->nombre;
    }

     public function id()
    {
        return $this->id;
    }

    
    public function fechaI()
    {
        return $this->fechaI;
    }

    public function fechaF()
    {
        return $this->fechaF;
    }

    public function academia()
    {
        return $this->academia;
    }


}