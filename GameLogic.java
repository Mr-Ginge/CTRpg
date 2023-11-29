
import java.util.Scanner;

public class GameLogic 
{
  static Scanner scanner = new Scanner(System.in);
  
  static Player player;
  
  public static boolean isRunning;

  public static String[] encounters = {"Battle","Battle","Battle","Battle","Rest","Rest"};

  public static String[] enemies =  {"Yellow Snow Goblin","Yellow Snow Goblin","Yellow Snow Goblin","Figgy Slime","Figgy Slime"};
  
  public static int place = 0;
  public static int act = 1;
  public static String[] places = {"Sickened Forest","Abandoned Workshop","Chimney Catacombs","Stopping Saint Necrolas"};
  
  public static int readInt(String prompt, int userChoices)
  {
    int input;
    
    do 
    {
      System.out.println(prompt);
      try
        {
          input = Integer.parseInt(scanner.next());
        }catch(Exception e)
        {
          input = -1;
          System.out.println("Please enter a number displayed next to choices.");
        }
    }while(input < 1 || input > userChoices);
    return input;
  }
  
  public static void clearConsole()
  {
    for (int i = 0; i < 100; i++)
      System.out.println();
  }
  
  public static void printSeperator(int n)
  {
    for(int i = 0; i < n; i++)
      System.out.print("-");
    System.out.println();
  }
  
  public static void printHeading(String title)
  {
    printSeperator(30);
    System.out.println(title);
    printSeperator(30);
  }
  
  public static void anythingToContinue()
  {
    System.out.println("Type/Enter anything to continue...");
    scanner.next();
  }
  
  public static void startGame()
  {
    boolean nameSet = false;
    String name;
    clearConsole();
    printSeperator(40);
    System.out.println("Christmas Curse of Saint Necrolas");
    printSeperator(40);
    anythingToContinue();

    do
    {
      clearConsole();
      printHeading("Type your chosen name?");
      name = scanner.next();
      clearConsole();
      printHeading("The name " + name + " works for you?");
      System.out.println("(1) Yes.");
      System.out.println("(2) No.");
      int input = readInt("->" , 2);
      if (input == 1)
        nameSet = true;
      
    }while(!nameSet);
    
    Story.printIntro();
    
    player = new Player(name);

    Story.printFirtActOpen();
    
    isRunning = true;
    
    gameLoop();
    
  }
  
  public static void checkAct()
  {
    if (player.maxExp >= 20 && act == 1)
    {
      act = 2;
      place = 1;
      Story.printFirtActClose();
      player.chooseFeat();
      Story.printSecondActOpen();
      enemies[0] = "Figgy Slushee";
      enemies[1] = "Figgy Slushee";
      enemies[2] = "Rotted Nutcracker";
      enemies[3] = "Rotted Nutcracker";
      enemies[4] = "Gift Mimic";
      encounters[0] ="Battle";
      encounters[1] ="Battle";
      encounters[2] ="Battle";
      encounters[3] ="Rest";
      encounters[4] ="Shop";
    }
    else if (player.maxExp >= 50 && act == 2)
    {
      act = 3;
      place = 2;
      Story.printSecondActClose();
      player.chooseFeat();
      Story.printThirdActOpen();
      player.tempHp = player.maxHp;
      enemies[0] = "Gift Mimic";
      enemies[1] = "Skull In A Box";
      enemies[2] = "Skull In A Box";
      enemies[3] = "Silent Knight";
      enemies[4] = "Silent Knight";
      encounters[0] ="Battle";
      encounters[1] ="Battle";
      encounters[2] ="Battle";
      encounters[3] ="Rest";
      encounters[4] ="Rest";
    }
    else if (player.maxExp >= 100 && act == 3)
    {
      act = 4;
      place = 3;
      Story.printThirdActClose();
      player.chooseFeat();
      Story.printFourthActOpen();
    }
  }
  
  public static void randomEncounter()
  {
    int encounter = (int) (Math.random()* encounters.length);
    if (encounters[encounter].equals("Battle"))
    {
      randomBattle();
    }
    else if (encounters[encounter].equals("Battle"))
    {
      //takeRest();
    }
    else
    {
      //shop();
    }
  }
  
  
  public static void continueAdventure()
  {
    checkAct();
    if (act != 4)
      randomEncounter();
  }
  
