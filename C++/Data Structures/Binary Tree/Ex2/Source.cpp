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

// Coste O(n) con respecto al número de nodos, siendo n el numero de nodos del arbol, ya que se recorre los nodos una sola vez cada uno.

//
// función que resuelve el problema
bool resolver(bintree<int> datos, int&ngen, int solprov) {
	if (esHoja(datos))
	{
		solprov++;
		if (solprov > ngen) ngen = solprov;
		return true;
	}
	else
	{
		solprov++;
		if (!datos.left().empty())
		{
			if (datos.root() < datos.left().root() + 18)return false;
			if (!resolver(datos.left(), ngen, solprov))return false;
			if (!datos.right().empty())
			{
				if (datos.left().root() < datos.right().root() + 2) return false;
				if (!resolver(datos.right(), ngen, solprov))return false;
				else return true;
			}
			else return true;
		}
		else return false;
	}
	
}

// Resuelve un caso de prueba, leyendo de la entrada la
// configuración, y escribiendo la respuesta
void resuelveCaso() {
	// leer los datos de la entrada
	int ngen = 0;
	bool esgen = true;
	bintree <int> arbol;
	arbol = arbol.leerArbol(-1);

	if (!arbol.empty())
	{
		esgen = resolver(arbol, ngen, 0);
	}
	
	// escribir sol
	if (esgen)
	{
		std::cout << "SI " << ngen << std::endl;
	}
	else std::cout << "NO" << std::endl;

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