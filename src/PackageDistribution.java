import java.util.Scanner;

public class PackageDistribution {

  static class MyNode<T> {
    public T value;
    public MyNode<T> next;

    MyNode(T num, MyNode<T> nextNode) {
      value = num;
      next = nextNode;
    }
    MyNode(T value) {
      this(value, null);
    }
  }

  static class MyLinkedList<T>{
    private MyNode<T> head;
    private MyNode<T> tail;
//    Getters
    public MyNode<T> getHead() {
      return head;
    }
    public MyNode<T> getTail() {
      return tail;
    }
//    Setters
    public void setHead(MyNode<T> head) {
      this.head = head;
    }
    public void setTail(MyNode<T> tail) {
      this.tail = tail;
    }
    public void setTailNext(MyNode<T> tailNext) {
      this.tail.next = tailNext;
    }

    public MyLinkedList(){
      setHead(null);
      setTail(null);
    }

    public T popFront(){
      T value = getHead().value;
      if (getHead() != getTail()){
        setHead(getHead().next);
      }else{
        setHead(null);
        setTail(null);
      }
      return value;
    }

    public void pushBack(T value){
      MyNode<T> pNode = new MyNode<T>(value);
      if(getTail() == null){
        setTail(pNode);
        setHead(getTail());
      }else{
        setTailNext(pNode);
        setTail(pNode);
      }
    }

    public boolean empty(){
      return getHead() == null;
    }

  }

  static class OpManagement{
    private int width;
    private int height;
    private int zones;
    private int packages;
    private int stacks;
    //Created
    private int zonesRoot;
    private int zoneWidth;
    private int zoneHeight;
    private int baseHeight;
    private MyLinkedList<MyLinkedList<Long>> trucks;

    //Getters
    public int getWidth() {
      return width;
    }
    public int getHeight() {
      return height;
    }
    public int getBaseHeight(){
      return baseHeight;
    }
    public int getZones() {
      return zones;
    }
    public int getPackages() {
      return packages;
    }
    public int getZonesRoot() {
      return zonesRoot;
    }
    public int getZoneWidth() {
      return zoneWidth;
    }
    public int getZoneHeight() {
      return zoneHeight;
    }
    public MyLinkedList<MyLinkedList<Long>> getTrucks() {
      return trucks;
    }

    //Setters
    public void setWidth(int width) {
      this.width = width;
    }
    public void setHeight(int height) {
      this.height = height;
      this.baseHeight = 1;
      while(this.height/this.baseHeight != 0){
        this.baseHeight *= 10;
      }
    }
    public void setPackages(int packages) {
      this.packages = packages;
    }
    public void setStacks(int stacks) {
      this.stacks = stacks;
    }
    public void setZones(int zones) {
      this.zones = zones;
      this.zonesRoot = sqrt(zones);
      this.zoneWidth = getWidth()/this.zonesRoot;
      this.zoneHeight = getHeight()/this.zonesRoot;
      for (int i = 0; i < getZones(); i++) {
        MyLinkedList<Long> truck = new MyLinkedList<Long>();
        this.trucks.pushBack(truck);
      }
    }

    OpManagement(){
      setHeight(0);
      setWidth(0);
      this.trucks =  new MyLinkedList<MyLinkedList<Long>>();
    }

    public int zoneSector(int x, int y){
      for (int i = 1; i <= getZonesRoot(); i++) {
        int rowPos = getHeight()-(getZoneHeight()*i);
        int rowVal = getZonesRoot() - (i-1);
        if (y >= rowPos){
          for (int j = 1; j <= getZonesRoot(); j++) {
            int colPos = getWidth() - (getZoneWidth()*j);
            int colVal = getZonesRoot() - (j-1);
            if (x >= colPos){
              return (rowVal-1)*getZonesRoot()+colVal;
            }
          }
        }
      }
      return 0;
    }

    public void loadTruck(int x, int y, Long refId){
      int truckId = zoneSector(x,y);
      MyNode<MyLinkedList<Long>> truck = getTrucks().head;
      for (int i = 1; i <= getZones(); i++) {
        if (i == truckId){
          truck.value.pushBack(refId);
          return;
        }else{
          truck = truck.next;
        }
      }
    }

    public void sorting(MyLinkedList<Long> packageList){
      MyNode<Long> tempa = packageList.head;
      MyNode<Long> tempb;

      if (packageList.head != null){
        while(tempa != null){
          tempb = tempa.next;
          while (tempb != null){
            if (tempa.value > tempb.value){
              Long alt = tempa.value;
              tempa.value = tempb.value;
              tempb.value = alt;
            }else {
              if(tempa.value == tempb.value){
                tempa.value = 0L;
              }
            }
            tempb = tempb.next;
          }
          tempa = tempa.next;
        }
      }
    }

    public void printLoad(){
      for (int i = 1; i <= getZones(); i++) {
        System.out.print(i + " ");
        MyLinkedList<Long> truck = getTrucks().popFront();
        sorting(truck);
        while (!truck.empty()){
          Long packageValue = truck.popFront();
          System.out.print(packageValue%100);
          if (!truck.empty()){
            System.out.print(" ");
          }
        }
        if (!trucks.empty()){
          System.out.println("");
        }
      }
    }
  }

  public static int sqrt(int i){
    int n = 0;
    while(true){
      if(n*n == i){
        return n;
      }else{
        n++;
      }
    }
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    OpManagement company = new OpManagement();
    company.setWidth(sc.nextInt());
    company.setHeight(sc.nextInt());
    company.setZones(sc.nextInt());
    company.setPackages(sc.nextInt());
    company.setStacks(sc.nextInt());

    for (int i = 0; i < company.getPackages(); i++) {
      int id  = sc.nextInt();
      int baseId = 100;
      int x = sc.nextInt();
      int y = sc.nextInt();
      long refId = (Long.valueOf(y) * Long.valueOf(company.getBaseHeight()) + Long.valueOf(x)) * Long.valueOf(baseId) + id;
      company.loadTruck(x, y,refId);
    }
    company.printLoad();
  }
}
