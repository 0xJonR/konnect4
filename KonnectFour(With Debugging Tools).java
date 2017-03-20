/*
 * Jonathan Rodriguez
 * AP COMPUTER SCIENCE CLASS 2016-2017
 * MS QIU PERIOD 1 
 * MARCH 15 2017 
 * ATTEMPT NUMBER 4
 * footnotes:
*possible optimations:
* make the potential checkers break when the options are taken
* add actual minimax
SO FOR A 2D ARRAY: COORDINATES WORK AS IF 
BOARD[Y-AXIS][X-AXIS]
winpotential WORKS because when checking for potential it checks if they're 'empty'
but checking for wins, it checks the actual amount of markers
*/
import java.util.*;
public class KonnectFour{
  public String[][] Board;
  private final int amntRow = 6;
  private final int amntCol = 7;
  private boolean isPlayer;
  public boolean isWon;
  public KonnectFour(){
    isWon = false;
    Board = new String[][]{
      {"[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"},//  0
      {"[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"},//  1
      {"[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"},//  2
      {"[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"},//  3
      {"[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"},//  4
      {"[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"}};// 5
    //   0     1     2     3     4     5     6  
    System.out.format("Board has been initialized with %d rows and %d columns.%n", amntRow, amntCol);
    Random rad = new Random();
    //isPlayer = rad.nextBoolean();
    isPlayer = true;
    while (isWon!=true){
    if (isPlayer){
      b_toString();
      p_move();
      isPlayer=!isPlayer;
  }
    else if (isPlayer==false){
      comp_move();
      b_toString();
      isPlayer=!isPlayer;
    }
    }
  }
      
  public void b_toString(){
    System.out.format("%n");
    for (int r = 0;r<Board.length;r++){
      for (int c=0;c<Board[0].length;c++){
        System.out.print(Board[r][c]);
      }
      System.out.format("%n");
    }
    System.out.format("%n");
  }
  public int columnDepthCheck(int r, int c){ //initialize with r=5
    if(r<0){
      return -1; //base case full column 
    }
    if(Board[r][c].equals("[ ]")){
      System.out.format("columnDepthCheck has determined the depth of %d to be %d%n", c,r);
      return r;
    }
    else {
      return columnDepthCheck(r-1, c);
    }
  }
  public void move(int c, boolean isPlayer){
    int r = columnDepthCheck(5,c);
    if (isPlayer){
      Board[r][c] = "[X]";
      b_toString();
      if (winCheck(r,c)){
        System.out.format("You Won!");
        isWon=true;
    }
    }
    else {
      Board[r][c] = "[O]";
      b_toString();
      if (winCheck(r,c)){
        System.out.format("You Lost!");
        isWon=true;
    }
    b_toString();
  }
  }
  public void p_move(){
    Scanner x = new Scanner(System.in);
    isPlayer = true;
    System.out.format("Human moves now. Enter in which x coordinate(0-6):%n");
    int a = x.nextInt();
    move(a, isPlayer);
  }
    
  public boolean winCheck(int r, int c){
    String marker = Board[r][c];
    System.out.format("Running method winCheck for Board[%d][%d]%n", r,c);
    if (leftWinPotential(r,c,marker)==4||rightWinPotential(r,c,marker)==4||upWinPotential(r,c,marker)==4||rightUpWinPotential(r,c,marker)==4||leftUpWinPotential(r,c,marker)==4){
      return true;
    }
    else{
      return false; 
    }
  }
  
  private int leftWinPotential(int r, int c, String mk){//max==4 
    System.out.format("Running method leftWinPotential(%d, %d, %s)%n", r, c, mk);
    int sum=0;
    for (int i=0;i<4;i++){
      if (c-i<0){break;}
      else if(Board[r][c-i].equals(mk)){
        sum++;
        System.out.format("Incrementing leftWinPotential by 1 for board[%d][%d-%d]%n", r,c,i);
      }
    }
    return sum;
  }
  private int rightWinPotential(int r, int c, String mk){
    System.out.format("Running method rightWinPotential(%d, %d, %s)%n", r, c, mk);
    int sum=0;
    for (int i=0;i<4;i++){
      if (c+i>6){break;}
      else if(Board[r][c+i].equals(mk)){
        sum++;
        System.out.format("Incrementing rightWinPotential by 1 for board[%d][%d+%d]%n", r, c, i);
      }
    }
    return sum;
  }
  private int upWinPotential(int r, int c, String mk){
    System.out.format("Running Method upWinPotential(%d, %d, %s)%n", r, c, mk);
    int sum=0;
    for (int i=0;i<4;i++){
      if (r+i>5){break;}
      else if(Board[r+i][c].equals(mk)){
        sum++;
        System.out.format("Incrementing upWinPotential by 1 for Board[%d][%d-%d]%n", r, c, i);
      }
    }
    return sum;
  }
  private int rightUpWinPotential(int r, int c, String mk){
    System.out.format("Running method rightUpWinPotential(%d, %d, %s)%n", r, c, mk);
    int sum=0;
    for (int i=0;i<4;i++){
      if (r+i>5||c+i>6){break;}
      else if(Board[r+i][c+i].equals(mk)){
        sum++;
        System.out.format("Incrementing rightUpWinPotential by 1 for Board[%d+%d][%d+%d]%n", r,i,r,i);
      }
    }
    return sum;
  }
  private int leftUpWinPotential(int r, int c, String mk){
    System.out.format("Running method leftUpWinPotential(%d, %d, %s)%n", r, c, mk);
    int sum=0;
    for (int i=0;i<4;i++){
      if (r+i>5||c-i<0){break;}
      else if(Board[r+i][c-i].equals(mk)){
        sum++;
        System.out.format("Incrementing leftUpWinPotential by 1 for Board[%d+%d][%d-%d]%n", r,i,r,i);
      }
    }
    return sum;
  }
  private int movePotentialAnalyzer(int r, int c){//calculates possible amount of win conditions: the more 4's the better
    String mark = Board[r][c];
    ArrayList<Integer> tempList = new ArrayList<Integer>();
    tempList.add(leftWinPotential(r,c,mark));
    tempList.add(rightWinPotential(r,c,mark));
    tempList.add(upWinPotential(r,c,mark));
    tempList.add(rightUpWinPotential(r,c,mark));
    tempList.add(leftUpWinPotential(r,c,mark));
    System.out.format("movePotentialAnalyzer(%d, %c) has created tempList.",r,c);
    if (tempList.indexOf(4)!=-1){
      int numOccurences=0;
      for (int i=0;i<tempList.size();i++){
        if (tempList.get(i)==4){
          numOccurences++;
        }
      }
      return numOccurences; //returns the num2 for r,c
    }
    else {
      return 0; 
    }
  }
  public int[][] minimaxAttempt(){
    int[][] potential = new int[amntRow][amntCol];
    for (int r=0;r<Board.length;r++){
      for (int c=0;c<Board[0].length;c++){
        potential[r][c] = movePotentialAnalyzer(r,c);//creates map of moves with most win potentials 
      }
    }
    return potential;
  }
  public void comp_move(){
    int[][] potMap = minimaxAttempt();
    int tempMax = 0;
    int tempC=0;
    int tempR=0;
    for (int i=0;i<amntRow;i++){//finds best move down-up
      for (int c=0;c<Board[0].length;c++){
        if (potMap[i][c]>tempMax){
          tempR=i;
          tempC=c;
          tempMax=potMap[i][c];
        }
      }
    }
    isPlayer=false;
    move(tempC, isPlayer);
  }
  
  public static void main(String[] args){
    KonnectFour a = new KonnectFour();   
}
}
         
    
       