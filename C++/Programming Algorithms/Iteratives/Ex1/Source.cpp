// Nombre del alumno: Tyson Mendes  Da Gracia
// Usuario del Juez: A47


#include <iostream>
#include <iomanip>
#include <fstream>
#include <vector>
using namespace std;


// función que resuelve el problema
bool resolver(vector<int> datos, int p) {
	int max = datos[0], i = 1;
	bool secumple = true;

	while(secumple && i < datos.size() && p < datos.size() - 1)
	{
		if (i <= p)
		{
			if (datos[i] > max)
			{
				max = datos[i];
			}
		}
		else
		{
			if(max >= datos[i])
			{
				secumple = false;
			}
		}
		i++;
	}

	return secumple;
}

// Resuelve un caso de prueba, leyendo de la entrada la
// configuración, y escribiendo la respuesta
void resuelveCaso() {
	// leer los datos de la entrada
	int tam, p;
	std::cin >> tam;
	std::cin >> p;
	vector<int> datos(tam);

	for (int i = 0; i < datos.size(); i++)
	{
		std::cin >> datos[i];
	}

	bool sol = resolver(datos, p);
	// escribir sol
	if (sol) std::cout << "SI" << std::endl;
	else std::cout << "NO" << std::endl;

}

int main() {
	// Para la entrada por fichero.
	// Comentar para acepta el reto
/*#ifndef DOMJUDGE
	std::ifstream in("datos.txt");
	auto cinbuf = std::cin.rdbuf(in.rdbuf()); //save old buf and redirect std::cin to casos.txt
#endif */


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