import org.testng.annotations.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CollectionItem {
    //Array maintains the order
    //ArrayList contains duplicate elements
    //Maintains in certain order
    //List,Map,Set is an Interface
    //List->ArrayList,Linkedlist
    //can insert elements
    //Linkedlist




    @Test
    public static void arraylist() {
        List<String> topcompanies = new ArrayList<String>();
        topcompanies.add("Google");
        topcompanies.add("Flipkart");
        topcompanies.add("Amazon");
        topcompanies.add("Swiggy");
        topcompanies.add("Microsoft");
        topcompanies.add("Apple");

        //for(int i=0;i<topcompanies.size();i++)
        //{
        // System.out.println(topcompanies.get(i));
        // }
        //System.out.println(topcompanies.isEmpty());

        Iterator<String> it = topcompanies.iterator();
        while (it.hasNext()) {
            //it.next();
            System.out.println("Elements in Iterator" + it.next());
        }

    }
      @Test
        public void linkedList()
        {
            List<String> humanspecies=new LinkedList<String>();
            humanspecies.add("Ape");
            humanspecies.add("Humans");
            humanspecies.add("Homosapiens");
            humanspecies.add("Homoerectus");

          Iterator<String> it1=humanspecies.listIterator();
          while(it1.hasNext())
          {
              System.out.println((it1.next()));
          }







        }


    }



