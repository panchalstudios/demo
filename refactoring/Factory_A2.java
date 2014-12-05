package refactoring;


import java.lang.reflect.Method;


//Create a Factory that creates singletons
//USE REFLECTION

public class Factory_A2 {
	
	private String athleteName = "";
	
	public String getAthleteName() {
		return athleteName;
	}

	public void setAthleteName(String athleteName){ this.athleteName = athleteName; }
	
	public static Factory_A2 getInstance(){
		return null;
	}

	
}

class GoldWinner extends Factory_A2{
	
	// Set to null to signify that an instance of
	// type GoldWinner doesn't exist
	private static GoldWinner goldAthlete = null;
	
	// Constructor is set to private to keep other
	// classes from creating an instance of GoldWinner
	private GoldWinner(String athleteName){ 
		
		setAthleteName(athleteName);
		
	}
	
	// Creates 1 instance of GoldWinner (Simple Singleton)
	public static GoldWinner getInstance(String athleteName){
		
		// If an instance of GoldWinner doesn't exist
		// create one
		// Singleton
		if(goldAthlete == null){
			
			goldAthlete = new GoldWinner(athleteName);
			
		} 
		
		return goldAthlete;
		
	}
	
}

class SilverWinner extends Factory_A2{
	
	private static SilverWinner silverAthlete = null;
	private SilverWinner(String athleteName){ 
		
		setAthleteName(athleteName);
		
	}
	
	public static SilverWinner getInstance(String athleteName){
		
		if(silverAthlete == null){
			silverAthlete = new SilverWinner(athleteName);
		} 
		
		return silverAthlete;
		
	}
	
}

class BronzeWinner extends Factory_A2{
	
	private static BronzeWinner bronzeAthlete = null;
	
	private BronzeWinner(String athleteName){ 
		setAthleteName(athleteName);
	}
	
	public static BronzeWinner getInstance(String athleteName){
		
		if(bronzeAthlete == null){
			bronzeAthlete = new BronzeWinner(athleteName);
		} 
		
		return bronzeAthlete;
		
	}
	
}

class MedalFactory{
	
	public Factory_A2 getMedal(String medalType, String athleteName){
		
		try {
			//.... REFLECTION ....//
			
			// Define the type of the parameter that will be passed 
			// to the method I create below
			Class[] athleteNameParameter = new Class[]{String.class};
			
			// forName returns a class object with the String that is
			// passed to it. getMethod returns the method provided 
			// the second parameter defines the type of parameter passed
			// to the method
			Method getInstanceMethod =  Class.forName(medalType).getMethod("getInstance", athleteNameParameter);
			
			// Create an array with the parameter values that will be
			// passed to the method getInstance
			Object[] params = new Object[]{new String(athleteName)};
			
			// Pass the parameters to method getInstance and return
			// a subclass of type Athlete
			
			return (Factory_A2) getInstanceMethod.invoke(null, params);
			
		}
		
		catch(Exception e){
			
			throw new IllegalArgumentException("Invalid Medal Type");
		
		}
		
	}
	
}

class TestMedalWinner{
	
	public static void main(String[] args){
		
		MedalFactory medalFactory = new MedalFactory();
		
		Factory_A2 goldWinner = medalFactory.getMedal("GoldWinner", "Dave Thomas");
		Factory_A2 silverWinner = medalFactory.getMedal("SilverWinner", "Mac McDonald");
		Factory_A2 bronzeWinner = medalFactory.getMedal("BronzeWinner", "David Edgerton");
		
		Factory_A2 goldWinner2 = medalFactory.getMedal("GoldWinner", "Ray Kroc");
		
		System.out.println("Gold Medal Winner: " + goldWinner.getAthleteName());
		System.out.println("Silver Medal Winner: " + silverWinner.getAthleteName());
		System.out.println("Bronze Medal Winner: " + bronzeWinner.getAthleteName());
		
		// Even though I tried to create a new Object of type GoldWinner
		// it was rejected and the original object remained
		
		System.out.println("Gold Medal Winner: " + goldWinner2.getAthleteName());
		
	}
	
}