package UnorderedList;

import java.util.Arrays;

public class Main {

  public static void main(String[] args) {
    final int NUM_THREADS = 10;
    UnorderedListStructure<Integer> list = new FineGrainedUnorderedList<Integer>();
    Thread[] threads = new Thread[NUM_THREADS];


    for (int i = 0; i < NUM_THREADS; i++) {
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
    private final UnorderedListStructure<T> list;
    private final T item;

    public Remover(UnorderedListStructure<T> list, T item) {
      this.list = list;
      this.item = item;
    }

    public void run() {
      this.list.remove(item);
    }
  }
}
