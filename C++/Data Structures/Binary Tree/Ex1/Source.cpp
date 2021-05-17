// Nombre del alumno: Tyson Mendes Da Gracia y Daniel Ledesma
// Usuario del Juez: C67 y C55


#include <iostream>
#include <iomanip>
#include <fstream>
#include "bintree.h"

bool esHoja(bintree<int> datos)
{
	return datos.left().empty() && datos.right().empty();
}

// Coste O(n) siendo n el numero de elementos del arbol ya que se recorre los elementos una sola vez cada uno.

//
// función que resuelve el problema
int resolver(bintree<int> datos, int&sol, int solprov) {
	if (esHoja(datos))
	{
		solprov += datos.root();
		if (solprov > sol) sol = solprov;

		if (datos.root() != 0) return 1;
		else return 0;
	}
	else
	{
		int eqleft = 0, eqright = 0;
		if (datos.root() != 0) solprov += datos.root();
		if (!datos.left().empty()) eqleft = resolver(datos.left(), sol, solprov);
		if (!datos.right().empty()) eqright = resolver(datos.right(), sol, solprov);

		if(datos.root() != 0 && eqleft == 0 && eqright == 0) return 1;
		else return eqleft + eqright;
	}

}

// Resuelve un caso de prueba, leyendo de la entrada la
// configuración, y escribiendo la respuesta
void resuelveCaso() {
	// leer los datos de la entrada
	int sol = 0;
	bintree <int> arbol;
	arbol = arbol.leerArbol(-1);


	int grupos = resolver(arbol, sol, 0);
	// escribir sol
	std::cout << grupos << " " << sol << std::endl;

}

int main() {
	// Para la entrada por fichero.
	// Comentar para acepta el reto
#ifndef DOMJUDGE
	std::ifstream in("datos.txt");
	auto cinbuf = std::cin.rdbuf(in.rdbuf()); //save old buf and redirect std::cin to casos.txt
#endif 


	int numCasos;
	std::cin >> numCasos;
	for (int i = 0; i < numCasos; ++i)
		resuelveCaso();


	// Para restablecer entrada. Comentar para acepta el reto
#ifndef DOMJUDGE // para dejar todo como estaba al principio
	std::cin.rdbuf(cinbuf);
	system("PAUSE");
#endif

	return 0;
}