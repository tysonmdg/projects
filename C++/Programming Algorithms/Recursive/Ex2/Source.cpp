// Nombre del alumno: Tyson Mendes	
// Usuario del Juez: A47


#include <iostream>
#include <iomanip>
#include <fstream>



// función que resuelve el problema
long long int resolver(long long int datos,long long int pot) {

	if (datos < 10)
	{
		if (datos % 2 == 0) return 0;
		else
		{
			return datos*pot;
		}
	}
	else
	{
		if ((datos % 10) % 2 == 0) return resolver(datos/10,pot);
		else
		{
			return (datos % 10) * pot + resolver(datos/10, pot*10);
		}

	}
}

// Resuelve un caso de prueba, leyendo de la entrada la
// configuración, y escribiendo la respuesta
bool resuelveCaso() {
	// leer los datos de la entrada
	long long int datos;

	std::cin >> datos;
	if (!std::cin)
		return false;

	long long int sol = resolver(datos, 1);

	// escribir sol
	std::cout << sol << std::endl;

	return true;

}

int main() {
	// Para la entrada por fichero.
	// Comentar para acepta el reto
/*#ifndef DOMJUDGE
	std::ifstream in("datos.txt");
	auto cinbuf = std::cin.rdbuf(in.rdbuf()); //save old buf and redirect std::cin to casos.txt
#endif 
*/

	while (resuelveCaso())
		;


	// Para restablecer entrada. Comentar para acepta el reto
/*#ifndef DOMJUDGE // para dejar todo como estaba al principio
	std::cin.rdbuf(cinbuf);
	system("PAUSE");
#endif
*/
	return 0;
}