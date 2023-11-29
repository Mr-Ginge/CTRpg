





public abstract class Character
{
  
  // Overall Variables and Attributes.
  public String name;
  public int maxHp;
  public int tempHp;
  public int maxExp;
  
  //Character Constructor
  public Character(String name, int maxHp, int maxExp)
  {
    this.name = name;
    this.maxHp = maxHp;
    this.maxExp = maxExp;
    this.tempHp = maxHp;
  }
  
  // Overall Actions: Combat
  public abstract int attack();
  public abstract int defend();
}