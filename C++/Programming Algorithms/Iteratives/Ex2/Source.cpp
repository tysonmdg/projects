// Nombre del alumno: Tyson Mendes Da Gracia
// Usuario del Juez: A47


#include <iostream>
#include <iomanip>
#include <fstream>
#include <vector>


// función que resuelve el problema
int resolver(const std::vector<int>&datos, std::vector<int>&sol, int sec) {

	int  seguidos = 1, sMax = 0, mayor = datos[datos.size() - 1];

	for (int i = datos.size()-2; i >= 0; i--)
	{
		if (mayor < datos[i]) mayor = datos[i];
		if (datos[i] == datos[i + 1] && datos[i] >= mayor)
		{
			seguidos++;
		}
		else
		{
			if (seguidos >= sec)
			{
				sol.push_back(i + seguidos);
				if (seguidos > sMax) sMax = seguidos;
			}
			seguidos = 1;
		}
	}
	if (seguidos >= sec)
	{
		sol.push_back(seguidos - 1);
		if (seguidos > sMax) sMax = seguidos;
	}
	return sMax;

}

// Resuelve un caso de prueba, leyendo de la entrada la
// configuración, y escribiendo la respuesta
bool resuelveCaso() {
	// leer los datos de la entrada
	int tam, sec, tamMax;
	std::cin >> tam;
	if (!std::cin)
		return false;
	std::cin >> sec;

	std::vector<int> datos(tam);
	std::vector<int> sol;

	for (int i = 0; i < datos.size(); i++) std::cin >> datos[i];
	

	int solucion = resolver(datos,sol, sec);

	// escribir sol
	std::cout << solucion << " " << sol.size() << " ";
	for (int i = 0; i < sol.size(); i++)
	{
		std::cout << sol[i] << " ";
	}
	std::cout << std::endl;

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