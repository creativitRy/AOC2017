//Gahwon ctRy Lee

import java.io.*;
import java.util.*;

public class day6_2 {
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
        
        HashMap<ArrayList<Integer>, Long> set = new HashMap<>();
        final int size = list.size();
        
        long i;
        for (i = 1; true; i++) {
            set.put(list, i);
            ArrayList<Integer> newList = new ArrayList<>(list);
            int old = newList.set(maxIndex, 0);
            int index = maxIndex;
            for (int j = old; j > 0; j--) {
                index++;
                if (index >= size)
                    index = 0;
                newList.set(index, newList.get(index) + 1);
            }
            
            Long contains = set.get(newList);
            if (contains != null) {
                System.out.println(i - contains + 1);
                return;
            }
            
            list = newList;
            maxIndex = 0;
            for (int j = 1; j < size; j++) {
                if (list.get(j) > list.get(maxIndex))
                    maxIndex = j;
            }
        }
    }
    
    
    public static void main(String[] args) throws IOException {
        new day6_2().run();
        
    }
}