/**
 * A program to carry on conversations with a human user.
 * This is the initial version that:  
 * <ul><li>
 *       Uses indexOf to find strings
 * </li><li>
 *       Handles responding to simple words and phrases 
 * </li></ul>
 * This version uses a nested if to handle default responses.
 * @author Laurie White
 * @version April 2012
 */
public class Magpie
{
  /**
   * Get a default greeting  
   * @return a greeting
   */
  public String getGreeting()
  {
    return "Hello, let's talk.";
  }
  
  /**
   * Gives a response to a user statement
   * 
   * @param statement
   *            the user statement
   * @return a response based on the rules given
   */
  public String getResponse(String statement)
  {
    String response = "";
    if (statement.trim().length() == 0)
    {
      response = "Please say something.";
    }
    
    else if (findKeyword(statement, "mother") >= 0
               || findKeyword(statement, "father") >= 0
               || findKeyword(statement, "sister") >= 0
               || findKeyword(statement, "brother") >= 0)
    {
      response = "Tell me more about your family.";
    }
    else if (findKeyword(statement, "dog") >= 0 
               || findKeyword(statement, "puppy") >= 0
               || findKeyword(statement, "cat") >= 0
               || findKeyword(statement, "kitten") >= 0)
    {
      response = "Tell me more about your pets.";
    }
    else if (findKeyword(statement, "kiang") >= 0 
               || findKeyword(statement, "landgraf") >= 0) 
      
    {
      response = "Your teacher sounds nice.";
    }
    else if (findKeyword(statement,"school") >= 0) 
      
      
    {
      response = "Tell me about your favorite subject.";
    }
    else if (findKeyword(statement, "song") >= 0
               
               || findKeyword(statement, "music") >= 0) 
      
    {
      response = "Tell me more about the music you listen to.";
    } 
    else if (findKeyword(statement, "no") >= 0)
    {
      response = "Why so negative?";
    }
    
    // Responses which require transformations
    else if (findKeyword(statement, "I want to", 0) >= 0)
    {
      response = transformIWantToStatement(statement);
    }
    else if (findKeyword(statement, "I want", 0) >= 0)
    {
      response = transformIWantStatement(statement);
    }
    else 
    {
      
      // Look for a two word (you <something> me)
      // pattern
      int psn = findKeyword(statement, "you", 0);
      
      if (psn >= 0
            && findKeyword(statement, "me", psn) >= 0)
      {
        response = transformYouMeStatement(statement);
      }
      else
      {
        //look for the word is
        //Move the is to the front and put why in front of it
        psn = findKeyword(statement, "is", 0);
        if (psn >= 0)
        {
          response = transformIsStatement(statement);
        }
        else
        {
          psn =findKeyword (statement, "I", 0);
          if (psn >= 0 
                && findKeyword(statement, "am", psn) >= 0)
          {
            response = transformIAmStatement(statement);
          }
          else
          {
            //look for "you are" & "they are" statements
            psn = findKeyword (statement, "are", 0);
            if (psn >= 0 
                  && findKeyword(statement, "you") < 0)
            {
              response = transformTheyAreStatement(statement);
            }
            
            //else
            // Move the are to the front
            //if you comes before are change you to I
            //else change you to me
            else
            {
              //  Part of student solution
              // Look for a two word (I <something> you)
              // pattern
              psn = findKeyword(statement, "i", 0);
              
              if (psn >= 0
                    && findKeyword(statement, "you", psn) >= 0)
              {
                response = transformIYouStatement(statement);
              }
              else
              {
                response = getRandomResponse();
              }
            }
          }
          
        }
        
      }
      
    }
    return response;
  }
  
  
  
