<?php
namespace es\ucm\fdi\aw;

class Academia
{

    public static function buscaAcademia($nombre)
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query = sprintf("SELECT * FROM academia A WHERE A.nombre = '%s'", $conn->real_escape_string($nombre));
        $rs = $conn->query($query);
        $result = false;
        if ($rs) {
            if ( $rs->num_rows == 1) {
                $fila = $rs->fetch_assoc();
                $academy = new Academia($fila['Nombre'],$fila['id'],$fila['Direccion']);
                $academy->id = $fila['id'];
                $result = $academy;
            }
            $rs->free();
        } else {
            echo "Error al consultar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }
        return $result;
    }

    public static function buscaAcademiaId($id)
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query = sprintf("SELECT * FROM academia WHERE id = '%s'", $conn->real_escape_string($id));
        $rs = $conn->query($query);
        $result = false;
        if ($rs) {
            if ( $rs->num_rows == 1) {
                $fila = $rs->fetch_assoc();
                $academy = new Academia($fila['Nombre'],$fila['id'],$fila['Direccion']);
                $academy->id = $fila['id'];
                $result = $academy;
            }
            $rs->free();
        } else {
            echo "Error al consultar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }
        return $result;
    }

    public static function inscribirse($academia)
{
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query=sprintf("INSERT INTO rel_usuario_academia(idUsuario, idAcademia) VALUES('%s', '%s')"
            , $conn->real_escape_string($_SESSION['nombre'])
            , $conn->real_escape_string($academia));
        if ( $conn->query($query) ) {
            if ( $conn->affected_rows != 1) {
                echo "No se ha podido inscribir el usuario: " . $_SESSION['nombre']. "en la academia: " . $academia->id();
                exit();
            }
        } else {
            echo "Error al insertar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }

}

public static function salirse($academia)
{
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query=sprintf("DELETE FROM rel_usuario_academia WHERE  idUsuario = '%s' && idAcademia = '%s'"
            , $conn->real_escape_string($_SESSION['nombre'])
            , $conn->real_escape_string($academia));
        if ( !$conn->query($query) ) {
            echo "Error al insertar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }

}
    
    public static function listaAcademias($nombre)
    {
        $html='';
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query =  "SELECT * FROM academia WHERE Nombre LIKE '%$nombre%'";
        $rs = $conn->query($query);

        if ($rs && $rs->num_rows > 0) {
            while($fila = $rs->fetch_assoc()){
                 $academy = new Academia($fila['Nombre'], $fila['id'], $fila['Direccion']);       
          
                $html .= "<p> <a href='verAcademia.php?nombre=$academy->nombre'> $academy->nombre </a> </p> ";
            }
        }else  $html .= '';

        $rs->free();

        return $html;
    }

    public static function crea($nombre, $direccion)
    {
        $academy = self::buscaAcademia($nombre);
        if ($academy) {
            return false;
        }
        $lastId = self::lastId();
        $academy = new Academia($nombre, $lastId, $direccion);
        return self::guarda($academy);
    }
    
    public static function lastId()
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $lastId = "SELECT id FROM academia ORDER BY id DESC LIMIT 1";
         $res = mysqli_query($conn, $lastId);
        while ($registro = mysqli_fetch_array($res)){
        $i = intval($registro['id']);
        }
        $ress = $i + 1;
        return $ress;
    }

    public static function guarda($academia)
    {
        $academy = self::buscaAcademia($academia->nombre());
        if ($academy) {
            return self::actualiza($academia);
        }
        return self::inserta($academia);
    }
    
    private static function inserta($academia)
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        //insertar en la tabla de academia
        $query=sprintf("INSERT INTO academia(id, nombre, direccion) VALUES('%s', '%s', '%s')"
            , $conn->real_escape_string($academia->id)
            , $conn->real_escape_string($academia->nombre)
            , $conn->real_escape_string($academia->direccion));
        if (!$conn->query($query) ) {
            echo "Error al insertar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }
        //insertamos en la tabla rel_usuario_academia
        $query=sprintf("INSERT INTO rel_usuario_academia(IdAcademia, IdUsuario, Gestor) VALUES ('%s', '%s', '%s')"
            ,$conn->real_escape_string($academia->id)
            ,$conn->real_escape_string($_SESSION['nombre'])
            ,$conn->real_escape_string("1"));
        if ( $conn->query($query) ){
            $academia->id = $conn->insert_id;
        } else {
            echo "Error al insertar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }
        return $academia;
    }
    
    public static function actualiza($academia, $nombre, $direccion)
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query=sprintf("UPDATE academia SET nombre='%s', direccion='%s' WHERE id='%s'"
            , $conn->real_escape_string($nombre)
            , $conn->real_escape_string($direccion)
            , $academia);
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

    public static function mostrarAcademia($academia)
    {
        $academy = self::buscaAcademia($academia);
        $contenidoPrincipal = '';
        $html='';
        $idAcademia = $academy->id();
        $direccion = $academy->direccion();

        if(isset($_SESSION['login'])){
            $app = Aplicacion::getSingleton();
            $conn = $app->conexionBd();
            $id = $_SESSION['nombre'];
            $query = sprintf("SELECT U.idAcademia FROM rel_usuario_academia U WHERE U.idUsuario = '%s' AND U.idAcademia = '%s'", $conn->real_escape_string($id), $conn->real_escape_string($idAcademia));
            $rs = $conn->query($query);
            $fila= mysqli_fetch_array($rs);
    
            if(!empty($fila['idAcademia']) || $_SESSION['rol']=="Admin"){
                $comp = false;
                $html .= "<h1>Ya estás registrado en esta academia</h1>";
                $query = sprintf("SELECT * FROM rel_usuario_curso INNER JOIN Cursos WHERE Usuario = '%s'"
                                ,$conn->real_escape_string($id));
                $res = $conn->query($query);
                if ($res && $res->num_rows > 0) {
                    while($fila2 = $res->fetch_assoc()){
                        if($fila2['Academia']==$fila['idAcademia'])
                            $comp = true;
                    }
                }else  $html .= '';
                if(!$comp && $_SESSION['rol']!="Admin"){
                    $html .= "¿Quieres salirte de esta academia?
                    <a href='SalirAcademia.php?idAcademia=$idAcademia'> Salirse </a>";
                }
            //mostrar cursos de la academia
            $query = sprintf("SELECT * FROM cursos WHERE Academia = '%s'"
                            , $conn->real_escape_string($idAcademia));
            $rs = $conn->query($query);
            $html .= "<p> Cursos disponibles en esta academia</p>";
            $html .= Curso::ListaCursos("$idAcademia");
           
            }else {
                $html .= "
                <p>¿Quieres unirte a esta academia?
                <a href='Inscribirse.php?idAcademia=$idAcademia'> Unirse </a></p>";
            }

    

            if($_SESSION['rol']=="Profesor" ){
                $query = sprintf("SELECT * FROM rel_usuario_academia WHERE IdUsuario = '%s' AND IdAcademia = '%s' AND Gestor = '1'"
                        , $conn->real_escape_string($id)
                        , $conn->real_escape_string($idAcademia));
                if($conn->query($query)->num_rows == 1 ){
                    $html.= "<p><a href='registroCurso.php?nombre=$idAcademia'> Crear nuevo curso </a></p>

                    <p><form action=editarAcademia.php?nombre=$idAcademia method='post'>
                    <input type='submit' name='edit' value='Editar academia' /></form>

                    <form action=borradoAcademia.php?nombre=$idAcademia method='post'>
                    <input type='submit' name='delete' value='Borrar academia' /></form> </p>";

                }
            }

            if($_SESSION['rol'] == "Admin"){
                $boton = "<form action=borradoAcademia.php?nombre=$idAcademia method='post'>
                                <input type='submit' name='delete' value='Borrar academia' /></form></p>";

                    $html.= "<p> $boton </p>";
            }
            
        }


        $contenidoPrincipal .= <<<EOS
            <p> <h1>$academia</h1> </p>
            <p> <h3>$direccion</h3> </p>
            <p> $html </p>
    
        EOS;

    return $contenidoPrincipal;
    }

    public static function eliminaAcademia($academy)
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query = sprintf("DELETE FROM academia WHERE id = '%s'", $conn->real_escape_string($academy));
        $rs = $conn->query($query);
        if ( !$rs ) {
            echo "Error al borrar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }
    }

 public static function esGestor($nombreUsuario, $idAcademia)
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query = sprintf("SELECT * FROM rel_usuario_academia WHERE IdUsuario = '%s' AND IdAcademia = '%s' AND Gestor = '1'"
            , $conn->real_escape_string($nombreUsuario)
            , $conn->real_escape_string($idAcademia));
        if($conn->query($query)->num_rows == 1) return true;
        else return false;
    }


    private $id;

    private $nombre;

    private $direccion;

    private $anuncio;


    private function __construct($nombre, $id, $direccion)
    {
        $this->nombre = $nombre;
        $this->direccion=$direccion;
        $this->id=$id;
    }

     public function nombre()
    {
        return $this->nombre;
    }

     public function direccion()
    {
        return $this->direccion;
    }

     public function anuncio()
    {
        return $this->anuncio;
    }

     public function id()
    {
        return $this->id;
    }

}