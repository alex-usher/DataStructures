package OrderedSet;

import java.util.Arrays;

public class Main {

  public static void main(String[] args) {
    final int NUM_THREADS = 10;
    OrderedSetStructure<Integer> set1 = new FineGrainedOrderedSet<Integer>();
    Thread[] removers = new Thread[NUM_THREADS];

    for (int i = 0; i < NUM_THREADS; i++) {
      set1.add(i);
      removers[i] = new Remover<Integer>(i, set1);
    }

    System.out.println(set1);

    Arrays.stream(removers).forEach(Thread::start);
    Arrays.stream(removers)
        .forEach(
            t -> {
              try {
                t.join();
              } catch (InterruptedException ignored) {
              }
            });

    System.out.println(set1);

    OrderedSetStructure<Integer> set2 = new CoarseGrainedOrderedSet<Integer>();
    Thread[] adders = new Thread[NUM_THREADS];

    for (int i = 0; i < NUM_THREADS; i++) {
      adders[i] = new Adder<Integer>(i, set2);
    }

    Arrays.stream(adders).forEach(Thread::start);
    Arrays.stream(adders)
        .forEach(
            t -> {
              try {
                t.join();
              } catch (InterruptedException ignored) {
              }
            });

    System.out.println(set2);

    for(int i = 0; i < NUM_THREADS; i++) {
      set2.remove(i);
    }

    System.out.println(set2);
  }

  private static class Adder<T> extends Thread {
    private final OrderedSetStructure<T> set;
    private final T t;

    public Adder(T t, OrderedSetStructure<T> set) {
      this.set = set;
      this.t = t;
    }

    public void run() {
      set.add(t);
    }
  }

  private static class Remover<T> extends Thread {
    private final OrderedSetStructure<T> set;
    private final T t;

    public Remover(T t, OrderedSetStructure<T> set) {
      this.set = set;
      this.t = t;
    }

    public void run() {
      set.remove(t);
    }
  }
}
