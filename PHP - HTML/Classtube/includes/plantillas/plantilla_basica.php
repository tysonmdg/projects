<!DOCTYPE html>
<html>

<head>
	<link rel="stylesheet" type="text/css" href="css/estilo.css" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title><?= $tituloPagina ?></title>
</head>
<body>
	<div id="cuerpo">
		<?php require("includes/comun/sidebarIzq.php"); ?>
		<div id="top">
			<?php require("includes/comun/cabecera.php"); ?>

			<main id="articulo">
				<article>
					<?= $contenidoPrincipal ?>
				</article>
			</main>
			
		</div>
		<?php require("includes/comun/pie.php"); ?>
	</div>
</body>

</html>