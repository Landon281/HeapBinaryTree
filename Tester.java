//Test Class
public class Tester {
    public static void main(String[] args){

        Heap<Integer> tester = new Heap<Integer>();
        tester.insert(15);
        tester.insert(16);
        tester.insert(25);
        tester.insert(35);
        tester.insert(99);
        tester.insert(100);
        tester.insert(5);
        tester.insert(3);
        tester.insert(1);
        tester.insert(500);


        System.out.println("On my own");
        System.out.println(tester);
        System.out.println();
        tester.extractMax();


        System.out.println("Number of potential leaf slots:");
        System.out.println(tester.printNumLeafSlots());
        System.out.println();


        System.out.println("Heap after ROOT is swapped");
        tester.extractMax();
        System.out.println(tester);


    }
}
