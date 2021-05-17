
// Nombre del alumno: Tyson Mendes y Daniel Ledesma
// Usuario del Juez: C67 y C55

#include <iostream>
#include <iomanip>
#include <fstream>
#include "List.h"

void mostrar(List<int> l) {
	List<int>::Iterator i = l.begin();
	std::cout << i.elem();
	i.next();
	while (i != l.end())
	{
		std::cout << " " << i.elem() ;
		i.next();
	}
	std::cout << std::endl;
}

// función que resuelve el problema
void resolver(List<int>&l) {
	List<int>::Iterator i = l.begin();
	int n;
	n = i.elem();
	i.next();

	while (i != l.end())
	{
		if (n > i.elem())
		{
			i = l.erase(i);
		}
		else
		{
			n = i.elem();
			i.next();
		}
	}

}

// Resuelve un caso de prueba, leyendo de la entrada la
// configuración, y escribiendo la respuesta
void resuelveCaso() {
	// leer los datos de la entrada
	int n, i = 0;
	List<int> l;
	std::cin >> n;

	while (n != -1)
	{
		l.push_back(n);
		std::cin >> n;
	}

	if (l.size() > 0)
	{
		resolver(l);
		mostrar(l);
	}
	else
	{
		std::cout << std::endl;
	}
	// escribir sol
	

}

int main() {
	// Para la entrada por fichero.
	// Comentar para acepta el reto
/*#ifndef DOMJUDGE
	std::ifstream in("datos.txt");
	auto cinbuf = std::cin.rdbuf(in.rdbuf()); //save old buf and redirect std::cin to casos.txt
#endif 
*/

	int numCasos;
	std::cin >> numCasos;
	for (int i = 0; i < numCasos; ++i)
		resuelveCaso();


	// Para restablecer entrada. Comentar para acepta el reto
/*#ifndef DOMJUDGE // para dejar todo como estaba al principio
	std::cin.rdbuf(cinbuf);
	system("PAUSE");
#endif
*/
	return 0;
}