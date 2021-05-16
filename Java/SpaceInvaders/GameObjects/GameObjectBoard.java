package GameObjects;

public class GameObjectBoard {
	private GameObject[] objects;
	private int currentObjects;
	
	public GameObjectBoard (int width, int height) {
		objects = new GameObject[width*height];
		this.currentObjects = 0;
	}
	
	private int getCurrentObjects() {
		return currentObjects;
	}
	
	public void add (GameObject object) {
		objects[currentObjects] = object;
		currentObjects++;
	}
	
	private GameObject getObjectInPosition (int x, int y) {
		int i = 0;
		boolean encontrado = false;
		while(i<getCurrentObjects() && !encontrado)
		{
			if(objects[i].isOnPosition(x, y)) encontrado = true;
			i++;
		}
		if(encontrado) return objects[i - 1];
		else return null;
		
	}
	
	private int getIndex(int x, int y) {
		int i = getCurrentObjects() - 1;
		boolean encontrado = false;
		while(i >= 0 && !encontrado)
		{
			if(objects[i].isOnPosition(x, y)) encontrado = true;
			i--;
		}
		return i + 1;
		
	}
	
	public void remove (GameObject object) {
		int pos;
		pos = getIndex(object.getX(),object.getY());
		objects[pos].onDelete();
		
		for (int posicion = pos;posicion<getCurrentObjects()-1;++posicion)
		{
			
			objects[posicion] = objects[posicion+1];
		}
		this.currentObjects--;
		
	}
	
	public void update() {
		
		for(int i = 0; i< getCurrentObjects();++i)
		{
			objects[i].move();
			if(objects[i].isAlive()) checkAttacks(objects[i]);
		}
		
		removeDead();
	}
	
	private void checkAttacks(GameObject object) {
		boolean encontrado = false;
		int i = 0;
		while(i<getCurrentObjects() && !encontrado)
		{
			if(objects[i] != object && object.isOnPosition(objects[i].getX(), objects[i].getY())) 
			{
				object.performAttack(objects[i]);
				encontrado = true;
			}
			i++;
		}
	}
	
	public void computerAction() {
		for(int i = 0; i<getCurrentObjects();++i)
		{
			objects[i].computerAction();
		}
	}
	
	private void removeDead() {
		for(int i = getCurrentObjects()-1;i >= 0;--i)
		{
			if(!objects[i].isAlive()) 
				{
				remove(objects[i]);
				}
		}
	}
	
	public void ShockWave(GameObject object)
	{
		for(int i = 0; i<getCurrentObjects();++i)
		{
			object.performAttack(objects[i]);
		}
	}
	
	public void flameThrower(GameObject object)
	{
		for(int i = 0; i<getCurrentObjects() ;i++)
		{
			if(objects[i].isOnPosition(objects[i].getX(), object.getY()))
				object.performAttack(objects[i]);
		}
	}
	
	public void Explosive(GameObject object)
	{
		GameObject other;
		
		for(int i = object.getX()-1 ; i<object.getX()+2;++i)
		{
			for(int j =object.getY()-1 ;j<object.getY()+2;++j)
			{
				other = getObjectInPosition(i,j);
				if(other!=null)
				{
					object.performAttack(other);
					
				}
			}
		}
	}
	
	public String toString(int x, int y) {
		
		GameObject object;
		object = getObjectInPosition(x,y);
		if(object == null) return " ";
		else return object.toString();
			
	}

	public String stringify() {
		String text = "";
		for(int i = 0; i<getCurrentObjects();++i)
		{
			text += objects[i].stringify() + "\n";
		}
		return text;
	}

}
