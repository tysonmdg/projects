<?php
namespace es\ucm\fdi\aw;

class Usuario
{

    public static function login($nombreUsuario, $password)
    {
        $user = self::buscaUsuario($nombreUsuario);
        if ($user && $user->compruebaPassword($password)) {
            return $user;
        }
        return false;
    }

    public static function buscaUsuario($nombreUsuario)
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query = sprintf("SELECT * FROM usuario U WHERE U.id = '%s'", $conn->real_escape_string($nombreUsuario));
        $rs = $conn->query($query);
        $result = false;
        if ($rs) {
            if ( $rs->num_rows == 1) {
                $fila = $rs->fetch_assoc();
                $user = new Usuario($fila['id'], $fila['Nombre'],$fila['Apellido1'],$fila['Apellido2'], $fila['Password'], $fila['Tipo']);
                $user->id = $fila['id'];
                $result = $user;
            }
            $rs->free();
        } else {
            echo "Error al consultar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }
        return $result;
    }
    
    public static function crea($nombreUsuario, $nombre, $apellido1, $apellido2, $password, $rol)
    {
        $user = self::buscaUsuario($nombreUsuario);
        if ($user) {
            return false;
        }
        $user = new Usuario($nombreUsuario, $nombre, $apellido1, $apellido2, self::hashPassword($password), $rol);
        return self::guarda($user);
    }
    
    private static function hashPassword($password)
    {
        return password_hash($password, PASSWORD_DEFAULT);
    }
    
    public static function guarda($usuario)
    {
        return self::inserta($usuario);
    }
    
    private static function inserta($usuario)
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query=sprintf("INSERT INTO usuario(id, nombre, apellido1, apellido2, password, tipo) VALUES('%s', '%s', '%s', '%s', '%s', '%s')"
            , $conn->real_escape_string($usuario->nombreUsuario)
            , $conn->real_escape_string($usuario->nombre)
            , $conn->real_escape_string($usuario->apellido1)
            , $conn->real_escape_string($usuario->apellido2)
            , $conn->real_escape_string($usuario->password)
            , $conn->real_escape_string($usuario->rol));
        if ( $conn->query($query) ) {
            $usuario->nombreUsuario = $conn->insert_id;
        } else {
            echo "Error al insertar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }
        return $usuario;
    }
    
    public static function actualiza($usuario, $nuevoId, $nuevoNombre, $nuevoApellido1, $nuevoApellido2, $password)
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query=sprintf("UPDATE usuario U SET id = '%s', nombre='%s', apellido1='%s', apellido2='%s', password='%s' WHERE U.id= '%s'"
            , $conn->real_escape_string($nuevoId)
            , $conn->real_escape_string($nuevoNombre)
            , $conn->real_escape_string($nuevoApellido1)
            , $conn->real_escape_string($nuevoApellido2)
            , $conn->real_escape_string(self::hashPassword($password))
            , $usuario);
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

    public static function mostrarUsuario($usuario)
    {
        $user = self::buscaUsuario($usuario);
        $contenidoPrincipal = '';
        $nombreCompleto = $user->nombre . " " . $user->apellido1 ." " . $user->apellido2;
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query=sprintf("SELECT a.Nombre FROM rel_usuario_academia r JOIN academia a ON r.IdAcademia = a.id WHERE r.IdUsuario = '%s'"
        , $conn->real_escape_string($user->id));
        $rs = $conn->query($query);
        if($rs && $rs->num_rows > 0)
        {
            $html = "<h3>Academias a las que estás inscrito: </h3>";
            while($fila = $rs->fetch_assoc()){
                $html .="<li> <a href='verAcademia.php?nombre=${fila['Nombre']}'> ${fila['Nombre']} </a> </li>";
            }
        } else
        {
            $html ="No se ha inscrito a ninguna academia todavía.";
        }

        $contenidoPrincipal .= <<<EOS
            <p> <h1>$usuario</h1> </p>
            <p> <h3>$nombreCompleto</h3> </p>
            <p> $html </p>
    
        EOS;

    return $contenidoPrincipal;
    }

    public static function listaUsuarios($nombre)
    {
        $html='';
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query =  "SELECT * FROM usuario WHERE Nombre LIKE '%$nombre%'";
        $rs = $conn->query($query);

        if ($rs && $rs->num_rows > 0) {
            while($fila = $rs->fetch_assoc()){
                 $user = new Usuario($fila['id'],$fila['Nombre'], $fila['Apellido1'], $fila['Apellido2'], $fila['Password'], $fila['Tipo']);       
          
                $html .= "<p> <a href='perfil.php?nombre=$user->nombreUsuario'> $user->nombreUsuario </a> </p> ";
            }
        }else  $html .= '';

        $rs->free();

        return $html;
    }

    public static function eliminaUsuario($nombreUsuario)
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query = sprintf("DELETE FROM usuario WHERE id = '%s'", $conn->real_escape_string($nombreUsuario));
        $rs = $conn->query($query);
        if ( !$rs ) {
            echo "Error al borrar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }
    }

    private $nombreUsuario;

    private $nombre;

    private $apellido1;

    private $apellido2;

    private $password;

    private $rol;

    private $academia;

    private function __construct($nombreUsuario, $nombre,$apellido1,$apellido2, $password, $rol)
    {
        $this->nombreUsuario= $nombreUsuario;
        $this->nombre = $nombre;
        $this->password = $password;
        $this->rol = $rol;
        $this->apellido1=$apellido1;
        $this->apellido2=$apellido2;
    }

     public function nombre()
    {
        return $this->nombre;
    }

     public function academia()
    {
        return $this->academia;
    }

    public function rol()
    {
        return $this->rol;
    }

    public function apellido1()
    {
        return $this->apellido1;
    }

    public function apellido2()
    {
        return $this->apellido2;
    }

    public function nombreUsuario()
    {
        return $this->nombreUsuario;
    }

    public function compruebaPassword($password)
    {
        return password_verify($password, $this->password);
    }

    public function cambiaPassword($nuevoPassword)
    {
        $this->password = self::hashPassword($nuevoPassword);
    }
}