  /**
   * Take a statement with "I want to <something>." and transform it into 
   * "What would it mean to <something>?"
   * @param statement the user statement, assumed to contain "I want to"
   * @return the transformed statement
   */
  private String transformYouAreStatement(String statement)
  {
    //move the are to the front and put why in front of it
    statement = statement.trim();
    String lastChar = statement.substring(statement
                                            .length() - 1);
    if (lastChar.equals("."))
    {
      statement = statement.substring(0, statement
                                        .length() - 1);
    }
    int psn = findKeyword (statement, "you", 0);
    String restOfStatement = statement.substring(psn + 4).trim();
    String beginningOfStatement = statement.substring(0, psn).trim();
    return beginningOfStatement + " me " + restOfStatement;
  }
  private String transformTheyAreStatement(String statement)
  {
    //move the are to the front and put why in front of it
    statement = statement.trim();
    String lastChar = statement.substring(statement
                                            .length() - 1);
    if (lastChar.equals("."))
    {
      statement = statement.substring(0, statement
                                        .length() - 1);
    }
    int psn = findKeyword (statement, "are", 0);
    String restOfStatement = statement.substring(psn + 4).trim();
    String beginningOfStatement = statement.substring(0, psn).trim();
    //forms the neq question with the original sentence ("is" taken out), 
    //"Why is" is in front, and everything else is lowercased
    return "Why are " + beginningOfStatement.toLowerCase() + " " + restOfStatement.toLowerCase() + "?";
  }
  
  private String transformIWantToStatement(String statement)
  {
    //  Remove the final period, if there is one
    statement = statement.trim();
    String lastChar = statement.substring(statement
                                            .length() - 1);
    if (lastChar.equals("."))
    {
      statement = statement.substring(0, statement
                                        .length() - 1);
    }
    int psn = findKeyword (statement, "I want to", 0);
    String restOfStatement = statement.substring(psn + 9).trim();
    return "What would it mean to " + restOfStatement + "?";
  }
  private String transformIsStatement(String statement)
  {
    statement = statement.trim();
    String lastChar = statement.substring(statement.length() - 1);
    if (lastChar.equals("."))
    {
      statement = statement.substring(0, statement.length() - 1);
    }
    int psn = findKeyword (statement, "is", 0);
    //separate the beginning of the sentence (up until the "is") from the 
    //end of the sentence (everything after the "is")
    String restOfStatement = statement.substring(psn + 3).trim();
    String beginningOfStatement = statement.substring(0, psn).trim();
    //forms the neq question with the original sentence ("is" taken out), 
    //"Why is" is in front, and everything else is lowercased
    return "Why is " + beginningOfStatement.toLowerCase() + " " + restOfStatement.toLowerCase() + "?";
  }
  private String transformIAmStatement(String statement)
  {
    statement = statement.trim();
    String lastChar = statement.substring(statement.length() - 1);
    if (lastChar.equals("."))
    {
      statement = statement.substring(0, statement.length() - 1);
    }
    int psn = findKeyword (statement, "I am", 0);
    String restOfStatement = statement.substring(psn + 4).trim();
    return "Why are you " + restOfStatement + "?";
  }
  private String transformIWantStatement(String statement)
  {
    statement = statement.trim();
    String lastChar = statement.substring(statement.length() - 1);
    if (lastChar.equals("."))
    {
      statement = statement.substring(0, statement.length() - 1);
    }
    int psn = findKeyword (statement, "I want", 0);
    String restOfStatement = statement.substring(psn + 6).trim();
    return "Would you really be happy if you had " + restOfStatement + "?";
  }
  
  
  
