Program T1000
{

	boolean Lefagy(Program P)
	{
		  if(P has infinite loop)
			return true;
		 else
			return false; 
	}

	boolean Lefagy2(Program P)
	{
		 if(Lefagy(P))
			return true;
		 else
			for(;;); 
	}

	main(Input Q)
	{
		Lefagy2(Q)
	}

}