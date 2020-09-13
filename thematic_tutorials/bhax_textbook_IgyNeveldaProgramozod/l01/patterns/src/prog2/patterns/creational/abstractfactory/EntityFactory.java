package prog2.patterns.creational.abstractfactory;

public class EntityFactory {
	public Entity createPerson(){
		return  new Entity("person", "people", true, "", "white","black","hispanic","");
	}
	
	public Entity createFurniture(){
		return  new Entity("chair", "chairs", false, "sofa","high","recliner","wheelchair");
	}
	
	public Entity createPhone(){
		return  new Entity("phone", "phones", false, "","cell","rotary","payphone");
	}
	
	public Entity createPizza(){
		return  new Entity("pizza", "pizzas", true, "","cheese","bacon","pepperoni");
	}
}
