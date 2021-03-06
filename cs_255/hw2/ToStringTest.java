// Test program for cs255 hw2
// DO NOT make any changes to this program
//
// For hw2, you must write another program in file "Nano.java"
// that contains the static methods:
//
//         public static int parseNano(String s)
//         public static String toString(int a)
//
// Compile the program (after you have written "Nano.java")
// with:
//         javac ToStringTest.java
//
// and run it with the command:
//         java ToStringTest
//

import java.util.Scanner;


public class ToStringTest
{
   public static char[] digit
            = {'#', '!', '%', '@', '(', ')', '[', ']', '$'};

   public static void main (String[] args) 
   {
      int i, j;
      int input;
      String ans;

      System.out.println("Single digit nano number tests....");
      for ( i = 0; i < 9; i ++ )
      {
         input = i;
         ans = Nano.toString( input );

         if ( ans.compareTo("" + digit[i]) != 0 )
         {
           System.out.println(" ---- FAILED for value: " + input);
           System.out.println(" Student's answer = '" + ans + "'");
           System.out.println(" Correct answer   = '" + digit[i] + "'");
           System.exit(1);
         }
      }

      for ( i = 1; i < 9; i ++ )
      {
         input = -i;
         ans = Nano.toString( input );

         if ( ans.compareTo("-" + digit[i]) != 0 )
         {
           System.out.println(" ---- FAILED for value: " + input);
           System.out.println(" Student's answer = '" + ans + "'");
           System.out.println(" Correct answer   = '-" + digit[i] + "'");
           System.exit(1);
         }
      }
      System.out.println("Single digit nano number tests....  PASSED !");

      System.out.println("Double digits nano number tests....");
      for ( i = 1; i < 9; i ++ )
         for ( j = 0; j < 9; j ++ )
         {
            input = i*9 + j;
            ans = Nano.toString( input );

            if ( ans.compareTo("" + digit[i] + digit[j]) != 0 )
            {
              System.out.println(" ---- FAILED for value: " + input);
              System.out.println(" Student's answer = '" + ans + "'");
              System.out.println(" Correct answer   = '" + digit[i] 
				    + digit[j] + "'");
              System.exit(1);
            }

         }

      for ( i = 1; i < 9; i ++ )
         for ( j = 0; j < 9; j ++ )
         {
            input = -(i*9 + j);
            ans = Nano.toString( input );

            if ( ans.compareTo("-" + digit[i] + digit[j]) != 0 )
            {
              System.out.println(" ---- FAILED for value: " + input);
              System.out.println(" Student's answer = '" + ans + "'");
              System.out.println(" Correct answer   = '-" + digit[i]
                                    + digit[j] + "'");
              System.exit(1);
            }

         }
      System.out.println("Double digits nano number tests.... PASSED !!");

      System.out.println("Large nano number tests....");
      input = 7*9*9*9*9 + 6*9*9*9 + 4*9*9 + 8*9 + 3;
      ans = Nano.toString( input );

      String correct;

      correct = "" + digit[7] + digit[6] + digit[4] + digit[8] + digit[3];


      if ( ans.compareTo( correct ) != 0 )
      {
         System.out.println(" ---- FAILED for value: " + input);
         System.out.println(" Student's answer = '" + ans + "'");
         System.out.println(" Correct answer   = '" + correct + "'");
         System.exit(1);
      }

      input = -(8*9*9*9*9 + 0*9*9*9 + 1*9*9 + 2*9 + 5 );
      ans = Nano.toString( input );
      correct = "-" + digit[8] + digit[0] + digit[1] + digit[2] + digit[5];

      if ( ans.compareTo( correct ) != 0 )
      {
         System.out.println(" ---- FAILED for value: " + input);
         System.out.println(" Student's answer = '" + ans + "'");
         System.out.println(" Correct answer   = '" + correct + "'");
         System.exit(1);
      }

      System.out.println("Large nano number tests.... PASSED !!\n");

      System.out.println(" ---- All toString tests passed !");

   }
}

