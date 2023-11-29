




public class Player extends Character
{
  public int AtkFtCnt;
  public int DefFtCnt;
  
  int gold;
  int restsLeft;
  int pots;
  
  public String[] AtkFeats = {"Bionic Action","Batteries Included","Choking Hazard","Made With Lead"};
  public String[] DefFeats = {"Leather Skin","Metal Frame","Rubber Joints","Holy Light"};
  
  public Player(String name)
  {
    super(name, 100, 0);
    
    this.AtkFtCnt = 0;
    this.DefFtCnt = 0;
    this.gold = 25;
    this.restsLeft = 1;
    this.pots = 0;
    
    chooseFeat();
  }
  
  @Override
  public int attack()
  {
    return (int) (Math.random()*(maxExp / 4 + AtkFtCnt * 3 + 3) + maxExp /10 + AtkFtCnt*2 + DefFtCnt + 1);
  }
  
  @Override
  public int defend()
  {
    return (int) (Math.random()*(maxExp / 4 + DefFtCnt * 3 + 3) + maxExp /10 + DefFtCnt*2 + AtkFtCnt + 1);
  }
  
  public void chooseFeat()
  {
    GameLogic.clearConsole();
    GameLogic.printHeading("Choose a Feat:");
    System.out.println("1 " + AtkFeats[AtkFtCnt]);
    System.out.println("2 " + DefFeats[DefFtCnt]);
    int input = GameLogic.readInt("->", 2);
    if(input == 1)
    {
      GameLogic.printHeading("You Chose " + AtkFeats[AtkFtCnt] + "!");
      AtkFtCnt++;
    }
    else
    {
      GameLogic.printHeading("You Chose " + DefFeats[DefFtCnt] + "!");
      DefFtCnt++;
    }
    GameLogic.anythingToContinue();
  }
  
  
  
}

