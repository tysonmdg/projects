<?php 
		$ca = <<<EOS
		<ul>
		
	<form action="lista.php" method="get">
		<div id="contenedor_formulario">
			<input id="search" type="text" name="nombre" placeholder="Busca algo"/>
			<input id="lupita" type="image" src="img/search.png" border="0" alt="Buscar" />
		</div>


	</form>
		<li><a class="link" href="index.php" class="sbi_redirect">Inicio</a></li>
		<li><a class="link" href="lista.php" class="sbi_redirect">Listar Academias</a></li>
		<li><a class="link" href="listaU.php" class="sbi_redirect">Listar Usuarios</a></li>	

					
	EOS;

	if(isset($_SESSION['login']) && $_SESSION['rol'] == 'Profesor')
	{
		$ca .= <<<EOS
		<li><a class="link" href="registroAcademias.php" class="sbi_redirect">Registrar Academia</a></li>
		</ul>
		EOS;
	}
	else
	{
		$ca .= <<<EOS
		</ul>
		EOS;
	}
?>

<script src = "js/comun/comun.js">
document.querySelector('[data-toggle]').onclick = toggleNav();
</script>
<link rel="stylesheet" href="css/comun/sidebarIzq.css">


<nav id="sbi_nav" data-hideme>

	<a id="close_nav_button" onclick="hideNav()">&times;</a> 
	<?= $ca ?>
	
</nav>
