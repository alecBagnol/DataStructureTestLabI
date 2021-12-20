import java.util.Scanner;

public class CardGame {
  static class MyNode {
    public int number;
    public MyNode next;

    MyNode(int num, MyNode nextNode) {
      number = num;
      next = nextNode;
    }
    MyNode(int num) {
      this(num, null);
    }
  }

  static class MyLinkedList{
    private MyNode head;
    private MyNode tail;
//    Getters
    public MyNode getHead() {
      return head;
    }
    public MyNode getTail() {
      return tail;
    }
//    Setters
    public void setHead(MyNode head) {
      this.head = head;
    }
    public void setHeadNext(MyNode headNext) {
      this.head.next = headNext;
    }
    public void setTail(MyNode tail) {
      this.tail = tail;
    }
    public void setTailNext(MyNode tailNext) {
      this.tail.next = tailNext;
    }

    public MyLinkedList(){
      setHead(null);
      setTail(null);
    }

    public void pushFront(int number){
      MyNode pNode = new MyNode(number, getHead());
      setHead(pNode);
      if (getTail() == null){
        setTail(getHead());
      }
    }

    public int popFront(){
      int num = getHead().number;
      if (getHead() != getTail()){
        setHead(getHead().next);
      }else{
        setHead(null);
        setTail(null);
      }
      return num;
    }

    public void pushBack(int number){
      MyNode pNode = new MyNode(number);
      if(getTail() == null){
        setTail(pNode);
        setHead(getTail());
      }else{
        setTailNext(pNode);
        setTail(pNode);
      }
    }

    public int popBack(){
      int num = getTail().number;
      if (getHead() != getTail()){
        MyNode pNode = getHead();
        while(pNode.next.next != null){
          pNode = pNode.next;
        }
        pNode.next = null;
        setTail(pNode);
      }
      return num;
    }

    public boolean empty(){
      return getHead() == null;
    }

    public void reset(){
      setHead(null);
      setTail(null);
    }

  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int tCases = sc.nextInt();
    // Entering tests cases
    for (int i = 1; i <= tCases; i++) {
      int nCards = sc.nextInt();
      int kPlayers = sc.nextInt();
      MyLinkedList deck = new MyLinkedList();

      for (int j = 0; j < nCards; j++) {
        // Getting each card
        deck.pushBack(sc.nextInt());
      }


      MyLinkedList playersList = new MyLinkedList();
      for (int j = 0; j < kPlayers; j++) {
        playersList.pushBack(0);
      }

      while (!deck.empty()){
        MyNode playerTurn = playersList.getHead();
        for (int j = 0; j < kPlayers; j++) {
          if (!deck.empty()){
            if (deck.getHead().number>=deck.getTail().number){
              playerTurn.number += deck.popFront();
            }else {
              playerTurn.number += deck.popBack();
            }
            if (playerTurn.next != null){
              playerTurn = playerTurn.next;
            }
          }
        }
      }
      MyLinkedList winners = new MyLinkedList();
      int maxScore = 0;
      for (int j = 1; j <= kPlayers; j++) {
        int score = playersList.popFront();
        if (score > maxScore){
          winners.reset();
          winners.pushBack(j);
          maxScore = score;
        }else if (score == maxScore){
          winners.pushBack(j);
        }
      }

      System.out.println("Caso #" + i + ":");
      while(!winners.empty()){
        System.out.print(winners.popFront());
        if (!winners.empty()){
          System.out.print(" ");
        }
      }
      if (i < tCases){
        System.out.println("");
      }
    }
  }
}
