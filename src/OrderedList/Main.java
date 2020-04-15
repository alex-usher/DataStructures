package OrderedList;

import java.util.Arrays;

public class Main {

  public static void main(String[] args) {
    final int NUM_THREADS = 10;
    OrderedListStructure<Integer> list = new FineGrainedOrderedList<Integer>();

    Thread[] threads = new Thread[NUM_THREADS];

    for(int i = 0; i < NUM_THREADS; i++) {
      list.add(i);
      threads[i] = new Remover<Integer>(list, i);
    }

    System.out.println(list);

    Arrays.stream(threads).forEach(Thread::start);
    Arrays.stream(threads).forEach(t -> {
      try {
        t.join();
      } catch(InterruptedException ignored) {
      }
    });

    System.out.println(list);
  }


  private static class Remover<T> extends Thread {
    private final OrderedListStructure<T> list;
    private final T item;

    public Remover(OrderedListStructure<T> list, T item) {
      this.list = list;
      this.item = item;
    }

    public void run() {
      this.list.remove(item);
    }
  }
}
