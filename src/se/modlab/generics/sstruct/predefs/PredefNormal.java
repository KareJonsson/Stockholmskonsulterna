package se.modlab.generics.sstruct.predefs;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.values.*;

public class PredefNormal implements ArithmeticEvaluable 
{

	public PredefNormal()
	{
	}

	public sValue evaluate(Scope s) throws IntolerableException
	{
		return new sDouble(heldEasy());
	}

	public static double heldEasy()
	{
		double random = Math.random();
		if(random < 0.5)
		{
			return (-1)*underHalf(random);
		}
		return underHalf(random-0.5);
	} 

	public static double underHalf(double val)
	{
		int head = (int) Math.floor(val*100);
		double tail = val*100-head; 
		return fromHalf[head] + (fromHalf[head+1] - fromHalf[head])*tail;
	} 

	private static final double fromHalf[] = 
	{
		0.00000, 0.02500, 0.05025, 0.07525,
		0.10050, 0.12564, 0.15100, 0.17641,
		0.20180, 0.22744, 0.25333, 0.27923,
		0.30553, 0.33184, 0.35842, 0.38541,
		0.41243, 0.44000, 0.46778, 0.49583,

		0.52441, 0.55343, 0.58294, 0.61273,
		0.64333, 0.67452, 0.70645, 0.73871,
		0.77207, 0.80655, 0.84179, 0.87786,
		0.91538, 0.95423, 0.99458, 1.03652,
		1.08045, 1.12636, 1.17500, 1.22632,

		1.28167, 1.34063, 1.40533, 1.47571,
		1.55500, 1.64500, 1.75111, 1.88143,
		2.05375, 2.32630, 4.37500, 5.00000
	};

	public static void main(String args[])
	{
		int lineFeed = 0;
		for(int i = 50 ; i <= 100 ; i++)
		{
			double d = ((double)i) / 100;
			double low = 0.0;
			double high = 5.0;
			double dev = 0;
			double guess = (high+low)/2;
			if((lineFeed++ % 4) == 0) System.out.print("\n      "+d+"  ");

			do
			{
				if(PredefFi.easyCase(guess) > d)
				{  
					high = guess;
				}
				else
				{
					low = guess;
				}
				guess = (high+low)/2;
				dev = Math.abs(PredefFi.easyCase(guess)-d);
				//System.out.println("High = "+high+", low = "+low+", guess = "+guess+", dev = "+dev);
			} while(dev > 0.0000001);
			System.out.print(""+guess+", ");
		}
		System.out.println("");
	}

	public void verify(Scope s) throws IntolerableException {
	}

	  public String reproduceExpression() {
		  return "normal()";
	  }

}