  /**
   * Take a statement with "you <something> me" and transform it into 
   * "What makes you think that I <something> you?"
   * @param statement the user statement, assumed to contain "you" followed by "me"
   * @return the transformed statement
   */
  private String transformYouMeStatement(String statement)
  {
    //  Remove the final period, if there is one
    statement = statement.trim();
    String lastChar = statement.substring(statement.length() - 1);
    if (lastChar.equals("."))
    {
      statement = statement.substring(0, statement.length() - 1);
    }
    
    int psnOfYou = findKeyword (statement, "you", 0);
    int psnOfMe = findKeyword (statement, "me", psnOfYou + 3);
    
    String restOfStatement = statement.substring(psnOfYou + 3, psnOfMe).trim();
    return "What makes you think I " + restOfStatement + " you?";
  }
  private String transformIYouStatement(String statement)
  {
    //  Remove the final period, if there is one
    statement = statement.trim();
    String lastChar = statement.substring(statement.length() - 1);
    if (lastChar.equals("."))
    {
      statement = statement.substring(0, statement.length() - 1);
    }
    
    int psnOfYou = findKeyword (statement, "I", 0);
    int psnOfMe = findKeyword (statement, "you", psnOfYou + 1);
    
    String restOfStatement = statement.substring(psnOfYou + 1, psnOfMe).trim();
    return "Why do you " + restOfStatement + " me?";
  }
  
  /**
   * Search for one word in phrase. The search is not case
   * sensitive. This method will check that the given goal
   * is not a substring of a longer string (so, for
   * example, "I know" does not contain "no").
   * 
   * @param statement
   *            the string to search
   * @param goal
   *            the string to search for
   * @param startPos
   *            the character of the string to begin the
   *            search at
   * @return the index of the first occurrence of goal in
   *         statement or -1 if it's not found
   */private String getRandomResponse()
   {
     final int NUMBER_OF_RESPONSES = 6;
     double r = Math.random();
     int whichResponse = (int)(r * NUMBER_OF_RESPONSES);
     String response = "";
     
     if (whichResponse == 0)
     {
       response = "Interesting, tell me more.";
     }
     else if (whichResponse == 1)
     {
       response = "Hmmm.";
     }
     else if (whichResponse == 2)
     {
       response = "Do you really think so?";
     }
     else if (whichResponse == 3)
     {
       response = "You don't say.";
     }
     else if (whichResponse == 4)
     {
       response = "Cool!";
     }
     else if (whichResponse == 5)
     {
       response = "I see.";
     }
     
     
     return response;
   }
   private int findKeyword(String statement, String goal,
                           int startPos)
   {
     String phrase = statement.trim();
     // The only change to incorporate the startPos is in
     // the line below
     int psn = phrase.toLowerCase().indexOf(
                                            goal.toLowerCase(), startPos);
     
     // Refinement--make sure the goal isn't part of a
     // word
     while (psn >= 0)
     {
       // Find the string of length 1 before and after
       // the word
       String before = " ", after = " ";
       if (psn > 0)
       {
         before = phrase.substring(psn - 1, psn)
           .toLowerCase();
       }
       if (psn + goal.length() < phrase.length())
       {
         after = phrase.substring(
                                  psn + goal.length(),
                                  psn + goal.length() + 1)
           .toLowerCase();
       }
       
       // If before and after aren't letters, we've
       // found the word
       if (((before.compareTo("a") < 0) || (before
                                              .compareTo("z") > 0)) // before is not a
             // letter
             && ((after.compareTo("a") < 0) || (after
                                                  .compareTo("z") > 0)))
       {
         return psn;
       }
       
       // The last position didn't work, so let's find
       // the next, if there is one.
       psn = phrase.indexOf(goal.toLowerCase(),
                            psn + 1);
       
     }
     
     return -1;
   }
   
   /**
    * Search for one word in phrase. The search is not case
    * sensitive. This method will check that the given goal
    * is not a substring of a longer string (so, for
    * example, "I know" does not contain "no"). The search
    * begins at the beginning of the string.
    * 
    * @param statement
    *            the string to search
    * @param goal
    *            the string to search for
    * @return the index of the first occurrence of goal in
    *         statement or -1 if it's not found
    */
   private int findKeyword(String statement, String goal)
   {
     return findKeyword(statement, goal, 0);
   }
   
   /**
    * Pick a default response to use if nothing else fits.
    * @return a non-committal string
    */
}