  public static void characterInfo()
  {
    clearConsole();
    printHeading("Player Stats");
    System.out.println(player.name + " HP: " + player.tempHp + " / " + player.maxHp);
    printSeperator(20);
    System.out.println("EXP: " + player.maxExp + " Gold: " + player.gold);
    printSeperator(20);
    System.out.println(" Potion Count: " + player.pots);
    printSeperator(20);
    
    if (player.AtkFtCnt > 0)
    {
      System.out.println("Offensive Feats: " + player.AtkFeats[player.AtkFtCnt -1]);
      printSeperator(20);
    }
    if (player.DefFtCnt > 0)
    {
      System.out.println("Deffensive Feats: " + player.DefFeats[player.DefFtCnt -1]);
      printSeperator(20);
    }
    
    anythingToContinue();
    
  }
  
  public static void randomBattle()
  {
    clearConsole();
    printHeading("You encounter a corrupted being. You must fight!");
    anythingToContinue();
    battle(new Enemy(enemies[(int)(Math.random()*enemies.length)], player.maxExp));
  }
  
  public static void battle(Enemy enemy)
  {
    while(true)
    {
      clearConsole();
      printHeading(enemy.name + " HP: " + enemy.tempHp + " / " + enemy.maxHp);
      printHeading(player.name + " HP: " + player.tempHp + " / " + player.maxHp);
      System.out.println("Choose Your Action:");
      printSeperator(30);
      System.out.println("(1) Fight");
      System.out.println("(2) Heal");
      System.out.println("(3) Run Away");
      int input = readInt("->",3);
      if (input == 1)
      {
        int dmg = player.attack() - enemy.defend();
        int dmgTook = enemy.attack() - player.defend();
        if (dmgTook < 0)
        {
          dmg -= dmgTook/2;
          dmgTook = 0;
        }
        if (dmg < 0)
        {
          dmg = 0;
        }
        player.tempHp -= dmgTook;
        enemy.tempHp -= dmg;
        clearConsole();
        printHeading("BATTLE");
        printSeperator(20);
        System.out.println("You dealt " + dmg + " damage to the " + enemy.name + ".");
        printSeperator(15);
        System.out.println(enemy.name + " dealt " + dmgTook + " to you.");
        printSeperator(20);
        anythingToContinue();
        if (player.tempHp <= 0)
        {
          playerDied();
          break;
        }
        else if (enemy.tempHp <= 0)
        {
          clearConsole();
          printHeading("You Won! " + enemy.name + " is defeated.");
          player.maxExp += enemy.maxExp;
          System.out.println("You earned " + enemy.maxExp + " experience!");
          anythingToContinue();
          break;
        }
      }
      else if (input == 2)
      {
        clearConsole();
        if (player.pots > 0 && player.tempHp < player.maxHp)
        {
          player.tempHp = player.maxHp;
        }
        else
        {
          printHeading("You cannot use a potion right now.");
          anythingToContinue();
        }
      }
      else
      {
        clearConsole();
        if (act != 4)
        {
          if (Math.random()*10 + 1 <= 3.5)
          {
            printHeading("You ran away from the " + enemy.name + ".");
            break;
          }
          else
          {
            printHeading("You couldn't escape the " + enemy.name + ", but you managed to dodge!");
            anythingToContinue();
          }
        }
        else
        {
          printHeading("THERE IS NO ESCAPING SAINT NECROLAS, but you managed to dodge!");
          anythingToContinue();
        }
      }
      
    }
  }
  
  public static void printMenu()
  {
    clearConsole();
    printHeading(places[place]);
    System.out.println("Choose your action:");
    printSeperator(20);
    System.out.println("(1) Continue The Adventure!");
    System.out.println("(2) Player Stats");
    System.out.println("(3) Exit The Game");
  }
  
  public static void playerDied()
  {
    clearConsole();
    printHeading("YOU ARE DEAD!");
    printHeading("Final Exp: " + player.maxExp);
    printHeading("Thank you for playing!");
    isRunning = false;
  }
  
  public static void gameLoop()
  {
    while(isRunning)
    {
      printMenu();
      int input = readInt("->",3);
      if (input == 1)
      {
        continueAdventure();
      }
      else if(input == 2)
      {
        characterInfo();
      }
      else
        isRunning = false;
      
    }
    
  }
  
}