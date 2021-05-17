// Nombre del alumno: Tyson Mendes Da Gracia
// Usuario del Juez: A47 


#include <iostream>
#include <iomanip>
#include <fstream>



// función que resuelve el problema
int resolver(long long int datos, int sol) {
	if (datos < 10)
	{
		return sol;
	}
	else
	{
		return resolver(datos / 10, sol + 1);
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

    int sol = resolver(datos, 1);

	// escribir sol
	std::cout << sol << std::endl;

	return true;
}

int main() {
	// Para la entrada por fichero.
	// Comentar para acepta el reto
#ifndef DOMJUDGE
	std::ifstream in("datos.txt");
	auto cinbuf = std::cin.rdbuf(in.rdbuf()); //save old buf and redirect std::cin to casos.txt
#endif


	while (resuelveCaso());


	// Para restablecer entrada. Comentar para acepta el reto
#ifndef DOMJUDGE // para dejar todo como estaba al principio
	std::cin.rdbuf(cinbuf);
	system("PAUSE");
#endif

	return 0;
}