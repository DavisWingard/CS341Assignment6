package Testing;
public class testfile {	
	/*
	This is a file
	of random methods
	testing
	Assignment 6
	*/
	public static void main(String[] args) {
		//variables
		int num1 = 13;
		int num2 = 7;
		//calculate sum
		int sum = calculateSum(num1, num2);
		System.out.println(sum);
		//calculate quotient
		double quotient = calculateQuotient(num1, num2);
		System.out.println(quotient);
		//random math
		equivalentNumbers(num1, num2);
	}
	//method 1
	public static int calculateSum(int num1, int num2) {
		return num1 + num2;
	}
	//method 2
	public static double calculateQuotient(int num1, int num2) {
		if (num1 > num2) {
			return (double) num1/num2;
		}
		else {
			return 0;
		}
	}
	//method 3
	public static void equivalentNumbers(int num1, int num2) {
		while (num1 > num2) {
			num2++;
		}
		for (int i = 0; i < 10; i++) {
			System.out.println(num1 * num2);
			num1--;
			num2--;
		}
	}
}