//Gahwon ctRy Lee

import java.io.*;
import java.util.*;

public class day6_1 {
    private void run() throws FileNotFoundException {
        Scanner in = new Scanner(new File("day6_input.in"));
        
        ArrayList<Integer> list = new ArrayList<>();
        int maxIndex = 0;
        while (in.hasNext()) {
            int i = in.nextInt();
            list.add(i);
            if (i > list.get(maxIndex))
                maxIndex = list.size() - 1;
        }
        
        HashSet<ArrayList<Integer>> set = new HashSet<>();
        final int size = list.size();
        
        long i;
        for (i = 1; true; i++) {
            System.out.println(list);
            set.add(list);
            ArrayList<Integer> newList = new ArrayList<>(list);
            int old = newList.set(maxIndex, 0);
            int index = maxIndex;
            for (int j = old; j > 0; j--) {
                index++;
                if (index >= size)
                    index = 0;
                newList.set(index, newList.get(index) + 1);
            }
            
            if (set.contains(newList))
                break;
            
            list = newList;
            maxIndex = 0;
            for (int j = 1; j < size; j++) {
                if (list.get(j) > list.get(maxIndex))
                    maxIndex = j;
            }
        }
        
        System.out.println(i);
    }
    
    
    public static void main(String[] args) throws IOException {
        new Main().run();
        
    }
}